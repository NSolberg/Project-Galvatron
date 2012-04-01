package bteam.capstone.guiTestArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GUILogOnPanel extends JPanel implements ClientUser {
	private Application app;
	private Object dbms;
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1:8000/legacy";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "498";
	private static boolean Val = true;
	private static String users;
	private static String passs;

	public static boolean testInformation(){
		  Connection conn = null;
		  Statement stmt = null;
		   try{
		      //STEP 2: Register JDBC driver
			   Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		      System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		      String sql;
		      sql = "SELECT userName, password FROM Employees";
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      while(rs.next()){
		         //Retrieve by column name
		         users  = rs.getString("user");
		         passs = rs.getString("password");
		         String email = rs.getString("last");
		         int key = rs.getInt("key");
		         System.out.println(users + " ");
		      }
		      //STEP 6: Clean-up environment
		      rs.close();
		      stmt.close();
		      conn.close();
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end tr
		
		
		return Val;
	}
	
	public GUILogOnPanel(final Application app) {
		this.app = app;
		this.setLayout(null);
		JLabel lblUser = new JLabel("User Name:");
		lblUser.setBounds(0, 0, 75, 25);
		JLabel lblPass = new JLabel("Password :");
		lblPass.setBounds(0, 30, 75, 25);
		final JTextField txtUser = new JTextField();
		txtUser.setBounds(80, 0, 100, 25);
		JTextField txtPass = new JTextField();
		txtPass.setBounds(80, 30, 100, 25);
		JButton btnLog = new JButton("Log On");
		btnLog.setBounds(200, 30, 100, 25);
		btnLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String user = txtUser.getText();
				app.userName = user;
				if (!user.equals("")) {
					app.client.connect("localhost", 1337);
					if (app.client.isConnected()) {
						app.client.start();
						app.client.sendData(user);
					} else {

					}
				}
			}
		});
		this.add(lblUser);
		this.add(txtUser);
		this.add(lblPass);
		this.add(txtPass);
		this.add(btnLog);
	}

	@Override
	public void displayData(String string) {
		// TODO Auto-generated method stub
		if (string.equals("Connected")) {
			app.switchView(1);
		}else{
			app.userName = "";
			System.out.println(string);
		}
	}
}