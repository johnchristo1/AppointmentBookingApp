package doctor.appointment.registration.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

public class BookDoctorEntity {
	@NotEmpty(message = "Day is not given")
	public String day;
	@NotEmpty(message = "Time is not given")
	public String time;
	@Range(min = 100, max=999)
	public int doctorId;
	
	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
