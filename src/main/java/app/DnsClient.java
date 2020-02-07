package app;

import app.config.QueryConfig;
import app.config.ArgumentParser;

public class DnsClient {
  public static void main(String[] args) {


    //Read in Data

  QueryConfig newConfig = ArgumentParser.parseArgs(args);
  System.out.println("Timeout : " + newConfig.timeout());
  System.out.println("Max Retries : "+newConfig.maxRetries());
  System.out.println("Port : "+newConfig.dnsPort());
  System.out.println("IP : "+ newConfig.dnsIP());
  System.out.println("Domain : "+newConfig.domain());
  System.out.println("Type : "+newConfig.type());

  }

}
