package IOStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Data.PayloadData;

public class PayloadObjectTX
{
	public ObjectOutputStream objectOutputStream;
	
	public void sendObject(Socket socket, PayloadData payloadLastData, ObjectOutputStream objectOutputStream)
	{
		try 
		{
			objectOutputStream.writeObject(payloadLastData);
			objectOutputStream.flush();
			objectOutputStream.reset();
		} 
		catch (IOException e)
		{
			try {
				objectOutputStream.reset();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

}

