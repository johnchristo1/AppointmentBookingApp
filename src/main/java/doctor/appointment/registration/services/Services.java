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

//add dr
	public ResponseObject saveDoctor(DoctorDetailsEntity doctorDetailsEntity) {
		int drId = (int) (Math.random() * (300 - 200 + 1) + 200);
		while (userRepository.GetDrIdList().contains(drId)) {
			drId++;
		}
		doctorDetailsEntity.setDoctorId(drId);

		if (userRepository.GetDrRegisteredMailIdList().contains(doctorDetailsEntity.getEmailId()) == false) {
			userRepository.saveDoctor(doctorDetailsEntity);
			responseObject.setStatus(messages.getSuccessStatus());
			responseObject.setMessage(messages.getDrAdded());
		} else {
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getUniqueMail());
		}
		return responseObject;
	}

//delete dr
	public ResponseObject deleteDr(int drId) {
		if (userRepository.DeleteDrbyDrId(drId) == 1) {
			responseObject.setStatus(messages.getSuccessStatus());
			responseObject.setMessage(messages.getDrDeleted());
		} else {
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getNoDrAvailable());
		}
		return responseObject;
	}

//delete user
	public ResponseObject deleteUser(int userId) {
		if (userRepository.DeleteUserById(userId) == 1) {
			responseObject.setStatus(messages.getSuccessStatus());
			responseObject.setMessage(messages.getUserDeleted());
		} else {
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getNoUserId());
		}
		return responseObject;
	}

	public ResponseObject addUser(UserRegistrationEntity userRegistrationEntity) {
		if (userRepository.UserRegisteredMailIdList().contains(userRegistrationEntity.getMailId()) == false) {
			if (userRepository.UserRegisteredPhNoList().contains(userRegistrationEntity.getPhoneNumber()) == false) {
				userRepository.addUser(userRegistrationEntity);
				responseObject.setStatus(messages.getSuccessStatus());
				responseObject.setMessage(String.format(messages.getUserAdded(),
						userRepository.GetUserId(userRegistrationEntity.getMailId())));
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

//		if (rulesEntity.getFee().getProcessingFee() ) {
//
//		}

		rulesEntity.getOffers().getSpecialOffer();
		rulesEntity.getDeductions().getBefore4hr();
		rulesEntity.getDeductions().getAfter4hr();

		userRepository.updateRules(rulesEntity);
		responseObject.setStatus(messages.getSuccessStatus());
		responseObject.setMessage(messages.getUpdateRules());
		return responseObject;
	}

//find doctor
	public Object findDoctor(String specialization, int userId) {
		if (userRepository.GetUserIdListFromUserDetails().contains(userId)) {
			if (userRepository.findBySpesialization(specialization).isEmpty()) {
				responseObject.setStatus(messages.getFailedStatus());
				responseObject.setMessage(messages.getNoDrWithSpecialization());
				return responseObject;
			} else {
				return userRepository.findBySpesialization(specialization);
			}
		} else

		{
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getNoUserId());
		}
		return responseObject;
	}

//book Dr 
	public ResponseObject checkDateAvailability(BookDoctorEntity bookDoctorEntity) {
		if (userRepository.GetUserIdListFromUserDetails().contains(bookDoctorEntity.getUserId())) {
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
								userRepository.getDrDetails(bookDoctorEntity.getDoctorId()).getName(),
								feeAfterDiscount);
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
		} else {
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getNoUserId());
		}
		return responseObject;
	}

//confirm Dr
	public ResponseObject confirmAppointment(ConfirmAppointmentEntity confirmAppointmentEntity) throws ParseException {
		if (userRepository.GetUserIdListFromUserDetails()
				.contains(confirmAppointmentEntity.getBookingInfo().getUserId())) {
			if (userRepository.GetDrIdList().contains(confirmAppointmentEntity.getBookingInfo().getDoctorId())) {
				String availability = userRepository
						.getDrDetails(confirmAppointmentEntity.getBookingInfo().getDoctorId()).getAvailability();
				List<String> availabilityList = new ArrayList<String>();
				JSONObject availabilityJson = (JSONObject) parser.parse(availability);
				availabilityList = (List<String>) availabilityJson
						.get(confirmAppointmentEntity.getBookingInfo().getDay());
				if (availabilityList != null) {
					if (availabilityList.contains(confirmAppointmentEntity.getBookingInfo().getTime())) {
						int specialDiscount = userRepository.getOffers().getSpecialDiscount();
						int processingFee = userRepository.getOffers().getPorcessingFee();
						int consultationFee = userRepository
								.getDrDetails(confirmAppointmentEntity.getBookingInfo().getDoctorId())
								.getConsultationFee();
						long discount = (long) (((consultationFee + processingFee) * specialDiscount) / 100);
						long feeAfterDiscount = (long) (consultationFee + processingFee) - discount;
						if (feeAfterDiscount == confirmAppointmentEntity.getPaymentInfo().getAmount()) {
							if (userRepository.GetUserIdList()
									.contains(confirmAppointmentEntity.getBookingInfo().getUserId()) == false) {
								availabilityList.remove(confirmAppointmentEntity.getBookingInfo().getTime());
								availabilityJson.remove(confirmAppointmentEntity.getBookingInfo().getDay());
								availabilityJson.put(confirmAppointmentEntity.getBookingInfo().getDay(),
										availabilityList);
								String newavailabilityJson = availabilityJson.toJSONString();
								// save time of confirm in table
								DateFormat dateFormat = new SimpleDateFormat("HH-mm");
								Calendar calendar = Calendar.getInstance();
								String currentTime = dateFormat.format(calendar.getTime());
								confirmAppointmentEntity.setTimeOfBooking(currentTime);
								userRepository.saveUser(confirmAppointmentEntity);
								userRepository.updateDrAvailability(newavailabilityJson,
										confirmAppointmentEntity.getBookingInfo().getDoctorId());
								int bookingId = userRepository
										.GetBookedUserByUserId(confirmAppointmentEntity.getBookingInfo().getUserId())
										.getBookingId();
								responseObject.setStatus(messages.getSuccessStatus());
								responseObject.setMessage(String.format(messages.getAppointmentConfirm(), bookingId));
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
			} else {
				responseObject.setStatus(messages.getFailedStatus());
				responseObject.setMessage(messages.getNoDrAvailable());
			}
		} else {
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getNoUserId());
		}
		return responseObject;
	}

	public ResponseObject rescheduleAppointment(RescheduleEntity rescheduleEntity) throws ParseException {
		if (userRepository.GetUserIdListFromUserDetails().contains(rescheduleEntity.getUserId())) {
			boolean flag = false;
			try {
				if (userRepository.GetBookedUserByUserId(rescheduleEntity.getUserId())
						.getBookingId() == rescheduleEntity.getBookingId()) {
					flag = true;
				}
			} catch (Exception e) {
				flag = false;
			}
			if (flag) {
				String availability = userRepository
						.getDrDetails(userRepository.GetBookedUserByUserId(rescheduleEntity.getUserId()).getDoctorId())
						.getAvailability();
				List<String> availabilityList = new ArrayList<String>();
				JSONObject availabilityJson = (JSONObject) parser.parse(availability);
				availabilityList = (List<String>) availabilityJson
						.get(userRepository.GetBookedUserByUserId(rescheduleEntity.getUserId()).getBookedDay());
				if (availabilityList.contains(rescheduleEntity.getNewTime())) {
					availabilityList.remove(rescheduleEntity.getNewTime());
					availabilityList
							.add(userRepository.GetBookedUserByUserId(rescheduleEntity.getUserId()).getBookedTime());
					availabilityJson
							.remove(userRepository.GetBookedUserByUserId(rescheduleEntity.getUserId()).getBookedDay());
					availabilityJson.put(
							userRepository.GetBookedUserByUserId(rescheduleEntity.getUserId()).getBookedDay(),
							availabilityList);
					String newavailabilityJson = availabilityJson.toJSONString();
					userRepository.updateDrAvailability(newavailabilityJson,
							userRepository.GetBookedUserByUserId(rescheduleEntity.getUserId()).getDoctorId());
					userRepository.UpdateUserDetails(rescheduleEntity.getNewTime(), rescheduleEntity.getUserId());
					responseObject.setStatus(messages.getSuccessStatus());
					responseObject.setMessage(
							String.format(messages.getRescheduleCompleted(), rescheduleEntity.getNewTime()));
				} else {
					responseObject.setStatus(messages.getFailedStatus());
					responseObject.setMessage(messages.getTimeNotAvailable());
				}
			} else {
				responseObject.setStatus(messages.getFailedStatus());
				responseObject.setMessage(messages.getWrongBookingId());
			}
		} else {
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getNoUserId());
		}
		return responseObject;
	}

	public ResponseObject cancelAppointment(CancelAppointmentEntity cancelAppointmentEntity) throws ParseException {
		String availability = null;
		boolean flag = false;
		try {
			availability = userRepository
					.getDrDetails(
							userRepository.GetBookedUserByUserId(cancelAppointmentEntity.getUserId()).getDoctorId())
					.getAvailability();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}

		if (flag) {
			List<String> availabilityList = new ArrayList<String>();
			JSONObject availabilityJson = (JSONObject) parser.parse(availability);
			availabilityList = (List<String>) availabilityJson
					.get(userRepository.GetBookedUserByUserId(cancelAppointmentEntity.getUserId()).getBookedDay());
			boolean flag2 = false;
			try {
				if (userRepository.GetBookedUserByUserId(cancelAppointmentEntity.getUserId())
						.getBookingId() == cancelAppointmentEntity.getBookingId()) {
					flag2 = true;
				}
			} catch (Exception e) {
				flag2 = false;
			}
			if (flag2) {
				availabilityList
						.add(userRepository.GetBookedUserByUserId(cancelAppointmentEntity.getUserId()).getBookedTime());
				availabilityJson.put(
						userRepository.GetBookedUserByUserId(cancelAppointmentEntity.getUserId()).getBookedDay(),
						availabilityList);
				String newavailabilityJson = availabilityJson.toJSONString();
				userRepository.updateDrAvailability(newavailabilityJson,
						userRepository.GetBookedUserByUserId(cancelAppointmentEntity.getUserId()).getDoctorId());
				DateFormat dateFormat = new SimpleDateFormat("HH-mm");
				Calendar calendar = Calendar.getInstance();
				String currentTime = dateFormat.format(calendar.getTime());
				String[] currentTimeList = currentTime.split("-");
				int currentTimeInMinutes = 60 * Integer.parseInt(currentTimeList[0])
						+ Integer.parseInt(currentTimeList[1]);
				String timeOfBooking = userRepository.GetBookedUserByUserId(cancelAppointmentEntity.getUserId())
						.getTimeOfBooking();
				String[] timeOfBookingList = timeOfBooking.split("-");
				int timeOfBookingInMinutes = 60 * Integer.parseInt(timeOfBookingList[0])
						+ Integer.parseInt(timeOfBookingList[1]);
				if ((currentTimeInMinutes - timeOfBookingInMinutes) < 240) {
					int paidFee = userRepository.GetBookedUserByUserId(cancelAppointmentEntity.getUserId())
							.getFeePaid();
					int refundAmount = paidFee - ((paidFee * userRepository.getOffers().getWithin4hr()) / 100);

					System.out.println("paidFee if : " + paidFee);
					System.out.println("refundAmount if : " + refundAmount);

					responseObject.setStatus(messages.getSuccessStatus());
					responseObject.setMessage(String.format(messages.getAppointmentCancelled(), refundAmount));
				} else {
					int paidFee = userRepository.GetBookedUserByUserId(cancelAppointmentEntity.getUserId())
							.getFeePaid();
					int refundAmount = paidFee - ((paidFee * userRepository.getOffers().getBefore4hr()) / 100);

					System.out.println("paidFee else : " + paidFee);
					System.out.println("refundAmount else : " + refundAmount);

					responseObject.setStatus(messages.getSuccessStatus());
					responseObject.setMessage(String.format(messages.getAppointmentCancelled(), refundAmount));
				}
			} else {
				responseObject.setStatus(messages.getFailedStatus());
				responseObject.setMessage(messages.getWrongBookingId());
			}
		} else {
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getInvalidUserId()); // invalid bookid getIncorrectDetails()
		}
		return responseObject;
	}

	public Object showBooking(CancelAppointmentEntity showBookingEntity) {

		if (userRepository.GetUserIdListFromUserDetails().contains(showBookingEntity.getUserId())) {

			boolean flag = false;
			try {
				if (userRepository.GetBookedUserByUserId(showBookingEntity.getUserId())
						.getBookingId() == showBookingEntity.getBookingId()) {
					flag = true;
				}
			} catch (Exception e) {
				flag = false;
				
			}
			if (flag) {
				System.out.println(" true..!!");
				return userRepository.GetBookedUserByUserId(showBookingEntity.getUserId());
				
			} else {
				responseObject.setStatus(messages.getFailedStatus());
				responseObject.setMessage(messages.getNoUserId());
			}
		} else {
			responseObject.setStatus(messages.getFailedStatus());
			responseObject.setMessage(messages.getWrongBookingId());
		}
		return responseObject;
	}

}
