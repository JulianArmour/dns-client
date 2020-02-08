package packet;

import java.nio.ByteBuffer;

public class ServerPacketParserImpl implements ServerPacketParser {
  @Override
  public ServerResponse parseServerResponse(ByteBuffer response) {
    ServerResponseImpl serverResponse = new ServerResponseImpl();
    serverResponse.setPacketID(parseID(response));

    char qRtoRCode = response.getChar();
    serverResponse.setQR(parseQR(qRtoRCode));
    serverResponse.setOpcode(parseOpcode(qRtoRCode));
    serverResponse.setAuthoritative(parseAA(qRtoRCode));
    serverResponse.setTruncated(parseTR(qRtoRCode));
    serverResponse.setRecursionDesired(parseRD(qRtoRCode));
    serverResponse.setRecursionAvailable(parseRA(qRtoRCode));
    serverResponse.setRCode(parseRCode(qRtoRCode));

    return serverResponse;
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

  /**
   * @param response QR to RCODE bit-field.
   * @return the packet ID.
   */
  private char parseID(ByteBuffer response) {
    return response.getChar();
  }
}
