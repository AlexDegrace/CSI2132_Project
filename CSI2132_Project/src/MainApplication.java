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
    
    //TODO: add all customers and choose which one you are
    public static void main(String[] args) {
    	
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
            else{
                System.out.println("Please enter employee, customer or admin");
            }
        }
        return typeOfUser;
    }
    
    private static String askUserForCustomerId(ConnectionDB connection){
    	ArrayList<String> customers;
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
    	// List all of the customers
    	for(String cu : customers) {
    		System.out.println(cu);
    	}
    	System.out.println("Which customer are you (choose a valid id)"); 
    	//This store the custId for creating booking later
    	String custId;
    	do
        { 
    		// This ask the user for its customer id
            try {
            	custId = scanner.nextLine();
            	int custIdInt = Integer.parseInt(custId);
            	if(custIdInt<1 || custIdInt>customers.size()) {
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
    	ConnectionDB connection = new ConnectionDB();
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
    	
    	ConnectionDB connection = new ConnectionDB();
    	
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
    	
    }

}
