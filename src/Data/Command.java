package Data;

import java.io.Serializable;

public class Command implements Serializable
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6747567172135677536L;
	
	public long timeStamp;
	public String payloadName;
	public String terminalName;
	public boolean commandOne;
	public boolean commandTwo;
	public boolean commandThree;
	public boolean commandFour;
	public boolean getPayloadList;
}
