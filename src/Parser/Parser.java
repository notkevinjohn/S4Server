package Parser;

import java.util.Vector;


public class Parser 
{
	public Vector<MySqlData> gpsSensorData;
	public MySqlData mySqlData;
	public int beginIndex;
	public int endIndex;
	
	public Parser()
	{
	}
	
	public MySqlData parseData(String gpsData, String sensorData)
	{
		gpsSensorData = new Vector<MySqlData>();
		mySqlData = new MySqlData();
		
		beginIndex = gpsData.indexOf(',', 0);
		endIndex = gpsData.indexOf(',', beginIndex+1);
		
		mySqlData.GPS_Time = gpsData.substring(beginIndex+1, endIndex);
		
		Index(gpsData);
		
		mySqlData.GPS_Lat = GPSLat(gpsData);
		
		Index(gpsData);
		
		mySqlData.GPS_Lon = GPSLon(gpsData);
		
		Index(gpsData);
		
		mySqlData.GPS_Fix = gpsData.substring(beginIndex, endIndex);
		
		Index(gpsData);
		Index(gpsData);
		Index(gpsData);
		
		
		mySqlData.GPS_Alt = gpsData.substring(beginIndex, endIndex);
	
		beginIndex = sensorData.indexOf(',', 0);
		endIndex = sensorData.indexOf(',', beginIndex+1);
		
		mySqlData.Sen_1_Key = sensorData.substring(beginIndex+1, endIndex);
		Index(sensorData);
		mySqlData.Sen_1_Value = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_2_Key = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_2_Value = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_3_Key = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_3_Value = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_4_Key = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_4_Value = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_5_Key = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_5_Value = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_6_Key = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_6_Value = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_7_Key = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_7_Value = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_8_Key = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_8_Value = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_9_Key = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_9_Value = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_10_Key = sensorData.substring(beginIndex, endIndex);
		Index(sensorData);
		mySqlData.Sen_10_Value = sensorData.substring(beginIndex, endIndex);
		
		
		
		return mySqlData;
		
	}
	
	public void Index(String string)
	{
		
		if(beginIndex < string.length() && beginIndex > 0)
		{
			beginIndex = string.indexOf(',', endIndex)+1;
		}
		else
		{
			beginIndex = 0;
		}
		
		
		
		if(endIndex < string.length() && endIndex > 0)
		{
			endIndex = string.indexOf(',', beginIndex+1);
		}
		
		if(endIndex < string.length() && endIndex > 0)
		{
		}
		else
		{
			endIndex = 0;
			beginIndex = 0;
		}
		
		
	}
	
	public String GPSLat(String gpsData)
	{
		String gps_Lat = gpsData.substring(beginIndex, endIndex);
		Index(gpsData);
		String direction = gpsData.substring(beginIndex, endIndex);
		
		if(direction.equals("S"))
		{
			String tempString = "-";
			gps_Lat = tempString += gps_Lat;
		}
		
		return gps_Lat;
	}
	
	public String GPSLon(String gpsData)
	{
		String gps_Lon = gpsData.substring(beginIndex, endIndex);
		Index(gpsData);
		String direction = gpsData.substring(beginIndex, endIndex);
		
		if(direction.equals("W"))
		{
			String tempString = "-";
			gps_Lon = tempString += gps_Lon;
		}
		
		return gps_Lon;
	}
	
	
}

