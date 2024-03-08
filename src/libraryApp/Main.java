package libraryApp;

import java.awt.EventQueue;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.Toolkit;
import javax.swing.*;

public class Main {
	
	public static void main(String[] args) throws SQLException,IOException{
		String kAdi = "postgres";
		String sifre = "1234";
		String localhost = "5432";
		String vtAdi = "libraryDB";
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage window = new HomePage();
					window.frame.setResizable(false);
					window.frame.setBounds(400, 50, 700, 650);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}
	
}
