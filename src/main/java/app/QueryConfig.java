package app;

public class QueryConfig {
  private int timeout = 5;
  private static int maxRetries = 3;
  private static int dnsPort = 53;
  private QueryType type = QueryType.A;
  private String domain = "www.mcgill.ca";
  private  int [] dns = new int [4];

  public  QueryConfig parseArgs(String[] args) {
    final QueryConfig queryConfig = new QueryConfig();
//    queryConfig.set
    
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

  public int timeout() {
    return timeout;
  }

  public int maxRetries() {
    return maxRetries;
  }

  public int dnsPort() {
    return dnsPort;
  }

  public QueryType type() {
    return type;
  }

  public int[] dnsIP() {
    return new int[] {123, 206, 85, 18};
  }

  public String domain() {
    return domain;
  }

  private void setTimeout(int timeout) {
    this.timeout = timeout;
  }

  private void setMaxRetries(int maxRetries) {
    this.maxRetries = maxRetries;
  }

  private void setDnsPort(int dnsPort) {
    this.dnsPort = dnsPort;
  }

  private void setType(QueryType type) {
    this.type = type;
  }

  private void setDomain(String domain) {
    this.domain = domain;
  }
  
  private void setIP(String ip) {
	  	String [] temp = ip.split("\\.");
	  	int [] tempIP  = new int [4];
	  	System.out.println(temp.length);
	   for (int i = 0; i < temp.length; i++) {
		   tempIP[i] = Integer.parseInt(temp[i]);
	   }
	   this.dns = tempIP;
	  }
}
