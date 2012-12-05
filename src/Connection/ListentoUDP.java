package Connection;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Formatter;
import java.util.Vector;
import Data.BrodcastMessage;
import Data.Payload;
import GUI.GUI;
import Main.Controller;
import SocketHandelers.PayloadDataController;


public class ListentoUDP  extends Thread
{

	public Vector<PayloadDataController> payloadDataList;
	public  Vector<Payload> payloadList;
	public Controller controller;
	public DatagramSocket socket;
	public BrodcastMessage brodcastMessage;
	public GUI gui;
	
	public void listentoUDP(GUI gui)
	{
		this.gui = gui;

		try {
				socket = new DatagramSocket(2003, InetAddress.getByName("192.168.1.2"));
			} 
		catch (SocketException | UnknownHostException e1) 
		{
			e1.printStackTrace();
		}
		
	}
	
	public void run() 
	{
		while(true)
		{
			byte[] buf = new byte[110];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try 
			{
				socket.receive(packet);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
		    }
			   
			parseHex(hexEncode(buf));
			try { Thread.sleep(10); } catch(InterruptedException e) { /* we tried */}
		}
	}
		
	public static Appendable hexEncode(byte buf[], Appendable sb)  
	{  
		final Formatter formatter = new Formatter(sb);  
	    for (int i = 0; i < buf.length; i++)  
	    {  
	        formatter.format("%02x", buf[i]);  
	    }  
	    
	    formatter.close();
	    return sb;    
	 }  
	 
	public static String hexEncode(byte buf[])  
	{  
	    return hexEncode(buf, new StringBuilder()).toString();  
	} 
	
	public void parseHex(String hexString)
	{
		brodcastMessage = new BrodcastMessage();
		
		brodcastMessage.macAddress = hexString.substring(0, 12);
		brodcastMessage.channel = hexString.substring(12, 14);
		brodcastMessage.RSSI = hexString.substring(14, 16);
		brodcastMessage.localTCPPort = hexString.substring(16, 20);
		brodcastMessage.RTCvalue = hexString.substring(20,28);
		brodcastMessage.BatteryVoltage = hexString.substring(28, 32);
		brodcastMessage.valueofGPIO = hexString.substring(32, 36);
		
		StringBuilder output = new StringBuilder();
	    for (int i = 0; i < hexString.substring(36, 64).length(); i+=2) {
	        String str = hexString.substring(36, 64).substring(i, i+2);
	        output.append((char)Integer.parseInt(str, 16));
	    }
	    
		brodcastMessage.ASCIITime = new String(output);
		
		StringBuilder output2 = new StringBuilder();
	    for (int i = 0; i < hexString.substring(64, 120).length(); i+=2) {
	        String str = hexString.substring(64, 120).substring(i, i+2);
	        output2.append((char)Integer.parseInt(str, 16));
	    }
	    
		brodcastMessage.Version = new String(output2);
		
		StringBuilder output3 = new StringBuilder();
	    for (int i = 0; i < hexString.substring(120, 180).length(); i+=2) {
	        String str = hexString.substring(120, 180).substring(i, i+2);
	        output3.append((char)Integer.parseInt(str, 16));
	    }
		brodcastMessage.DeviceID = 	new String(output3);
		
		brodcastMessage.BootTime = hexString.substring(180, 182);
		brodcastMessage.VoltageSensors = hexString.substring(182, hexString.length());

//		System.out.println(brodcastMessage.macAddress);
//		System.out.println(brodcastMessage.channel);
//		System.out.println(brodcastMessage.RSSI);
//		System.out.println(brodcastMessage.localTCPPort);
//		System.out.println(brodcastMessage.RTCvalue);
//		System.out.println(brodcastMessage.BatteryVoltage);
//		System.out.println(brodcastMessage.valueofGPIO);
//		System.out.println(brodcastMessage.ASCIITime);
//		System.out.println(brodcastMessage.Version);
//		System.out.println(brodcastMessage.DeviceID);
//		System.out.println(brodcastMessage.BootTime);
//		System.out.println(brodcastMessage.VoltageSensors);
		
		String tempString = "RSSI: -";
			   tempString += Long.parseLong(brodcastMessage.RSSI, 16);
			   tempString += "dbm     Battery: ";
			   tempString +=  Long.parseLong(brodcastMessage.BatteryVoltage, 16);
			   tempString += "\n";
			   
		gui.updateText(tempString);
	}
}