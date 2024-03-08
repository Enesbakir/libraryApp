package libraryApp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BooksView {
	
	 private final JButton addBtn = new JButton("Add Book");
	 private final JButton deleteBtn = new JButton("Delete Book");
	 private final JButton showBtn = new JButton("Show All Books");
	 private DefaultTableModel booksTableModel;
	 private JTextField textField;
	 private JTextField textField2;
	 private JTextField authorField;
	 private JTextField dateField;
	 private JFrame frame;
	 private JFrame books;
	 private JTable bookstbl;
	 private int row=-1;
	 public BooksView(Connection conn) {
		initialize(conn);
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addBook(conn);	
			}
		});
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//deleteBook(conn);
				deleteBookWithClick(conn);
			}
		});
	}
	private void initialize(Connection conn) {
		books = new JFrame();
		books.setBackground(Color.CYAN);
		books.setBounds(500,500,603, 539);
		JPanel booksPanel= new JPanel();
		booksPanel.setBackground(Color.CYAN);
		books.getContentPane().add(booksPanel);
		books.setBounds(10,11,567,478);
		books.setVisible(true);
		books.setTitle("Books");
		addBtn.setBackground(Color.orange);
		deleteBtn.setBackground(Color.orange);
		booksPanel.add(addBtn);
		booksPanel.add(deleteBtn);
		Object[] columnNames = {"id","Name", "Author Name"};
		booksTableModel = new DefaultTableModel(columnNames, 0);
        bookstbl = new JTable(booksTableModel);
    	JScrollPane booksPane = new JScrollPane(bookstbl);
    	booksPanel.add(booksPane);
    	show(conn);
	}
	private void addBook(Connection conn) {
	 	frame = new JFrame();
	 	AuthorView author = new AuthorView(conn);
        frame.setBounds(100, 100, 300, 300);
        frame.getContentPane().setLayout(null);
	 	textField = new JTextField();
        textField.setBounds(128, 28, 86, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        JLabel lblName = new JLabel("Name");
        frame.getContentPane().add(lblName);
        lblName.setBounds(65, 31, 70, 14);
        textField2 = new JTextField();
        textField2.setBounds(128, 70, 86, 20);
        frame.getContentPane().add(textField2);
        textField2.setColumns(10);
        JLabel lblName2 = new JLabel("Author Id");
        lblName2.setBounds(65, 70, 70, 14);
        frame.getContentPane().add(lblName2);
        frame.setVisible(true);
        JButton getButton= new JButton("Enter");
        getButton.setBounds(35, 120, 70, 25);
        LocalDate localDate = LocalDate.now();
        frame.getContentPane().add(getButton);
        getButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("") || textField2.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Fields can't be empty");
				}else {
					String query= "insert into books values (nextval('books_seq'),'"+textField2.getText()+ "','"+localDate+"','"+textField.getText()+"')";            
					Statement s = null;
			        try {
			            s = conn.createStatement();
			            s.executeUpdate(query);
			            conn.setAutoCommit(false);
			            conn.commit();
			            s.close();
			            author.close();
			            frame.setVisible(false);
				}catch(SQLException exception){
					JOptionPane.showMessageDialog(frame, "Unvalid Id");	
				}  
			        show(conn);
				}
			}
		});
 }
	/*private void deleteBook(Connection conn) {
	 	frame = new JFrame();
        frame.setBounds(100, 100, 300, 300);
        frame.getContentPane().setLayout(null);
	 	textField = new JTextField();
        textField.setBounds(128, 28, 86, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        JLabel lblName = new JLabel("Enter ID");
        frame.getContentPane().add(lblName);
        lblName.setBounds(65, 31, 70, 14);
        frame.setVisible(true);
        JButton getButton= new JButton("Delete");
        getButton.setBounds(35, 120, 70, 25);
        frame.getContentPane().add(getButton);
	        getButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String query= "delete from books where book_id='"+textField.getText()+"'";         
					Statement s = null;
			        try {
			            s = conn.createStatement();
			            s.executeUpdate(query);
			            conn.setAutoCommit(false);
			            conn.commit();
			            s.close();
				}catch(SQLException exception){
					JOptionPane.showMessageDialog(frame, "Author has books.First remove author's books");
					exception.printStackTrace();
				}
			        frame.setVisible(false);
			        show(conn);
				}
			});
	 }*/
	private void getTableRow() {
		bookstbl.addMouseListener(new java.awt.event.MouseAdapter() {
 		    @Override
 		    public void mouseClicked(java.awt.event.MouseEvent evt) {
 		        row = bookstbl.getSelectedRow();
 		    }
 		});
	}
	private void deleteBookWithClick(Connection conn) {
		getTableRow();
		if(row==-1) {
			JOptionPane.showMessageDialog(frame,
					"Choose one Item",
					"WARNING.",
				    JOptionPane.WARNING_MESSAGE);
		}else {
			String query="select * from takes where book_id='"+booksTableModel.getValueAt(row, 0)+"' and brought_date is null";
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
	        }catch(SQLException e){
	 			e.printStackTrace();
	 			return;
		 	}
        	
			int dialogButton = JOptionPane.YES_NO_OPTION;
	        int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure to delete "+booksTableModel.getValueAt(row, 1),"Warning",dialogButton);
	        if(dialogResult == JOptionPane.YES_OPTION){
		        query= "delete from books where book_id='"+booksTableModel.getValueAt(row, 0)+"'";
	        	Statement s = null;
		        try {
		            s = conn.createStatement();
		            s.executeUpdate(query);
		            conn.setAutoCommit(false);
		            conn.commit();
		            s.close();
			}catch(SQLException exception){
				JOptionPane.showMessageDialog(frame, "Book has given a person");
				exception.printStackTrace();
			}
		        
		        show(conn);
	        }
		}
		
	}
	private void show(Connection conn) {
		 String query ="select book_id,books.name,authors.name,authors.surname\r\n"
		 		+ "from authors,books\r\n"
		 		+ "where author_id =authors.id  ";
		 PreparedStatement p;
		 booksTableModel.setRowCount(0);
	     try{
             p = conn.prepareStatement(query);
             ResultSet r;
             r = p.executeQuery();
             if (!r.next ()){
	         }else{
	            while(r.next()) {
		        	String id= r.getString(1);    	 
			 		String bookName = r.getString(2);
			 		String authorName = r.getString(3);
			 		String authorSurname= r.getString(4)+" "+authorName;
			 		Object[] satir ={id, bookName, authorSurname};
			 		booksTableModel.addRow(satir);
		        }
			    p.close();
		     }
	 	}catch(SQLException e){
	 		JOptionPane.showMessageDialog(frame, e.getMessage());
	 	}		 
	 }
	/*private void updateBook(Connection conn,int funcParameter) {
		frame = new JFrame();
	 	AuthorView author = new AuthorView(conn);
        frame.setBounds(100, 100, 300, 300);
        frame.getContentPane().setLayout(null);
	 	textField = new JTextField();
        textField.setBounds(128, 28, 86, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        JLabel lblName = new JLabel("Name");
        frame.getContentPane().add(lblName);
        lblName.setBounds(65, 31, 70, 14);
        authorField = new JTextField();
        authorField.setBounds(128, 70, 86, 20);
        frame.getContentPane().add(authorField);
        authorField.setColumns(10);
        JLabel lblName2 = new JLabel("Author Id");
        lblName2.setBounds(65, 70, 70, 14);
        frame.getContentPane().add(lblName2);
        frame.setVisible(true);
        JButton getButton= new JButton("Enter");
        getButton.setBounds(35, 120, 70, 25);
        LocalDate localDate = LocalDate.now();
        JTextField idField = new JTextField();
        if(funcParameter==0) {
        	dateField = new JTextField();
            dateField.setBounds(128, 28, 86, 20);
            frame.getContentPane().add(textField);
            dateField.setColumns(10);
            JLabel dateLbl = new JLabel("Date");
            frame.getContentPane().add(dateLbl);
            dateLbl.setBounds(65, 31, 70, 14);
        	bookstbl.addMouseListener(new java.awt.event.MouseAdapter() {
	 		    @Override
	 		    public void mouseClicked(java.awt.event.MouseEvent evt) {
	 		        int row =bookstbl.getSelectedRow();
	 		        idField.setText(booksTableModel.getValueAt(row, 0).toString());
	 		        textField.setText(booksTableModel.getValueAt(row, 1).toString());
	 		        authorField.setText(booksTableModel.getValueAt(row, 2).toString());
	 		        dateField.setText(booksTableModel.getValueAt(row, 3).toString());  
	 		    }
	 		});
        }
        frame.getContentPane().add(getButton);
        getButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("") || authorField.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Fields can't be empty");
				}else {
					String query= "insert into books values (nextval('books_seq'),'"+textField2.getText()+ "','"+localDate+"','"+textField.getText()+"')";            
					Statement s = null;
			        try {
			            s = conn.createStatement();
			            s.executeUpdate(query);
			            conn.setAutoCommit(false);
			            conn.commit();
			            s.close();
			            author.close();
			            frame.setVisible(false);
				}catch(SQLException exception){
					JOptionPane.showMessageDialog(frame, "There is no author has this id");	
				}  
			        show(conn);
				}
			}
		});
	}*/
	public void close() {
		 books.setVisible(false);
	 }
	
	
}
