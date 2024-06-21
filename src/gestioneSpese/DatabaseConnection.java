package gestioneSpese;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static String url = "jdbc:mysql://localhost:3306/gestione_spese";
	private static String user;
	private static String password;
	
	public static void setCredentials(String user, String password) {
		DatabaseConnection.user = user;
		DatabaseConnection.password = password;
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, user, password);
	}
}
