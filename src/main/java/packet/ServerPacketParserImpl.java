package packet;

import app.config.QueryType;

import java.nio.ByteBuffer;

public class ServerPacketParserImpl implements ServerPacketParser {
  @Override
  public ServerResponse parseServerResponse(ByteBuffer response) {
    ServerResponseImpl serverResponse = new ServerResponseImpl();
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
    serverResponse.setNameServerCount(response.getChar());
    //parse ARCOUNT
    serverResponse.setAdditionalCount(response.getChar());
    //parse question
    serverResponse.setQuestionSection(parseQuestionSection(response));

    return serverResponse;
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
      case 0x000f : return QueryType.MX;
      default: return null;//TODO throw Exception
    }
  }

  private String parseQName(ByteBuffer response) {
    StringBuilder sb = new StringBuilder();
    int zeroOctetOrPointer = parseLabels(response, sb);
    if (isPointer(zeroOctetOrPointer)) {
      parsePointer(response, sb, zeroOctetOrPointer);
    }
    sb.deleteCharAt(sb.length() - 1);//remove trailing '.'
    return sb.toString();
  }

  /**
   * Parses a sequence of labels pointed by a pointer.
   * PostCondition:
   * The response ByteBuffer's position will be on the octet
   * <b>directly after</b> the pointer.
   * @param response the ByteBuffer with position on the length
   * @param dest the destination of the parsed output.
   * @param pointer the pointer.
   */
  private void parsePointer(ByteBuffer response, StringBuilder dest,
                            int pointer) {
    response.mark();
    response.position(pointer);
    parseLabels(response, dest);
  }

  /**
   * Parses labels until it finds the zero octet or a pointer.
   * PostCondition:
   * The response ByteBuffer's position will be on the octet
   * <b>directly after</b> the zero octet or pointer.
   * @param response the ByteBuffer with position on the length octet.
   * @param dest the destination of the parsed output.
   * @return either the zero octet or a pointer found at the end of the label
   * sequence.
   */
  private int parseLabels(ByteBuffer response, StringBuilder dest) {
    int labelLen;
    while ((labelLen = response.get()) != 0 && !isPointer(labelLen)) {
      for (int i = 0; i < labelLen; i++) {
        dest.append((char)response.get());
      }
      dest.append('.');
    }
    return labelLen;
  }

  private boolean isPointer(int labelLenOctet) {
    return (labelLenOctet >> 14) == 0b11;
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
