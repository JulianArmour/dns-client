package app.config;

public class QueryConfig implements QueryConfiguration {
  private int timeout = 5000;
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

  /**
   * @param timeout desired receive timeout, in seconds.
   */
  public void setTimeout(int timeout) {
    this.timeout = timeout * 1000;
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
