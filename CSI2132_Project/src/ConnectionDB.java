import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ConnectionDB {
	
	Connection db = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Statement st = null;
    String sql;

	public static void main(String[] args) {
		
		
		
	}
	
	//Create a connection with the database
	public void getConnection() {
		
		try {
			
			Class.forName("org.postgresql.Driver");
			db = DriverManager.getConnection("jdbc:postgresql://web0.eecs.uottawa.ca:15432/group_b07_g08","adegr091","Jtx5y2gd**");
			
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
	
	//Close the connection with the database
	public void closeDB() {
		try {
			if(rs != null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(st!=null){
				st.close();
			}
			if(db!=null){
				db.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Return the name of all hotel brand name
	public ArrayList<String> getHotelBrand() {
		getConnection();
		
		ArrayList<String> hotel_brand = new ArrayList<String>();
		
        try{
            ps = db.prepareStatement("select brand_name from public.hotel_brand");
            	               
            rs = ps.executeQuery();

			while(rs.next()) {
				hotel_brand.add(rs.getString(1));
			}
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
        	closeDB();
        }
		return hotel_brand;
	}
	
	//Return all hotels chains (Not all attribute but most of them) link with the hotel brand given as the parameter
	public ArrayList<HotelChain> getHotelChain(String hotelBrand) {
		getConnection();
		
		ArrayList<HotelChain> hotel_chain = new ArrayList<HotelChain>();
		
        try{
        	String query ="select hotel_id,star_category,street_number,street_name,city,state_province,phone_number from hotel_chain where brand_name=\'"+hotelBrand+"\' order by hotel_id" ;
       
            ps = db.prepareStatement(query);
            	               
            rs = ps.executeQuery();

			while(rs.next()) {
				HotelChain hc = new HotelChain(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
				hotel_chain.add(hc);
			}
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
        	closeDB();
        }
		return hotel_chain;
	}
	
	
	//Return all customers cust_id, first_name and last_name
	public ArrayList<String> getCustomer(){
		getConnection();
		
		ArrayList<String> customers = new ArrayList<String>();
		
        try{
        	String query ="select customer_id,first_name,last_name from customer";
       
            ps = db.prepareStatement(query);
            	               
            rs = ps.executeQuery();

            while(rs.next()) {
				Customer c = new Customer(rs.getString(1),rs.getString(2),rs.getString(3));
				customers.add(c.toString());
			}
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
        	closeDB();
        }
		return customers;
	}

}
