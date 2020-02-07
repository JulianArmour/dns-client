package packet;

import java.nio.ByteBuffer;

public interface ServerPacketParser {
  /**
   * Parses a server response packet.
   * @param response a ByteBuffer that <b>must</b> be in read mode (flipped).
   * @return a {@link ServerResponse} that contains the data in the packet.
   */
  ServerResponse parseServerResponse(ByteBuffer response);
}
