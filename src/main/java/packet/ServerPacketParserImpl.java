package packet;

import java.nio.ByteBuffer;

public class ServerPacketParserImpl implements ServerPacketParser {
  @Override
  public ServerResponse parseServerResponse(ByteBuffer response) {
    ServerResponseImpl serverResponse = new ServerResponseImpl();
    serverResponse.setPacketID(response.getChar());
    return serverResponse;
  }
}
