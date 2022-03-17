package doctor.appointment.registration.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

public class CancelAppointmentEntity {

	@Range(min = 1, max = 999, message = "Tocken id cannot be empty")
	public int userId;
	@Range(min = 1, max = 999, message = "Booking id cannot be empty")
	public int bookingId;

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
