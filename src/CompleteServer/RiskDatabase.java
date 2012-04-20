package CompleteServer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class RiskDatabase {
	Connection conn;

	public static void main(String[] args){
		RiskDatabase b = new RiskDatabase();
		System.out.println("enter herp");
		Scanner scan = new Scanner(System.in);
		String s = scan.nextLine();
		b.exists(s);
	}
	
	public RiskDatabase() {
		System.out.println("Trying to connect to database");
		conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "legacy";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "498";
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			System.out.println("Connected to the database");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			conn.close();
			System.out.println("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean exists(String creditials) {
		try{
		Statement stmt = null;
		// STEP 4: Execute a query
		System.out.println("Creating statement...");
		stmt = conn.createStatement();
		String sql;
		Scanner scan = new Scanner(creditials);
		String username = scan.next();
		String password = scan.next();
		sql = "SELECT * FROM user WHERE userName = '"+username+"' and password = '"+password+"'";
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next()){
			System.out.println("Johny 5 is alive");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;

	}
}
