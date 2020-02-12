package packet;

import java.util.List;

/**
 * A container for all the data the server sends back as a response to a query.
 */
public interface ServerResponse {

  /**
   * @return The packet ID.
   */
  int ID();

  /**
   * @return the QR field.
   */
  PacketType QR();

  /**
   * @return the opcode field.
   */
  int Opcode();

  /**
   * @return the AA field. true if AA = 1.
   */
  boolean authoritative();

  /**
   * @return the TC field. true if TC = 1.
   */
  boolean truncated();

  /**
   * @return the RD field. true if RD = 1.
   */
  boolean recursionDesired();

  /**
   * @return the RA field. true if RD = 1.
   */
  boolean recursionAvailable();

  /**
   * @return the RCODE field.
   */
  int rCode();

  /**
   * @return the QDCOUNT field.
   */
  int questionCount();

  /**
   *  @return the ANCOUNT field.
   */
  int answerCount();

  /**
   * @return the NSCOUNT field.
   */
  int nameServerCount();

  /**
   * @return the ARCOUNT field.
   */
  int additionalCount();

  /**
   * @return the question section.
   */
  QuestionSection getQuestionSection();

  /**
   * @param answerNumber a number within [0,ARCOUNT)
   * @return the (answerNumber+1)'th answer resource record.
   */
  ResourceRecord getAnswer(int answerNumber);

  /**
   * @return all the answers.
   */
  List<ResourceRecord> getAnswers();

  /**
   * @param authorityNumber a number within [0, NSCOUNT)
   * @return the (authorityNumber+1)'th authority resource record.
   */
  ResourceRecord getAuthority(int authorityNumber);

  /**
   * @param additionalNumber a number within [0, ARCOUNT)
   * @return the (additionalNumber+1)'th additional resource record.
   */
  ResourceRecord getAdditional(int additionalNumber);
}
