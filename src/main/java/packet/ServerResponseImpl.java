package packet;

import app.config.QueryType;

public class ServerResponseImpl implements ServerResponse {
  private int packetID;
  private PacketType QR;
  private int opcode;
  private boolean isAuthoritative;
  private boolean isTruncated;
  private boolean isRecursionDesired;
  private boolean isRecursionAvailable;
  private int rCode = -1;

  @Override
  public int ID() {
    return packetID;
  }

  @Override
  public PacketType QR() {
    return QR;
  }

  @Override
  public int Opcode() {
    return opcode;
  }

  @Override
  public boolean authoritative() {
    return isAuthoritative;
  }

  @Override
  public boolean truncated() {
    return isTruncated;
  }

  @Override
  public boolean recursionDesired() {
    return isRecursionDesired;
  }

  @Override
  public boolean recursionAvailable() {
    return isRecursionAvailable;
  }

  @Override
  public int rCode() {
    return rCode;
  }

  public void setPacketID(int packetID) {
    this.packetID = packetID;
  }

  public void setQR(PacketType QR) {
    this.QR = QR;
  }

  public void setOpcode(int opcode) {
    this.opcode = opcode;
  }

  public void setAuthoritative(boolean authoritative) {
    isAuthoritative = authoritative;
  }

  public void setTruncated(boolean truncated) {
    isTruncated = truncated;
  }

  public void setRecursionDesired(boolean recursionDesired) {
    isRecursionDesired = recursionDesired;
  }

  public void setRecursionAvailable(boolean recursionAvailable) {
    isRecursionAvailable = recursionAvailable;
  }

  public void setRCode(int rCode) {
    this.rCode = rCode;
  }
}
