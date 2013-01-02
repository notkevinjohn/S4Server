package SocketHandelers;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import Data.BrodcastMessage;
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
	public BrodcastMessage brodcastMessage;
	
	
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
				try {
					socket.getInputStream().reset();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
							try
							{
							payloadData.Sen_1_Value = Double.parseDouble(mySqlDataParsed.Sen_1_Value);
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						payloadData.Sen_2_Key = mySqlDataParsed.Sen_2_Key;
						if(!mySqlDataParsed.Sen_2_Value.contentEquals(""))
						{
							try
							{
							payloadData.Sen_2_Value = Double.parseDouble(mySqlDataParsed.Sen_2_Value);
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						payloadData.Sen_3_Key = mySqlDataParsed.Sen_3_Key;
						if(!mySqlDataParsed.Sen_3_Value.contentEquals(""))
						{
							try
							{
							payloadData.Sen_3_Value = Double.parseDouble(mySqlDataParsed.Sen_3_Value);
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						payloadData.Sen_4_Key = mySqlDataParsed.Sen_4_Key;
						if(!mySqlDataParsed.Sen_4_Value.contentEquals(""))
						{
							try
							{
							payloadData.Sen_4_Value = Double.parseDouble(mySqlDataParsed.Sen_4_Value);
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						payloadData.Sen_5_Key = mySqlDataParsed.Sen_5_Key;
						if(!mySqlDataParsed.Sen_5_Value.contentEquals(""))
						{
							try
							{
								payloadData.Sen_5_Value = Double.parseDouble(mySqlDataParsed.Sen_5_Value);
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						payloadData.Sen_6_Key = mySqlDataParsed.Sen_6_Key;
						if(!mySqlDataParsed.Sen_6_Value.contentEquals(""))
						{
							try
							{
								payloadData.Sen_6_Value = Double.parseDouble(mySqlDataParsed.Sen_6_Value);
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						payloadData.Sen_7_Key = mySqlDataParsed.Sen_7_Key;
						if(!mySqlDataParsed.Sen_7_Value.contentEquals(""))
						{
							try
							{
								payloadData.Sen_7_Value = Double.parseDouble(mySqlDataParsed.Sen_7_Value);
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						payloadData.Sen_8_Key = mySqlDataParsed.Sen_8_Key;
						if(!mySqlDataParsed.Sen_8_Value.contentEquals(""))
						{
							try
							{
								payloadData.Sen_8_Value = Double.parseDouble(mySqlDataParsed.Sen_8_Value);
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						payloadData.Sen_9_Key = mySqlDataParsed.Sen_9_Key;
						if(!mySqlDataParsed.Sen_9_Value.contentEquals(""))
						{
							try
							{
								payloadData.Sen_9_Value = Double.parseDouble(mySqlDataParsed.Sen_9_Value);
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						payloadData.Sen_10_Key = mySqlDataParsed.Sen_10_Key;
						if(!mySqlDataParsed.Sen_10_Value.contentEquals(""))
						{
							try
							{
								payloadData.Sen_10_Value = Double.parseDouble(mySqlDataParsed.Sen_10_Value);
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						
						if(mySqlDataParsed.GPS_Alt.matches("[0-9]+") | mySqlDataParsed.GPS_Alt.contains(".") )  // Need to handel this better
						{
							try
							{
								payloadData.alt = Double.parseDouble(mySqlDataParsed.GPS_Alt);
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						if(mySqlDataParsed.GPS_Lon.matches("[0-9]+") | mySqlDataParsed.GPS_Alt.contains("."))
						{
							try
							{
								payloadData.lon = Double.parseDouble(mySqlDataParsed.GPS_Lon);
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						if(mySqlDataParsed.GPS_Lat.matches("[0-9]+") | mySqlDataParsed.GPS_Alt.contains("."))
						{
							try
							{
								payloadData.lat = Double.parseDouble(mySqlDataParsed.GPS_Lat);
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						
						payloadData.timeStamp = System.currentTimeMillis();
						
						if(brodcastMessage != null)
						{
							payloadData.brodcastMessage = brodcastMessage;
						}
						
						payloadDataVector.addElement(payloadData);
						payloadData = new PayloadData();
						payloadLogger.recieveText(tempGpsData);
						payloadLogger.recieveText(tempScienceData);	
					}
				}
			
			}
			try { Thread.sleep(10); } catch(InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public void StreamOut(String sendText)
	{
		streamOut.streamOut(sendText);
	}
	
	public void updateBrodcastMessage(BrodcastMessage brodcastMessage)
	{
		this.brodcastMessage = brodcastMessage;
		System.out.println("BrodcastmessageRecived");
	}
}
