package IOStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import Data.Command;

public class CommandObjectRX 
{
	public Socket socket;
	public ObjectInputStream objectInputStream;
	public Command command;
	
	public CommandObjectRX(Socket socket)
	{
		this.socket = socket;
		
		try 
		{
			objectInputStream = new ObjectInputStream(socket.getInputStream());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public Command getCommandObject()	
	{	
		System.out.println("Getcommand");
		command = new Command();
		try 
		{
			command = (Command)objectInputStream.readObject();
		} 
		catch (ClassNotFoundException e) 
		{	
		}
		catch(IOException e)
		{
		}
		
		System.out.println("Sendcommand");
		
		return command;
	}
}