package com.example.sample_member_servelt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public static Connection getConnection() throws SQLException, ClassNotFoundException  {

		Connection conn = null;		
	
		String url = "jdbc:mariadb://localhost:3306/sample_jsp_servlet_member_board";
		String user = "root";
		String password = "3432";

		Class.forName("org.mariadb.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);		
		
		return conn;
	}	
}
