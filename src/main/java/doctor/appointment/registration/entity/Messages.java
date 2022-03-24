package doctor.appointment.registration.entity;

import org.springframework.stereotype.Component;

@Component
public class Messages {
	final static String successStatus = "200";
	final static String failedStatus = "404";
	final static String drAdded = "Doctor added succesfully";
	final static String addRules = "Rules added succesfully";
	final static String updateRules = "Rules updated succesfully";
	final static String drAvailable = "Dr %s is available in the selected time, Total consultation fee is %d, including processing fee and special discount. ";
	final static String drNotAvailInTime = "Sorry Dr %s is not available in the selected time, please change the time and try again";
	final static String drNotAvailOnDay = "Sorry Dr %s is not available on the selected day, please select an available day";
	final static String appointmentConfirm = "Appoitnment confiremed, Your booking id is %d , Thank you.";
	final static String noDrAvailable = "No doctor is available with the given doctorId";
	final static String rescheduleCompleted = "Rescheduled your appointment, new time slot is %s .";
	final static String timeNotAvailable = "Time you have selected is not available, please select another time.";
	final static String appointmentCancelled = "Your appointment is cancelled succesfully, You will get an amount of %d as refund.";
	final static String incorrectDetails = "You have enetered incorrect tocken id, please check your input and try again.";
	final static String noDrWithSpecialization = "Sorry, No doctor is available with the given specialization";
	final static String enterCorrectdetails = "Please enter correct doctorId";
	final static String enterCorrectDay = "Please enter a correct day, reffer availability of doctor";
	final static String enterCorrectBookedTime = "Please enter a correct booked time.";
	final static String enterCorrectBookeDay = "Please enter a correct booked day";
	final static String userAdded = "User added succesfully, your user id is %d .";
	final static String uniquePhNo = "Phone number is already registered. ";
	final static String uniqueMail = "Email id is already registered. ";
	final static String payCorrectAmount = "Please pay correct fee, Rs. %d. ";
	final static String tockenIdAlready = "User id already registered. ";
	final static String drDeleted = "Doctor deleted succesfully. ";
	final static String noUserId = "Please enter a valid user id. ";
	final static String wrongBookingId = "Wrong booking id, please check the booking id.";
	final static String invalidUserId = "You have entered invali user id.";
	final static String userDeleted = "User deleted succesfully.";
	final static String timeFormat = "HH-mm";
	final static String stringToSplitTime = "-";
	final static String rulesAreadyAdded = "Rules already added, please use update rules to make any changes in rules.";

	public static String getRulesareadyadded() {
		return rulesAreadyAdded;
	}

	public static String getStringtosplittime() {
		return stringToSplitTime;
	}

	public static String getTimeformat() {
		return timeFormat;
	}

	public static String getSuccessstatus() {
		return successStatus;
	}

	public static String getFailedstatus() {
		return failedStatus;
	}

	public static String getDradded() {
		return drAdded;
	}

	public static String getAddrules() {
		return addRules;
	}

	public static String getUpdaterules() {
		return updateRules;
	}

	public static String getDravailable() {
		return drAvailable;
	}

	public static String getDrnotavailintime() {
		return drNotAvailInTime;
	}

	public static String getDrnotavailonday() {
		return drNotAvailOnDay;
	}

	public static String getAppointmentconfirm() {
		return appointmentConfirm;
	}

	public static String getNodravailable() {
		return noDrAvailable;
	}

	public static String getReschedulecompleted() {
		return rescheduleCompleted;
	}

	public static String getTimenotavailable() {
		return timeNotAvailable;
	}

	public static String getAppointmentcancelled() {
		return appointmentCancelled;
	}

	public static String getIncorrectdetails() {
		return incorrectDetails;
	}

	public static String getNodrwithspecialization() {
		return noDrWithSpecialization;
	}

	public static String getEntercorrectdetails() {
		return enterCorrectdetails;
	}

	public static String getEntercorrectday() {
		return enterCorrectDay;
	}

	public static String getEntercorrectbookedtime() {
		return enterCorrectBookedTime;
	}

	public static String getEntercorrectbookeday() {
		return enterCorrectBookeDay;
	}

	public static String getUseradded() {
		return userAdded;
	}

	public static String getUniquephno() {
		return uniquePhNo;
	}

	public static String getUniquemail() {
		return uniqueMail;
	}

	public static String getPaycorrectamount() {
		return payCorrectAmount;
	}

	public static String getTockenidalready() {
		return tockenIdAlready;
	}

	public static String getDrdeleted() {
		return drDeleted;
	}

	public static String getNouserid() {
		return noUserId;
	}

	public static String getWrongbookingid() {
		return wrongBookingId;
	}

	public static String getInvaliduserid() {
		return invalidUserId;
	}

	public static String getUserdeleted() {
		return userDeleted;
	}

}
