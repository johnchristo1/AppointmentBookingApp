package doctor.appointment.registration.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

public class CancelAppointmentEntity {

	@Range(min = 1, max = 999, message = "Tocken id cannot be empty")
	public int tockenId;

	public int getTockenId() {
		return tockenId;
	}

	public void setTockenId(int tockenId) {
		this.tockenId = tockenId;
	}

}
