package Events;

import SocketHandelers.TerminalDataController;

public class CompleteTerminalTXEventListener implements ICompleteTerminalTXEventListener
{
	private TerminalDataController terminalDataController;
	
	public CompleteTerminalTXEventListener (TerminalDataController terminalDataController)
	{
		this.terminalDataController = terminalDataController;
	}
	

	public void CompleteTerminalTXEventHandler(CompleteTerminalTXEvent event) {
		// TODO Auto-generated method stub
		
	}
}

