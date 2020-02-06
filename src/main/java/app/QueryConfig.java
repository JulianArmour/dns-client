package app;

import parser.ArgumentParser;

import java.util.Optional;

public class QueryConfig implements QueryConfiguration {
  private int timeout = 5;
  private int maxRetries = 3;
  private int dnsPort = 53;
  private QueryType type = QueryType.A;
  private String domain;
  private String dnsIP;

  private QueryConfig() {
  }

  static QueryConfig parseArgs(String[] args) throws BadCommandLineArgument {
    final QueryConfig qc = new QueryConfig();

    Optional<String> dnsIP = ArgumentParser.parseDnsIP(args);
        qc.setDnsIP(dnsIP
          .orElseThrow(() -> new BadCommandLineArgument("Missing DNS IP")));
    return qc;
  }

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

  private void setDnsIP(String dnsIP) {
    this.dnsIP = dnsIP;
  }
}
