package doctor.appointment.registration.entity;

public class GetBookedUser {
	public int id;
	public int feePaid;
	public String bookedDay;
	public int doctorId;
	public String userName;
	public String bookedDoctor;
	public String bookedTime;
	public int tockenId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getTockenId() {
		return tockenId;
	}
	public void setTockenId(int tockenId) {
		this.tockenId = tockenId;
	}

}
