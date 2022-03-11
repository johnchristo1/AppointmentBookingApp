package doctor.appointment.registration.entity;

import org.springframework.stereotype.Component;

@Component
public class ResponseObject {

	public String message;
	public String status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
