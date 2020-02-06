package packet;

import app.QueryConfiguration;

import java.nio.ByteBuffer;

public interface PacketBuilder {
  /**
   * Builds a DNS packet.
   * @param queryConfiguration A queryConfiguration.
   * @return A {@link ByteBuffer} that is in <B>"read mode"</B> (i.e. has been flipped).
   */
  ByteBuffer buildPacket(QueryConfiguration queryConfiguration);
}
