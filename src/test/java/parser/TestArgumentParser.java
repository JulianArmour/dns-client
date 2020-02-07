package parser;

import app.config.ArgumentParser;
import app.config.QueryConfig;
import app.config.QueryType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestArgumentParser {

  @Test
  public void parseArgs_NoOptions_DefaultQueryConfig() {
    String[] args = new String[]{"@111.111.111.111", "www.mcgill.ca"};
    QueryConfig qc = ArgumentParser.parseArgs(args);

    assertEquals(5, qc.timeout());
    assertEquals(3, qc.maxRetries());
    assertEquals(53, qc.dnsPort());
    assertEquals(QueryType.A, qc.type());
    assertEquals("111.111.111.111", qc.dnsIP());
    assertEquals("www.mcgill.ca", qc.domain());
  }
}