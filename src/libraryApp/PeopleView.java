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

import Models.People;


public class PeopleView {
	 private final JButton addBtn = new JButton("Add Person");
	 private final JButton deleteBtn = new JButton("Delete Person");
	 private final JButton updateBtn = new JButton("Update Person Info");/////////////////////
	 private JTextField textField;
	 private JTextField textField2;
	 private final JLabel outputLbl  = new JLabel(" ");
	 private JFrame frame;
	 private JFrame people;
	 private JTable peopletbl;
	 private int row=-1;
	 private DefaultTableModel peopleTableModel;
	 
	 public PeopleView(Connection conn) {
		 initialize(conn);
		 addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addPerson(conn);
			}
		});
		 deleteBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//deletePerson(conn);
					deletePersonWithClick(conn);
				}
			});
		 //update
		 updateBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				popUpPage(conn,0);
				
			}
		});
	 }
	 
	 private void initialize(Connection conn) {
			people = new JFrame();
			people.setBounds(100,100,603, 539);
			JPanel peoplePanel= new JPanel();
			peoplePanel.setBackground(Color.CYAN);
			people.getContentPane().add(peoplePanel);
			people.setBounds(10,11,567,478);
			people.setVisible(true);
			people.setTitle("people");
			addBtn.setBackground(Color.orange);
			deleteBtn.setBackground(Color.orange);
			updateBtn.setBackground(Color.orange);
			peoplePanel.add(addBtn);
			peoplePanel.add(deleteBtn);
			peoplePanel.add(updateBtn);
			peoplePanel.add(outputLbl);
			Object[] columnNames = {"id", "Name","Surname","birthdate","phone number"};
			peopleTableModel = new DefaultTableModel(columnNames, 0);
	        peopletbl = new JTable(peopleTableModel);
	        JScrollPane peoplePane = new JScrollPane(peopletbl);
	    	peoplePanel.add(peoplePane);
	    	show(conn);
		}
	 private void addPerson(Connection conn) {
		 	/*frame = new JFrame();
	        frame.setBounds(100, 100, 300, 300);
	        frame.getContentPane().setLayout(null);
	        
		 	JTextField nameField = new JTextField();
		 	nameField.setBounds(128, 28, 86, 20);
	        frame.getContentPane().add(nameField );
	        nameField .setColumns(10);
	        JLabel namelbl = new JLabel("Name");
	        frame.getContentPane().add(namelbl);
	        namelbl.setBounds(65, 31, 70, 14);
	        
	        JTextField surnameField = new JTextField();
	        surnameField.setBounds(128, 70, 86, 20);
	        frame.getContentPane().add(surnameField);
	        surnameField.setColumns(10);
	        JLabel surnameLbl = new JLabel("Surname");
	        surnameLbl.setBounds(65, 70, 70, 14);
	        frame.getContentPane().add(surnameLbl);
	        
	        JTextField birthdateField = new JTextField();
	        birthdateField.setBounds(128, 120, 86, 20);
	        frame.getContentPane().add(birthdateField);
	        birthdateField .setColumns(10);
	        JLabel birthdatelbl = new JLabel("BirthDate");
	        birthdatelbl.setBounds(65, 120, 70, 14);
	        frame.getContentPane().add(birthdatelbl);
	        
	        JTextField phoneField = new JTextField();
	        phoneField.setBounds(128, 170, 86, 20);
	        frame.getContentPane().add(phoneField);
	        phoneField.setColumns(10);
	        JLabel phoneLbl = new JLabel("Phone Number");
	        phoneLbl.setBounds(65, 170, 70, 14);
	        frame.getContentPane().add(phoneLbl);
	        frame.setVisible(true);
	        JButton getButton= new JButton("Enter");
	        getButton.setBounds(35, 220, 70, 25);
	        frame.getContentPane().add(getButton);
	        getButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(nameField.getText().equals("") || surnameField.getText().equals("")|| birthdateField.getText().equals("")||phoneField.getText().equals("")) {
						outputLbl.setText("Fields can't be empty");
					}else {
						String query= "insert into people values (nextval('person_seq'),'"+nameField.getText()+ "','"+surnameField.getText()+"','"+birthdateField.getText()+"','"+phoneField.getText()+"')";            
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
			
	 */popUpPage(conn, 1);}
	 private void deletePerson(Connection conn) {
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
					String query= "delete from people where person_id='"+textField.getText()+"'";         
					Statement s = null;
			        try {
			            s = conn.createStatement();
			            s.executeUpdate(query);
			            conn.setAutoCommit(false);
			            conn.commit();
			            s.close();
				}catch(SQLException exception){
					outputLbl.setText("This person have already barrowed books.You can't delete this person");
					exception.printStackTrace();
				}
			        frame.setVisible(false);
			        show(conn);
				}
			});
	 }
	 private void deletePersonWithClick(Connection conn) {
		 peopletbl.addMouseListener(new java.awt.event.MouseAdapter() {
	 		    @Override
	 		    public void mouseClicked(java.awt.event.MouseEvent evt) {
	 		        row = peopletbl.getSelectedRow();
	 		    }
	 		});
			if(row==-1) {
				JOptionPane.showMessageDialog(frame,
						"Choose one Item",
						"WARNING.",
					    JOptionPane.WARNING_MESSAGE);
			}else {
				String query="select * from takes where person_id='"+peopleTableModel.getValueAt(row, 0)+"' and brought_date is null";
	        	PreparedStatement p;
	        	try{
		             p = conn.prepareStatement(query);
		             ResultSet r;
		             r = p.executeQuery();
		        if(r.next()) {
		        	System.out.println("sa");
		        	JOptionPane.showMessageDialog(frame, "Person has book.Can't delete "+peopleTableModel.getValueAt(row, 1)+" "+peopleTableModel.getValueAt(row, 2));
		        	return;
		        }
		        }catch(SQLException e){
		 			e.printStackTrace();
		 			return;
			 	}
	        	
				int dialogButton = JOptionPane.YES_NO_OPTION;
		        int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure to delete "+peopleTableModel.getValueAt(row, 1)+" "+peopleTableModel.getValueAt(row, 2),"Warning",dialogButton);
		        if(dialogResult == JOptionPane.YES_OPTION){
			        query= "delete from people where person_id='"+peopleTableModel.getValueAt(row, 0)+"'"; 
		        	Statement s = null;
			        try {
			            s = conn.createStatement();
			            s.executeUpdate(query);
			            conn.setAutoCommit(false);
			            conn.commit();
			            s.close();
				}catch(SQLException exception){
					JOptionPane.showMessageDialog(frame, "Person has book.Can't delete this person.");
					exception.printStackTrace();
				}
			        
			        show(conn);
		        }
			}
	 }
	 private void show(Connection conn) {
		 	String query ="Select * from people order by CAST (person_id as int)";
	    	PreparedStatement p;
	    	peopleTableModel.setRowCount(0);
	    	People people;
	        try{
	             p = conn.prepareStatement(query);
	             ResultSet r;
	             r = p.executeQuery();
	 	    if (!r.next ()){
	             }else{
	        while(r.next()) {
	        	people = new People(r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5));
		 		Object[] satir ={people.getId(), people.getId(),people.getName(),people.getSurname(),people.getBirthdate(),people.getPhoneNumber()};
		 		peopleTableModel.addRow(satir);
	        }
	        p.close();
	             }
	 	}catch(SQLException e){
	 			outputLbl.setText(e.getMessage());
	             
	 	}
	 }
	 private void popUpPage(Connection conn,int funcParameter) {
		 	
		 	frame = new JFrame();
	        frame.setBounds(100, 100, 400, 400);
	        frame.getContentPane().setLayout(null);
	        
		 	JTextField nameField = new JTextField();
		 	nameField.setBounds(128, 28, 86, 20);
	        frame.getContentPane().add(nameField );
	        nameField .setColumns(10);
	        JLabel namelbl = new JLabel("Name");
	        frame.getContentPane().add(namelbl);
	        namelbl.setBounds(65, 31, 70, 14);
	        
	        JTextField surnameField = new JTextField();
	        surnameField.setBounds(128, 70, 86, 20);
	        frame.getContentPane().add(surnameField);
	        surnameField.setColumns(10);
	        JLabel surnameLbl = new JLabel("Surname");
	        surnameLbl.setBounds(65, 70, 70, 14);
	        frame.getContentPane().add(surnameLbl);
	        
	        JTextField birthdateField = new JTextField();
	        birthdateField.setBounds(128, 120, 86, 20);
	        frame.getContentPane().add(birthdateField);
	        birthdateField .setColumns(10);
	        JLabel birthdatelbl = new JLabel("BirthDate");
	        birthdatelbl.setBounds(65, 120, 70, 14);
	        frame.getContentPane().add(birthdatelbl);
	        
	        JTextField phoneField = new JTextField();
	        phoneField.setBounds(128, 170, 86, 20);
	        frame.getContentPane().add(phoneField);
	        phoneField.setColumns(10);
	        JLabel phoneLbl = new JLabel("Phone Number");
	        phoneLbl.setBounds(65, 170, 70, 14);
	        frame.getContentPane().add(phoneLbl);
	        frame.setVisible(true);
	        JTextField idField = new JTextField();
	        /*if(funcParameter==0) {
	 	        idField.setBounds(128, 220, 86, 20);
	 	        frame.getContentPane().add(idField);
	 	        idField .setColumns(10);
	 	        JLabel idlbl = new JLabel("ID");
	 	        idlbl.setBounds(65, 220, 70, 14);
	 	        frame.getContentPane().add(idlbl);
	        }*/
	        
	        if(funcParameter==0) {
	        	peopletbl.addMouseListener(new java.awt.event.MouseAdapter() {
		 		    @Override
		 		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		 		        int row = peopletbl.getSelectedRow();
		 		        idField.setText(peopleTableModel.getValueAt(row, 0).toString());
		 		        nameField.setText(peopleTableModel.getValueAt(row, 1).toString());
		 		        surnameField.setText(peopleTableModel.getValueAt(row, 2).toString());
		 		        birthdateField.setText(peopleTableModel.getValueAt(row, 3).toString());
		 		        phoneField.setText(peopleTableModel.getValueAt(row, 4).toString());
		 		    }
		 		});
	        }
	        JButton getButton= new JButton("Enter");
	        getButton.setBounds(35, 270, 70, 25);
	        frame.getContentPane().add(getButton);
	        getButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(nameField.getText().equals("") || surnameField.getText().equals("")|| birthdateField.getText().equals("")||phoneField.getText().equals("")) {
						outputLbl.setText("Fields can't be empty");
					}else {
						String query=null;
						if(funcParameter==1) {
							query= "insert into people values (nextval('person_seq'),'"+nameField.getText()+ "','"+surnameField.getText()+"','"+birthdateField.getText()+"','"+phoneField.getText()+"')";
						}else {
							query="update people set birthdate='"+birthdateField.getText()+ "',name ='"+nameField.getText()+"',surname='"+surnameField.getText()+"',phone_number='"+phoneField.getText()+"' where person_id='"+idField.getText()+"'";
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

	 public void close() {
		 people.setVisible(false);
	 }
}
