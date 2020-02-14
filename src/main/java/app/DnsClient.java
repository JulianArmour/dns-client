package app;

import app.config.QueryConfig;
import packet.CreatPacket;
import java.net.*;
import app.config.ArgumentParser;

public class DnsClient {
  public static void main(String[] args) {


    //Read in Data

  QueryConfig newConfig = ArgumentParser.parseArgs(args);
  System.out.println("DnsClient sending request for " + newConfig.domain());
  System.out.println("Server : "+newConfig.dnsIP());
  System.out.println("Request type: "+newConfig.type());
  //we set the try counter at one, as it is the first time we are attempting
  makeRequest(1, newConfig);
  
  
  

  }
  
  public static void makeRequest(int retries, QueryConfig config) {
	 
	  //first we need to test that we did not exceed the retry count
	  if(retries > config.maxRetries()) {
		  System.out.println("Maximum tries exceed : " + config.maxRetries());
		  return ;
	  }
	  
	  //we try to create the Datagram packet
	  try {
		  //create the socket
		  DatagramSocket socket = new DatagramSocket();
		  //set the timeout for the socket
          socket.setSoTimeout(config.timeout());
          //to get the InetAddress for when we send the request packet
          InetAddress inetAdd = InetAddress.getByAddress(ipSwitch(config.dnsIP()));
          //To build the request
          CreatPacket request = new CreatPacket(config);
          
          //create to byte array to send and save 
          byte[] requestArray = request.getRequest();
          byte[] responseArrays = new byte[1024];
          
          //create to packets, to send and recieve
          DatagramPacket sendPack = new DatagramPacket(requestArray, requestArray.length, inetAdd, config.dnsPort());
          DatagramPacket receivePack = new DatagramPacket(responseArrays, responseArrays.length);
          
          //to be able to track time, we will create a time
          long start = System.currentTimeMillis(), end;
          socket.send(sendPack);
          socket.receive(receivePack);
          end = System.currentTimeMillis();
          //now that it is done, we can close
          socket.close();
          
          //Here we display the the total time it took and the amount of times
          long diff = (end - start)/1000;
          System.out.println("Total to send and receive: " + diff + " seconds" + "It took " + retries +" retires.");
          
          //HERE I LEAVE FOR YOU TO CALL YOUR RESPONSE FUNCITON


	  } catch (SocketException e) {
            System.out.println("ERROR\tCould not create socket");
        } catch (UnknownHostException e ) {
            System.out.println("ERROR\tUnknown host");
        } catch (SocketTimeoutException e) {
            System.out.println("ERROR\tSocket Timeout");
            System.out.println("Reattempting request...");
            makeRequest(++retries);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	  
  }
  
  //Helper function to create IP into Byte array for inetAddress
  public static byte [] ipSwitch(String ip) {
	  byte [] newIP = new byte[4];
	  String [] splitIP = ip.split("\\.");
	  for(int i = 0; i < splitIP.length; i++) {
		  int temp = Integer.parseInt(splitIP[i]);
		  newIP[i] = (byte) temp;
	  }
	  return newIP;
	  
  }

}
