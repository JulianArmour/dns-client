package app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestQueryConfig {

  @Test
  public void parseArgs_NoOptions_DefaultQueryConfig() {
    String[] args = new String[] {"@123.206.85.18", "www.mcgill.ca"};
    QueryConfig qc = null;
    try {
      qc = QueryConfig.parseArgs(args);
    } catch (BadCommandLineArgument badCommandLineArgument) {
      fail();
    }

    assertEquals(5, qc.timeout());
    assertEquals(3, qc.maxRetries());
    assertEquals(53, qc.dnsPort());
    assertEquals(QueryType.A, qc.type());
    assertEquals("123.206.85.18", qc.dnsIP());
    assertEquals("www.mcgill.ca", qc.domain());
  }


  @Test
  public void parseArgs_OnlyServerIP_ServerIPSetInConfig() {
    String[] args = new String[] {"@888.888.888.888"};
    QueryConfiguration qc = null;
    try {
      qc = QueryConfig.parseArgs(args);
    } catch (BadCommandLineArgument badCommandLineArgument) {
      fail();
    }
    assertEquals("888.888.888.888", qc.dnsIP());
  }
}
