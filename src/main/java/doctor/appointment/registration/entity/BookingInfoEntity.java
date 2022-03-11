package doctor.appointment.registration.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

public class BookingInfoEntity {

	@NotEmpty(message = "Doctor name is not given")
	public String nameOfDoctor;
	
	@Range(min = 100, max = 999)
	public int doctorId;
	
	@Range(min = 1, max = 999)
	public int tockenId;
	

	public int getTockenId() {
		return tockenId;
	}

	public void setTockenId(int tockenId) {
		this.tockenId = tockenId;
	}

	@NotEmpty(message = "Day is not given")
	public String day;

	@NotEmpty(message = "Time is not given")
	public String time;


	public String getNameOfDoctor() {
		return nameOfDoctor;
	}

	public void setNameOfDoctor(String nameOfDoctor) {
		this.nameOfDoctor = nameOfDoctor;
	}
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
