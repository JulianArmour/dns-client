package app.config;

import app.config.QueryConfig;
import app.config.QueryType;

public class ArgumentParser {

  public static QueryConfig parseArgs(String[] args) {
    final QueryConfig queryConfig = new QueryConfig();

    for (int i = 0; i < args.length; i++) {
      switch(args[i]) {

        case "-t":
          queryConfig.setTimeout(Integer.parseInt(args[i+1]));
          i++;
          break;

        case "-r":
          queryConfig.setMaxRetries(Integer.parseInt(args[i+1]));
          i++;
          break;

        case "-p":
          queryConfig.setDnsPort(Integer.parseInt(args[i+1]));
          i++;
          break;

        case "-mx":
          queryConfig.setType(QueryType.MX);
          break;

        case "-ns":
          queryConfig.setType(QueryType.NS);
          break;

      }

      if(args[i].charAt(0) == '@') {
        queryConfig.setDnsIP(args[i].substring(1));
      }

      if(args[i].charAt(0) != '@'
      && args[i].charAt(0) != '-' ) {
        queryConfig.setDomain(args[i]);
      }
    }
    return queryConfig;
  }
}
