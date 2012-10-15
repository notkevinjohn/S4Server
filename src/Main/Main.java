package Main;

import MySql.InsertDataIntoDatabase;

//import MySql.CreateTable;

public class Main 
{
	
	public static void main(String[] args) 
	{
		//new CreateTable();
		//new Controller();
		InsertDataIntoDatabase da = new InsertDataIntoDatabase();
		da.insertDataIntoDatabase("SSU-01", "$GGPGA,234,234,2*54", "@,!,44564,&,3345");
	}
}
