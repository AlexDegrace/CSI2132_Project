import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class MainApplication {

    final static private String CUSTOMER_USERNAME = "customer";
    final static private String STAFF_USERNAME = "employee";
    
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
        	
        }catch(Exception e) {
        	System.out.println(e);
        }
    }

    //Ask the user if he is an employee or a customer
    public static int getCredential(){
        boolean isLogIn =false;
        int typeOfUser = 0;
        while(!isLogIn){
            System.out.println("Are you a employee or customer?");
            String username = scanner.nextLine();

            if(username.equals(CUSTOMER_USERNAME) ){
                typeOfUser = 1;
                isLogIn = true;
            }

            else if(username.equals(STAFF_USERNAME) ){
                typeOfUser = 2;
                isLogIn = true;
            }

            else{
                System.out.println("Please enter employee or customer");
            }
        }
        return typeOfUser;


    }
    
    public static void handleCustomer() throws InterruptedException {
    	ConnectionDB connection = new ConnectionDB();
    	// Ask the user for its customer id
    	System.out.println("Which customer are you (choose a valid id)"); 
    	ArrayList<String> customers;
    	do{
    		try {
        		customers = connection.getCustomer();
        		break;
        	}
        	catch(Exception e) {
        		System.out.println(e);
        		Thread.sleep(5000);
        	}
    	}
    	while(true);
    	
    	
    	
    	// List all of the customers
    	for(String cu : customers) {
    		System.out.println(cu);
    	}
    	
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
    	
    	ArrayList<String> hotel_brands = connection.getHotelBrand();
    	// This let you choose the hotel brand you want to book
        System.out.print("Choose which hotel brand you want to book in ");
    	for(int i =0; i < hotel_brands.size(); i++) {
        	System.out.print(hotel_brands.get(i)+" ("+i+")"+", ");
        }
    
        System.out.print(": ");
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
        
        ArrayList<HotelChain> hotel_chains = connection.getHotelChain(hotel_brands.get(selectedInt));
        
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
        
        // Query all the available rooms for the hotelId and start and end date
        ArrayList<Room> availableRooms = connection.getRoomByHotelAndDate(selectedHotelId, startDate,endDate );
        
        System.out.println("Choose which room you want to book (choose a valid room id)");
        ArrayList<Integer> availableRoomId = new ArrayList<Integer>();
        
        //Print all the room available and store there ids in an array list to validate id later
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
        
        //Store the room price of the selected room
        String roomPrice = "-1";
        for(int i =0; i < availableRooms.size(); i++) {
        	if(availableRooms.get(i).getRoomId().equals(selectedRoomId)) {
        		roomPrice = availableRooms.get(i).getPrice();
        	}
        }
        
        // Try to create a room_booking and a had_booking for the information collected from the user
        boolean bookingCreated = connection.addRoomBookingAndHasBooking(selectedRoomId,startDate,endDate,roomPrice,custId);
        
        //Display if the insert worked or not
        if(bookingCreated) {
        	System.out.println("A booking has been created for room "+ selectedRoomId +" in hotel "+selectedHotelId +" from " + startDate + " to "+ endDate + " for " + roomPrice);
        }
        else {
        	System.out.println("The booking could not be created please try later.");
        }
        
    }

}
