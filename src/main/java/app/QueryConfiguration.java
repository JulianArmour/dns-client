package app;

public interface QueryConfiguration {

  int timeout();

  int maxRetries();

  int dnsPort();

  QueryType type();

  String dnsIP();

  String domain();
}
