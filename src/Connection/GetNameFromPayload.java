package Connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;


public class GetNameFromPayload 
{
	private boolean deviceNameSet = false;
	private int available = 0;	
	private Socket socket;
	public String payloadName = null;
	public Timer timer;
	private PrintWriter out = null;
	
	public GetNameFromPayload(Socket socket)
	{
		this.socket = socket;
		requestForNameFromPayload(1000);
	}
	
	public boolean nameFromPayload()
	{
		while(!deviceNameSet)
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
				String streamInString = StreamIn();
				if(streamInString.startsWith("DeviceName"))
				{
					payloadName = streamInString.substring(10);
					payloadName = payloadName.replaceAll("[\\r\\n]", "");   
					deviceNameSet = true;
					streamOut("@");
				}
			}
		}
		
		return deviceNameSet;
	}
	
	public  String StreamIn()
	{
		String recieve = null;
		
		try 
		{
			int available = socket.getInputStream().available();
			byte packet[] = new byte[available];
			socket.getInputStream().read(packet, 0, available);
			recieve = new String(packet);
	    } 
		catch (IOException e) 
		{
			e.printStackTrace();
			
		}
		return recieve;
	}
	
	public  void streamOut(String sendText)
	{
		try 
		{
			out = new PrintWriter(socket.getOutputStream(), true);
			out.print(sendText);
			out.flush();
	    } 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void requestForNameFromPayload(int miliseconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), 0, miliseconds);
	}

    class RemindTask extends TimerTask 
    {
        public void run() 
        {
        	streamOut("#");
        }
    }
}
