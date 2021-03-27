import java.util.Scanner;

public class MainApplication {

    final static private String CUSTOMER_USERNAME = "customer";
    final static private String CUSTOMER_PASSWORD = "password";
    final static private String STAFF_USERNAME = "employee";
    final static private String STAFF_PASSWORD = "password";
    
    public static void main(String[] args) {
    	
    	ConnectionDB connection = new ConnectionDB();
    	connection.getConnection();
        
        /*int typeOfUser = getCredential();
        System.out.println(typeOfUser);

        //if user is a customer
        if(typeOfUser == 1){
            
        }*/
    }


    public static int getCredential(){
        Scanner scanner = new Scanner(System.in);
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
        scanner.close();
        return typeOfUser;


    }

}
