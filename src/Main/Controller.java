package Main;

import java.util.Vector;
import Connection.ConnectPayloadSocket;
import Connection.ConnectTerminalSocket;
import Connection.ListentoUDP;
import Data.Command;
import Data.PayloadRX;
import Events.CompletePayloadTXEventListener;
import Events.CompleteTerminalTXEvent;
import Events.CompleteTerminalTXEventListener;
import Events.ICompletePayloadTXEventListener;
import Events.ICompleteTerminalTXEventListener;
import GUI.GUI;
import IOStream.PayloadObjectTX;
import MySql.QuarryMySql;
import SocketHandelers.PayloadDataController;
import SocketHandelers.TerminalDataController;

public class Controller extends Thread
{
	public Vector<PayloadDataController> payloadDataControllerList;
	public Vector<TerminalDataController> terminalDataControllerList;
	
	
	public static javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
	public GUI gui;
	public ListentoUDP listentoUDP;
	
	public Controller()
	{	
		gui = new GUI(this);
		listentoUDP = new ListentoUDP();
		listentoUDP.listentoUDP(this, gui);
		listentoUDP.start();
		
		terminalDataControllerList = new Vector<TerminalDataController>();
		payloadDataControllerList = new Vector<PayloadDataController>();

		this.start();
		
		ConnectPayloadSocket connectPayloadSocket = new ConnectPayloadSocket(this);
		ConnectTerminalSocket connectTerminalSocket = new ConnectTerminalSocket(this);
		connectPayloadSocket.start();
		connectTerminalSocket.start();

	}
	
//	public void UPDatePayloadList(Vector<PayloadDataController> payloadDataList, Vector<Payload> payloadList)
//	{
//		this.payloadDataControllerList = payloadDataList;
//		this.payloadList = payloadList;
////		terminalPayloadList = new TerminalPayloadList();
////		terminalPayloadList.deviceName = payloadList.lastElement().deviceName;
////		terminalPayloadList.IP = payloadList.lastElement().socket.getLocalAddress().toString();
////		terminalPayloadList.localPort = payloadList.lastElement().socket.getLocalPort();
////		terminalPayloadList.remotePort = payloadList.lastElement().socket.getPort();
////		payloadListVector.add(terminalPayloadList);
//	}
//	
//	public void run() 
//	{
//		payloadTXController();
//	}
//	
//	public  void payloadTXController()
//	{
//		addCompletePayloadTXEventListener(new CompletePayloadTXEventListener(this));
//	}
//	public  void terminalTXController()
//	{
//		addCompletedTerminalTXEventListener(new CompleteTerminalTXEventListener(this));
//	}
	
	// Takes a request from a Terminal for the Payload update and updates it with the newest data
//	public void  terminalRequestForUpdate(TerminalDataController termDataController, String payloadName, ObjectOutputStream objectOutputStream)
//	{
//		for(int i = 0; i < payloadDataList.size(); i++)
//		{
//			if(payloadName.equals(payloadDataList.get(i).deviceName) && payloadDataList.size() >0 && payloadDataList.get(0).payloadDataVector.size() > 0)
//			{
//				
//				PayloadData payloadLastData = payloadDataList.get(i).payloadDataVector.get(payloadDataList.get(i).payloadDataVector.size()-1);
//				streamOut.attachSocket(termDataController.socket);
//				streamOut.streamOut("PayloadUpdate");
//				try { Thread.sleep(30); } catch(InterruptedException e) { } // to not overload the output stream
//				//payloadObjectTX.sendObject(termDataController.socket, payloadLastData, objectOutputStream);
//			}
//		}
//	}
	
	
	public void requestPayloadDataUpdate(TerminalDataController terminalDataController, PayloadObjectTX payloadObjectTX, long lastReciveTimeStamp, String payloadDeviceName)
	{
		addCompletedTerminalTXEventListener(new CompleteTerminalTXEventListener(this, lastReciveTimeStamp, payloadDeviceName, terminalDataController));	
		//new QuarryMySql(this, lastReciveTimeStamp, payloadDeviceName);
	}
	
	public void sendPayloadDataToTerminalController(Command command, String terminalName)
	{
		for(int i = 0; i < terminalDataControllerList.size(); i++)
		{
			if(terminalName.equals(terminalDataControllerList.get(i).terminalName) && terminalDataControllerList.size() > 0)
			{
				terminalDataControllerList.get(i).getCommandForTerminal(command);
			}
		}
	}
	
	public void passOnCommandToPayload(String payloadDeviceName, Command command)
	{
		for(int i = 0; i < payloadDataControllerList.size(); i++)
		{
			if(payloadDeviceName.equals(payloadDataControllerList.get(i).deviceName) && payloadDataControllerList.size() >0 && payloadDataControllerList.get(0).payloadDataVector.size() > 0)
			{
				payloadDataControllerList.get(i).PassOnCommand(command);
			}
		}
	}
	
//	public static void addCompletePayloadTXEventListener (ICompletePayloadTXEventListener completeTXEventListener)
//	{
//		listenerList.add(ICompletePayloadTXEventListener.class, completeTXEventListener);
//	}
	public static void addCompletedTerminalTXEventListener (ICompleteTerminalTXEventListener completeTerminalTXEventListener)
	{
		listenerList.add(ICompleteTerminalTXEventListener.class, completeTerminalTXEventListener);
	}


}
