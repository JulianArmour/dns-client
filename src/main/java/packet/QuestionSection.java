package packet;

import app.config.QueryType;

public class QuestionSection {
  private String qName;
  private QueryType qType;
  private int qClass;

  public String getqName() {
    return qName;
  }

  public void setqName(String qName) {
    this.qName = qName;
  }

  public QueryType getqType() {
    return qType;
  }

  public void setqType(QueryType qType) {
    this.qType = qType;
  }

  public int getqClass() {
    return qClass;
  }

  public void setqClass(int qClass) {
    this.qClass = qClass;
  }
}
