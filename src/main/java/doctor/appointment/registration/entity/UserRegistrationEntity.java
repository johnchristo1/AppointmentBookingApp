package doctor.appointment.registration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_details")
public class UserRegistrationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int userId;

	@Column(name = "user_name")
	@NotEmpty(message = "Please enter a username. ")
	public String userName;

	@Column(name = "mail_id", unique = true)
	@NotEmpty(message = "Please enter a mailId. ")
	@Email(message = "Please provide a valid email address")
	public String mailId;

	@Column(name = "phone_number", unique = true)
	@NotEmpty(message = "Please enter a phoneNumber. ")
	@Size(min = 10, max = 10, message = "Please provide a valid phone number")
	public String phoneNumber;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
