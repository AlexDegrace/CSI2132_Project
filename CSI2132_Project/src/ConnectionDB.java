import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class ConnectionDB {
	
	Connection db = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Statement st = null;
    String sql;

	public static void main(String[] args) {
		
		
		
	}
	
	public void getConnection() {
		
		try {
			
			Class.forName("org.postgresql.Driver");
			db = DriverManager.getConnection("jdbc:postgresql:/web0.eecs.uottawa.ca:15432/","","");
			
			if(db!=null) {
				System.out.println("Connection work");
			}
			else {
				System.out.println("Connection do not work");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		
	}

}
