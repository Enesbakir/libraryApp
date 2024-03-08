package libraryApp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AuthorView {
	
	private final JButton addBtn = new JButton("Add Author");
	 private final JButton deleteBtn = new JButton("Delete Author");
	 private final JButton updateBtn = new JButton("Update Author");
	 private JTextField textField;
	 private JTextField textField2;
	 private final JLabel outputLbl  = new JLabel(" ");
	 private JFrame frame;
	 private JFrame authors;
	 private JTable authorstbl;
	 private int row=-1;
	 private DefaultTableModel authorsTableModel;
	 public AuthorView(Connection conn) {
		 initialize(conn);
		 addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addAuthor(conn);
			}
		});
		 deleteBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//deleteAuthor(conn);
					deleteAuthorWithClick(conn);
				}
			});
		 updateBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateAuthor(conn, 0);
				
			}
		});
	 }
	 private void initialize(Connection conn) {
			authors = new JFrame();
			authors.setBounds(100,100,603, 539);
			JPanel authorsPanel= new JPanel();
			authorsPanel.setBackground(Color.CYAN);
			authors.getContentPane().add(authorsPanel);
			authors.setBounds(10,11,567,478);
			authors.setVisible(true);
			authors.setTitle("authors");
			addBtn.setBackground(Color.orange);
			deleteBtn.setBackground(Color.orange);
			updateBtn.setBackground(Color.orange);
			authorsPanel.add(addBtn);
			authorsPanel.add(deleteBtn);
			authorsPanel.add(updateBtn);
			authorsPanel.add(outputLbl);
			Object[] columnNames = {"id", "Name","Surname"};
			authorsTableModel = new DefaultTableModel(columnNames, 0);
	        authorstbl = new JTable(authorsTableModel);
	        JScrollPane authorsPane = new JScrollPane(authorstbl);
	    	authorsPanel.add(authorsPane);
	    	show(conn);
		}
	 private void addAuthor(Connection conn) {
		 	frame = new JFrame();
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
	        JLabel lblName2 = new JLabel("Surname");
	        lblName2.setBounds(65, 70, 70, 14);
	        frame.getContentPane().add(lblName2);
	        frame.setVisible(true);
	        JButton getButton= new JButton("Enter");
	        getButton.setBounds(35, 120, 70, 25);
	        frame.getContentPane().add(getButton);
	        getButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(textField.getText().equals("") || textField2.getText().equals("")) {
						outputLbl.setText("Name and Surname field can't be empty");
					}else {
						String query= "insert into authors values (nextval('author_seq'),'"+textField.getText()+ "','"+textField2.getText()+"')";            
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
					}
					
				}
			});
	 }
	 private void updateAuthor(Connection conn, int funcParameter) {
		 	frame = new JFrame();
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
		    JLabel lblName2 = new JLabel("Surname");
		    lblName2.setBounds(65, 70, 70, 14);
		    frame.getContentPane().add(lblName2);
		    frame.setVisible(true);
		    JButton getButton= new JButton("Enter");
		    getButton.setBounds(35, 120, 70, 25);
		    frame.getContentPane().add(getButton);
		    JTextField idField = new JTextField();
		    if(funcParameter==0) {
	        	authorstbl.addMouseListener(new java.awt.event.MouseAdapter() {
		 		    @Override
		 		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		 		        int row = authorstbl.getSelectedRow();
		 		        idField.setText(authorsTableModel.getValueAt(row, 0).toString());
		 		        textField.setText(authorsTableModel.getValueAt(row, 1).toString());
		 		        textField2.setText(authorsTableModel.getValueAt(row, 2).toString());
		 		    }
		 		});
	        }
		    
	        getButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String query;
					if(textField.getText().equals("") || textField2.getText().equals("")) {
						outputLbl.setText("Name and Surname field can't be empty");
					}else {
						if(funcParameter==1) {
							query= "insert into authors values (nextval('author_seq'),'"+textField.getText()+ "','"+textField2.getText()+"')";
						}else {
							query = "update authors set name='"+textField.getText()+"',surname='"+textField2.getText()+"' where id='"+idField.getText()+"'";
						}
						            
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
					}
					
				}
			});
	 }
	 /*private void deleteAuthor(Connection conn) {
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
					String query= "delete from authors where id='"+textField.getText()+"'";         
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
	 private void deleteAuthorWithClick(Connection conn) {
		 authorstbl.addMouseListener(new java.awt.event.MouseAdapter() {
	 		    @Override
	 		    public void mouseClicked(java.awt.event.MouseEvent evt) {
	 		        row = authorstbl.getSelectedRow();
	 		    }
	 		});
		 if(row==-1) {
				JOptionPane.showMessageDialog(frame,
						"Choose one Item",
						"WARNING.",
					    JOptionPane.WARNING_MESSAGE);
			}else {
				String query="select * from books where author_id='"+authorsTableModel.getValueAt(row, 0)+"'";
	        	PreparedStatement p;
	        	try{
		             p = conn.prepareStatement(query);
		             ResultSet r;
		             r = p.executeQuery();
		        if(r.next()) {
		        	System.out.println("sa");
		        	JOptionPane.showMessageDialog(frame, "Author has book(s).");
		        	return;
		        }
		        }catch(SQLException e){
		 			e.printStackTrace();
		 			return;
			 	}
	        	
				int dialogButton = JOptionPane.YES_NO_OPTION;
		        int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure to delete "+authorsTableModel.getValueAt(row, 1),"Warning",dialogButton);
		        if(dialogResult == JOptionPane.YES_OPTION){
			        query= "delete from authors where id='"+authorsTableModel.getValueAt(row, 0)+"'";
		        	Statement s = null;
			        try {
			            s = conn.createStatement();
			            s.executeUpdate(query);
			            conn.setAutoCommit(false);
			            conn.commit();
			            s.close();
				}catch(SQLException exception){
					JOptionPane.showMessageDialog(frame, "Author has book(s).");
					exception.printStackTrace();
				}
			        
			        show(conn);
		        }
			}
	 }
	 private void show(Connection conn) {
		 String query ="Select * from authors order by CAST (id as int)";
	    	PreparedStatement p;
	    	authorsTableModel.setRowCount(0);
	        try{
	             p = conn.prepareStatement(query);
	             ResultSet r;
	             r = p.executeQuery();
	        while(r.next()) {
	        	String id= r.getString(1);    	 
		 		String name = r.getString(2);
		 		String surname = r.getString(3);
		 		Object[] satir ={id, surname, name};
		 		authorsTableModel.addRow(satir);
	        }
	        p.close();
	             
	 	}catch(SQLException e){
	 			outputLbl.setText(e.getMessage());
	             
	 	}		  
	 }
	 public void close() {
		 authors.setVisible(false);
	 }
}

