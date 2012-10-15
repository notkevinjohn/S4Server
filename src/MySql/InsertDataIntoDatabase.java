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
	public String userName = "root";
	public String password = "nasaepo";
	public Connection connection = null;
	public Parser parser;
	public Vector<MySqlData> gpsSensorData;
	
	public InsertDataIntoDatabase()
	{
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
	
	public void insertDataIntoDatabase(String payloadName, String gpsData, String sensorData)
	{
		Statement stmt;
		
		gpsSensorData = parser.parseData("$GPGGA,123519,4807.038,N,01131.000,W,1,08,0.9,545.4,M,46.9,M,,*47", "@,%,50004,$,6015,*,5050");
		
		
		try {
			stmt = connection.createStatement();
			String insetStatement = "INSERT INTO PayloadData VALUES('" +
					System.currentTimeMillis() +
					"','" +
					payloadName +
					"','" +
					gpsData +
					"','" +
					sensorData +
					"');";
			stmt.executeUpdate(insetStatement);
			System.out.println("Complete");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
