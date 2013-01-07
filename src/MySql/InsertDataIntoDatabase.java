package MySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import Parser.MySqlData;
import Parser.Parser;

public class InsertDataIntoDatabase 
{
	public String databaseURL = "jdbc:mysql://localhost:3306";
	public String databaseName = "/S4";
	public String driver = "com.mysql.jdbc.Driver";
	public String userName;
	public String password;
	public Connection connection = null;
	public Parser parser;
	public MySqlData mySqlDataParsed;
	public String tableName = "S4PayloadData ";
	public Vector<String> records;
	
	
	public InsertDataIntoDatabase()
	{
		Config config = new Config();
		records = config.config();
		userName = records.get(0);
		password = records.get(1);
		
		try
		{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(databaseURL + databaseName, userName, password);
		System.out.println("Connected to Database");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		parser = new Parser();
	}
	
	public MySqlData insertDataIntoDatabase(String payloadName, String gpsData, String sensorData)
	{
		Statement stmt;
		
		mySqlDataParsed = parser.parseData(gpsData, sensorData + ",");  // , makes the last value to be parsed by parser
		
		
		try {
			long time = System.currentTimeMillis();
			
			stmt = connection.createStatement();
			String insetStatement = "INSERT INTO " +
					tableName +
					"VALUES('" +
					time +
					"','" +
					payloadName +
					"','" +
					gpsData +
					"','" +
					sensorData +
					"','" +
					mySqlDataParsed.GPS_Time +
					"','" +
					mySqlDataParsed.GPS_Fix +
					"','" +
					mySqlDataParsed.GPS_Lon +
					"','" +
					mySqlDataParsed.GPS_Lat +
					"','" +
					mySqlDataParsed.GPS_Alt +
					"','" +
					mySqlDataParsed.Sen_1_Key +
					"','" +
					mySqlDataParsed.Sen_1_Value +
					"','" +
					mySqlDataParsed.Sen_2_Key +
					"','" +
					mySqlDataParsed.Sen_2_Value +
					"','" +
					mySqlDataParsed.Sen_3_Key +
					"','" +
					mySqlDataParsed.Sen_3_Value +
					"','" +
					mySqlDataParsed.Sen_4_Key +
					"','" +
					mySqlDataParsed.Sen_4_Value +
					"','" +
					mySqlDataParsed.Sen_5_Key +
					"','" +
					mySqlDataParsed.Sen_5_Value +
					"','" +
					mySqlDataParsed.Sen_6_Key +
					"','" +
					mySqlDataParsed.Sen_6_Value +
					"','" +
					mySqlDataParsed.Sen_7_Key +
					"','" +
					mySqlDataParsed.Sen_7_Value +
					"','" +
					mySqlDataParsed.Sen_8_Key +
					"','" +
					mySqlDataParsed.Sen_8_Value +
					"','" +
					mySqlDataParsed.Sen_9_Key +
					"','" +
					mySqlDataParsed.Sen_9_Value +
					"','" +
					mySqlDataParsed.Sen_10_Key +
					"','" +
					mySqlDataParsed.Sen_10_Value +
					"');";
			
			stmt.executeUpdate(insetStatement);

		
			
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mySqlDataParsed;
	}
}
