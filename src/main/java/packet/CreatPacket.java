package packet;
import java.nio.ByteBuffer;
import java.util.Random;

import app.config.QueryConfig;
import app.config.QueryType;

public class CreatPacket {
	
	private static QueryConfig config;
	
	//this will allow me to use our config throughout the whole funciton
	public CreatPacket(QueryConfig config) {
		this.config = config;
	}
	
	//HELPER FUNCTION AT THE TOP
	//To create this funtion, I inspired from the link bellow
	// https://www.tutorialspoint.com/convert-hex-string-to-byte-array-in-java
	public static byte[] hxStrngToByteArr(String s) {
		int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	//As we have three possible QTYPES
	//they are all associated to a specific char
	//as mentioned in the handout
	public static char haxValQuery(QueryType type){
		if (type == QueryType.A) {
			return '1';
		} else if (type == QueryType.NS) {
			return '2';
		} else {
			return 'F';
		}
	}
	
	//This is out main funciton that will get the request
	public byte[] getRequest(){
		//first its imoportant to get eh lenfth of our website name
		int qNameLength = getLengthOfWebsite();
		//once we know that, we know how long to make our buffer,
		//need to include the Packet Header, question and length of domain
		ByteBuffer request = ByteBuffer.allocate(12 + 5 + qNameLength);
		//push the header
		request.put(packetHeader());
		//push the quesiton header
		request.put(questionHeader(qNameLength));
		//finally we return the array
        return request.array();
	}
	
	//Create a funciton to get length of Q website name
	public static int getLengthOfWebsite() {
		int length = 0;
		String [] temp = config.domain().split("//.");
		//we need to calcuate for each character as well as the word itself
		for(int i = 0; i < temp.length; i++) {
			length = length + temp[i].length() + 1;
		}
		return length;
	}
	
	//3 DNS Header
	public static byte[] packetHeader() {
		//We will use 12 as there is 6 levels each requring a seperation, so 6+6
		ByteBuffer head = ByteBuffer.allocate(12);		
		//It is requested that we create an ID of 16 bit of random digits
		byte [] rand = new byte[2];
		new Random().nextBytes(rand);
		//we push the random number into the header
		head.put(rand);
		//we skip the line
		head.put((byte)0x01);
		//We set the rest of the next two bits to zero
		head.put((byte)0x00);
		head.put((byte)0x00);
		//we specify that we are at the end
		head.put((byte)0x01);
		//we return the head of the packet
		return head.array();
	}
	
	
	//As int he document, here we are creating 4 from the handout, DNS Question
	public static byte[] questionHeader(int length)
	{
		//we add 5 as we know there is the QNAME + 5 (QTYPE, QCLASS, 3 line skips)
		ByteBuffer question = ByteBuffer.allocate(length+5);
		
		//first calculate how many bytes we need so we know the size of the array
				String[] items = config.domain().split("\\.");
				for(int i=0; i < items.length; i ++){
					//her we save the digit for the number of items
					question.put((byte) items[i].length());
					for (int j = 0; j < items[i].length(); j++){
						//here we save the actual char
						question.put((byte) ((int) items[i].charAt(j)));
					}
				}		
				//to go to the line below, marking the end
				question.put((byte) 0x00);

				//Add Query Type
				question.put(hxStrngToByteArr("000" + haxValQuery(config.type())));
				
				//to go to the line below, marking the end
				question.put((byte) 0x00);
				
				//As mentioned,, we always use 0x0001 in this feild, 
				//representing an Internet address
				question.put((byte) 0x0001);

				//here we return the array
				return question.array();

	}
	



}
