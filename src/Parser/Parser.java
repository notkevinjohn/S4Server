package Parser;

import java.util.Arrays;
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
		
		
		
		String[] tempGpsData = gpsData.split(",");
		
		mySqlData.GPS_Time = tempGpsData[1];
		
		if(tempGpsData[3] == "S")
		{
			String stringTempString = "-";
			stringTempString += tempGpsData[2];
			mySqlData.GPS_Lat  = stringTempString;
		}
		else
		{
			mySqlData.GPS_Lat  = tempGpsData[2];
		}
		
		if(tempGpsData[5] == "W")
		{
			String stringTempString = "-";
			stringTempString += tempGpsData[4];
			mySqlData.GPS_Lat  = stringTempString;
		}
		else
		{
			mySqlData.GPS_Lat  = tempGpsData[4];
		}		
		
		mySqlData.GPS_Fix = tempGpsData[6];
		mySqlData.GPS_Alt = tempGpsData[9];
		

		String[] tempSensorData = Arrays.copyOf(sensorData.split(","), sensorData.split(",").length +20);
		
		mySqlData.Sen_1_Key = tempSensorData[1];
		mySqlData.Sen_1_Value = tempSensorData[2];
		mySqlData.Sen_2_Key = tempSensorData[3];
		mySqlData.Sen_2_Value = tempSensorData[4];
		mySqlData.Sen_3_Key = tempSensorData[5];
		mySqlData.Sen_3_Value = tempSensorData[6];
		mySqlData.Sen_4_Key = tempSensorData[7];
		mySqlData.Sen_4_Value = tempSensorData[8];
		mySqlData.Sen_5_Key = tempSensorData[9];
		mySqlData.Sen_5_Value = tempSensorData[10];
		mySqlData.Sen_6_Key = tempSensorData[11];
		mySqlData.Sen_6_Value = tempSensorData[12];
		mySqlData.Sen_7_Key = tempSensorData[13];
		mySqlData.Sen_7_Value = tempSensorData[14];
		mySqlData.Sen_8_Key = tempSensorData[15];
		mySqlData.Sen_8_Value = tempSensorData[16];
		mySqlData.Sen_9_Key = tempSensorData[17];
		mySqlData.Sen_9_Value = tempSensorData[18];
		mySqlData.Sen_10_Key = tempSensorData[19];
		mySqlData.Sen_10_Value = tempSensorData[20];
		
		
		
//		beginIndex = gpsData.indexOf(',', 0);
//		endIndex = gpsData.indexOf(',', beginIndex+1);
//		
//		mySqlData.GPS_Time = gpsData.substring(beginIndex+1, endIndex);
//		
//		Index(gpsData);
//		
//		mySqlData.GPS_Lat = GPSLat(gpsData);
//		
//		Index(gpsData);
//		
//		mySqlData.GPS_Lon = GPSLon(gpsData);
//		
//		Index(gpsData);
//		
//		mySqlData.GPS_Fix = gpsData.substring(beginIndex, endIndex);
//		
//		Index(gpsData);
//		Index(gpsData);
//		Index(gpsData);
		
//		
//		mySqlData.GPS_Alt = gpsData.substring(beginIndex, endIndex);
//	
//		beginIndex = sensorData.indexOf(',', 0);
//		endIndex = sensorData.indexOf(',', beginIndex+1);
		
//		mySqlData.Sen_1_Key = sensorData.substring(beginIndex+1, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_1_Value = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_2_Key = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_2_Value = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_3_Key = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_3_Value = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_4_Key = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_4_Value = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_5_Key = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_5_Value = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_6_Key = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_6_Value = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_7_Key = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_7_Value = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_8_Key = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_8_Value = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_9_Key = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_9_Value = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_10_Key = sensorData.substring(beginIndex, endIndex);
//		Index(sensorData);
//		mySqlData.Sen_10_Value = sensorData.substring(beginIndex, endIndex);
//		
//		
		
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

