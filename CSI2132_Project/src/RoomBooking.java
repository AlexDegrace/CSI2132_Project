import java.sql.Date;

public class RoomBooking {
	
	public String room_id;
	public String start_date;
	public String end_date;
	public String price;
	public String is_paid;
	public String was_cancelled;
	
	public RoomBooking(String room_id, String start_date, String end_date, String price, String is_paid, String was_cancelled) {
		this.room_id=room_id;
		this.start_date=start_date;
		this.end_date=end_date;
		this.price=price;
		this.is_paid=is_paid;
		this.was_cancelled=was_cancelled;
		
	}
	
	public String toString() {
		return "{room_id: "+ this.room_id + ", start_date: " + this.start_date + ", end_date: " + this.end_date + ", price: "+ this.price+", is_paid: "+ this.is_paid + ", was_cancelled: "+was_cancelled+"}";
	}
	
	public String getRoomId() {
		return room_id;
	}
	public String getStartDate() {
		return start_date;
	}
	public String getEndDate() {
		return end_date;
	}
	public String getPrice() {
		return price;
	}

}
