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
			
			/*******Delete existing table Warning: all information in the table will be lost forever!!! ************/
			/*******Comment out this when not using !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!************/
			
//			Statement stmt =  connection.createStatement();
//			String table = "DROP TABLE " + tableName;
//					
//			stmt.executeUpdate(table);
//			
//			stmt.close();
//			connection.close();
//			System.out.println("Table Deleted!");	
			/*******Delete existing table Warning: all information in the table will be lost forever!!! ************/
			
			
			Statement stmt =  connection.createStatement();
			String table = "CREATE TABLE "+ tableName +"(System_Time long, Payload_Name varchar(20), " +
					"GPS_Data varchar(100), Sensor_Data varchar(100), GPS_Time varchar(10), " +
					"GPS_Fix varchar(10), GPS_Lon varchar(20), GPS_Lat varchar(20), GPS_Alt varchar(20), " +
					"Sen_1_Key varchar(20), Sen_1_Value varchar(20), Sen_2_Key varchar(20), " +
					"Sen_2_Value varchar(20), Sen_3_Key varchar(20), Sen_3_Value varchar(20), " +
					"Sen_4_Key varchar(20), Sen_4_Value varchar(20), Sen_5_Key varchar(20), " +
					"Sen_5_Value varchar(20), Sen_6_Key varchar(20), Sen_6_Value varchar(20), " +
					"Sen_7_Key varchar(20), Sen_7_Value varchar(20), Sen_8_Key varchar(20), " +
					"Sen_8_Value varchar(20), Sen_9_Key varchar(20), Sen_9_Value varchar(20), " +
					"Sen_10_Key varchar(20), Sen_10_Value varchar(20), Brodcast_RSSI varchar(10), Brodcast_Bat varchar(10))";
			
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
