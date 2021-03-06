package doctor.appointment.registration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.json.simple.JSONObject;

@Entity
@Table(name = "doctors_list")
public class DoctorDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotEmpty(message = "Doctor name is not given")
	@Column(name = "name")
	public String name;

	@Column(name = "doctor_id")
	public int doctorId;

	@NotEmpty(message = "Specialization is not given")
	@Column(name = "specialization")
	public String specialization;

	@Range(min = 1, max = 999, message = "Consultation fee cannot be empty")
	@Column(name = "consultation_fee")
	public int consultationFee;

	@Column(name = "email_id")
	@Email(message = "please enter a valid email address")
	@NotEmpty(message = "Email id is not given")
	public String emailId;

	public JSONObject availability;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public JSONObject getAvailability() {
		return availability;
	}

	public void setAvailability(JSONObject availability) {
		this.availability = availability;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public int getConsultationFee() {
		return consultationFee;
	}

	public void setConsultationFee(int consultationFee) {
		this.consultationFee = consultationFee;
	}
}
