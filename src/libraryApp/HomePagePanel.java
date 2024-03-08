package libraryApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;

public class HomePagePanel extends JPanel {
	
	private final DefaultTableModel takes_table;
	private final JButton booksBtn = new JButton("Books");
	private final JButton authorsBtn = new JButton("Authors");
	private final JButton peopleBtn = new JButton("People");
	private final JButton takesBtn = new JButton("Takes");
	private final JButton refreshBtn = new JButton("Refresh");
	private final JLabel outputLbl  = new JLabel(" ");
	private final int totalMaxDay=30;
	public HomePagePanel(Connection conn){
		this.setLayout(null);
		this.setBackground(Color.CYAN);
		Object[] columnNames = {"Person","Book","Remain Days", "Phone Number"};
		takes_table = new DefaultTableModel(columnNames,0);
		JTable table= new JTable (takes_table);
		JScrollPane sp = new JScrollPane(table);
		add(sp);
		sp.setLocation(10, 10);
		sp.setSize(500,500);
		booksBtn.setLocation(550,100);
		booksBtn.setSize(100,30);
		booksBtn.setBackground(Color.ORANGE);
		add(booksBtn);
		authorsBtn.setLocation(550,170);
		authorsBtn.setSize(100,30);
		authorsBtn.setBackground(Color.ORANGE);
		add(authorsBtn);
		peopleBtn.setLocation(550,240);
		peopleBtn.setSize(100,30);
		peopleBtn.setBackground(Color.ORANGE);
		add(peopleBtn);
		takesBtn.setLocation(550,310);
		takesBtn.setSize(100,30);
		takesBtn.setBackground(Color.ORANGE);
		add(takesBtn);
		refreshBtn.setLocation(550,380);
		refreshBtn.setSize(100,30);
		refreshBtn.setBackground(Color.ORANGE);
		add(refreshBtn);
		outputLbl.setLocation(20,530);
		takesBtn.setSize(100,30);
		add(outputLbl);
		
		booksBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BooksView books = new BooksView(conn);
				
			}
		});
		authorsBtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						AuthorView authors = new AuthorView(conn);
						
					}
				});
		peopleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PeopleView people=new PeopleView(conn);
				
			}
		});
		takesBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TakesView takes = new TakesView(conn);
				
			}
		});
		refreshBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				show(conn);
				
			}
		});
	
		String query = "select p.name,p.surname,phone_number,b.name,taken_date\r\n"
				+ "from people as p,takes as t,books as b\r\n"
				+ "where t.person_id= p.person_id and b.book_id=t.book_id and brought_date is null Order by taken_date";
		
		PreparedStatement p;
    	takes_table.setRowCount(0);
    	LocalDate today = LocalDate.now(); 
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    	 try{
             p = conn.prepareStatement(query);
             ResultSet r;
             r = p.executeQuery();
 	   
        while(r.next()) {
        	String name= r.getString(1);    	 
	 		String surname = r.getString(2);
	 		String number = r.getString(3);
	 		String bookName= r.getString(4);
	 		String date = r.getString(5);
	 		LocalDate takenDate = LocalDate.parse(date, formatter);
	 		Object[] satir ={name+" "+surname,bookName,totalMaxDay- takenDate.until(today, ChronoUnit.DAYS), number};
	 		takes_table.addRow(satir);
        }
        p.close();
 	}catch(SQLException e){
 		e.printStackTrace();
 		outputLbl.setText(e.getMessage());
 	}		
	 
	}
	public void show(Connection conn) {
		String query = "select p.name,p.surname,phone_number,b.name,taken_date\r\n"
				+ "from people as p,takes as t,books as b\r\n"
				+ "where t.person_id= p.person_id and b.book_id=t.book_id and brought_date is null Order by taken_date";
		
		PreparedStatement p;
    	takes_table.setRowCount(0);
    	LocalDate today = LocalDate.now(); 
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    	 try{
             p = conn.prepareStatement(query);
             ResultSet r;
             r = p.executeQuery();
 	   
        while(r.next()) {
        	String name= r.getString(1);    	 
	 		String surname = r.getString(2);
	 		String number = r.getString(3);
	 		String bookName= r.getString(4);
	 		String date = r.getString(5);
	 		LocalDate takenDate = LocalDate.parse(date, formatter);
	 		Object[] satir ={name+" "+surname,bookName,totalMaxDay- takenDate.until(today, ChronoUnit.DAYS), number};
	 		takes_table.addRow(satir);
        }
        p.close();
 	}catch(SQLException e){
 		e.printStackTrace();
 		outputLbl.setText(e.getMessage());
 	}		
	}
	
}
