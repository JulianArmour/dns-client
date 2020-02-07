package packet;

public class ServerResponseImpl implements ServerResponse {
  private int packetID;

  @Override
  public int id() {
    return packetID;
  }

  public void setPacketID(int packetID) {
    this.packetID = packetID;
  }
}
