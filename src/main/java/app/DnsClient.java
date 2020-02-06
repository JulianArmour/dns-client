package app;

public class DnsClient {
  public static void main(String[] args) {

	  
	  	//Read in Data
	  
	  QueryConfig newConfig = new QueryConfig();
	  System.out.println("LOL");
	  newConfig = newConfig.parseArgs(args);
	  System.out.println("Timeout : " + newConfig.timeout());
	  System.out.println("Max Retries : "+newConfig.maxRetries());
	  System.out.println("Port : "+newConfig.dnsPort());
	  System.out.println("IP : "+printIP(newConfig.dnsIP()));
	  System.out.println("Domain : "+newConfig.domain());
	  System.out.println("Type : "+newConfig.type());
	  	
  }
  
  public static String printIP (int[] ip) {
	  String ipString = "";
	  
	  for(int i = 0; i < ip.length; i++) {
		  ipString = ipString + Integer.toString(ip[i]) + ".";
	  }
	  ipString = ipString.substring(0, ipString.length()-1);
	  
	  return ipString;
  }
}
