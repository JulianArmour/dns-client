package packet;

import app.config.QueryType;

public class ResourceRecordBuilder {
  private String name;
  private QueryType queryType;
  private int clazz;
  private long ttl;
  private int rdLength;
  private String data;
  private int preference;

  public ResourceRecord build() {
    return new ResourceRecord() {
      @Override
      public String getName() {
        return name;
      }

      @Override
      public QueryType getType() {
        return queryType;
      }

      @Override
      public int getClazz() {
        return clazz;
      }

      @Override
      public long getTTL() {
        return ttl;
      }

      @Override
      public int getRDLength() {
        return rdLength;
      }

      @Override
      public String getData() {
        return data;
      }

      @Override
      public int getPreference() {
        return preference;
      }
    };
  }

  public ResourceRecordBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ResourceRecordBuilder withQueryType(QueryType queryType) {
    this.queryType = queryType;
    return this;
  }

  public ResourceRecordBuilder withClass(int clazz) {
    this.clazz = clazz;
    return this;
  }

  public ResourceRecordBuilder withTTL(long ttl) {
    this.ttl = ttl;
    return this;
  }

  public ResourceRecordBuilder withRDLength(int rdLength) {
    this.rdLength = rdLength;
    return this;
  }

  public ResourceRecordBuilder withData(String data) {
    this.data = data;
    return this;
  }

  public ResourceRecordBuilder withPreference(int preference) {
    this.preference = preference;
    return this;
  }
}
