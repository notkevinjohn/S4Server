package MySql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;


public class Config 
{
	public String  filename = "Database/Config.txt";
	public Vector<String> records;
	public String userName;
	public String password;
	
	public Config()
	{
	}
	public Vector<String> config()
	{
		BufferedReader reader;
		records = new Vector<String>();
		try 
		{
			reader = new BufferedReader(new FileReader(filename));
		
		
		
	    String line;
	    
	    while ((line = reader.readLine()) != null)       
	    {
	    	records.addElement(line);
	      							
	    }
	    reader.close();  
	    Parse(records);
	    
	    
	    
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		
		return records;
	}
		
	public Vector<String> Parse(Vector<String> records)
	{
		userName = records.get(0);
		password = records.get(1);
		
		if(password.startsWith("PASSWORD "))
		{
			password = password.substring(password.indexOf(" ")+1, password.length());
		}
		else
		{
			System.out.print("Config not formated correctly, Check PASSWORD");
		}
		
		if(userName.startsWith("USERNAME "))
		{
			userName = userName.substring(userName.indexOf(" ")+1, userName.length());
		}
		else
		{
			System.out.print("Config not formated correctly, Check USERNAME");
		}
		userName = userName.replaceAll("\\s","");
		password = password.replaceAll("\\s","");
		records.clear();
		records.addElement(userName);
		records.addElement(password);
		return records;
	}
	
}
