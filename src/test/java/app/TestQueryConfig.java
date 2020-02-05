package app;

import org.junit.Test;

import javax.management.Query;

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

    assertConfig(qc,
        5,
        3,
        53,
        QueryType.A,
        new int[] {123, 206, 85, 18},
        "www.mcgill.ca");
  }


  @Test
  public void parseArgs_NoOptionsWithServerAndDNS_DefaultQueryConfig() {
    String[] args = new String[] {"@888.888.888.888", "www.yahoo.ca"};
    QueryConfig qc = QueryConfig.parseArgs(args);

    assertConfig(qc,
        5,
        3,
        53,
        QueryType.A,
        new int[]{888, 888, 888, 888},
        "www.yahoo.ca");
  }

  private static void assertConfig(QueryConfig qc,
                                   int timeout,
                                   int maxRetries,
                                   int dnsPort,
                                   QueryType queryType,
                                   int[] dnsIP,
                                   String domain) {
    assertEquals(timeout, qc.timeout());
    assertEquals(maxRetries, qc.maxRetries());
    assertEquals(dnsPort, qc.dnsPort());
    assertEquals(queryType, qc.type());
    assertArrayEquals(dnsIP, qc.dnsIP());
    assertEquals(domain, qc.domain());
  }
}
