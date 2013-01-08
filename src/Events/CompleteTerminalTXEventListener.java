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
		new QuarryMySql(lastReciveTimeStamp, payloadDeviceName);
	}

	public void completeTerminalTXEventHandler(CompleteTerminalTXEvent event) 
	{
		terminalDataController.payloadObjectTX.sendPayloadObject(event.payloadRX);
		Controller.removeCompletedTerminalTXEventListener(this);
	}
}
