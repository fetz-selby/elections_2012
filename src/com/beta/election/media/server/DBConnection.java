package com.beta.election.media.server;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

public class DBConnection {
	private static Connection con;
	//private String url = "jdbc:mysql://23.21.230.88:3306/election";
	private String url = "jdbc:mysql://localhost:3306/election2";
	//private String url = "jdbc:mysql://localhost:10001/dce6b03937c4745a5a893ce1ec1157847";

	private static DBConnection dbc = new DBConnection();
	
	private DBConnection(){
		//String services = java.lang.System.getenv("VCAP_SERVICES");
		//Object service_obj = JSON.decode(services)
		//Array<Object> mysql_service = service_obj.get('mysql-5.1')
		//Object mysql_connection_details = mysql_service[0].get('credentials')
		
		
		//mysql_connection_details.get('name') 		//app database name
		//mysql_connection_details.get('username') 	// app database username
		//mysql_connection_details.get('password') 	//app authentication password
		//mysql_connection_details.get('host') 		// mysql server hostname
		//mysql_connection_details.get('port') 		// mysql connection port
		
		//"jdbc:mysql://%s:%d/%s".sprintf(mysql_connection_details.get('host'),mysql_connection_details.get('port'),mysql_connection_details.get('name'))
	}
	
	public static Connection getConnection(){
		dbc.establishConnection();
		return con;
	}
	
	private void establishConnection(){
		try{
            Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			//con = (Connection)DriverManager.getConnection(url, "uPP7VRFdOvIyx", "pprW9TBz5i84q");
			con = (Connection)DriverManager.getConnection(url, "root", "");

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
