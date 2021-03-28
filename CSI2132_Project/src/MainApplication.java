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
    	//ArrayList<String> customers = connection.getCustomer();
    	System.out.println("Which customer are you "); 
    	
    	ArrayList<String> hotel_brands = connection.getHotelBrand();
        System.out.print("Choose which hotel brand you want to book in ");
    	for(int i =0; i < hotel_brands.size(); i++) {
        	System.out.print(hotel_brands.get(i)+" ("+i+")"+", ");
        }
    
        System.out.print(": ");
        int selectedInt;
        do
        { 
            try {
            	String selectedHB = scanner.nextLine();
            	selectedInt = Integer.parseInt(selectedHB);
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
        
        ArrayList<String> hotel_chains = connection.getHotelChain(hotel_brands.get(selectedInt));
        
        for(String chain: hotel_chains) {
        	System.out.println(chain);
        }
    }

}
