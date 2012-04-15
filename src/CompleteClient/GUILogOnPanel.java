package CompleteClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
	static final String DB_URL = "jdbc:mysql://lampd.vf.cnu.edu:3306/legacy";

	// Database credentials
	static final String USER = "ns";
	static final String PASS = "password";
	private static boolean Val = true;
	private static String users;
	private static String passs;
	private JButton btnLog;
	private JTextField txtPass,txtUser;
	private int prewid;	

 	public static boolean testInformation() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT userName, password FROM Employees";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				users = rs.getString("user");
				passs = rs.getString("password");
				String email = rs.getString("last");
				int key = rs.getInt("key");
				System.out.println(users + " ");
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end tr

		return Val;
	}

	public GUILogOnPanel(final Application app) {
		this.app = app;
		this.setLayout(null);
		txtUser = new JTextField();
		//txtUser.setBorder(null);
		txtPass = new JTextField();
		//txtPass.setBorder(null);
		btnLog = new JButton("Log On");
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
		this.add(txtUser);
		this.add(txtPass);
		this.add(btnLog);
		this.reposition();
	}
	
	public void reposition(){
		prewid = app.size.width;
		double diffh = (double) (app.size.width)
				/ app.graphics.logOnPage.getWidth(null);
		double diffv = (double) (app.size.height)
				/ app.graphics.logOnPage.getHeight(null);
		txtUser.setFont(new Font("Ariel", Font.PLAIN, (int)(24*diffv)));
		txtUser.setSize((int) (402 * diffh), (int)(75*diffv));
		txtUser.setLocation((int) (547 * diffh), (int)(565*diffv));
		txtPass.setFont(new Font("Ariel", Font.PLAIN, (int)(24*diffv)));
		txtPass.setSize((int) (402 * diffh), (int)(75*diffv));
		txtPass.setLocation((int) (542 * diffh), (int)((565+140)*diffv));
		btnLog.setSize((int) (402 * diffh), (int)(75*diffv));
		btnLog.setLocation((int) (547 * diffh), (int)((565+140*2)*diffv));
	}

	@Override
	public void displayData(String string) {
		// TODO Auto-generated method stub
		if (string.equals("Connected")) {
			app.logout.setEnabled(true);
			app.switchView(1);
		} else {
			app.userName = "";
			System.out.println(string);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(app.size.width!=prewid){
			reposition();
		}
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, app.size.width, app.size.height);
		g.drawImage(app.graphics.logOnPage, 0, 0, app.size.width,
				app.size.height, 0, 0, app.graphics.logOnPage.getWidth(null),
				app.graphics.logOnPage.getHeight(null), null);
	}
}