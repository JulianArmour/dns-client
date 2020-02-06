package parser;

import java.util.Arrays;
import java.util.Optional;

public class ArgumentParser {

  public static Optional<String> parseDnsIP(String[] args) {
    return
      Arrays
        .stream(args)
        .filter(ArgumentParser::isDnsIPArg)
        .map(s -> s.substring(1))// "@123.123.123.123" -> "123.123.123.123"
        .findFirst();
  }

  private static boolean isDnsIPArg(String programArgument) {
    return programArgument.length() >= 1
        && programArgument.charAt(0) == '@';
  }
}
