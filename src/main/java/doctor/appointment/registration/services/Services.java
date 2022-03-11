package doctor.appointment.registration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import doctor.appointment.registration.entity.BookDoctorEntity;
import doctor.appointment.registration.entity.CancelAppointmentEntity;
import doctor.appointment.registration.entity.ConfirmAppointmentEntity;
import doctor.appointment.registration.entity.DoctorDetailsEntity;
import doctor.appointment.registration.entity.Messages;
import doctor.appointment.registration.entity.RescheduleEntity;
import doctor.appointment.registration.entity.ResponseObject;
import doctor.appointment.registration.entity.RulesEntity;
import doctor.appointment.registration.entity.UserRegistrationEntity;
import doctor.appointment.registration.repository.UserRepository;

@Service
public class Services {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ResponseObject responseObject;
	@Autowired
	Messages messages;
	JSONParser parser = new JSONParser();

	public ResponseObject saveDoctor(DoctorDetailsEntity doctorDetailsEntity) {
		int drId = (int) (Math.random() * (300 - 200 + 1) + 200);
		while (userRepository.GetDrIdList().contains(drId)) {
			drId++;
		}
		doctorDetailsEntity.setDoctorId(drId);
		userRepository.saveDoctor(doctorDetailsEntity);
		responseObject.setStatus(messages.getSuccessStatus());
		responseObject.setMessage(messages.getDrAdded());

		return responseObject;
	}

	public ResponseObject addUser(UserRegistrationEntity userRegistrationEntity) {
		if (userRepository.UserRegisteredMailIdList().contains(userRegistrationEntity.getMailId()) == false) {
			if (userRepository.UserRegisteredPhNoList().contains(userRegistrationEntity.getPhoneNumber()) == false) {
				userRepository.addUser(userRegistrationEntity);
				responseObject.setStatus(messages.getSuccessStatus());
				responseObject.setMessage(String.format(messages.getUserAdded(),
						userRepository.GetTockenId(userRegistrationEntity.getMailId())));
			} else {
				responseObject.setStatus(messages.getFailedStatus());
				responseObject.setMessage(messages.getUniquePhNo());
			}
		} else {
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getUniqueMail());
		}
		return responseObject;
	}

	public ResponseObject addRules(RulesEntity rulesEntity) {
		userRepository.addRules(rulesEntity);
		responseObject.setStatus(messages.getSuccessStatus());
		responseObject.setMessage(messages.getAddRules());
		return responseObject;
	}

	public ResponseObject updateRules(RulesEntity rulesEntity) {
		userRepository.updateRules(rulesEntity);
		responseObject.setStatus(messages.getSuccessStatus());
		responseObject.setMessage(messages.getUpdateRules());
		return responseObject;
	}

//find doctor
	public Object findByDoctor(String specialization) {
		if (userRepository.findBySpesialization(specialization).isEmpty()) {
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getNoDrWithSpecialization());
			return responseObject;
		} else {
			return userRepository.findBySpesialization(specialization);
		}
	}

//book Dr 
	public ResponseObject checkDateAvailability(BookDoctorEntity bookDoctorEntity) {
		try {
			String availability = userRepository.getDrDetails(bookDoctorEntity.getDoctorId()).getAvailability();
			List<String> availabilityList = new ArrayList<String>();
			try {
				JSONObject availabilityJson = (JSONObject) parser.parse(availability);
				availabilityList = (List<String>) availabilityJson.get(bookDoctorEntity.getDay());
				if (availabilityList.contains(bookDoctorEntity.getTime())) {
					int specialDiscount = userRepository.getOffers().getSpecialDiscount();
					int processingFee = userRepository.getOffers().getPorcessingFee();
					int consultationFee = userRepository.getDrDetails(bookDoctorEntity.getDoctorId())
							.getConsultationFee();
					long discount = (long) (((consultationFee + processingFee) * specialDiscount) / 100);
					long feeAfterDiscount = (long) (consultationFee + processingFee) - discount;
					String drAvailabilityStatus = String.format(messages.getDrAvailable(),
							userRepository.getDrDetails(bookDoctorEntity.getDoctorId()).getName(), feeAfterDiscount);
					responseObject.setStatus(messages.getSuccessStatus());
					responseObject.setMessage(drAvailabilityStatus);
				} else {
					responseObject.setStatus(messages.getFailedStatus());
					responseObject.setMessage(String.format(messages.getDrNotAvailInTime(),
							userRepository.getDrDetails(bookDoctorEntity.getDoctorId()).getName()));
				}
			} catch (Exception e) {
				responseObject.setStatus(messages.getFailedStatus());
				responseObject.setMessage(String.format(messages.getDrNotAvailOnDay(),
						userRepository.getDrDetails(bookDoctorEntity.getDoctorId()).getName()));
			}
		} catch (Exception e) {
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getEnterCorrectdetails());
		}
		return responseObject;
	}

//confirm Dr
	public ResponseObject confirmAppointment(ConfirmAppointmentEntity confirmAppointmentEntity) throws ParseException {
		try {
			String availability = userRepository.getDrDetails(confirmAppointmentEntity.getBookingInfo().getDoctorId())
					.getAvailability();
			List<String> availabilityList = new ArrayList<String>();
			JSONObject availabilityJson = (JSONObject) parser.parse(availability);
			availabilityList = (List<String>) availabilityJson.get(confirmAppointmentEntity.getBookingInfo().getDay());
			if (availabilityList != null) {
				if (availabilityList.contains(confirmAppointmentEntity.getBookingInfo().getTime())) {
					int specialDiscount = userRepository.getOffers().getSpecialDiscount();
					int processingFee = userRepository.getOffers().getPorcessingFee();
					int consultationFee = userRepository
							.getDrDetails(confirmAppointmentEntity.getBookingInfo().getDoctorId()).getConsultationFee();
					long discount = (long) (((consultationFee + processingFee) * specialDiscount) / 100);
					long feeAfterDiscount = (long) (consultationFee + processingFee) - discount;
					if (feeAfterDiscount == confirmAppointmentEntity.getPaymentInfo().getAmount()) {
						if (userRepository.GetTockenIdList()
								.contains(confirmAppointmentEntity.getBookingInfo().getTockenId()) == false) {

							availabilityList.remove(confirmAppointmentEntity.getBookingInfo().getTime());
							availabilityJson.remove(confirmAppointmentEntity.getBookingInfo().getDay());
							availabilityJson.put(confirmAppointmentEntity.getBookingInfo().getDay(), availabilityList);
							String newavailabilityJson = availabilityJson.toJSONString();
							userRepository.saveUser(confirmAppointmentEntity);
							userRepository.updateDrAvailability(newavailabilityJson,
									confirmAppointmentEntity.getBookingInfo().getDoctorId());
							responseObject.setStatus(messages.getSuccessStatus());
							responseObject.setMessage(messages.getAppointmentConfirm());
						} else {
							responseObject.setStatus(messages.getFailedStatus());
							responseObject.setMessage(messages.getTockenIdAlready());
						}
					} else {
						responseObject.setStatus(messages.getFailedStatus());
						responseObject.setMessage(String.format(messages.getPayCorrectAmount(), feeAfterDiscount));
					}
				} else {
					responseObject.setStatus(messages.getFailedStatus());
					responseObject.setMessage(messages.getTimeNotAvailable());
				}
			} else {
				responseObject.setStatus(messages.getFailedStatus());
				responseObject.setMessage(messages.getEnterCorrectDay());
			}
		} catch (Exception e) {
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getNoDrAvailable());
		}
		return responseObject;
	}

	public ResponseObject rescheduleAppointment(RescheduleEntity rescheduleEntity) throws ParseException {
		try {
			String availability = userRepository
					.getDrDetails(userRepository.GetBookedUserByTockenId(rescheduleEntity.getTockenId()).getDoctorId())
					.getAvailability();
			List<String> availabilityList = new ArrayList<String>();
			JSONObject availabilityJson = (JSONObject) parser.parse(availability);
			availabilityList = (List<String>) availabilityJson
					.get(userRepository.GetBookedUserByTockenId(rescheduleEntity.getTockenId()).getBookedDay());
			if (availabilityList != null) {
				if (availabilityList.contains(rescheduleEntity.getNewTime())) {
					availabilityList.remove(rescheduleEntity.getNewTime());
					availabilityList.add(
							userRepository.GetBookedUserByTockenId(rescheduleEntity.getTockenId()).getBookedTime());
					availabilityJson.remove(
							userRepository.GetBookedUserByTockenId(rescheduleEntity.getTockenId()).getBookedDay());
					availabilityJson.put(
							userRepository.GetBookedUserByTockenId(rescheduleEntity.getTockenId()).getBookedDay(),
							availabilityList);
					String newavailabilityJson = availabilityJson.toJSONString();
					userRepository.updateDrAvailability(newavailabilityJson,
							userRepository.GetBookedUserByTockenId(rescheduleEntity.getTockenId()).getDoctorId());
					userRepository.UpdateUserDetails(rescheduleEntity.getNewTime(), rescheduleEntity.getTockenId());
					responseObject.setStatus(messages.getSuccessStatus());
					responseObject.setMessage(
							String.format(messages.getRescheduleCompleted(), rescheduleEntity.getNewTime()));
				} else {
					responseObject.setStatus(messages.getFailedStatus());
					responseObject.setMessage(messages.getTimeNotAvailable());
				}
			} else {
				responseObject.setStatus(messages.getFailedStatus());
				responseObject.setMessage(messages.getEnterCorrectBookeDay());
			}

		} catch (Exception e) {
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getNoDrAvailable());
		}
		return responseObject;
	}

	public ResponseObject cancelAppointment(CancelAppointmentEntity cancelAppointmentEntity) throws ParseException {
		String availability = userRepository
				.getDrDetails(
						userRepository.GetBookedUserByTockenId(cancelAppointmentEntity.getTockenId()).getDoctorId())
				.getAvailability();
		List<String> availabilityList = new ArrayList<String>();
		JSONObject availabilityJson = (JSONObject) parser.parse(availability);
		availabilityList = (List<String>) availabilityJson
				.get(userRepository.GetBookedUserByTockenId(cancelAppointmentEntity.getTockenId()).getBookedDay());

		if (userRepository.GetTockenIdList().contains(cancelAppointmentEntity.getTockenId())) {
			availabilityList
					.add(userRepository.GetBookedUserByTockenId(cancelAppointmentEntity.getTockenId()).getBookedTime());
			availabilityJson.put(
					userRepository.GetBookedUserByTockenId(cancelAppointmentEntity.getTockenId()).getBookedDay(),
					availabilityList);
			String newavailabilityJson = availabilityJson.toJSONString();
			userRepository.updateDrAvailability(newavailabilityJson,
					userRepository.GetBookedUserByTockenId(cancelAppointmentEntity.getTockenId()).getDoctorId());
			DateFormat dateFormat = new SimpleDateFormat("HH-mm");
			Calendar calendar = Calendar.getInstance();
			String currentTime = dateFormat.format(calendar.getTime());
			String[] currentTimeList = currentTime.split("-");
			int currentTimeInMinutes = 60 * Integer.parseInt(currentTimeList[0]) + Integer.parseInt(currentTimeList[1]);
			String registeredTime = userRepository.GetBookedUserByTockenId(cancelAppointmentEntity.getTockenId())
					.getBookedTime();
			String[] registeredTimeList = registeredTime.split("-");
			String[] newRegisteredTimeList = registeredTimeList[0].split(":");
			int registeredTimeInMinutes = 60 * Integer.parseInt(newRegisteredTimeList[0])
					+ Integer.parseInt(newRegisteredTimeList[1]);
			if ((registeredTimeInMinutes - currentTimeInMinutes) > 240) {
				// id, userid
				int paidFee = userRepository
						.getUserDetails(
								userRepository.GetBookedUserByTockenId(cancelAppointmentEntity.getTockenId()).getId())
						.getFeePaid();
				int refundAmount = paidFee - ((paidFee * userRepository.getOffers().getBefore4hr()) / 100);
				System.out.println("refundAmount : " + refundAmount);
				responseObject.setStatus(messages.getSuccessStatus());
				responseObject.setMessage(String.format(messages.getAppointmentCancelled(), refundAmount));
				System.out.println("refundAmount");
			} else {
				int paidFee = userRepository
						.getUserDetails(
								userRepository.GetBookedUserByTockenId(cancelAppointmentEntity.getTockenId()).getId())
						.getFeePaid();
				int refundAmount = paidFee - ((paidFee * userRepository.getOffers().getWithin4hr()) / 100);
				responseObject.setStatus(messages.getSuccessStatus());
				responseObject.setMessage(String.format(messages.getAppointmentCancelled(), refundAmount));
			}

		} else {
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getIncorrectDetails()); // invalid bookid
		}
		return responseObject;
	}

}
