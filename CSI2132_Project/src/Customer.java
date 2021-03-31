
public class Customer {

	public String customer_id;
	public String first_name;
	public String last_name;
	
	public Customer(String customer_id ,String first_name,String last_name) {
		this.customer_id = customer_id;
		this.first_name = first_name;
		this.last_name = last_name;
	}

	public String toString() {
		return "{customer_id: "+ this.customer_id + ", first_name: " + this.first_name + ", last_name: " + this.last_name + " }";
	}
	
	public String getCustomerId() {
		return customer_id;
	}
	
}
