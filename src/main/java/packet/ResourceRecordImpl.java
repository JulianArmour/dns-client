package packet;

import app.config.QueryType;

public class ResourceRecordImpl implements ResourceRecord {
  private String name;
  private QueryType type;
  private int clazz;
  private long ttl;
  private int rdLength;
  private String rData;
  private int preference;

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public QueryType getType() {
    return type;
  }

  public void setType(QueryType type) {
    this.type = type;
  }

  @Override
  public int getClazz() {
    return clazz;
  }

  public void setClazz(int clazz) {
    this.clazz = clazz;
  }

  @Override
  public long getTTL() {
    return ttl;
  }

  public void setTTL(long ttl) {
    this.ttl = ttl;
  }

  @Override
  public int getRDLength() {
    return rdLength;
  }

  public void setRDLength(int rdLength) {
    this.rdLength = rdLength;
  }

  /**
   * @return the RDATA field. Or, if this resource record's type is MX, then
   * the this returns the EXCHANGE field. PREFERENCE can be accessed via
   * getPreference().
   */
  @Override
  public String getData() {
    return rData;
  }

  public void setData(String rData) {
    this.rData = rData;
  }

  @Override
  public int getPreference() {
    return preference;
  }

  public void setPreference(int preference) {
    this.preference = preference;
  }
}
