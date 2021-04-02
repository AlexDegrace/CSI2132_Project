import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class MainApplication {

    final static private String CUSTOMER_USERNAME = "customer";
    final static private String STAFF_USERNAME = "employee";
    final static private String ADMIN_USERNAME = "admin";
    
    private static Scanner scanner = new Scanner(System.in);
    
    private static String DB_USERNAME;
    private static String DB_PASSWORD;
    
    //TODO: add all customers and choose which one you are
    public static void main(String[] args) {
    	boolean badCredential;
    	do {
    		System.out.println("Please enter your postgres username");
        	DB_USERNAME = scanner.nextLine();
        	System.out.println("Please enter your postgres password");
        	DB_PASSWORD = scanner.nextLine();
        	ConnectionDB testConnection = new ConnectionDB(DB_USERNAME,DB_PASSWORD);
        	try {
        		testConnection.getConnection();
        		testConnection.closeDB();
        		badCredential = false;
        	}
        	catch(Exception e) {
        		System.out.println(e);
        		testConnection.closeDB();
        		badCredential = true;
        	}
    	}while(badCredential);
    	
    	boolean notDone = false;
    	do {
    		
	        try {
	        	int typeOfUser = getCredential();
	            System.out.println(typeOfUser);
	
	            //if user is a customer
	            if(typeOfUser == 1){
	                handleCustomer();
	            }
	            else if(typeOfUser == 2) {
	            	handlerEmployee();
	            }
	            else if(typeOfUser == 3) {
	            	handlerAdmin();
	            }
	        	
	        }catch(Exception e) {
	        	System.out.println(e);
	        }        
	        notDone = askUserIfDone();
      
        }while(notDone);
    }

    //Ask the user if he is an employee or a customer
    public static int getCredential(){
        boolean isLogIn =false;
        int typeOfUser = 0;
        while(!isLogIn){
            System.out.println("Are you an employee, customer or admin?");
            String username = scanner.nextLine();

            if(username.equals(CUSTOMER_USERNAME) ){
                typeOfUser = 1;
                isLogIn = true;
            }

            else if(username.equals(STAFF_USERNAME) ){
                typeOfUser = 2;
                isLogIn = true;
            }
            else if(username.equals(ADMIN_USERNAME) ){
                typeOfUser = 3;
                isLogIn = true;
            }
        }
        return typeOfUser;
    }
    
    private static String askUserForCustomerId(ConnectionDB connection){
    	ArrayList<Customer> customers;
    	do{
    		try {
        		customers = connection.getCustomer();
        		break;
        	}
        	catch(Exception e) {
        		System.out.println(e);
        		try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
        	}
    	}
    	while(true);
    	ArrayList<String> availableCustomerId = new ArrayList<String>();
    	// List all of the customers
    	for(Customer cu : customers) {
    		System.out.println(cu);
    		availableCustomerId.add(cu.getCustomerId());
    	}
    	System.out.println("Choose a customer (choose a valid id)"); 
    	//This store the custId for creating booking later
    	String custId;
    	do
        { 
    		// This ask the user for its customer id
            try {
            	custId = scanner.nextLine();
            	if(!availableCustomerId.contains(custId)) {
            		throw new Exception();
            	}
                break;
            }
            catch (Exception e)
            {
                System.out.println("Please enter a valid customer id");
            }
        }
        while (true);
    	
    	return custId;
    }
    
    private static String askUserForStaffId(ConnectionDB connection){
    	ArrayList<Staff> staff;
    	do{
    		try {
        		staff = connection.getStaff();
        		break;
        	}
        	catch(Exception e) {
        		System.out.println(e);
        		try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
        	}
    	}
    	while(true);
    	
    	ArrayList<String> availableStaffId = new ArrayList<String>();
    	// List all of the customers
    	for(Staff s : staff) {
    		System.out.println(s);
    		availableStaffId.add(s.getStaffId());
    	}
    	System.out.println("Choose the staff (choose a valid id)"); 
    	//This store the custId for creating booking later
    	String staffId;
    	do
        { 
    		// This ask the user for its customer id
            try {
            	staffId = scanner.nextLine();
            	
            	if(!availableStaffId.contains(staffId)) {
            		throw new Exception();
            	}
                break;
            }
            catch (Exception e)
            {
                System.out.println("Please enter a valid staff id");
            }
        }
        while (true);
    	
    	return staffId;
    }
    
    private static String askUserForHotelChainId(ConnectionDB connection){
    	ArrayList<HotelChain> hotelChain;
    	do{
    		try {
    			hotelChain = connection.getHotelChain();
        		break;
        	}
        	catch(Exception e) {
        		System.out.println(e);
        		try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
        	}
    	}
    	while(true);
    	
    	ArrayList<String> availableHotelChainId = new ArrayList<String>();
    	// List all of the customers
    	for(HotelChain hc : hotelChain) {
    		System.out.println(hc);
    		availableHotelChainId.add(hc.getHotelId());
    	}
    	System.out.println("Choose the hotel chain (choose a valid id)"); 
    	//This store the custId for creating booking later
    	String hotelId;
    	do
        { 
    		// This ask the user for its customer id
            try {
            	hotelId = scanner.nextLine();
            	
            	if(!availableHotelChainId.contains(hotelId)) {
            		throw new Exception();
            	}
                break;
            }
            catch (Exception e)
            {
                System.out.println("Please enter a valid hotel chain id");
            }
        }
        while (true);
    	
    	return hotelId;
    }
    
    private static String askUserForRoomId(ConnectionDB connection){
    	ArrayList<Room> room;
    	do{
    		try {
    			room = connection.getRoom();
        		break;
        	}
        	catch(Exception e) {
        		System.out.println(e);
        		try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
        	}
    	}
    	while(true);
    	
    	ArrayList<String> availableRoomId = new ArrayList<String>();
    	// List all of the customers
    	for(Room r : room) {
    		System.out.println(r);
    		availableRoomId.add(r.getRoomId());
    	}
    	System.out.println("Choose the room (choose a valid id)"); 
    	//This store the custId for creating booking later
    	String roomId;
    	do
        { 
    		// This ask the user for its customer id
            try {
            	roomId = scanner.nextLine();
            	
            	if(!availableRoomId.contains(roomId)) {
            		throw new Exception();
            	}
                break;
            }
            catch (Exception e)
            {
                System.out.println("Please enter a valid room id");
            }
        }
        while (true);
    	
    	return roomId;
    }
    
    private static String askUserForHotelBrand(ConnectionDB connection) {
    
    	ArrayList<String> hotel_brands;
    	do{
    		try {
    			hotel_brands = connection.getHotelBrand();
        		break;
        	}
        	catch(Exception e) {
        		System.out.println(e);
        		try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        	}
    		

    	}
    	while (true);
    	
    	// This let you choose the hotel brand you want to book
        System.out.print("Choose which hotel brand you want to book in ");
    	for(int i =0; i < hotel_brands.size(); i++) {
        	System.out.print("{"+hotel_brands.get(i)+" ("+i+")"+"} ");
        }
    	System.out.print("\n");
    	
    	// This keep the index of the Hotel Brand 
        int selectedInt;
        
        do
        { 
            try {
            	String selectedHB = scanner.nextLine();
            	selectedInt = Integer.parseInt(selectedHB);
            	//Make sure the entry is correct
            	if(selectedInt<0 || selectedInt>=hotel_brands.size()) {
            		throw new Exception();
            	}
                break;
            }
            catch (Exception e)
            {
                System.out.println("Please enter a valid integer");
            }
        }
        while (true);
        
        return hotel_brands.get(selectedInt);
    	
    }
    
    private static Room askUserForRoom(ConnectionDB connection,String hotelChainId, String startDate,String endDate) {
    	// Query all the available rooms for the hotelId and start and end date
        ArrayList<Room> availableRooms = connection.getRoomByHotelAndDate(hotelChainId, startDate,endDate );
        
        System.out.println("Choose which room you want (choose a valid room id)");
        ArrayList<Integer> availableRoomId = new ArrayList<Integer>();
        
        //Print all the rooms available and store their id in an array list to validate id later
        for(Room room: availableRooms) {
        	availableRoomId.add(Integer.parseInt(room.getRoomId()));
        	System.out.println(room);
        }
        
        //Store the id of the selected room to book
        String selectedRoomId;
        
        //Ask the user for the room to book
        do
        { 
            try {
            	selectedRoomId = scanner.nextLine();
            	int selectedRoomIdInt = Integer.parseInt(selectedRoomId);
            	//Make sure the entry is correct
            	if(!availableRoomId.contains(selectedRoomIdInt)) {
            		throw new Exception();
            	}
                break;
            }
            catch (Exception e)
            {
                System.out.println("Please enter a valid id");
            }
        }
        while (true);
        
        boolean found =false;
        int currentIndex = 0;
        Room r = null;
        while(!found || currentIndex<availableRooms.size()) {
        	r = availableRooms.get(currentIndex);
        	if(selectedRoomId.equals(r.getRoomId())) {
        		found = true;
        	}
        	currentIndex++;
        } 
        return r;
    }
    
    private static String askUserForHotelChain(ConnectionDB connection, String hotelBrandName) {
    	
    	ArrayList<HotelChain> hotel_chains;
    	
    	do{
    		try {
    			 hotel_chains = connection.getHotelChain(hotelBrandName);
        		break;
        	}
        	catch(Exception e) {
        		System.out.println(e);
        		try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
    	
    	}
    	while(true);
    	
        // Let the user select the hotel chain he want to book
        System.out.println("Choose which hotel you want to book (choose a valid hotel id)");
        for(HotelChain chain: hotel_chains) {
        	System.out.println(chain);
        }
        
        // Store the hotel chain id that the user want to book a room in
        String selectedHotelId;
        
        do
        { 
            try {
            	selectedHotelId = scanner.nextLine();
            	int selectedHotelIdInt = Integer.parseInt(selectedHotelId);
            	//Make sure the entry is correct
            	if(selectedHotelIdInt < Integer.parseInt(hotel_chains.get(0).getHotelId()) || selectedHotelIdInt > Integer.parseInt(hotel_chains.get(hotel_chains.size()-1).getHotelId())) {
            		throw new Exception();
            	}
                break;
            }
            catch (Exception e)
            {
                System.out.println("Please enter a valid id");
            }
        }
        while (true);
    	
        return selectedHotelId;
    }
    
    private static String askUserForStartDate() {
    	
    	//Let the use choose a end date for there booking
        System.out.println("Choose the start date of your booking (yyyy-mm-dd)");
        String startDate;
        
        do
        { 
            try {
            	startDate = scanner.nextLine();
            	//Make sure the entry is correct
            	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            	dateFormat.setLenient(false);
      
            		
            	java.util.Date d = dateFormat.parse(startDate);
            	startDate = dateFormat.format(d);
            	break;
            	 
            	// string contains valid date
            	}
  
               
            catch (Exception e)
            {
                System.out.println("Please enter a valid start date");
            }
        }
        while (true);
        
        return startDate;
    	
    }
    
    private static String askUserForEndDate() {
    	//Let the use choose a start date for there booking
        System.out.println("Choose the end date of your booking (yyyy-mm-dd)");
        String endDate;
        
        do
        { 
            try {
            	endDate = scanner.nextLine();
            	//Make sure the entry is correct
            	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            	dateFormat.setLenient(false);
      	
            	java.util.Date d = dateFormat.parse(endDate);
            	endDate = dateFormat.format(d);
            	break;
            }
               
            catch (Exception e)
            {
                System.out.println("Please enter a valid end date");
            }
        }
        while (true);
        return endDate;
    }
    
    private static boolean askUserForPriorBooking() {
    	
    	System.out.println("Does the customer have a prior booking (yes or no)?");
        
        boolean customerHasBooking;
        String userPriorBookingInput;
        
        do
        { 
            try {
            	userPriorBookingInput = scanner.nextLine();
            	
            	//Make sure the entry is correct
            	if("y".equals(userPriorBookingInput.toLowerCase())|| "yes".equals(userPriorBookingInput.toLowerCase())) {
            		customerHasBooking = true;
            	}
            	else if("n".equals(userPriorBookingInput.toLowerCase()) || "no".equals(userPriorBookingInput.toLowerCase())){
            		customerHasBooking = false;
            	}
            	else {
            		throw new Exception();
            	}
                break;
            }
            catch (Exception e)
            {
                System.out.println("Please enter yes or no");
            }
        }
        while (true);
    	return customerHasBooking;
    }
    
    private static boolean askUserIfDone() {
    	
    	System.out.println("Do you want to do more operation on the database (yes or no)?");
        
        boolean adminWantToContinue;
        String adminWantToContinueInput;
        
        do
        { 
            try {
            	adminWantToContinueInput = scanner.nextLine();
            	
            	//Make sure the entry is correct
            	if("y".equals(adminWantToContinueInput.toLowerCase())|| "yes".equals(adminWantToContinueInput.toLowerCase())) {
            		adminWantToContinue = true;
            	}
            	else if("n".equals(adminWantToContinueInput.toLowerCase()) || "no".equals(adminWantToContinueInput.toLowerCase())){
            		adminWantToContinue = false;
            	}
            	else {
            		throw new Exception();
            	}
                break;
            }
            catch (Exception e)
            {
                System.out.println("Please enter yes or no");
            }
        }
        while (true);
    	return adminWantToContinue;
    }
    
    private static String askUserForAdminOperation() {
    	
    	System.out.println("What operation do you want to do (insert, delete or update)?");
        
        String selectedOperation;
        
        do
        { 
            try {
            	selectedOperation = scanner.nextLine();
            	selectedOperation = selectedOperation.toLowerCase();
            	
            	//Make sure the entry is correct
            	if(selectedOperation.equals("insert") || selectedOperation.equals("delete") || selectedOperation.equals("update")) {
            		return selectedOperation;
            	}
            	else {
            		throw new Exception();
            	}
            }
            catch (Exception e)
            {
                System.out.println("Please enter insert, delete or update");
            }
        }
        while (true);
    }
    
    private static String askUserForTable() {
    	
    	System.out.println("What table do you want to modifie (customer, staff, hotel_chain or room)?");
        
        String selectedTable;
        
        do
        { 
            try {
            	selectedTable = scanner.nextLine();
            	selectedTable = selectedTable.toLowerCase();
            	
            	//Make sure the entry is correct
            	if(selectedTable.equals("staff") || selectedTable.equals("hotel_chain") || selectedTable.equals("room") || selectedTable.equals("customer")) {
            		return selectedTable;
            	}
            	else {
            		throw new Exception();
            	}
            }
            catch (Exception e)
            {
                System.out.println("Please enter customer, staff, hotel_chain or room");
            }
        }
        while (true);
    }
    
    private static RoomBooking askUserForRoomBooking(ConnectionDB connection, String custId) {
    	ArrayList<RoomBooking> roomBookings;
    	do{
    		try {
    			roomBookings = connection.getRoomBookingByCustomerId(custId);
        		break;
        	}
        	catch(Exception e) {
        		System.out.println(e);
        		try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        	}
    		
    	}
    	while (true);
    	
    	// This let you choose the hotel brand you want to book
        System.out.println("Choose which room booking you want to check in (use the number in parentheses)");
    	for(int i =0; i<roomBookings.size(); i++) {
    		RoomBooking rb = roomBookings.get(i);
        	System.out.println("("+i+") -> " +rb.toString());
        }
    	
    	// This keep the index of the Hotel Brand 
        int selectedBookingIndex;
        
        do
        { 
            try {
            	String stringBookingIndex = scanner.nextLine();
            	selectedBookingIndex = Integer.parseInt(stringBookingIndex);
            	//Make sure the entry is correct
            	if(selectedBookingIndex<0 || selectedBookingIndex>=roomBookings.size()) {
            		throw new Exception();
            	}
                break;
            }
            catch (Exception e)
            {
                System.out.println("Please enter a valid number");
            }
        }
        while (true);
        
        return roomBookings.get(selectedBookingIndex);
    }
    
    public static void handleCustomer() throws InterruptedException {
    	ConnectionDB connection = new ConnectionDB(DB_USERNAME,DB_PASSWORD);
    	// Ask the user for its customer id
    	
    	String custId = askUserForCustomerId(connection);
    	
    	String hotelBrandName = askUserForHotelBrand(connection);
    
    	String selectedHotelId = askUserForHotelChain(connection, hotelBrandName);
        
        String startDate = askUserForStartDate();
        
        String endDate = askUserForEndDate();
        
        Room room = askUserForRoom(connection, selectedHotelId,startDate,endDate);
        
        
 
        
        // Try to create a room_booking and a had_booking for the information collected from the user
        boolean bookingCreated = connection.addRoomBookingAndHasBooking(room.getRoomId(),startDate,endDate,room.getPrice(),custId);
        
        //Display if the insert worked or not
        if(bookingCreated) {
        	System.out.println("A booking has been created for room "+ room.getRoomId() +" in hotel "+selectedHotelId +" from " + startDate + " to "+ endDate + " for " + room.getPrice()+"/day");
        }
        else {
        	System.out.println("The booking could not be created please try later.");
        }
        
    }
    
    
    
    
    public static void handlerEmployee() {
    	
    	ConnectionDB connection = new ConnectionDB(DB_USERNAME,DB_PASSWORD);
    	
    	String hotelBrandName = askUserForHotelBrand(connection);
    	
    	String hotelChainId = askUserForHotelChain(connection,hotelBrandName);
        
        boolean customerHasBooking = askUserForPriorBooking();
        
        //If the customer has booking we need his customer if
        if(customerHasBooking) {
        	String custId = askUserForCustomerId(connection);
        	RoomBooking custRoomBooking = askUserForRoomBooking(connection,custId);
        	boolean worked= false;
        	do{
        		try {
        			worked = connection.addRoomRentingAndHasRentingFromRoomBooking(custRoomBooking.getRoomId(),custRoomBooking.getStartDate(),custRoomBooking.getEndDate(), custRoomBooking.getPrice(), custId);
            		break;
            	}
            	catch(Exception e) {
            		System.out.println(e);
            		try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
            		
            	}
        		
        	}
        	while (true);
        	
        	if(worked) {
        		System.out.println("Check in succesful. New entry in the database for Room_Renting and had_Renting");
        	}else {
        		
        	}
        }
        else {
        	String startDate = askUserForStartDate();
        	String endDate =  askUserForEndDate();
        	
        	Room room = askUserForRoom(connection, hotelChainId,startDate,endDate);
        	String custId = askUserForCustomerId(connection);
        	
        	// Try to create a room_booking and a had_booking for the information collected from the user
            boolean rentingCreated = connection.addRoomRentingAndHasRenting(room.getRoomId(),startDate,endDate,room.getPrice(),custId);
            
            //Display if the insert worked or not
            if(rentingCreated) {
            	System.out.println("A booking has been created for room "+ room.getRoomId() +" in hotel "+hotelChainId +" from " + startDate + " to "+ endDate + " for " + room.getPrice()+"/day");
            }
            else {
            	System.out.println("The booking could not be created please try later.");
            }
        	
        	
        }
        
    }
    
    
    public static void handlerAdmin() {
    	boolean adminWantToContinue;
    	ConnectionDB connection = new ConnectionDB(DB_USERNAME,DB_PASSWORD);
	    	
    	do {
	    	String selectedOperation = askUserForAdminOperation();
	    	
	    	String selectedTable = askUserForTable();
	    	
	    	boolean queryWorked = false;
	    	
	    	switch(selectedOperation) {
	    	  case "insert":
	    		  if(selectedTable.equals("customer")) {
	    			  	queryWorked = askUserForCustomerInfo(connection);
		    		}
		    		else if(selectedTable.equals("room")) {
		    			queryWorked = askUserForRoomInfo(connection);
		    		}
		    		else if(selectedTable.equals("hotel_chain")) {
		    			queryWorked = askUserForHotelChainInfo(connection);
		    		}
		    		else {
		    			queryWorked = askUserForStaffInfo(connection);
		    		}
	    	    break;
	    	  case "delete":
	    		if(selectedTable.equals("customer")) {
	    			String custId = askUserForCustomerId(connection);
	    			try {
	    				queryWorked = connection.deleteCustomer(custId);
	    			}
	    			catch(Exception e) {
	    				System.out.println(e);
	    			}
	    		}
	    		else if(selectedTable.equals("room")) {
	    			String roomId = askUserForRoomId(connection);
	    			try {
	    				queryWorked = connection.deleteRoom(roomId);
	    			}
	    			catch(Exception e) {
	    				System.out.println(e);
	    			}
	    			
	    		}
	    		else if(selectedTable.equals("hotel_chain")) {
	    			String hotelChainId = askUserForHotelChainId(connection);
	    			try {
	    				queryWorked = connection.deleteHotel(hotelChainId);
	    			}
	    			catch(Exception e) {
	    				System.out.println(e);
	    			}
	    			
	    		}
	    		else {
	    			String staffId = askUserForStaffId(connection);
	    			try {
	    				queryWorked = connection.deleteStaff(staffId);
	    			}
	    			catch(Exception e) {
	    				System.out.println(e);
	    			}
	    			
	    		}
	    	    break;
	    	  case "update":
	      	    System.out.println("Please use PGAdmin for this feature");
	      	    break;
	    	}
	    	
	    	if(queryWorked) {
	      		  System.out.println("Query "+selectedOperation+ " on table "+ selectedTable);
	      	 }else {
	      		  System.out.println("Query failed please try again later");
	      	 }
	    	adminWantToContinue = askUserIfDone();
    	}while(adminWantToContinue);
    }

	private static boolean askUserForStaffInfo(ConnectionDB connection) {
		System.out.println("Please enter the attributes of staff as follow: staff_id,first_name,middle_name,last_name,street_number,street_name,city,state_province,country,postal_code,sin,salary,employee_job,hotel_id");
		String[] staffAttribute;
		boolean worked;
		do
        { 
    		// This ask the user for its customer id
            try {
            	String allAttribiutes = scanner.nextLine();
            	staffAttribute = allAttribiutes.split(",");
            	worked =connection.insertStaff(staffAttribute[0],staffAttribute[1],staffAttribute[2],staffAttribute[3],staffAttribute[4],staffAttribute[5],staffAttribute[6],staffAttribute[7],staffAttribute[8],staffAttribute[9],staffAttribute[10],staffAttribute[11],staffAttribute[12],staffAttribute[13]);
            	break;
            }
            catch (Exception e)
            {
            	System.out.println("Please enter valid attributes");
                worked = false;
            }
        }
        while (true);
		return worked;
	}

	private static boolean askUserForHotelChainInfo(ConnectionDB connection) {
		System.out.println("Please enter the attributes of hotel chain as follow: hotel_id,brand_name,star_category,street_number,street_name,city,state_province,country,postal_code,contact_email,phone_number,manager_id");
		String[] hotelChainAttribute;
		boolean worked;
		do
        { 
    		// This ask the user for its customer id
            try {
            	String allAttribiutes = scanner.nextLine();
            	hotelChainAttribute = allAttribiutes.split(",");
            	worked =connection.insertHotel(hotelChainAttribute[0],hotelChainAttribute[1],hotelChainAttribute[2],hotelChainAttribute[3],hotelChainAttribute[4],hotelChainAttribute[5],hotelChainAttribute[6],hotelChainAttribute[7],hotelChainAttribute[8],hotelChainAttribute[9],hotelChainAttribute[10],hotelChainAttribute[11]);
            	break;
            }
            catch (Exception e)
            {
            	System.out.println("Please enter valid attributes");
                worked = false;
            }
        }
        while (true);
		return worked;
	}

	private static boolean askUserForRoomInfo(ConnectionDB connection) {
		System.out.println("Please enter the attributes of hotel chain as follow: room_id,hotel_id,price,room_capacity,is_extendable,view");
		String[] roomAttribute;
		boolean worked;
		do
        { 
    		// This ask the user for its customer id
            try {
            	String allAttribiutes = scanner.nextLine();
            	roomAttribute = allAttribiutes.split(",");
            	worked =connection.insertRoom(roomAttribute[0],roomAttribute[1],roomAttribute[2],roomAttribute[3],roomAttribute[4]);
            	break;
            }
            catch (Exception e)
            {
            	System.out.println("Please enter valid attributes");
                worked = false;
            }
        }
        while (true);
		return worked;
	}

	private static boolean askUserForCustomerInfo(ConnectionDB connection) {
		System.out.println("Please enter the attributes of hotel chain as follow: customer_id,first_name,middle_name,last_name,street_number,street_name,city,state_province,country,postal_code,sin,date_of_birth,phone");
		String[] customerAttribute;
		boolean worked;
		do
        { 
    		// This ask the user for its customer id
            try {
            	String allAttribiutes = scanner.nextLine();
            	customerAttribute = allAttribiutes.split(",");
            	worked =connection.insertCustomer(customerAttribute[0],customerAttribute[1],customerAttribute[2],customerAttribute[3],customerAttribute[4],customerAttribute[5],customerAttribute[6],customerAttribute[7],customerAttribute[8],customerAttribute[9],customerAttribute[10],customerAttribute[11],customerAttribute[12]);
            	break;
            }
            catch (Exception e)
            {
                System.out.println("Please enter valid attributes");
                worked = false;
            }
        }
        while (true);
		return worked;
	}
	
}
