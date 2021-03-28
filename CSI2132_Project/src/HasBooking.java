import java.sql.Date;

public class HasBooking {
	
	public Date start_date;
	public Date end_date;
	public String room_id;
	public String customer_id;
	
	public HasBooking(Date start_date, Date end_date, String room_id, String customer_id) {
		this.start_date=start_date;
		this.end_date=end_date;
		this.room_id=room_id;
		this.customer_id=customer_id;
		
	}
	
	public String toString() {
		return "{start_date: "+ this.start_date + ", end_date: " + this.end_date + ", room_id: " + this.room_id + ", customer_id: "+ this.customer_id+"}";
	}

}
