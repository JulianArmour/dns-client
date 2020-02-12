package packet;

import app.config.QueryType;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.*;

public class ServerPacketParserImplTest {

  private String badPacket =
      ("00 08 81 80 00 01 00 00 00 00 03 77 77 77" +
          "06 6d 63 67 69 6c 6c 02 63")
          .replace(" ", "");

  @Test(expected = MalformedPacketException.class)
  public void badPacket() throws MalformedPacketException {
    final byte[] bytes= hexStringToByteArray(badPacket);
    ByteBuffer packet = ByteBuffer.wrap(bytes);

    ServerPacketParserImpl p = new ServerPacketParserImpl();
    p.parseServerResponse(packet);
  }

  private String mcGillCa_TypeA_Query =
    ("00 08 81 80 00 01 00 01 00 00 00 00 03 77 77 77" +
    "06 6d 63 67 69 6c 6c 02 63 61 00 00 01 00 01 c0" +
    "0c 00 01 00 01 00 00 02 a0 00 04 84 d8 b1 a0")
    .replace(" ", "");

  @Test
  public void parseMcgillCaAQueryPacket() throws MalformedPacketException {
    final byte[] bytes= hexStringToByteArray(mcGillCa_TypeA_Query);
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

  private String facebookCom_TypeNS_Query =
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
  public void parseFacebookComNSQueryPacket() throws MalformedPacketException {
    final byte[] bytes= hexStringToByteArray(facebookCom_TypeNS_Query);
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
    //answer 3
    assertAnswer(r,2, "facebook.com", QueryType.NS,
        145164, 4,"a.ns.facebook.com");
    //answer 4
    assertAnswer(r,3, "facebook.com", QueryType.NS,
        145164, 4,"b.ns.facebook.com");

    //additional 1
    assertAdditional(r, 0, "a.ns.facebook.com", QueryType.A,
        147325,4, "69.171.239.12");
    //additional 2
    assertAdditional(r, 1, "b.ns.facebook.com", QueryType.A,
        147325,4, "69.171.255.12");
    //additional 3
    assertAdditional(r, 2, "c.ns.facebook.com", QueryType.A,
        147325,4, "185.89.218.12");
    //additional 4
    assertAdditional(r, 3, "d.ns.facebook.com", QueryType.A,
        147325,4, "185.89.219.12");
    //additional 5
    assertNull(r.getAdditional(4).getData());
    assertEquals(16, r.getAdditional(4).getRDLength());
    assertEquals(QueryType.IGNORE, r.getAdditional(4).getType());
    //additional 6
    assertNull(r.getAdditional(5).getData());
    assertEquals(16, r.getAdditional(5).getRDLength());
    assertEquals(QueryType.IGNORE, r.getAdditional(5).getType());
    //additional 7
    assertNull(r.getAdditional(6).getData());
    assertEquals(16, r.getAdditional(6).getRDLength());
    assertEquals(QueryType.IGNORE, r.getAdditional(6).getType());
    //additional 8
    assertNull(r.getAdditional(7).getData());
    assertEquals(16, r.getAdditional(7).getRDLength());
    assertEquals(QueryType.IGNORE, r.getAdditional(7).getType());
  }

  private String googleCom_TypeMX_Query =
      ("00 05 81 80 00 01 00 05 00 00 00 06 06 67 6f 6f" +
      "67 6c 65 03 63 6f 6d 00 00 0f 00 01 c0 0c 00 0f" +
      "00 01 00 00 00 92 00 11 00 14 04 61 6c 74 31 05" +
      "61 73 70 6d 78 01 6c c0 0c c0 0c 00 0f 00 01 00" +
      "00 00 92 00 09 00 1e 04 61 6c 74 32 c0 2f c0 0c" +
      "00 0f 00 01 00 00 00 92 00 09 00 32 04 61 6c 74" +
      "34 c0 2f c0 0c 00 0f 00 01 00 00 00 92 00 09 00" +
      "28 04 61 6c 74 33 c0 2f c0 0c 00 0f 00 01 00 00" +
      "00 92 00 04 00 0a c0 2f c0 2f 00 01 00 01 00 00" +
      "00 9b 00 04 ad c2 cd 1a c0 2a 00 01 00 01 00 00" +
      "00 18 00 04 40 e9 ba 1b c0 2a 00 1c 00 01 00 00" +
      "00 5d 00 10 28 00 03 f0 40 03 0c 00 00 00 00 00" +
      "00 00 00 1b c0 47 00 01 00 01 00 00 00 af 00 04" +
      "d1 55 ca 1a c0 5c 00 01 00 01 00 00 00 17 00 04" +
      "ac d9 da 1a c0 71 00 1c 00 01 00 00 00 05 00 10" +
      "2a 00 14 50 40 0c 0c 03 00 00 00 00 00 00 00 1b")
      .replace(" ", "");

  @Test
  public void mailServerGoogleCom() throws MalformedPacketException {
    final byte[] bytes= hexStringToByteArray(googleCom_TypeMX_Query);
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
    assertEquals(5, r.answerCount());
    //header NSCOUNT
    assertEquals(0, r.nameServerCount());
    //header ARCOUNT
    assertEquals(6, r.additionalCount());
    //QNAME
    assertEquals("google.com", r.getQuestionSection().getqName());
    //QTYPE
    assertEquals(QueryType.MX, r.getQuestionSection().getqType());
    //QCLASS
    assertEquals(1, r.getQuestionSection().getqClass());

    //answer 1
    assertAnswer(r, 0, "google.com", QueryType.MX, 146,
        17, "alt1.aspmx.l.google.com");
    assertEquals(20, r.getAnswer(0).getPreference());
    //answer 2
    assertAnswer(r, 1, "google.com", QueryType.MX, 146,
        9, "alt2.aspmx.l.google.com");
    assertEquals(30, r.getAnswer(1).getPreference());
    //answer 3
    assertAnswer(r, 2, "google.com", QueryType.MX, 146,
        9, "alt4.aspmx.l.google.com");
    assertEquals(50, r.getAnswer(2).getPreference());
    //answer 4
    assertAnswer(r, 3, "google.com", QueryType.MX, 146,
        9, "alt3.aspmx.l.google.com");
    assertEquals(40, r.getAnswer(3).getPreference());
    //answer 5
    assertAnswer(r, 4, "google.com", QueryType.MX, 146,
        4, "aspmx.l.google.com");
    assertEquals(10, r.getAnswer(4).getPreference());

    //additional 1
    assertAdditional(r, 0, "aspmx.l.google.com",
        QueryType.A,155, 4, "173.194.205.26");
    //additional 2
    assertAdditional(r, 1, "alt1.aspmx.l.google.com",
        QueryType.A,24, 4, "64.233.186.27");
    //additional 3
    assertNull(r.getAdditional(2).getData());
    assertEquals(16, r.getAdditional(2).getRDLength());
    assertEquals(QueryType.IGNORE, r.getAdditional(2).getType());
    //additional 4
    assertAdditional(r, 3, "alt2.aspmx.l.google.com",
        QueryType.A,175, 4, "209.85.202.26");
    //additional 5
    assertAdditional(r, 4, "alt4.aspmx.l.google.com",
        QueryType.A,23, 4, "172.217.218.26");
    //additional 6
    assertNull(r.getAdditional(2).getData());
    assertEquals(16, r.getAdditional(2).getRDLength());
    assertEquals(QueryType.IGNORE, r.getAdditional(2).getType());
  }

  String cnameQuery =
      ("00 14 81 80 00 01 00 01 00 00 00 00 0a 6d 79 63" +
      "6f 75 72 73 65 73 32 06 6d 63 67 69 6c 6c 02 63" +
      "61 00 00 05 00 01 c0 0c 00 05 00 01 00 00 07 e2" +
      "00 18 06 6d 63 67 69 6c 6c 0b 62 72 69 67 68 74" +
      "73 70 61 63 65 03 63 6f 6d 00")
      .replace(" ", "");

  @Test
  public void parseMyCourses2ComCNAMEQueryPacket() throws MalformedPacketException {
    final byte[] bytes= hexStringToByteArray(cnameQuery);
    ByteBuffer packet = ByteBuffer.wrap(bytes);

    ServerPacketParserImpl p = new ServerPacketParserImpl();
    ServerResponse r = p.parseServerResponse(packet);

    //header ID field
    assertEquals(20, r.ID());
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
    assertEquals(1, r.answerCount());
    //header NSCOUNT
    assertEquals(0, r.nameServerCount());
    //header ARCOUNT
    assertEquals(0, r.additionalCount());
    //QNAME
    assertEquals("mycourses2.mcgill.ca", r.getQuestionSection().getqName());
    //QTYPE
    assertEquals(QueryType.CNAME, r.getQuestionSection().getqType());
    //QCLASS
    assertEquals(1, r.getQuestionSection().getqClass());

    //answer
    assertAnswer(r, 0, "mycourses2.mcgill.ca", QueryType.CNAME,
        2018, 24, "mcgill.brightspace.com");
  }

  private void assertAdditional(ServerResponse r, int additionalNumber, String name,
                                QueryType type,int TTL, int dataLength, String data) {
    //additional NAME
    assertEquals(name, r.getAdditional(additionalNumber).getName());
    //additional TYPE
    assertEquals(type, r.getAdditional(additionalNumber).getType());
    //additional CLASS
    assertEquals(1, r.getAdditional(additionalNumber).getClazz());
    //additional TTL
    assertEquals(TTL, r.getAdditional(additionalNumber).getTTL());
    //additional RDLENGTH
    assertEquals(dataLength, r.getAdditional(additionalNumber).getRDLength());
    //additional RDATA
    assertEquals(data, r.getAdditional(additionalNumber).getData());
  }

  private void assertAnswer(ServerResponse r, int answerNumber, String name,
                            QueryType type,int TTL, int dataLength, String data) {
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
    assertEquals(data, r.getAnswer(answerNumber).getData());
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