package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static final String fileName = "db.properties";
	private static Connection connection = null;
	public static Connection getConnection() {
		String connString = null;
		try {
			connString = DBPropertyUtil.getPropertyString(fileName);
		}
		catch(IOException e) {
			System.out.println("Connection string creation failed");
			e.printStackTrace();
		}
		if(connString != null) {
			try {
				connection = DriverManager.getConnection(connString);
			}
			catch(SQLException e) {
				System.out.println("Error while establishisng DBConnection...");
				e.printStackTrace();
			}
		}
		return connection;
	}

}
