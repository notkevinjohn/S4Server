package Events;

import Main.Controller;
import MySql.QuarryMySql;
import SocketHandelers.TerminalDataController;


public class CompleteTerminalTXEventListener implements ICompleteTerminalTXEventListener
{
	private TerminalDataController terminalDataController;
	
	public CompleteTerminalTXEventListener (Controller controller, long lastReciveTimeStamp,String payloadDeviceName,  TerminalDataController terminalDataController)
	{
		this.terminalDataController = terminalDataController;
		new QuarryMySql(controller, lastReciveTimeStamp, payloadDeviceName);
	}

	public void CompleteTerminalTXEventHandler(CompleteTerminalTXEvent event) 
	{
		terminalDataController.payloadObjectTX.sendPayloadObject(event.payloadRX);
	}
}
