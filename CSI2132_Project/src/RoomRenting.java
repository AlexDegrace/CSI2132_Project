import java.sql.Date;

public class RoomRenting {
	
	public String room_id;
	public Date start_date;
	public Date end_date;
	public String price;
	public Boolean was_booked;
	
	public RoomRenting(String room_id, Date start_date, Date end_date, String price,  Boolean was_booked) {
		this.room_id=room_id;
		this.start_date=start_date;
		this.end_date=end_date;
		this.price=price;
		this.was_booked=was_booked;
		
	}
	
	public String toString() {
		return "{room_id: "+ this.room_id + ", start_date: " + this.start_date + ", end_date: " + this.end_date + ", price: "+ this.price + ", was_booked: "+this.was_booked+"}";
	}

}
