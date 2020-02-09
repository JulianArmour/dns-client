package packet;

import app.config.QueryType;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.*;

public class ServerPacketParserImplTest {

  private String testPacket1 =
    ("00 08 81 80 00 01 00 01 00 00 00 00 03 77 77 77" +
    "06 6d 63 67 69 6c 6c 02 63 61 00 00 01 00 01 c0" +
    "0c 00 01 00 01 00 00 02 a0 00 04 84 d8 b1 a0")
    .replace(" ", "");

  @Test
  public void parseServerResponse() {
    final byte[] bytes= hexStringToByteArray(testPacket1);
    ByteBuffer packet = ByteBuffer.wrap(bytes);

    ServerPacketParserImpl p = new ServerPacketParserImpl();
    ServerResponse r = p.parseServerResponse(packet);
    //ID field
    assertEquals(8, r.ID());
    //QR to RCODE
    assertEquals(PacketType.Response, r.QR());
    assertEquals(0, r.Opcode());
    assertFalse(r.authoritative());
    assertFalse(r.truncated());
    assertTrue(r.recursionDesired());
    assertTrue(r.recursionAvailable());
    assertEquals(0, r.rCode());
    //QDCOUNT
    assertEquals(1, r.questionCount());
    //ARCOUNT
    assertEquals(1, r.answerCount());
    //NSCOUNT
    assertEquals(0, r.nameServerCount());
    //ARCOUNT
    assertEquals(0, r.additionalCount());
    //QNAME
    assertEquals("www.mcgill.ca", r.getQuestionSection().getqName());
    //QTYPE
    assertEquals(QueryType.A, r.getQuestionSection().getqType());
    //QCLASS
    assertEquals(1, r.getQuestionSection().getqClass());
    //answer NAME
    assertEquals("www.mcgill.ca", r.getAnswers(0).getName());
    //answer TYPE
    assertEquals(QueryType.A, r.getAnswers(0).getType());
    //answer CLASS
    assertEquals(1, r.getAnswers(0).getClazz());
    //answer TTL
    assertEquals(672, r.getAnswers(0).getTTL());
  }

  //src: https://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java
  private static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                           + Character.digit(s.charAt(i+1), 16));
    }
    return data;
  }

  @Test
  public void hexStringToByteArray_test() {
    assertArrayEquals(new byte[] {0x32, (byte) 0xa7}, hexStringToByteArray("32A7"));
  }
}