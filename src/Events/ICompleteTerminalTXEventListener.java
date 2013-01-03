package Events;

import java.util.EventListener;

public interface ICompleteTerminalTXEventListener extends EventListener
{
	public void CompleteTerminalTXEventHandler (CompleteTerminalTXEvent event);
}
