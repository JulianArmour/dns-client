package packet;

import org.junit.Test;
import static org.junit.Assert.*;

import java.nio.ByteBuffer;

public class ServerPacketParserImplTest {

  @Test
  public void parseID_WHEN_ID_is_1_EXPECT_parsed_id_is_1() {
    ByteBuffer packet = ByteBuffer.allocate(16);
    packet.putChar((char)1);
    packet.flip();

    ServerPacketParserImpl p = new ServerPacketParserImpl();
    ServerResponse r = p.parseServerResponse(packet);
    assertEquals(1, r.id());
  }
}