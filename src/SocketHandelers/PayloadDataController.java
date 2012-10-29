package SocketHandelers;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import Data.PayloadData;
import FileWriters.PayloadLogger;
import IOStream.GetStreamIn;
import IOStream.SendStreamOut;
import Main.Controller;
import MySql.InsertDataIntoDatabase;
import Parser.MySqlData;

public class PayloadDataController extends Thread
{
	public Vector<PayloadData> payloadDataVector;
	public PayloadData payloadData;
	public String deviceName;
	private Socket socket;
	private int available = 0;
	private SendStreamOut streamOut;
	private String streamInString;
	private GetStreamIn getStreamIn;
	private boolean payloadConnected = true;
	private PayloadLogger payloadLogger;
	private InsertDataIntoDatabase mySqlData;
	public MySqlData mySqlDataParsed;
	
	public PayloadDataController(Socket socket, Controller controller, String deviceName)
	{
		this.socket = socket;
		this.deviceName = deviceName;
		getStreamIn = new GetStreamIn();
		streamOut = new SendStreamOut();
		streamOut.attachSocket(socket);
		payloadDataVector = new Vector<PayloadData>();
		payloadData = new PayloadData();
		payloadLogger = new PayloadLogger();
		payloadLogger.payloadLogger(deviceName);
		this.start();
		mySqlData = new InsertDataIntoDatabase();
		
	}
	
	public void run() 
	{
		while(payloadConnected)
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
				  streamInString = getStreamIn.StreamIn(socket);
				  System.out.println(streamInString);
				  
				  

				if(streamInString.startsWith("$GPGGA"))
				{
					
					int scienceDataStart = streamInString.indexOf('@');
					if(scienceDataStart > 0)
					{
					    String tempGpsData = streamInString.substring(0,scienceDataStart);
					    payloadData.gpsData = tempGpsData;
						String tempScienceData = streamInString.substring(scienceDataStart);
						tempScienceData += '\r';
						payloadData.scienceData = tempScienceData;

						mySqlDataParsed = mySqlData.insertDataIntoDatabase(deviceName, tempGpsData, tempScienceData); 
						
						payloadData.Sen_1_Key = mySqlDataParsed.Sen_1_Key;
						if(!mySqlDataParsed.Sen_1_Value.contentEquals(""))
						{
							payloadData.Sen_1_Value = Double.parseDouble(mySqlDataParsed.Sen_1_Value);
						}
						payloadData.Sen_2_Key = mySqlDataParsed.Sen_2_Key;
						if(!mySqlDataParsed.Sen_2_Value.contentEquals(""))
						{
							payloadData.Sen_2_Value = Double.parseDouble(mySqlDataParsed.Sen_2_Value);
						}
						payloadData.Sen_3_Key = mySqlDataParsed.Sen_3_Key;
						if(!mySqlDataParsed.Sen_3_Value.contentEquals(""))
						{
							payloadData.Sen_3_Value = Double.parseDouble(mySqlDataParsed.Sen_3_Value);
						}
						payloadData.Sen_4_Key = mySqlDataParsed.Sen_4_Key;
						if(!mySqlDataParsed.Sen_4_Value.contentEquals(""))
						{
							payloadData.Sen_4_Value = Double.parseDouble(mySqlDataParsed.Sen_4_Value);
						}
						payloadData.Sen_5_Key = mySqlDataParsed.Sen_5_Key;
						if(!mySqlDataParsed.Sen_5_Value.contentEquals(""))
						{
							payloadData.Sen_5_Value = Double.parseDouble(mySqlDataParsed.Sen_5_Value);
						}
						payloadData.Sen_6_Key = mySqlDataParsed.Sen_6_Key;
						if(!mySqlDataParsed.Sen_6_Value.contentEquals(""))
						{
							payloadData.Sen_6_Value = Double.parseDouble(mySqlDataParsed.Sen_6_Value);
						}
						payloadData.Sen_7_Key = mySqlDataParsed.Sen_7_Key;
						if(!mySqlDataParsed.Sen_7_Value.contentEquals(""))
						{
							payloadData.Sen_7_Value = Double.parseDouble(mySqlDataParsed.Sen_7_Value);
						}
						payloadData.Sen_8_Key = mySqlDataParsed.Sen_8_Key;
						if(!mySqlDataParsed.Sen_8_Value.contentEquals(""))
						{
							payloadData.Sen_8_Value = Double.parseDouble(mySqlDataParsed.Sen_8_Value);
						}
						payloadData.Sen_9_Key = mySqlDataParsed.Sen_9_Key;
						if(!mySqlDataParsed.Sen_9_Value.contentEquals(""))
						{
							payloadData.Sen_9_Value = Double.parseDouble(mySqlDataParsed.Sen_9_Value);
						}
						payloadData.Sen_10_Key = mySqlDataParsed.Sen_10_Key;
						if(!mySqlDataParsed.Sen_10_Value.contentEquals(""))
						{
							payloadData.Sen_10_Value = Double.parseDouble(mySqlDataParsed.Sen_10_Value);
						}
						
						payloadData.timeStamp = System.currentTimeMillis();
						payloadDataVector.addElement(payloadData);
						payloadData = new PayloadData();
						payloadLogger.recieveText(tempGpsData);
						payloadLogger.recieveText(tempScienceData);	
					}
				}
			}
			try { Thread.sleep(10); } catch(InterruptedException e) { }
		}
	}
	
	public void StreamOut(String sendText)
	{
		streamOut.streamOut(sendText);
	}
}
