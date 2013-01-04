package Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import IOStream.CommandObjectRX;
import IOStream.PayloadObjectTX;
import Main.Controller;
import SocketHandelers.TerminalDataController;


public class ConnectTerminalSocket extends Thread
{
	private int terminalPort = 2001;
	private Controller controller;
	private ServerSocket serverTerminalSocket;
	private PayloadObjectTX payloadObjectTX;
	private CommandObjectRX commandObjectRX;
	private GetPayloadNameFromTerminal getName;
	
	public ConnectTerminalSocket(Controller controller)
	{
		this.controller = controller;
		
		try 
		{
			serverTerminalSocket = new ServerSocket(terminalPort);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void run() 
	{
		while(true)
		{
			try 
			{
				Socket socket = serverTerminalSocket.accept();
				attachTerminal(socket);
			}
			catch (UnknownHostException e1) 
			{
				e1.printStackTrace();
			} 
			catch (IOException e1) 
			{	
				e1.printStackTrace();
			}
			try { Thread.sleep(10); } catch(InterruptedException e) { /* we tried */}
		}
	}
	
	public void attachTerminal(Socket socket)
	{
		payloadObjectTX = new PayloadObjectTX(socket);
		commandObjectRX = new CommandObjectRX(socket);
		
		getName = new GetPayloadNameFromTerminal(controller, payloadObjectTX, commandObjectRX);
		
		if(getName.getPayloadName(socket))
		{
			TerminalDataController terminalDataController = new TerminalDataController(getName.command.payloadName, getName.command.terminalName, controller, getName.payloadObjectTX, getName.commandObjectRX,socket);
			controller.terminalDataControllerList.add(terminalDataController);
		}
	}
}
