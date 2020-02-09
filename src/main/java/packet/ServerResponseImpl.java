package packet;

import java.util.LinkedList;
import java.util.List;

public class ServerResponseImpl implements ServerResponse {
  private int packetID;
  private PacketType QR;
  private int opcode;
  private boolean isAuthoritative;
  private boolean isTruncated;
  private boolean isRecursionDesired;
  private boolean isRecursionAvailable;
  private int rCode = -1;
  private int questionCount;
  private int answerCount;
  private int nameServerCount = -1;
  private int additionalCount = -1;
  private QuestionSection questionSection;
  private List<ResourceRecord> answers = new LinkedList<>();
  private List<ResourceRecord> authorities = new LinkedList<>();
  private List<ResourceRecord> additionals = new LinkedList<>();

  @Override
  public int ID() {
    return packetID;
  }

  @Override
  public PacketType QR() {
    return QR;
  }

  @Override
  public int Opcode() {
    return opcode;
  }

  @Override
  public boolean authoritative() {
    return isAuthoritative;
  }

  @Override
  public boolean truncated() {
    return isTruncated;
  }

  @Override
  public boolean recursionDesired() {
    return isRecursionDesired;
  }

  @Override
  public boolean recursionAvailable() {
    return isRecursionAvailable;
  }

  @Override
  public int rCode() {
    return rCode;
  }

  @Override
  public int questionCount() {
    return questionCount;
  }

  @Override
  public int answerCount() {
    return answerCount;
  }

  @Override
  public int nameServerCount() {
    return nameServerCount;
  }

  @Override
  public int additionalCount() {
    return additionalCount;
  }

  @Override
  public QuestionSection getQuestionSection() {
    return questionSection;
  }

  @Override
  public ResourceRecord getAnswer(int answerNumber) {
    return answers.get(answerNumber);
  }

  @Override
  public ResourceRecord getAuthority(int authorityNumber) {
    return authorities.get(authorityNumber);
  }

  @Override
  public ResourceRecord getAdditional(int additionalNumber) {
    return additionals.get(additionalNumber);
  }

  public void setPacketID(int packetID) {
    this.packetID = packetID;
  }

  public void setQR(PacketType QR) {
    this.QR = QR;
  }

  public void setOpcode(int opcode) {
    this.opcode = opcode;
  }

  public void setAuthoritative(boolean authoritative) {
    isAuthoritative = authoritative;
  }

  public void setTruncated(boolean truncated) {
    isTruncated = truncated;
  }

  public void setRecursionDesired(boolean recursionDesired) {
    isRecursionDesired = recursionDesired;
  }

  public void setRecursionAvailable(boolean recursionAvailable) {
    isRecursionAvailable = recursionAvailable;
  }

  public void setRCode(int rCode) {
    this.rCode = rCode;
  }

  public void setQuestionCount(int questionCount) {
    this.questionCount = questionCount;
  }

  public void setAnswerCount(int answerCount) {
    this.answerCount = answerCount;
  }

  public void setNameServerCount(int nameServerCount) {
    this.nameServerCount = nameServerCount;
  }

  public void setAdditionalCount(int additionalCount) {
    this.additionalCount = additionalCount;
  }

  public void setQuestionSection(QuestionSection questionSection) {
    this.questionSection = questionSection;
  }

  public void addAnswer(ResourceRecord answer) {
    answers.add(answer);
  }

  public void addAuthority(ResourceRecord authority) {
    authorities.add(authority);
  }

  public void addAdditionals(ResourceRecord additional) {
    additionals.add(additional);
  }
}
