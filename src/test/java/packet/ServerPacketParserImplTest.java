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
    //answer
    assertAnswer(r, 0, "www.mcgill.ca", QueryType.A, 672, 4,
        "132.216.177.160");
  }

  private String testPacket2 =
      ("00 05 81 80 00 01 00 04 00 00 00 08 08 66 61 63" +
      "65 62 6f 6f 6b 03 63 6f 6d 00 00 02 00 01 c0 0c" +
      "00 02 00 01 00 02 37 0c 00 07 01 64 02 6e 73 c0" +
      "0c c0 0c 00 02 00 01 00 02 37 0c 00 04 01 63 c0" +
      "2c c0 0c 00 02 00 01 00 02 37 0c 00 04 01 61 c0" +
      "2c c0 0c 00 02 00 01 00 02 37 0c 00 04 01 62 c0" +
      "2c c0 4d 00 01 00 01 00 02 3f 7d 00 04 45 ab ef" +
      "0c c0 5d 00 01 00 01 00 02 3f 7d 00 04 45 ab ff" +
      "0c c0 3d 00 01 00 01 00 02 3f 7d 00 04 b9 59 da" +
      "0c c0 2a 00 01 00 01 00 02 3f 7d 00 04 b9 59 db" +
      "0c c0 4d 00 1c 00 01 00 02 92 83 00 10 2a 03 28" +
      "80 ff fe 00 0c fa ce b0 0c 00 00 00 35 c0 5d 00" +
      "1c 00 01 00 02 92 83 00 10 2a 03 28 80 ff ff 00" +
      "0c fa ce b0 0c 00 00 00 35 c0 3d 00 1c 00 01 00" +
      "02 92 83 00 10 2a 03 28 80 f1 fc 00 0c fa ce b0" +
      "0c 00 00 00 35 c0 2a 00 1c 00 01 00 02 92 83 00" +
      "10 2a 03 28 80 f1 fd 00 0c fa ce b0 0c 00 00 00" +
      "35")
      .replace(" ", "");

  @Test
  public void parseTestPacket2() {
    final byte[] bytes= hexStringToByteArray(testPacket2);
    ByteBuffer packet = ByteBuffer.wrap(bytes);

    ServerPacketParserImpl p = new ServerPacketParserImpl();
    ServerResponse r = p.parseServerResponse(packet);
    //header ID field
    assertEquals(5, r.ID());
    //header QR to RCODE
    assertEquals(PacketType.Response, r.QR());
    assertEquals(0, r.Opcode());
    assertFalse(r.authoritative());
    assertFalse(r.truncated());
    assertTrue(r.recursionDesired());
    assertTrue(r.recursionAvailable());
    assertEquals(0, r.rCode());
    //header QDCOUNT
    assertEquals(1, r.questionCount());
    //header ARCOUNT
    assertEquals(4, r.answerCount());
    //header NSCOUNT
    assertEquals(0, r.nameServerCount());
    //header ARCOUNT
    assertEquals(8, r.additionalCount());
    //QNAME
    assertEquals("facebook.com", r.getQuestionSection().getqName());
    //QTYPE
    assertEquals(QueryType.NS, r.getQuestionSection().getqType());
    //QCLASS
    assertEquals(1, r.getQuestionSection().getqClass());

    //answer 1
    assertAnswer(r,0, "facebook.com", QueryType.NS,
            145164, 7,"d.ns.facebook.com");
    //answer 2
    assertAnswer(r,1, "facebook.com", QueryType.NS,
        145164, 4,"c.ns.facebook.com");

  }

  private void assertAnswer(ServerResponse r, int answerNumber, String name,
                            QueryType type,
                            int TTL, int dataLength, String data) {
    //answer NAME
    assertEquals(name, r.getAnswer(answerNumber).getName());
    //answer TYPE
    assertEquals(type, r.getAnswer(answerNumber).getType());
    //answer CLASS
    assertEquals(1, r.getAnswer(answerNumber).getClazz());
    //answer TTL
    assertEquals(TTL, r.getAnswer(answerNumber).getTTL());
    //answer RDLENGTH
    assertEquals(dataLength, r.getAnswer(answerNumber).getRDLength());
    //answer RDATA
    assertEquals(data, r.getAnswer(answerNumber).getRData());
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