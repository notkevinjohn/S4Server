package Connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import Data.Command;
import Data.PayloadData;
import Data.PayloadRX;
import Data.TerminalPayloadList;
import IOStream.CommandObjectRX;
import IOStream.GetStreamIn;
import IOStream.PayloadObjectTX;
import IOStream.SendStreamOut;
import Main.Controller;

public class GetName
{
	private int available = 0;	
	private boolean deviceNameSet = false;
	public GetStreamIn getStreamIn;
	public SendStreamOut sendStreamOut;
	public String streamInString;
	public String DeviceName;
	public Controller controller;
	public Vector<TerminalPayloadList> payloadListVector;
	public ObjectOutputStream objectOutputStream;
	public PayloadObjectTX payloadObjectTX;
	public CommandObjectRX commandObjectRX;
	public Command command;
	
	public GetName(Controller controller, PayloadObjectTX payloadObjectTX, CommandObjectRX commandObjectRX) 
	{
		this.controller = controller;
		this.payloadObjectTX = payloadObjectTX;
	}

	public boolean getPayloadName(Socket socket)
	{
		
//		sendStreamOut = new SendStreamOut();
//		sendStreamOut.attachSocket(socket);
//		getStreamIn = new GetStreamIn();
		
		while(!deviceNameSet)
		{
			try 
			{
				available = socket.getInputStream().available();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			if(available > 0)
			{
				command = commandObjectRX.getCommandObject();
				
				if(command.getPayloadList == true && command.payloadName == null)
				{
					PayloadRX payloadRX = new PayloadRX();
					PayloadData payloadData = new PayloadData();
					
					payloadData.PayloadList = controller.payloadListVector;
					payloadRX.payloadRX.add(payloadData);
					
					payloadObjectTX.sendPayloadObject(payloadRX);
				}
				
				if(command.getPayloadList == true && command.payloadName != null)
				{
					PayloadRX payloadRX = new PayloadRX();
					PayloadData payloadData = new PayloadData();
					payloadData.payloadName = command.payloadName;
					
					payloadRX.payloadRX.add(payloadData);
					
					payloadObjectTX.sendPayloadObject(payloadRX);
					
					deviceNameSet = true;
				}
				
			}
			
			try { Thread.sleep(10); } catch(InterruptedException e) {}	
		}
		return deviceNameSet;
}
	
	
//				 streamInString = getStreamIn.StreamIn(socket);
//				 System.out.println(streamInString);
//				 if(streamInString.startsWith("DeviceName"))
//				 {
//					 String tempName = streamInString.substring(10);
//					 DeviceName = tempName.replaceAll("[\n\r]", "");
//					 deviceNameSet = false;
//					 sendStreamOut.streamOut("@");
//				 }
//				 else if (streamInString.startsWith("Refresh"))
//				 {
//					sendStreamOut.streamOut("Refresh");
//					ObjectStream objectStream =  new ObjectStream(payloadListVector);
//					objectStream.sendObject(socket);
//					objectOutputStream = objectStream.objectOutputStream;
				 }
			
//			requestPayloadName();
			

	
//	public void requestPayloadName()
//	{
//		if((System.currentTimeMillis() - lastPingTime) > 1000)
//		{
//			sendStreamOut.streamOut("#"); 
//			lastPingTime = System.currentTimeMillis();
//		}
//	}

