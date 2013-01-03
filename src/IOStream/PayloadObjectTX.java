package IOStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import Data.PayloadRX;

public class PayloadObjectTX
{
	public ObjectOutputStream objectOutputStream;
	public Socket socket;
	
	public PayloadObjectTX(Socket socket)
	{
		this.socket = socket;
		try 
		{
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void sendPayloadObject(PayloadRX payloadRX)
	{
		try 
		{
			objectOutputStream.writeObject(payloadRX);
			objectOutputStream.flush();
			objectOutputStream.reset();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}

