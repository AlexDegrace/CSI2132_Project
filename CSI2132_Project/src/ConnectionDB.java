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
			closeDB();
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
        	String query ="select customer_id,first_name,last_name from customer order by customer_id";
       
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
	
	//Get all room in the hotel that does not have a reservation between startDate and endDate
	public ArrayList<Room> getRoomByHotelAndDate(String hotelId, String startDate, String endDate){
		
	
		getConnection();
		
		ArrayList<Room> rooms = new ArrayList<Room>();
		
        try{
        	String query ="select * from room where hotel_id = " + hotelId + " and room_id not in (select room_id from room_booking where '"+startDate+"' between start_date and end_date or '"+endDate+"' between start_date and end_date)";
       
            ps = db.prepareStatement(query);
            	               
            rs = ps.executeQuery();

            while(rs.next()) {
				Room r = new Room(rs.getString(1),rs.getString(2),rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6));
				rooms.add(r);
			}
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
        	closeDB();
        }
		return rooms;
	
	}
	
	public ArrayList<RoomBooking> getRoomBookingByCustomerId(String custId){
		
		getConnection();
		
		ArrayList<RoomBooking> roomBookings = new ArrayList<RoomBooking>();
		
        try{
        	String query ="select * from room_booking where room_id in (select room_id from has_booking where customer_id = "+custId+");";
       
            ps = db.prepareStatement(query);
            	               
            rs = ps.executeQuery();

            while(rs.next()) {
            	RoomBooking r = new RoomBooking(rs.getString(1),rs.getString(2),rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6));
				roomBookings.add(r);
			}
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
        	closeDB();
        }
		return roomBookings;
		
	}
	
	
	//Create a room_booking and a had_booking using roomId, startDate, endDate, price, custId
	public boolean addRoomBookingAndHasBooking(String roomId, String startDate, String endDate, String price, String custId) {
		
		getConnection();
		
		boolean worked = false;
		
        try{
        	String priceNoDollarsSign = price.substring(1,price.length()-1);

        	String query ="INSERT INTO public.room_booking(room_id, start_date, end_date, price) VALUES ("+ roomId +", '" + startDate + "', '"+ endDate +"', "+ priceNoDollarsSign +");";
        	st = db.createStatement();
        	st.executeUpdate(query);
        	
        	query = "INSERT INTO public.has_booking(start_date, end_date, room_id, customer_id) VALUES ('" + startDate + "', '"+ endDate +"', " +roomId +", "+ custId +");";
        	st = db.createStatement();
        	st.executeUpdate(query);
        	
            worked = true;
            
        }catch(SQLException e){
            e.printStackTrace();
            worked = false;
        }finally {
        	closeDB();
        }
		return worked;
		
	}
	
	//Create a room_booking and a had_booking using roomId, startDate, endDate, price, custId
	public boolean addRoomRentingAndHasRenting(String roomId, String startDate, String endDate, String price, String custId) {
		
		getConnection();
		
		boolean worked = false;
		
        try{
        	String priceNoDollarsSign = price.replace("$","");

        	String query ="INSERT INTO public.room_renting(room_id, start_date, end_date, price, was_booked) VALUES ("+ roomId +", '" + startDate + "', '"+ endDate +"', "+ priceNoDollarsSign +", false);";
        	st = db.createStatement();
        	st.executeUpdate(query);
        	
        	query = "INSERT INTO public.has_renting(start_date, end_date, room_id, customer_id) VALUES ('" + startDate + "', '"+ endDate +"', " +roomId +", "+ custId +");";
        	st = db.createStatement();
        	st.executeUpdate(query);
        	
        	query = "UPDATE public.room_booking SET is_paid = true WHERE room_id ="+roomId+" and start_date = '"+startDate+"' and end_date = '"+endDate+"' ;";
        	st = db.createStatement();
        	st.executeUpdate(query);
        	
            worked = true;
            
        }catch(SQLException e){
            e.printStackTrace();
            worked = false;
        }finally {
        	closeDB();
        }
		return worked;
		
	}
	
	public boolean addRoomRentingAndHasRentingFromRoomBooking(String roomId, String startDate, String endDate, String price, String custId) {
		
		getConnection();
		
		boolean worked = false;
		
        try{
        	String priceNoDollarsSign = price.replace("$","");

        	String query ="INSERT INTO public.room_renting(room_id, start_date, end_date, price, was_booked) VALUES ("+ roomId +", '" + startDate + "', '"+ endDate +"', "+ priceNoDollarsSign +", true);";
        	st = db.createStatement();
        	st.executeUpdate(query);
        	
        	query = "INSERT INTO public.has_renting(start_date, end_date, room_id, customer_id) VALUES ('" + startDate + "', '"+ endDate +"', " +roomId +", "+ custId +");";
        	st = db.createStatement();
        	st.executeUpdate(query);
        	
        	query = "UPDATE public.room_booking SET is_paid = true WHERE room_id ="+roomId+" and start_date = '"+startDate+"' and end_date = '"+endDate+"' ;";
        	st = db.createStatement();
        	st.executeUpdate(query);
        	
            worked = true;
            
        }catch(SQLException e){
            e.printStackTrace();
            worked = false;
        }finally {
        	closeDB();
        }
		return worked;
		
	}
	
	
	//insert into customers
	public boolean insertCustomers(String customer_id, String first_name, String middle_name, String last_name, String street_number, String street_name, String city, String state_province, String country, String postal_code, String sin, String date_of_birth, String phone) {
		getConnection();
		
		boolean worked = false;
		
		try {
			String query = "INSERT INTO public.customer(customer_id,first_name,middle_name,last_name,street_number,street_name,city,state_province,country,postal_code,sin,date_of_birth,phone) VALUES ("+customer_id + ", '" + first_name + "' , '" + middle_name + "' , '" + last_name + "' , " + street_number + ", '" + street_name + "' , '" + city + "' , '" + state_province + "' , '" + country + "' , '" + postal_code + "' , " + sin + " , '" + date_of_birth + "' , " + phone + ");";
		st = db.createStatement();
		st.execute(query);
		worked = true;
		}catch(SQLException e) {
			e.printStackTrace();
			worked=false;
		}finally {
			closeDB();
		}
		return worked;
		
	}
	
	//insert into staff
	public boolean insertStaff(String staff_id, String first_name, String middle_name, String last_name, String street_number, String street_name, String city, String state_province, String country, String postal_code, String sin, String salary, String employee_job, String hotel_id) {
		getConnection();
		
		boolean worked = false;
		
		try {
			String salaryNoDollarsSign = salary.substring(1,salary.length()-1);
			String query = "INSERT INTO public.staff(staff_id,first_name,middle_name,last_name,street_number,street_name,city,state_province,country,postal_code,sin,salary,employee_job,hotel_id) VALUES ("+staff_id + ", '" + first_name + "' , '" + middle_name + "' , '" + last_name + "' , " + street_number + ", '" + street_name + "' , '" + city + "' , '" + state_province + "' , '" + country + "' , '" + postal_code + "' , " + sin + " , " + salaryNoDollarsSign + " , '"+employee_job +" , " + hotel_id + ");";
		st = db.createStatement();
		st.execute(query);
		worked = true;
		}catch(SQLException e) {
			e.printStackTrace();
			worked=false;
		}finally {
			closeDB();
		}
		return worked;
		
	}
	
	//insert into hotel_chain
	public boolean insertHotel(String hotel_id, String brand_name, String star_category, String street_number, String street_name, String city, String state_province, String country, String postal_code,  String contact_email, String phone_number, String manager_id) {
		getConnection();
		
		boolean worked = false;
		
		try {
			String query = "INSERT INTO public.hotel_chain(hotel_id,brand_name,star_category,street_number,street_name,city,state_province,country,postal_code,contact_email,phone_number,manager_id) VALUES ("+hotel_id + ", '" + brand_name + "' , " + star_category + " , " + street_number + ", '" + street_name + "' , '" + city + "' , '" + state_province + "' , '" + country + "' , '" + postal_code + "' , '" + contact_email + "' , '" + phone_number + "' , " + manager_id + ");";
		st = db.createStatement();
		st.execute(query);
		worked = true;
		}catch(SQLException e) {
			e.printStackTrace();
			worked=false;
		}finally {
			closeDB();
		}
		return worked;
		
	}
	
		
	//insert into room
	public boolean insertRoom(String room_id, String hotel_id, String price, String room_capacity, String is_extendable) {
			getConnection();
			
			boolean worked = false;
			
			try {
				String priceNoDollarsSign = price.substring(1,price.length()-1);
				String query = "INSERT INTO public.room(room_id,hotel_id,price,room_capacity,is_extendable) VALUES ("+room_id + ","+hotel_id+ ","+priceNoDollarsSign + ","+ room_capacity + "," + is_extendable + ");";
			st = db.createStatement();
			st.execute(query);
			worked = true;
			}catch(SQLException e) {
				e.printStackTrace();
				worked=false;
			}finally {
				closeDB();
			}
			return worked;
			
		}
	
	public boolean deleteCustomer(String customer_id) {
		getConnection();
		
		boolean worked = false;
		
		try {
			String query = "DELETE FROM public.customer WHERE customer_id =" + customer_id;
		st = db.createStatement();
		st.execute(query);
		worked = true;
		}catch(SQLException e) {
			e.printStackTrace();
			worked=false;
		}finally {
			closeDB();
		}
		return worked;
		
	}
	
	public boolean deleteStaff(String staff_id) {
		getConnection();
		
		boolean worked = false;
		
		try {
			String query = "DELETE FROM public.staff WHERE staff_id =" + staff_id;
		st = db.createStatement();
		st.execute(query);
		worked = true;
		}catch(SQLException e) {
			e.printStackTrace();
			worked=false;
		}finally {
			closeDB();
		}
		return worked;
		
	}
	
	public boolean deleteHotel(String hotel_id) {
		getConnection();
		
		boolean worked = false;
		
		try {
			String query = "DELETE FROM public.hotel_chain WHERE hotel_id =" + hotel_id;
		st = db.createStatement();
		st.execute(query);
		worked = true;
		}catch(SQLException e) {
			e.printStackTrace();
			worked=false;
		}finally {
			closeDB();
		}
		return worked;
		
	}
	
	public boolean deleteRoom(String room_id) {
		getConnection();
		
		boolean worked = false;
		
		try {
			String query = "DELETE FROM public.room_id WHERE room_id =" + room_id;
		st = db.createStatement();
		st.execute(query);
		worked = true;
		}catch(SQLException e) {
			e.printStackTrace();
			worked=false;
		}finally {
			closeDB();
		}
		return worked;
		
	}
	
	
	

}
