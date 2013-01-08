package Events;

import java.util.EventListener;

public interface ICompleteTerminalTXEventListener extends EventListener
{
	public void completeTerminalTXEventHandler (CompleteTerminalTXEvent event);
}
