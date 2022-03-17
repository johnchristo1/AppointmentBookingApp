package doctor.appointment.registration.entity;

import javax.validation.constraints.NotEmpty;

public class GetDrBySpecializationEntity {

	public int userId;
	@NotEmpty(message = "Please enter specialization to get the details of doctor")
	public String specialization;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

}
