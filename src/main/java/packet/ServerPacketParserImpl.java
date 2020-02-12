package packet;

import app.config.QueryType;

import java.nio.ByteBuffer;

public class ServerPacketParserImpl implements ServerPacketParser {
  @Override
  public ServerResponse parseServerResponse(ByteBuffer response) throws MalformedPacketException {
    ServerResponseImpl serverResponse = new ServerResponseImpl();
    try {
      //parse ID
      serverResponse.setPacketID(response.getChar());
      //parse QR to RCODE field
      char qRtoRCode = response.getChar();
      serverResponse.setQR(parseQR(qRtoRCode));
      serverResponse.setOpcode(parseOpcode(qRtoRCode));
      serverResponse.setAuthoritative(parseAA(qRtoRCode));
      serverResponse.setTruncated(parseTR(qRtoRCode));
      serverResponse.setRecursionDesired(parseRD(qRtoRCode));
      serverResponse.setRecursionAvailable(parseRA(qRtoRCode));
      serverResponse.setRCode(parseRCode(qRtoRCode));
      //parse QDCOUNT
      serverResponse.setQuestionCount(response.getChar());
      //parse ANCOUNT
      serverResponse.setAnswerCount(response.getChar());
      //parse NSCOUNT
      serverResponse.setAuthorityCount(response.getChar());
      //parse ARCOUNT
      serverResponse.setAdditionalCount(response.getChar());
      //parse question
      serverResponse.setQuestionSection(parseQuestionSection(response));
      //parse answers
      for (int i = 0; i < serverResponse.answerCount(); i++) {
        serverResponse.addAnswer(parseResourceRecord(response));
      }
      //parse Authorities
      for (int i = 0; i < serverResponse.authorityCount(); i++) {
        serverResponse.addAuthority(parseResourceRecord(response));
      }
      //parse Additionals
      for (int i = 0; i < serverResponse.additionalCount(); i++) {
        serverResponse.addAdditional(parseResourceRecord(response));
      }

    } catch (Exception e) {
      throw new MalformedPacketException("Received a malformed packet.", e);
    }
    return serverResponse;
  }

  private ResourceRecord parseResourceRecord(ByteBuffer response) {
    ResourceRecordImpl rr = new ResourceRecordImpl();
    rr.setName(parseQName(response));
    rr.setType(parseQType(response));
    rr.setClazz(parseQClass(response));
    rr.setTTL(parseTTL(response));
    rr.setRDLength(response.getChar());
    parseRData(rr, response);
    return rr;
  }

  private void parseRData(ResourceRecordImpl rr, ByteBuffer response) {
    switch (rr.getType()) {
      case A:
        rr.setData(parseRDataAType(response));
        break;
      case NS:
      case CNAME:
        rr.setData(parseQName(response));
        break;
      case MX:
        rr.setPreference(response.getChar());
        rr.setData(parseQName(response));
        break;
      case IGNORE:
        //skips parsing
        response.position(response.position() + rr.getRDLength());
        break;
    }
  }

  /*Parses an ipv4 address in the RDATA field*/
  private String parseRDataAType(ByteBuffer response) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 3; i++) {
      sb.append(Byte.toUnsignedInt(response.get()));
      sb.append('.');
    }
    sb.append(Byte.toUnsignedInt(response.get()));
    return sb.toString();
  }

  private long parseTTL(ByteBuffer response) {
    return Integer.toUnsignedLong(response.getInt());
  }

  private QuestionSection parseQuestionSection(ByteBuffer response) {
    QuestionSection q = new QuestionSection();
    q.setqName(parseQName(response));
    q.setqType(parseQType(response));
    q.setqClass(parseQClass(response));
    return q;
  }

  private int parseQClass(ByteBuffer response) {
    return response.getChar();
  }

  private QueryType parseQType(ByteBuffer response) {
    switch (response.getChar()) {
      case 0x0001 : return QueryType.A;
      case 0x0002 : return QueryType.NS;
      case 0x0005 : return QueryType.CNAME;
      case 0x000f : return QueryType.MX;
      default: return QueryType.IGNORE;
    }
  }

  /**
   * This is a facade for parseLabels().
   * @param response the packet with position on label-length/pointer octet
   * @return the parsed domain name.
   */
  private String parseQName(ByteBuffer response) {
    StringBuilder sb = new StringBuilder();
    parseLabels(response, sb);
    sb.deleteCharAt(sb.length() - 1);//remove trailing '.'
    return sb.toString();
  }

  /**
   * Parses labels of a domain name, handling pointers along the way.
   * Post-Condition:
   * The response ByteBuffer's position will be on the terminating octet
   * @param response the ByteBuffer with position on the length octet.
   * @param dest the destination of the parsed output.
   */
  private void parseLabels(ByteBuffer response, StringBuilder dest) {
    int labelLen = Byte.toUnsignedInt(response.get());
    if (isPointer(labelLen)) {
      int completePointer = ((labelLen & 0x3f) << 8)
                            | Byte.toUnsignedInt(response.get());
      int savedPosition = response.position();
      response.position(completePointer);
      parseLabels(response, dest);
      response.position(savedPosition);
    } else if (!isTerminatingOctet(labelLen)) {
      for (int i = 0; i < labelLen; i++) {
        dest.append((char)response.get());
      }
      dest.append('.');
      parseLabels(response, dest);
    }
    //else, it's the terminating octet, simply return.
  }

  private boolean isTerminatingOctet(int labelLen) {
    return labelLen == 0;
  }

  private boolean isPointer(int labelLenOctet) {
    return (labelLenOctet & 0b1100_0000) == 0b1100_0000;
  }

  private int parseRCode(char qRtoRCode) {
    return qRtoRCode & 0x000f;
  }

  private boolean parseRA(char qRtoRCode) {
    return ((qRtoRCode >> 7) & 0x0001) == 1;
  }

  /**
   * @param qRtoRCode QR to RCODE bit-field.
   * @return true if RD is set to 1.
   */
  private boolean parseRD(char qRtoRCode) {
    return ((qRtoRCode >> 8) & 0x0001) == 1;
  }

  /**
   *
   * @param qRtoRCode QR to RCODE bit-field.
   * @return true if TR is set to 1.
   */
  private boolean parseTR(char qRtoRCode) {
    return ((qRtoRCode >> 9) & 0x0001) == 1;
  }

  /**
   * @param qRtoRCode QR to RCODE bit-field.
   * @return true if AA is set to 1.
   */
  private boolean parseAA(char qRtoRCode) {
    return ((qRtoRCode >> 10) & 0x0001) == 1;
  }


  /**
   * @param qRtoRCode QR to RCODE bit-field.
   * @return the OPCODE number.
   */
  private int parseOpcode(char qRtoRCode) {
    return (qRtoRCode>>11) & 0x000f;
  }

  /**
   * @param qrToRcode QR to RCODE bit-field.
   * @return the QR field (i.e. packet type).
   */
  private PacketType parseQR(char qrToRcode) {
    if ((qrToRcode & 0x8000) == 0)
      return PacketType.Query;
    return PacketType.Response;
  }
}
