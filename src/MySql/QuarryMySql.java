package MySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import Data.Command;
import Data.PayloadData;
import Data.PayloadRX;
import Events.CompleteTerminalTXEvent;
import Events.CompleteTerminalTXEventListener;
import IOStream.PayloadObjectTX;
import Main.Controller;
import Parser.MySqlData;
import Parser.Parser;
import SocketHandelers.TerminalDataController;

public class QuarryMySql extends Thread
{
	private long lastReciveTimeStamp;
	private String payloadDeviceName;
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
	
	public QuarryMySql(Controller controller, long lastReciveTimeStamp, String payloadDeviceName)
	{
		this.payloadDeviceName = payloadDeviceName;
		this.lastReciveTimeStamp = lastReciveTimeStamp;
		
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
	
	// Thanks To Ben Cunningham for the help with MySQL
	
	public void QueryDatabase()
	{
		PayloadRX payloadRX = new PayloadRX();
		payloadRX.payloadRX = new Vector<PayloadData>();
		
		
		String Script = "SELECT * FROM " +
				tableName +
				" WHERE " +
				"Payload_Name = '"+
				payloadDeviceName +
				"' AND System_Time >= ";
				Script = Script += lastReciveTimeStamp;
				
		//System.out.println(Script);
		
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(Script);
			
			if(resultSet.next())
//			while(resultSet.next())
			{
				PayloadData payloadData = new PayloadData();
				
				payloadData.timeStamp = Long.parseLong(resultSet.getString(1));
				payloadData.payloadName = resultSet.getString(2);
//				payloadData.gpsData = resultSet.getString(3);
//				payloadData.scienceData = resultSet.getString(4);
//				payloadData.lon = Double.parseDouble(resultSet.getString(7));
//				payloadData.lat = Double.parseDouble(resultSet.getString(8));
//				payloadData.alt = Double.parseDouble(resultSet.getString(9));
//				payloadData.Sen_1_Key = resultSet.getString(10);
//				
//				if(!resultSet.getString(11).isEmpty())
//				{
//					payloadData.Sen_1_Value = Double.parseDouble(resultSet.getString(11));
//				}
//				payloadData.Sen_2_Key = resultSet.getString(12);
//				
//				if(!resultSet.getString(13).isEmpty())
//				{
//					payloadData.Sen_2_Value = Double.parseDouble(resultSet.getString(13));
//				}
//				payloadData.Sen_3_Key = resultSet.getString(14);
//				
//				if(!resultSet.getString(15).isEmpty())
//				{
//					payloadData.Sen_3_Value = Double.parseDouble(resultSet.getString(15));
//				}
//				payloadData.Sen_4_Key = resultSet.getString(16);
//				
//				if(!resultSet.getString(17).isEmpty())
//				{
//				payloadData.Sen_4_Value = Double.parseDouble(resultSet.getString(17));
//				}
//				payloadData.Sen_5_Key = resultSet.getString(18);
//				
//				if(!resultSet.getString(19).isEmpty())
//				{
//					payloadData.Sen_5_Value = Double.parseDouble(resultSet.getString(19));
//				}
//				payloadData.Sen_6_Key = resultSet.getString(20);
//				
//				if(!resultSet.getString(21).isEmpty())
//				{
//					payloadData.Sen_6_Value = Double.parseDouble(resultSet.getString(21));
//				}
//				payloadData.Sen_7_Key = resultSet.getString(22);
//				
//				if(!resultSet.getString(23).isEmpty())
//				{
//					payloadData.Sen_7_Value = Double.parseDouble(resultSet.getString(23));
//				}
//				payloadData.Sen_8_Key = resultSet.getString(24);
//				
//				if(!resultSet.getString(25).isEmpty())
//				{
//					payloadData.Sen_8_Value = Double.parseDouble(resultSet.getString(25));
//				}
//				payloadData.Sen_9_Key = resultSet.getString(26);
//				
//				if(!resultSet.getString(27).isEmpty())
//				{
//					payloadData.Sen_9_Value = Double.parseDouble(resultSet.getString(27));
//				}
//				payloadData.Sen_10_Key = resultSet.getString(28);
//				
//				if(!resultSet.getString(29).isEmpty())
//				{
//					payloadData.Sen_10_Value = Double.parseDouble(resultSet.getString(29));
//				}
				
				payloadRX.payloadRX.add(payloadData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
                if (statement != null) {
                	statement.close();
                }
                if (connection != null) {
                	connection.close();
                }

            } catch (SQLException ex) 
            {
            	ex.printStackTrace();
            }
            
         CompleteTerminalTXEvent complete = new CompleteTerminalTXEvent(this, payloadRX);
         Object[] listeners = Controller.listenerList.getListenerList(); 
   		 for (int i=0; i<listeners.length; i+=2) 
   		 {
              if (listeners[i]==CompleteTerminalTXEventListener.class)
              {
                  ((CompleteTerminalTXEventListener)listeners[i+1]).CompleteTerminalTXEventHandler(complete);
              }
   		 }
   
		
		 

   		 
	}
	
}
