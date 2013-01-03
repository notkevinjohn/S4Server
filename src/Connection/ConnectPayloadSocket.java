package Connection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import Data.Payload;
import Main.Controller;
import SocketHandelers.PayloadDataController;

public class ConnectPayloadSocket extends Thread
{
	private int payloadPort = 2000;
	//private Payload payload;
	private ServerSocket serverInSocket;
	public Vector<PayloadDataController> payloadDataControllerList;
	public  Vector<Payload> payloadList;
	public Controller controller;
	
	public ConnectPayloadSocket(Controller controller)
	{
		this.controller = controller;
		//payloadList = new Vector<Payload>();
	//	payloadDataControllerList = new Vector<PayloadDataController>();
		
		try 
		{
			serverInSocket = new ServerSocket(payloadPort);
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
		GetNameFromPayload getNameFromPayload = new GetNameFromPayload(socket);
		if(getNameFromPayload.nameFromPayload())
		{
			System.out.println(getNameFromPayload.payloadName);
			PayloadDataController payloadDataController = new PayloadDataController(socket, controller, getNameFromPayload.payloadName);
			controller.payloadDataControllerList.add(payloadDataController);
		}
		
	//	GetPayloadNameFromTerminal getName = new GetPayloadNameFromTerminal(controller);
//		payload = new Payload();
//		payload.socket = socket;
//		payload.deviceName = getName.getPName(socket);
//		payloadList.add(payload);
	
//		PayloadDataController payloadDataController = new PayloadDataController(socket, controller, payload.deviceName);
//		payloadDataControllerList.add(payloadDataController);
//		controller.UPDatePayloadList(payloadDataControllerList,payloadList);
	}
}
