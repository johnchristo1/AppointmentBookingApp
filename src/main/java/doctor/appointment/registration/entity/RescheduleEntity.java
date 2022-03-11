package doctor.appointment.registration.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

public class RescheduleEntity {

	@Range(min = 1, max = 999, message = "Tocken id cannot be empty")
	public int tockenId;
	@NotEmpty(message = "New Time is not given")
	public String newTime;

	public int getTockenId() {
		return tockenId;
	}

	public void setTockenId(int tockenId) {
		this.tockenId = tockenId;
	}

	public String getNewTime() {
		return newTime;
	}

	public void setNewTime(String newTime) {
		this.newTime = newTime;
	}

}
