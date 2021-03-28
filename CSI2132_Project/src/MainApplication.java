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
    
    public static void handleCustomer() {
    	ConnectionDB connection = new ConnectionDB();
    	// Ask the user for its customer id
    	System.out.println("Which customer are you (choose a valid id)"); 
    	ArrayList<String> customers = connection.getCustomer();
    	
    	// List all of the customers
    	for(String cu : customers) {
    		System.out.println(cu);
    	}
    	
    	//This store the custId for creating booking later
    	int custIdInt;
    	
    	do
        { 
    		// This ask the user for its customer id
            try {
            	String custId = scanner.nextLine();
            	custIdInt = Integer.parseInt(custId);
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
        
        
        
    }

}
