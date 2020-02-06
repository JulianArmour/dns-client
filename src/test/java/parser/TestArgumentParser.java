package parser;

import org.junit.Test;
import parser.ArgumentParser;

import java.util.Optional;

import static org.junit.Assert.*;

public class TestArgumentParser {

  @Test
  public void parseDnsIP_EmptyIPString_EmptyOption() {
    String[] programArgs = new String[]{};
    Optional<String> dnsIP = ArgumentParser.parseDnsIP(programArgs);
    assertFalse(dnsIP.isPresent());
  }
  @Test
  public void parseDnsIP_1IPString_1IPintArray() {
    String[] programArgs = new String[]{"@1.1.1.1"};
    Optional<String> dnsIP = ArgumentParser.parseDnsIP(programArgs);
    assertTrue(dnsIP.isPresent());
    assertEquals("1.1.1.1", dnsIP.get());
  }

}