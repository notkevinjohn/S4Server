package Connection;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import Data.Command;
import Data.Payload;
import Data.PayloadData;
import Data.PayloadDeviceNameList;
import Data.PayloadRX;
import IOStream.CommandObjectRX;
import IOStream.PayloadObjectTX;
import Main.Controller;

public class GetPayloadNameFromTerminal
{
	private int available = 0;	
	private boolean deviceNameSet = false;
	private Controller controller;
	public PayloadObjectTX payloadObjectTX;
	public CommandObjectRX commandObjectRX;
	public Command command;
	
	public GetPayloadNameFromTerminal(Controller controller, PayloadObjectTX payloadObjectTX, CommandObjectRX commandObjectRX) 
	{
		this.controller = controller;
		this.payloadObjectTX = payloadObjectTX;
		this.commandObjectRX = commandObjectRX;
	}

	public boolean getPayloadName(Socket socket)
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
				command = new Command();
				command = commandObjectRX.getCommandObject();
				
				if(command.getPayloadList == true && command.payloadName == null)
				{
					PayloadRX payloadRX = new PayloadRX();
					PayloadData payloadData = new PayloadData();
					payloadRX.payloadRX = new Vector<PayloadData>();
					
					payloadData.PayloadList = makePayloadList();
					
					payloadRX.payloadRX.addElement(payloadData);
					
					payloadObjectTX.sendPayloadObject(payloadRX);
				}
				
				if(command.getPayloadList == true && command.payloadName != null)
				{
					PayloadRX payloadRX = new PayloadRX();
					payloadRX.payloadRX = new Vector<PayloadData>();
					PayloadData payloadData = new PayloadData();
					payloadData.payloadName = command.payloadName;
					
					payloadRX.payloadRX.add(payloadData);
					
					payloadObjectTX.sendPayloadObject(payloadRX);
					
					deviceNameSet = true;
				}
				
			}
			try { Thread.sleep(10); } catch(InterruptedException e) {}	
		}
		return deviceNameSet;
	}
	
	public PayloadDeviceNameList makePayloadList()
	{
		PayloadDeviceNameList payloadDeviceNameList = new PayloadDeviceNameList();
		payloadDeviceNameList.payloadDeviceNameList = new Vector<Payload>();
		
		for(int j = 0; j < controller.payloadDataControllerList.size(); j++)
		{
			Payload payload = new Payload();
			payload.deviceName = controller.payloadDataControllerList.get(j).deviceName;
			payloadDeviceNameList.payloadDeviceNameList.add(payload);
		}
		return payloadDeviceNameList;
	}
}