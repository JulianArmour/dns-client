package app;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class TestQueryConfig {

  @Test
  public void parseArgs_NoOptions_DefaultQueryConfig() {
    String[] args = new String[] {"@123.206.85.18", "www.mcgill.ca"};
    QueryConfig qc = QueryConfig.parseArgs(args);

    assertEquals(5, qc.timeout());
    assertEquals(3, qc.maxRetries());
    assertEquals(53, qc.dnsPort());
    assertEquals(QueryType.A, qc.type());
    assertArrayEquals(new int[]{123, 206, 85, 18}, qc.dnsIP());
    assertEquals("www.mcgill.ca", qc.domain());
  }


  @Test
  public void parseArgs_OnlyServerIP_ServerIPSetInConfig() {
    String[] args = new String[] {"@888.888.888.888"};
    QueryConfiguration qc = QueryConfig.parseArgs(args);
    assertEquals("888.888.888.888", qc.dnsIP());
  }
}
