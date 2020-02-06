package app;

import parser.ArgumentParser;

import java.util.Optional;

public class QueryConfig implements QueryConfiguration {
  private int timeout = 5;
  private static int maxRetries = 3;
  private static int dnsPort = 53;
  private QueryType type = QueryType.A;
  private String domain = "www.mcgill.ca";
  private  int [] dns = new int [4];

  public  QueryConfig parseArgs(String[] args) {
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
    		queryConfig.setType(type.MX);
    		break;
    		
    	case "-ns":
    		queryConfig.setType(type.NS);
    		break;
    		
    	}
    	
    	if(args[i].charAt(0) == '@') {
    		queryConfig.setIP(args[i].substring(1, args[i].length()));
    	}
    	
    	if(args[i].charAt(0) != '@' || args[i].charAt(0) != '-' ) {
    		queryConfig.setDomain(args[i]);
    	}


    	
    }
    return queryConfig;
  }

  @Override
  public int timeout() {
    return timeout;
  }

  @Override
  public int maxRetries() {
    return maxRetries;
  }

  @Override
  public int dnsPort() {
    return dnsPort;
  }

  @Override
  public QueryType type() {
    return type;
  }

  @Override
  public String dnsIP() {
    return this.dnsIP;
  }

  @Override
  public String domain() {
    return domain;
  }

  private void setDnsIP(String dnsIP) {
    this.dnsIP = dnsIP;
  }
  
  private void setIP(String ip) {
	  	String [] temp = ip.split("\\.");
	  	int [] tempIP  = new int[4];
	  	System.out.println(temp.length);
	   for (int i = 0; i < temp.length; i++) {
		   tempIP[i] = Integer.parseInt(temp[i]);
	   }
	   this.dns = tempIP;
	  }
}
