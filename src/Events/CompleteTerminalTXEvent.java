package Events;

import java.util.EventObject;
import Data.PayloadRX;
import SocketHandelers.TerminalDataController;

public class CompleteTerminalTXEvent extends EventObject 
{
	private static final long serialVersionUID = 1193711128248938869L;
	public PayloadRX payloadRX;
	public TerminalDataController terminalDataController;
	
	public  CompleteTerminalTXEvent (Object source, PayloadRX payloadRX)
	{
		super(source);
		this.payloadRX = payloadRX;
	}
}
