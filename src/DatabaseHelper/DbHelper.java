package DatabaseHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper {
	
	public static Connection connectDatabase() throws SQLException {
		String url,user,pass;
		url = "jdbc:postgresql://localhost:5432/libraryDB";
		user = "postgres";
		pass = "1234";
		return DriverManager.getConnection(url, user,pass);
	}
}
