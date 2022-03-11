package doctor.appointment.registration.entity;

public class GetUserDetailsEntity {
	public int id;
	public String userName;
	public int doctorId;
	public int feePaid;
	public String bookedTime;
	public String bookedDay;
	public String bookedDoctor;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public int getFeePaid() {
		return feePaid;
	}

	public void setFeePaid(int feePaid) {
		this.feePaid = feePaid;
	}

	public String getBookedTime() {
		return bookedTime;
	}

	public void setBookedTime(String bookedTime) {
		this.bookedTime = bookedTime;
	}

	public String getBookedDay() {
		return bookedDay;
	}

	public void setBookedDay(String bookedDay) {
		this.bookedDay = bookedDay;
	}

	public String getBookedDoctor() {
		return bookedDoctor;
	}

	public void setBookedDoctor(String bookedDoctor) {
		this.bookedDoctor = bookedDoctor;
	}

}
