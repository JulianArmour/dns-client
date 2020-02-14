package app;

import app.config.ArgumentParser;
import app.config.QueryConfig;
import app.presenter.ServerResponsePresenter;
import packet.*;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class DnsClient {
  public static void main(String[] args) {
    //Read in Data
    QueryConfig queryConfig = ArgumentParser.parseArgs(args);
    System.out.println("DnsClient sending request for " + queryConfig.domain());
    System.out.println("Server : " + queryConfig.dnsIP());
    System.out.println("Request type: " + queryConfig.type());
    System.out.println();

    ServerResponse response = null;
    int retry;
    long start = System.currentTimeMillis();
    for (retry = 0; retry <=queryConfig.maxRetries(); retry++) {
      response = sendQuery(queryConfig);
      if (response != null) {
        long end = System.currentTimeMillis();
        //Here we display the the total time it took and the amount of times
        double diff = (end - start) / 1000.0;
        System.out.printf("Response received after %f seconds (%d %s)" +
                "\r\n\r\n", diff, retry, retry == 1 ? "retry" : "retries");
        break;
      }
    }

    if (response == null) {
      System.out.printf("ERROR\tMaximum retries %d exceeded",
          queryConfig.maxRetries());
      return;
    }

    (new ServerResponsePresenter(response, System.out)).present();
  }

  public static ServerResponse sendQuery(QueryConfig config) {
    try (DatagramSocket socket = new DatagramSocket()) {
      //create the socket
      //set the timeout for the socket
      socket.setSoTimeout(config.timeout());
      //to get the InetAddress for when we send the request packet
      InetAddress inetAdd = InetAddress.getByAddress(ipSwitch(config.dnsIP()));
      //create two buffers to send and save
      byte[] requestData = new CreatPacket(config).getRequest();
      byte[] responseData = new byte[4096];

      DatagramPacket sendPack = new DatagramPacket(requestData, requestData.length, inetAdd, config.dnsPort());
      DatagramPacket receivePack = new DatagramPacket(responseData, responseData.length);

      socket.send(sendPack);
      socket.receive(receivePack);

      ServerPacketParser packetParser = new ServerPacketParserImpl();
      ByteBuffer receivedPacketBytes = ByteBuffer.wrap(receivePack.getData());
      return packetParser.parseServerResponse(receivedPacketBytes);

    } catch (SocketException e) {
      System.out.println("ERROR\tCould not create socket");
    } catch (UnknownHostException e) {
      System.out.println("ERROR\tUnknown host");
    } catch (SocketTimeoutException e) {
      System.out.println("Timed out");
    } catch (MalformedPacketException e) {
      System.out.println("ERROR\t" + e.getMessage());
    } catch (IOException e) {
      System.out.println("ERROR\tfailed to send or receive packet");
    }
    return null;
  }

  //Helper function to create IP into Byte array for inetAddress
  public static byte[] ipSwitch(String ip) {
    byte[] newIP = new byte[4];
    String[] splitIP = ip.split("\\.");
    for (int i = 0; i < splitIP.length; i++) {
      int temp = Integer.parseInt(splitIP[i]);
      newIP[i] = (byte) temp;
    }
    return newIP;

  }

}
