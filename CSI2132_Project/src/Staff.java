
public class Staff {
	
	private String staff_id; 
	private String last_name;
	private String first_name;

	public Staff(String staff_id, String first_name, String last_name) {
		this.staff_id=staff_id;
		this.last_name= last_name;
		this.first_name = first_name;
	}
	
	public String toString() {
		return "{staff_id: "+ this.staff_id + ", last_name: " + this.last_name + ", first_name: " + this.first_name + "}";
	}

	public String getStaffId() {
		return this.staff_id;
	}
}
