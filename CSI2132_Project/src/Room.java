
public class Room {
	
	public String room_id;
	public String hotel_id;
	public String price;
	public String room_capacity;
	public String is_extendable;
	public String view;
	
	public Room(String room_id, String hotel_id, String price, String room_capacity, String is_extendable, String view) {
		this.room_id=room_id;
		this.hotel_id=hotel_id;
		this.price=price;
		this.room_capacity=room_capacity;
		this.is_extendable=is_extendable;
		this.view = view;

		
	}
	
	public String toString() {
		return "{room_id: "+ this.room_id+ ", hotel_id: " + this.hotel_id + ", price: " + this.price + ", room_capacity: "+ this.room_capacity+", is_extendable: "+ this.is_extendable +", view: "+ this.view+"}";
	}
	
	public String getRoomId() {
		return this.room_id;
	}
	
	public String getPrice() {
		return this.price;
	}

}
