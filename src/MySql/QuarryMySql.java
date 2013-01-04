package MySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import Data.Command;
import IOStream.PayloadObjectTX;
import Main.Controller;
import Parser.MySqlData;
import Parser.Parser;

public class QuarryMySql extends Thread
{
	private long lastReciveTimeStamp;
	private String payloadDeviceName;
	private Command command;
	//private Controller controller;
	
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
	
	public QuarryMySql(long lastReciveTimeStamp, String payloadDeviceName)
	{
		this.payloadDeviceName = payloadDeviceName;
		this.lastReciveTimeStamp = lastReciveTimeStamp;
		
		command = new Command();
		this.start();
	}
	public void run() 
	{
		ConnectToDatabase();
		
	}
	
	public void ConnectToDatabase()
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
		QueryDatabase();
	}
	public void QueryDatabase()
	{
		String Script = "SELECT*FROM" +
				tableName +
				"WHERE" +
				"Payload_Name='"+
				payloadDeviceName +
				"' WHERE System_Time >";
				Script = Script += lastReciveTimeStamp;
				
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT GPS_Data FROM S4PayloadData");
			
			
			System.out.println(resultSet.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
