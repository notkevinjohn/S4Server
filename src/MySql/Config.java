package MySql;

import java.io.BufferedReader;
import java.io.FileReader;


public class Config 
{
	public String  filename = "Config.txt";
	public void Config()
	{
		BufferedReader reader = new BufferedReader(new FileReader(filename)); 
	    String line;
	    
	    while ((line = reader.readLine()) != null)       
	    {
	      							
	    }
	    reader.close();  
	}
}
