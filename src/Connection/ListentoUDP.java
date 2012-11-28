package Connection;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Vector;
import Data.Payload;
import Data.GetName;
import Main.Controller;
import SocketHandelers.PayloadDataController;


public class ListentoUDP  extends Thread
{
	private Payload payload;
	private ServerSocket serverInSocket;
	public Vector<PayloadDataController> payloadDataList;
	public  Vector<Payload> payloadList;
	public Controller controller;
	public DatagramSocket socket;
	public ListentoUDP()
	{
		

//	try {
//		serverInSocket = new ServerSocket(payloadPort);
//		Socket socket2 = serverInSocket.accept();
//		
//		int temp =  socket2.getInputStream().available(); 
//		System.out.println(temp);
//		
//	} catch (UnknownHostException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//       
      
		
		
		
	

			try {
				
				socket = new DatagramSocket(2003, InetAddress.getByName("192.168.1.2"));
			} catch (SocketException | UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while(true)
			{
				 byte[] buf = new byte[110];
				 DatagramPacket packet = new DatagramPacket(buf, buf.length);
			      try {
					socket.receive(packet);
					 String response = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
					 System.out.println(response);
					
				} catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
			
	
			      byte[] tempbuf = new byte[110];
			      byte[] mac = new byte[6];
			      tempbuf =  packet.getData();
			     System.out.println(new String(packet.getData()));
			      for(int i = 0; i < 110; i++)
			      {
			    	 System.out.print(tempbuf[i]);
			    	 
			      }
			      
			      System.out.println(mac);

//			    }
//			 }
 	 }
	}

		
//		try 
//		{
//			serverInSocket = new ServerSocket(payloadPort);
//		} 
//		catch (IOException e) 
//		{
//			e.printStackTrace();
//		}

	
	public void run() 
	{
		while(true)
		{
			try 
			{
				Socket socket = serverInSocket.accept();
				attachPayload(socket);
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
	
	public void attachPayload(Socket socket)
	{
		GetName getName = new GetName(controller);
		payload = new Payload();
		payload.socket = socket;
		payload.deviceName = getName.getPName(socket);
		payloadList.add(payload);
	
		PayloadDataController payloadDataController = new PayloadDataController(socket, controller, payload.deviceName);
		payloadDataList.add(payloadDataController);
		controller.UPDatePayloadList(payloadDataList,payloadList);
	}
}
