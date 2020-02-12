package packet;

import app.config.QueryType;

public interface ResourceRecord {
  String getName();

  QueryType getType();

  int getClazz();

  long getTTL();

  int getRDLength();

  String getData();

  int getPreference();
}
