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
import FileWriters.PayloadLogger;
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
	public PayloadLogger payloadLogger1;
	public PayloadLogger payloadLogger2;
	public String ipAddressLocal;
	StringBuilder output;
	StringBuilder output2;
	StringBuilder output3;
	
	
	public void listentoUDP(Controller controller, GUI gui, String ipAddressLocal)
	{
		this.gui = gui;
		this.controller = controller;
		this.ipAddressLocal = ipAddressLocal;
		
		try {
				socket = new DatagramSocket(2003, InetAddress.getByName(ipAddressLocal));
			} 
		catch (SocketException | UnknownHostException e1) 
		{
			e1.printStackTrace();
		}
		output = new StringBuilder();
		output2 = new StringBuilder();
		output3 = new StringBuilder();
		payloadLogger1 = new PayloadLogger();
		payloadLogger1.payloadLogger("SSU-01_RSSI");
		payloadLogger2 = new PayloadLogger();
		payloadLogger2.payloadLogger("SSU-02_RSSI");
		
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
		
		output = new StringBuilder();
	    for (int i = 0; i < hexString.substring(36, 64).length(); i+=2) {
	        String str = hexString.substring(36, 64).substring(i, i+2);
	        output.append((char)Integer.parseInt(str, 16));
	    }
	    
		brodcastMessage.ASCIITime = new String(output);
		
		output2 = new StringBuilder();
	    for (int i = 0; i < hexString.substring(64, 120).length(); i+=2) {
	        String str = hexString.substring(64, 120).substring(i, i+2);
	        output2.append((char)Integer.parseInt(str, 16));
	    }
	    
		brodcastMessage.Version = new String(output2);
		
		output3 = new StringBuilder();
	    for (int i = 0; i < hexString.substring(120, 180).length(); i+=2) {
	        String str = hexString.substring(120, 180).substring(i, i+2);
	        output3.append((char)Integer.parseInt(str, 16));
	    }
	    
		brodcastMessage.DeviceID = 	new String(output3);
		brodcastMessage.BootTime = hexString.substring(180, 182);
		brodcastMessage.VoltageSensors = hexString.substring(182, hexString.length());


		
//		String tempString = brodcastMessage.DeviceID.substring(0, 6);
//			   tempString += "   ";
//		       tempString += "RSSI: -";
//			   tempString += Long.parseLong(brodcastMessage.RSSI, 16);
//			   tempString += "dbm     Battery: ";
//			   tempString +=  Long.parseLong(brodcastMessage.BatteryVoltage, 16);
//			   tempString += "\r";
//			 //  System.out.println(tempString); 
		
		BrodcastMessageUpdate(brodcastMessage.DeviceID.substring(0, 6));
	}
	public void  BrodcastMessageUpdate(String payloadName)
	{
		
		if(controller.payloadDataControllerList != null)
		{
			for(int i = 0; i < controller.payloadDataControllerList.size(); i++)
			{
				if(payloadName.equals(controller.payloadDataControllerList.get(i).deviceName) && controller.payloadDataControllerList.size() >0 && controller.payloadDataControllerList.get(0).payloadDataVector.size() > 0)
				{
					controller.payloadDataControllerList.get(i).updateBrodcastMessage(brodcastMessage);
				}
			}
		}
	}
	
}