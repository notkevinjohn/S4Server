package Events;

import java.util.EventObject;

import Data.Command;

public class CompleteTerminalTXEvent extends EventObject 
{
	private static final long serialVersionUID = 1193711128248938869L;
	public Command command;
		
	public  CompleteTerminalTXEvent (Object source, Command command)
	{
		super(source);
		this.command = command;
	}
}
