package doctor.appointment.registration.entity;

public class GetBookedUser {
	public int bookingId;
	public int feePaid;
	public String bookedDay;
	public int doctorId;
	public String userName;
	public String bookedDoctor;
	public String bookedTime;
	public int userId;
	public String timeOfBooking;

	public String getTimeOfBooking() {
		return timeOfBooking;
	}

	public void setTimeOfBooking(String timeOfBooking) {
		this.timeOfBooking = timeOfBooking;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getFeePaid() {
		return feePaid;
	}

	public void setFeePaid(int feePaid) {
		this.feePaid = feePaid;
	}

	public String getBookedDay() {
		return bookedDay;
	}

	public void setBookedDay(String bookedDay) {
		this.bookedDay = bookedDay;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBookedDoctor() {
		return bookedDoctor;
	}

	public void setBookedDoctor(String bookedDoctor) {
		this.bookedDoctor = bookedDoctor;
	}

	public String getBookedTime() {
		return bookedTime;
	}

	public void setBookedTime(String bookedTime) {
		this.bookedTime = bookedTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
