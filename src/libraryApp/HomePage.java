package libraryApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;

public class HomePage {
	JFrame frame;
	
	public HomePage() throws SQLException,IOException{
		initialize();
	}
	
	private void initialize() throws SQLException,IOException{
		String url,user,pass;
		 url = "jdbc:postgresql://localhost:5432/libraryDB";
	    user = "postgres";
        pass = "1234";
        Connection conn = DriverManager.getConnection(url, user,pass);
        frame = new HomePageFrame(conn);
        frame.setVisible(true);
	}
	
}
