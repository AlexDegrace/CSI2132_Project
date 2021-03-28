
public class HotelChain {
	
	public String star_category;
	public String street_number;
	public String street_name;
	public String city;
	public String state_province;
	public String phone_number;
	
	public HotelChain(String star_category,String street_number,String street_name,String city,String state_province,String phone_number) {
		this.star_category = star_category;
		this.street_number = street_number;
		this.street_name = street_name;
		this.city = city;
		this.state_province = state_province;
		this.phone_number = phone_number;
		
	}
	
	public String toString() {
		return "{star_category: "+ this.star_category + ", street_number: " + this.street_number + ", street_name: " + this.street_name + ", city: "+ this.city+", state_province: "+ this.state_province + ", phone_number: "+phone_number+"}";
	}

}
