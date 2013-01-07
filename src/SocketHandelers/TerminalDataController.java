package SocketHandelers;

import java.io.IOException;
import java.net.Socket;
import Data.Command;
import IOStream.CommandObjectRX;
import IOStream.PayloadObjectTX;
import Main.Controller;

public class TerminalDataController extends Thread
{
	private int available = 0;
	public Socket socket;
	private boolean terminalConnected = true;
	private Controller controller;
	private CommandObjectRX commandObjectRX;
	public String payloadDeviceName;
	public String terminalName;
	public Command command;
	public PayloadObjectTX payloadObjectTX;
	
	public TerminalDataController(String payloadDeviceName, String terminalName, Controller controller, PayloadObjectTX payloadObjectTX, CommandObjectRX commandObjectRX, Socket socket)
	{
		this.controller = controller;
		this.commandObjectRX = commandObjectRX;
		this.payloadObjectTX = payloadObjectTX;
		this.payloadDeviceName = payloadDeviceName;
		this.terminalName = terminalName;
		this.socket = socket;
		
		this.start();
	}
	
	public void run() 
	{
		while(terminalConnected)
		{
			try 
			{
				available = socket.getInputStream().available();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			if(available > 0)
			{
				command = new Command();
				command = commandObjectRX.getCommandObject();
				
				if(command.getPayloadList == false)
				{
					controller.requestPayloadDataUpdate(this,payloadObjectTX, command.timeStamp, command.payloadName);
				}
				else
				{
					// take care of payload update requests while running
				}
				
				if(command.commandOne == true || command.commandTwo == true || command.commandThree == true || command.commandFour == true)
				{
					controller.passOnCommandToPayload(command.payloadName, command);
				}
			}
			try { Thread.sleep(10); } catch(InterruptedException e) { /* we tried */}
		}
	}
	
	public void getCommandForTerminal(Command command)
	{
		
	}
}