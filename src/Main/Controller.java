package Main;

import java.util.Vector;
import Connection.ConnectPayloadSocket;
import Connection.ConnectTerminalSocket;
import Connection.ListentoUDP;
import Data.Command;
import Events.CompleteTerminalTXEventListener;
import Events.ICompleteTerminalTXEventListener;
import GUI.GUI;
import IOStream.PayloadObjectTX;
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
	
	public void requestPayloadDataUpdate(TerminalDataController terminalDataController, PayloadObjectTX payloadObjectTX, long lastReciveTimeStamp, String payloadDeviceName, Command command)
	{
		addCompletedTerminalTXEventListener(new CompleteTerminalTXEventListener(this, lastReciveTimeStamp, payloadDeviceName, terminalDataController));	
	}
	
	public void passOnCommandToPayload(String payloadDeviceName, final Command command)
	{
		for(int i = 0; i < payloadDataControllerList.size(); i++)
		{
			final int j = i;
			if(payloadDeviceName.equals(payloadDataControllerList.get(i).deviceName) && payloadDataControllerList.size() >0 && payloadDataControllerList.get(0).payloadDataVector.size() > 0)
			{
				    payloadDataControllerList.get(j).PassOnCommand(command);
			}
		}
	}

	public static void addCompletedTerminalTXEventListener (CompleteTerminalTXEventListener completeTerminalTXEventListener)
	{
		listenerList.add(ICompleteTerminalTXEventListener.class, completeTerminalTXEventListener);
	}
	public static void removeCompletedTerminalTXEventListener (CompleteTerminalTXEventListener completeTerminalTXEventListener)
	{
		listenerList.remove(ICompleteTerminalTXEventListener.class, completeTerminalTXEventListener);
	}
}
