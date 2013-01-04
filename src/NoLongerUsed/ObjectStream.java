package NoLongerUsed;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import Data.PayloadDeviceNameList;

public class ObjectStream
{
	public Vector<PayloadDeviceNameList> payloadListVector;
	public ObjectOutputStream objectOutputStream;
	public ObjectStream(Vector<PayloadDeviceNameList> payloadListVector)
	{
		this.payloadListVector = payloadListVector;
		
	}
	public void sendObject(Socket socket)
	{
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			//Object obj = (Object)payloadList;
			objectOutputStream.writeUnshared(payloadListVector);
			objectOutputStream.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		

	}

}
