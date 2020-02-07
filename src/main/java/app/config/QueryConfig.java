package app.config;

public class QueryConfig implements QueryConfiguration {
  private int timeout = 5;
  private int maxRetries = 3;
  private int dnsPort = 53;
  private QueryType type = QueryType.A;
  private String domain;
  private String dnsIP;

  @Override
  public int timeout() {
    return timeout;
  }

  @Override
  public int maxRetries() {
    return maxRetries;
  }

  @Override
  public int dnsPort() {
    return dnsPort;
  }

  @Override
  public QueryType type() {
    return type;
  }

  @Override
  public String dnsIP() {
    return this.dnsIP;
  }

  @Override
  public String domain() {
    return domain;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }

  public void setMaxRetries(int maxRetries) {
    this.maxRetries = maxRetries;
  }

  public void setDnsPort(int dnsPort) {
    this.dnsPort = dnsPort;
  }

  public void setType(QueryType type) {
    this.type = type;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public void setDnsIP(String dnsIP) {
    this.dnsIP = dnsIP;
  }
}
