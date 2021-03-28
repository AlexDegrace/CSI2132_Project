import java.util.ArrayList;
import java.util.Scanner;

public class MainApplication {

    final static private String CUSTOMER_USERNAME = "customer";
    final static private String CUSTOMER_PASSWORD = "password";
    final static private String STAFF_USERNAME = "employee";
    final static private String STAFF_PASSWORD = "password";
    
    private static Scanner scanner = new Scanner(System.in);
    
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
            System.out.println("Enter username");
            String username = scanner.nextLine();
            System.out.println("Enter password");
            String password = scanner.nextLine();

            if(username.equals(CUSTOMER_USERNAME) && password.equals(CUSTOMER_PASSWORD)){
                typeOfUser = 1;
                isLogIn = true;
            }

            else if(username.equals(STAFF_USERNAME) && password.equals(STAFF_PASSWORD)){
                typeOfUser = 2;
                isLogIn = true;
            }

            else{
                System.out.println("Sorry but there is no user with that username and password (Hint: Customer-> (u: customer p: password) Employee-> (u: employee p: password))");
            }
        }
        return typeOfUser;


    }
    
    public static void handleCustomer() {
    	ConnectionDB connection = new ConnectionDB();
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
