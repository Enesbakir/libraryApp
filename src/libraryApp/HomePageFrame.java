package libraryApp;

import java.sql.Connection;

import javax.swing.JFrame;

public class HomePageFrame extends JFrame {
	public HomePageFrame() {
	}
    public HomePageFrame (Connection conn) {

	setTitle("Library");
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setSize(1000, 750);       
	setLocation(200, 200); 
	getContentPane().add(new HomePagePanel(conn));		
    }
}
