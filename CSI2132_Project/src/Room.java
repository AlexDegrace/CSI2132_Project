
public class Room {
	
	public String room_id;
	public String hotel_id;
	public String price;
	public String room_capacity;
	public Boolean is_extendable;
	
	public Room(String room_id, String hotel_id, String price, String room_capacity, Boolean is_extendable) {
		this.room_id=room_id;
		this.hotel_id=hotel_id;
		this.price=price;
		this.room_capacity=room_capacity;
		this.is_extendable=is_extendable;

		
	}
	
	public String toString() {
		return "{room_id: "+ this.room_id+ ", hotel_id: " + this.hotel_id + ", price: " + this.price + ", room_capacity: "+ this.room_capacity+", is_extendable: "+ this.is_extendable +"}";
	}

}
