package doctor.appointment.registration.entity;

import org.springframework.stereotype.Component;

@Component
public class Messages {
	public String successStatus = "200";
	public String failedStatus = "404";
	public String drAdded = "Doctor added succesfully";
	public String addRules = "Rules added succesfully";
	public String updateRules = "Rules updated succesfully";
	public String drAvailable = "Dr %s is available in the selected time, Total fee is %d, including processing fee and special discount. ";
	public String drNotAvailInTime = "Sorry Dr %s is not available in the selected time, please change the time and try again";
	public String drNotAvailOnDay = "Sorry Dr %s is not available on the selected day, please select an available day";
	public String appointmentConfirm = "Appoitnment confiremed, Thank you.";
	public String noDrAvailable = "No doctor is available with the given doctorId";
	public String rescheduleCompleted = "Rescheduled your appointment, new time slot is %s .";
	public String timeNotAvailable = "Time you have selected is not available, please select another time.";
	public String appointmentCancelled = "Your appointment is cancelled succesfully, You will get an amount of %d as refund.";
	public String incorrectDetails = "You have enetered incorrect tocken id, please check your input and try again.";
	public String noDrWithSpecialization = "Sorry, No doctor is available with the given specialization";
	public String enterCorrectdetails = "Please enter correct doctorId";
	public String enterCorrectDay = "Please enter a correct day, reffer availability of doctor";
	public String enterCorrectBookedTime = "Please enter a correct booked time.";
	public String enterCorrectBookeDay = "Please enter a correct booked day";
	public String userAdded = "User added succesfully, your tocken id is %d .";
	public String uniquePhNo = "Phone number is already registered. ";
	public String uniqueMail = "Email id is already registered. ";
	public String payCorrectAmount = "Please pay correct fee, Rs. %d. ";
	public String tockenIdAlready = "Tocken id already registered. ";
	

	public String getTockenIdAlready() {
		return tockenIdAlready;
	}

	public String getPayCorrectAmount() {
		return payCorrectAmount;
	}

	public String getUniquePhNo() {
		return uniquePhNo;
	}

	public String getUniqueMail() {
		return uniqueMail;
	}

	public String getUserAdded() {
		return userAdded;
	}

	public String getEnterCorrectBookedTime() {
		return enterCorrectBookedTime;
	}

	public String getEnterCorrectBookeDay() {
		return enterCorrectBookeDay;
	}

	public String getNoDrAvailable() {
		return noDrAvailable;
	}

	public String getEnterCorrectDay() {
		return enterCorrectDay;
	}

	public String getEnterCorrectdetails() {
		return enterCorrectdetails;
	}

	public String getNoDrWithSpecialization() {
		return noDrWithSpecialization;
	}

	public String getIncorrectDetails() {
		return incorrectDetails;
	}

	public String getAppointmentCancelled() {
		return appointmentCancelled;
	}

	public String getTimeNotAvailable() {
		return timeNotAvailable;
	}

	public String getRescheduleCompleted() {
		return rescheduleCompleted;
	}

	public String getAppointmentConfirm() {
		return appointmentConfirm;
	}

	public String getDrNotAvailOnDay() {
		return drNotAvailOnDay;
	}

	public String getDrNotAvailInTime() {
		return drNotAvailInTime;
	}

	public String getFailedStatus() {
		return failedStatus;
	}

	public String getDrAvailable() {
		return drAvailable;
	}

	public String getSuccessStatus() {
		return successStatus;
	}

	public String getDrAdded() {
		return drAdded;
	}

	public void setDrAdded(String drAdded) {
		this.drAdded = drAdded;
	}

	public String getAddRules() {
		return addRules;
	}

	public String getUpdateRules() {
		return updateRules;
	}
}
