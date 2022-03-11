package doctor.appointment.registration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

@Table(name = "booking_details")
@Entity
public class BookingDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int id;

	@Column(name = "booked_doctor")
	@NotEmpty(message = "Doctor name is not given")
	public String nameOfDoctor;

	@Column(name = "booked_day")
	@NotEmpty(message = "Day is not given")
	public String day;

	@Column(name = "booked_time")
	@NotEmpty(message = "Time is not given")
	public String time;

	@Column(name = "doctor_id")
	@Range(min = 100, max = 999)
	public int doctorId;

	@Column(name = "fee_paid")
	@Range(min = 100, max = 999)
	public int amount;

	@Column(name = "user_name")
	@NotEmpty(message = "Name in debit card is not given")
	public String nameAsInTheCard;

	public String getNameOfDoctor() {
		return nameOfDoctor;
	}

	public void setNameOfDoctor(String nameOfDoctor) {
		this.nameOfDoctor = nameOfDoctor;
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

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getNameAsInTheCard() {
		return nameAsInTheCard;
	}

	public void setNameAsInTheCard(String nameAsInTheCard) {
		this.nameAsInTheCard = nameAsInTheCard;
	}
}
