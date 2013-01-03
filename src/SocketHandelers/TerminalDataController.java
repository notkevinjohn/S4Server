package SocketHandelers;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Data.Command;
import Data.PayloadRX;
import Events.ICompleteTerminalTXEventListener;
import IOStream.CommandObjectRX;
import IOStream.GetStreamIn;
import IOStream.PayloadObjectTX;
import IOStream.SendStreamOut;
import Main.Controller;

public class TerminalDataController extends Thread
{
	public static javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
	
	
	private int available = 0;
	public Socket socket;
	public SendStreamOut streamOut;
	public String streamInString;
	public GetStreamIn getStreamIn;
	public boolean terminalConnected = true;
//	public String payloadConnectIP = "";
	public boolean isPayloadIPSet = false;
	public String payloadDeviceName;
	public Controller controller;
	public long lastTimeStamp = 0;
	public ObjectOutputStream objectOutputStream;
	public String terminalName;
	public PayloadObjectTX payloadObjectTX;
	public CommandObjectRX commandObjectRX;
	public String requestedPayloadName;
	public TerminalDataController(String payloadDeviceName, String terminalName, Controller controller, PayloadObjectTX payloadObjectTX, CommandObjectRX commandObjectRX)
	{
		this.controller = controller;
		this.payloadDeviceName = payloadDeviceName;
		this.commandObjectRX = commandObjectRX;
		this.payloadObjectTX = payloadObjectTX;
		
//		getStreamIn = new GetStreamIn();
//		streamOut = new SendStreamOut();
//		streamOut.attachSocket(socket);
		
		this.start();
//		streamOut.streamOut("@"); // Complete handshake
	}
	
	public void run() 
	{
		while(terminalConnected)
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
				Command command = commandObjectRX.getCommandObject();
				if(command.getPayloadList == false)
				{
					
					// create event handeler!!!!!!
					
					payloadObjectTX.sendPayloadObject(payloadRX);
					payloadRX = controller.requestPayloadDataUpdate(this,lastTimeStamp, payloadDeviceName);
					
					payloadObjectTX.sendPayloadObject(payloadRX);
				}
				else
				{
					// take care of payload update requests while running
				}
				
				
					
					
				
//				  streamInString = getStreamIn.StreamIn(socket);
//				  if(streamInString.startsWith("payloadUpdateRequest."))
//				  {
//					  String payloadRequested = streamInString.substring(21);
//
//					  payloadRequested = payloadRequested.replaceAll("(\\r|\\n)", "");
//					  payloadUpdateRequested(payloadRequested);
//				  }
//			}
			try { Thread.sleep(10); } catch(InterruptedException e) { /* we tried */}
			}
		}
	
	}
	
	
//	public void payloadUpdateRequested(String payloadRequested)
//	{
//		controller.terminalRequestForUpdate(this, payloadRequested, objectOutputStream);
//	}
//	public void StreamOut(String sendText)
//	{
//		streamOut.streamOut(sendText);
//	}
	
	public static void addCompletedTerminalTXEventListener (ICompleteTerminalTXEventListener completeTerminalTXEventListener)
	{
		listenerList.add(ICompleteTerminalTXEventListener.class, completeTerminalTXEventListener);
	}

}