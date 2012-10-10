package MySql;
import java.sql.*;
public class CreateTable 
{

	/**
	 * @param args
	 */	
	public String databaseURL = "jdbc:mysql://localhost:3306";
	public String databaseName = "/S4";
	public String driver = "com.mysql.jdbc.Driver";
	public String userName = "root";
	public String password = "nasaepo";
	public Connection connection = null;
	public String tableName = "S4PayloadData";
	public CreateTable()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(databaseURL + databaseName, userName, password);
			System.out.println("Connected to Database");
					
			Statement stmt =  connection.createStatement();
			String table = "CREATE TABLE "+ tableName +"(System_Time long, Payload_Name varchar(20), " +
					"GPS_Data varchar(100), Sensor_Data varchar(100), GPS_Time varchar(10), " +
					"GPS_Fix varchar(10), GPS_Lon double, GPS_Lat double, GPS_Alt double, " +
					"Sen_1_Key varchar(20), Sen_1_Value double, Sen_2_Key varchar(20), " +
					"Sen_2_Value double, Sen_3_Key varchar(20), Sen_3_Value double, " +
					"Sen_4_Key varchar(20), Sen_4_Value double, Sen_5_Key varchar(20), " +
					"Sen_5_Value double, Sen_6_Key varchar(20), Sen_6_Value double, " +
					"Sen_7_Key varchar(20), Sen_7_Value double, Sen_8_Key varchar(20), " +
					"Sen_8_Value double, Sen_9_Key varchar(20), Sen_9_Value double, " +
					"Sen_10_Key varchar(20), Sen_10_Value double)";
			
			stmt.executeUpdate(table);
				
			stmt.close();
			connection.close();
			System.out.println("Table Created!");		
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
}
