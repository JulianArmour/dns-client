package packet;

import java.nio.ByteBuffer;

public interface ServerPacketParser {
  ServerResponse parseServerResponse(ByteBuffer response);
}
