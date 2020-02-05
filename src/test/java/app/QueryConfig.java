package app;

public class QueryConfig {
  private int timeout = 5;
  private int maxRetries = 3;
  private int dnsPort = 53;
  private QueryType type = QueryType.A;
  private String domain = "www.mcgill.ca";

  private QueryConfig() {
  }
  public static QueryConfig parseArgs(String[] args) {
    final QueryConfig queryConfig = new QueryConfig();
//    queryConfig.set

    return queryConfig;
  }

  public int timeout() {
    return timeout;
  }

  public int maxRetries() {
    return maxRetries;
  }

  public int dnsPort() {
    return dnsPort;
  }

  public QueryType type() {
    return type;
  }

  public int[] dnsIP() {
    return new int[] {123, 206, 85, 18};
  }

  public String domain() {
    return domain;
  }

  private void setTimeout(int timeout) {
    this.timeout = timeout;
  }

  private void setMaxRetries(int maxRetries) {
    this.maxRetries = maxRetries;
  }

  private void setDnsPort(int dnsPort) {
    this.dnsPort = dnsPort;
  }

  private void setType(QueryType type) {
    this.type = type;
  }

  private void setDomain(String domain) {
    this.domain = domain;
  }
}
