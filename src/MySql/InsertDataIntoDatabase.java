package MySql;

import java.sql.Connection;
import java.sql.DriverManager;

import com.mysql.jdbc.Statement;

public class InsertDataIntoDatabase 
{
	public String databaseURL = "jdbc:mysql://localhost:3306";
	public String databaseName = "/S4";
	public String driver = "com.mysql.jdbc.Driver";
	public String userName = "root";
	public String password = "nasaepo";
	public Connection connection = null;
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
		
	}
	
	public void insertDataIntoDatabase(String payloadName, String gpsData, String sensorData)
	{
		Statement stmt2 = connection.createStatement();
		String insetStatement = "INSERT INTO PayloadData VALUES('60000','SSU-01','$GGPGA,234,234,2*54','@,!,44564,&,3345');";
		stmt2.executeUpdate(insetStatement);
		System.out.println("Complete");
		
	}
	

}
