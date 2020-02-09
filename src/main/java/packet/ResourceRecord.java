package packet;

import app.config.QueryType;

public class ResourceRecord {
  private String name;
  private QueryType type;
  private int clazz;
  private long ttl;
  private int rdLength;
  private String rData;
  private int preference;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public QueryType getType() {
    return type;
  }

  public void setType(QueryType type) {
    this.type = type;
  }

  public int getClazz() {
    return clazz;
  }

  public void setClazz(int clazz) {
    this.clazz = clazz;
  }

  public long getTTL() {
    return ttl;
  }

  public void setTTL(long ttl) {
    this.ttl = ttl;
  }

  public int getRDLength() {
    return rdLength;
  }

  public void setRDLength(int rdLength) {
    this.rdLength = rdLength;
  }

  public String getRData() {
    return rData;
  }

  public void setRData(String rData) {
    this.rData = rData;
  }

  public int getPreference() {
    return preference;
  }

  public void setPreference(int preference) {
    this.preference = preference;
  }
}