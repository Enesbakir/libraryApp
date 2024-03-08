package libraryApp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TakesView {
	private final JButton giveBtn = new JButton("Give Book");
	private final JButton getBookBtn = new JButton("Get Book");
	private JTextField textField;
	private JTextField textField2;
	private final JLabel outputLbl  = new JLabel(" ");
	private JFrame frame;
	private JFrame takes;
	private DefaultTableModel takesTableModel;
	public TakesView(Connection conn) {
		 initialize(conn);
		 giveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				giveBook(conn);
			}
		});
		 getBookBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getBook(conn);
				}
			});
	 }
	private void initialize(Connection conn) {
		takes = new JFrame();
		takes.setBounds(100,100,603, 539);
		JPanel takesPanel= new JPanel();
		takesPanel.setBackground(Color.CYAN);
		takes.getContentPane().add(takesPanel);
		takes.setBounds(10,11,567,478);
		takes.setVisible(true);
		takes.setTitle("takes");
		getBookBtn.setBackground(Color.orange);
		giveBtn.setBackground(Color.orange);
		takesPanel.add(getBookBtn);
		takesPanel.add(giveBtn);
		takesPanel.add(outputLbl);
		Object[] columnNames = {"id", "Book Name","Person Name","Taken Date","Brought Date"};
		takesTableModel = new DefaultTableModel(columnNames, 0);
        JTable takestbl = new JTable(takesTableModel);
        JScrollPane takesPane = new JScrollPane(takestbl);
    	takesPanel.add(takesPane);
    	show(conn);
	}
	 private void giveBook(Connection conn) {
		 	BooksView book = new BooksView(conn);
		 	PeopleView person = new PeopleView(conn);
		 	frame = new JFrame();
	        frame.setBounds(100, 100, 300, 300);
	        frame.getContentPane().setLayout(null);
		 	textField = new JTextField();
	        textField.setBounds(128, 28, 86, 20);
	        frame.getContentPane().add(textField);
	        textField.setColumns(10);
	        JLabel lblName = new JLabel("Person ID");
	        frame.getContentPane().add(lblName);
	        lblName.setBounds(65, 31, 70, 14);
	        textField2 = new JTextField();
	        textField2.setBounds(128, 70, 86, 20);
	        frame.getContentPane().add(textField2);
	        textField2.setColumns(10);
	        JLabel lblName2 = new JLabel("Book ID");
	        lblName2.setBounds(65, 70, 70, 14);
	        frame.getContentPane().add(lblName2);
	        frame.setVisible(true);
	        JButton getButton= new JButton("Enter");
	        getButton.setBounds(35, 120, 70, 25);
	        frame.getContentPane().add(getButton);
	        LocalDate localDate = LocalDate.now();
	        
	        getButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(textField.getText().equals("") || textField2.getText().equals("")) {
						JOptionPane.showMessageDialog(frame, "Fields can't be empty");
					}else {
						
						String query="select * from takes where book_id='"+textField2.getText()+"' and brought_date is null";
			        	PreparedStatement p;
			        	try{
				             p = conn.prepareStatement(query);
				             ResultSet r;
				             r = p.executeQuery();
				        if(r.next()) {
				        	System.out.println("sa");
				        	JOptionPane.showMessageDialog(frame, "Book has given a person");
				        	return;
				        }
				        }catch(SQLException exception){
				 			exception.printStackTrace();
				 			return;
					 	}
			        	
						query= "insert into takes values (nextval('takes_seq'),'"+textField2.getText()+ "','"+textField.getText()+"','"+localDate +"',null)";            
						Statement s = null;
				        try {
				            s = conn.createStatement();
				            s.executeUpdate(query);
				            conn.setAutoCommit(false);
				            conn.commit();
				            s.close();
					}catch(SQLException exception){
						exception.printStackTrace();
					}
				        frame.setVisible(false);
				        show(conn);
				        outputLbl.setText("");
				        book.close();
				        person.close();
					}
					
				}
			});
	 }
	 private void getBook(Connection conn) {
		 	BooksView book = new BooksView(conn);
		 	frame = new JFrame();
	        frame.setBounds(100, 100, 300, 300);
	        frame.getContentPane().setLayout(null);
	        textField2 = new JTextField();
	        textField2.setBounds(128, 70, 86, 20);
	        frame.getContentPane().add(textField2);
	        textField2.setColumns(10);
	        JLabel lblName2 = new JLabel("Book ID");
	        lblName2.setBounds(65, 70, 70, 14);
	        frame.getContentPane().add(lblName2);
	        frame.setVisible(true);
	        JButton getButton= new JButton("Enter");
	        getButton.setBounds(35, 120, 70, 25);
	        frame.getContentPane().add(getButton);
	        LocalDate localDate = LocalDate.now(); 
	        getButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(textField2.getText().equals("")) {
						JOptionPane.showMessageDialog(frame, "Fields can't be empty");
					}else {
						String query= "update takes set brought_date ='"+localDate+"' where book_id='"+textField2.getText()+"'";            
						System.out.println(query);
						Statement s = null;
				        try {
				            s = conn.createStatement();
				            s.executeUpdate(query);
				            conn.setAutoCommit(false);
				            conn.commit();
				            s.close();
					}catch(SQLException exception){
						exception.printStackTrace();
					}
				        frame.setVisible(false);
				        show(conn);
				        outputLbl.setText("");
				        book.close();
					}
					
				}
			});
	 }
	 private void show(Connection conn) {
		 String query ="select takes.id,books.name,people.name,people.surname,taken_date,brought_date \r\n"
		 		+ "from takes,books,people\r\n"
		 		+ "where books.book_id =takes.book_id and takes.person_id =people.person_id";
	    	PreparedStatement p;
	    	takesTableModel.setRowCount(0);
	        try{
	             p = conn.prepareStatement(query);
	             ResultSet r;
	             r = p.executeQuery();
	        while(r.next()) {
	        	String id= r.getString(1);    	 
		 		String personName = r.getString(3);
		 		String bookName = r.getString(2);
		 		String personSurname= r.getString(4);
		 		String takenDate = r.getString(5);
		 		String broughtDate= r.getString(6);
		 		Object[] satir ={id, bookName, personName+" "+personSurname,takenDate,broughtDate};
		 		takesTableModel.addRow(satir);
	        }
	        p.close();
	 	}catch(SQLException e){
	 		e.printStackTrace();
	 			outputLbl.setText(e.getMessage());
	 	}		 
	 }
	 public void close() {
		 takes.setVisible(false);
	 }
}
