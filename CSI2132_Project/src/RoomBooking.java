import java.sql.Date;

public class RoomBooking {
	
	public String room_id;
	public Date start_date;
	public Date end_date;
	public String price;
	public Boolean is_paid;
	public Boolean was_cancelled;
	
	public RoomBooking(String room_id, Date start_date, Date end_date, String price, Boolean is_paid, Boolean was_cancelled) {
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

}
