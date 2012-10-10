package MySql;

import java.sql.*;

public class MySqlDatabase 
{
	public String databaseURL = "jdbc:mysql://localhost:3306";
	public String databaseName = "/S4";
	public String driver = "com.mysql.jdbc.Driver";
	public String userName = "root";
	public String password = "nasaepo";
	public Connection connection = null;
	
	public MySqlDatabase()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(databaseURL + databaseName, userName, password);
			System.out.println("Connected to Database");
			
			
//			Statement stmt2 = connection.createStatement();
//			String insetStatement = "INSERT INTO PayloadData VALUES('60000','SSU-01','$GGPGA,234,234,2*54','@,!,44564,&,3345');";
//			stmt2.executeUpdate(insetStatement);
//			System.out.println("Complete");

			
			Statement stmt3 = connection.createStatement();
			
			ResultSet rs = stmt3.executeQuery("SELECT GPS_Data FROM PayloadData");
			while(rs.next())
			{
				System.out.print(rs.getString(1) + ", ");
			}
			
			rs.close();
			stmt3.close();
			connection.close();
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	

}
