package doctor.appointment.registration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import UtilityEntity.FeeCalculations;
import UtilityEntity.TimeCalculations;

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
import doctor.appointment.registration.entity.GetDrBySpecializationEntity;
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
	FeeCalculations feeCalculations = new FeeCalculations();
	TimeCalculations timeCalculations = new TimeCalculations();

	public ResponseObject saveDoctor(DoctorDetailsEntity doctorDetailsEntity) {
		int doctorId = (int) (Math.random() * (300 - 200 + 1) + 200);
		while (userRepository.checkDoctorId(doctorId)) {
			doctorId++;
		}
		doctorDetailsEntity.setDoctorId(doctorId);

		if (userRepository.checkDoctorId(doctorDetailsEntity.getEmailId()) == false) {
			userRepository.saveDoctor(doctorDetailsEntity);
			responseObject.setStatus(messages.getSuccessstatus());
			responseObject.setMessage(messages.getDradded());
		} else {
			responseObject.setStatus(messages.getFailedstatus());
			responseObject.setMessage(messages.getUniquemail());
		}
		return responseObject;
	}

	public ResponseObject deleteDr(int drId) {
		if (userRepository.DeleteDrbyDrId(drId) == 1) {
			responseObject.setStatus(messages.getSuccessstatus());
			responseObject.setMessage(messages.getDrdeleted());
		} else {
			responseObject.setStatus(messages.getFailedstatus());
			responseObject.setMessage(messages.getNodravailable());
		}
		return responseObject;
	}

	public ResponseObject deleteUser(int userId) {
		if (userRepository.DeleteUserById(userId) == 1) {
			responseObject.setStatus(messages.getSuccessstatus());
			responseObject.setMessage(messages.getUserdeleted());
		} else {
			responseObject.setStatus(messages.getFailedstatus());
			responseObject.setMessage(messages.getInvaliduserid());
		}
		return responseObject;
	}

	public ResponseObject addUser(UserRegistrationEntity userRegistrationEntity) {
		if (userRepository.checkUserMailId(userRegistrationEntity.getMailId()) == false) {
			if (userRepository.checkUserPhoneNumber(userRegistrationEntity.getPhoneNumber()) == false) {
				userRepository.addUser(userRegistrationEntity);
				responseObject.setStatus(messages.getSuccessstatus());
				responseObject.setMessage(String.format(messages.getUseradded(),
						userRepository.GetUserId(userRegistrationEntity.getMailId())));
			} else {
				responseObject.setStatus(messages.getFailedstatus());
				responseObject.setMessage(messages.getUniquephno());
			}
		} else {
			responseObject.setStatus(messages.getFailedstatus());
			responseObject.setMessage(messages.getUniquemail());
		}
		return responseObject;
	}

	public ResponseObject addRules(RulesEntity rulesEntity) {
		if (userRepository.checkUserRules()) {
			userRepository.addRules(rulesEntity);
			responseObject.setStatus(messages.getSuccessstatus());
			responseObject.setMessage(messages.getAddrules());
		} else {
			responseObject.setStatus(messages.getFailedstatus());
			responseObject.setMessage(messages.getRulesareadyadded());
		}

		return responseObject;
	}

	public ResponseObject updateRules(RulesEntity rulesEntity) {
		if (rulesEntity.getFee().getProcessingFee() != 0) {
			userRepository.updateProcessingFee(rulesEntity);
		}
		if (rulesEntity.getDeductions().getAfter4hr() != 0) {
			userRepository.updateAfter4hour(rulesEntity);
		}
		if (rulesEntity.getDeductions().getBefore4hr() != 0) {
			userRepository.updateBefore4hour(rulesEntity);
		}
		if (rulesEntity.getOffers().getSpecialOffer() != 0) {
			userRepository.updateSpecialDiscount(rulesEntity);
		}
		responseObject.setStatus(messages.getSuccessstatus());
		responseObject.setMessage(messages.getUpdaterules());
		return responseObject;
	}

	public Object findDoctor(GetDrBySpecializationEntity getDrBySpecializationEntity) {
		if (userRepository.checkUserIdFromUserDetails(getDrBySpecializationEntity.getUserId())) {
			if (userRepository.findBySpesialization(getDrBySpecializationEntity.getSpecialization()).isEmpty()) {
				responseObject.setStatus(messages.getFailedstatus());
				responseObject.setMessage(messages.getNodrwithspecialization());
				return responseObject;
			} else {
				return userRepository.findBySpesialization(getDrBySpecializationEntity.getSpecialization());
			}
		} else {
			responseObject.setStatus(messages.getFailedstatus());
			responseObject.setMessage(messages.getInvaliduserid());
		}
		return responseObject;
	}

	public ResponseObject checkDateAvailability(BookDoctorEntity bookDoctorEntity) {
		if (userRepository.checkUserIdFromUserDetails(bookDoctorEntity.getUserId())) {
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
						String drAvailabilityStatus = String.format(messages.getDravailable(),
								userRepository.getDrDetails(bookDoctorEntity.getDoctorId()).getName(),
								feeAfterDiscount);
						responseObject.setStatus(messages.getSuccessstatus());
						responseObject.setMessage(drAvailabilityStatus);
					} else {
						responseObject.setStatus(messages.getFailedstatus());
						responseObject.setMessage(String.format(messages.getDrnotavailintime(),
								userRepository.getDrDetails(bookDoctorEntity.getDoctorId()).getName()));
					}
				} catch (Exception e) {
					responseObject.setStatus(messages.getFailedstatus());
					responseObject.setMessage(String.format(messages.getDrnotavailonday(),
							userRepository.getDrDetails(bookDoctorEntity.getDoctorId()).getName()));
				}
			} catch (Exception e) {
				responseObject.setStatus(messages.getFailedstatus());
				responseObject.setMessage(messages.getEntercorrectdetails());
			}
		} else {
			responseObject.setStatus(messages.getFailedstatus());
			responseObject.setMessage(messages.getInvaliduserid());
		}
		return responseObject;
	}

	public ResponseObject confirmAppointment(ConfirmAppointmentEntity confirmAppointmentEntity) throws ParseException {
		if (userRepository.checkUserIdFromUserDetails(confirmAppointmentEntity.getBookingInfo().getUserId())) {
			if (userRepository.checkDoctorId(confirmAppointmentEntity.getBookingInfo().getDoctorId())) {
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
							if (userRepository.checkUserIdFromBookingDetails(
									confirmAppointmentEntity.getBookingInfo().getUserId()) == false) {
								availabilityList.remove(confirmAppointmentEntity.getBookingInfo().getTime());
								availabilityJson.remove(confirmAppointmentEntity.getBookingInfo().getDay());
								availabilityJson.put(confirmAppointmentEntity.getBookingInfo().getDay(),
										availabilityList);
								String newavailabilityJson = availabilityJson.toJSONString();
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
								responseObject.setStatus(messages.getSuccessstatus());
								responseObject.setMessage(String.format(messages.getAppointmentconfirm(), bookingId));
							} else {
								responseObject.setStatus(messages.getFailedstatus());
								responseObject.setMessage(messages.getTockenidalready());
							}
						} else {
							responseObject.setStatus(messages.getFailedstatus());
							responseObject.setMessage(String.format(messages.getPaycorrectamount(), feeAfterDiscount));
						}
					} else {
						responseObject.setStatus(messages.getFailedstatus());
						responseObject.setMessage(messages.getTimenotavailable());
					}
				} else {
					responseObject.setStatus(messages.getFailedstatus());
					responseObject.setMessage(messages.getEntercorrectday());
				}
			} else {
				responseObject.setStatus(messages.getFailedstatus());
				responseObject.setMessage(messages.getNodravailable());
			}
		} else {
			responseObject.setStatus(messages.getFailedstatus());
			responseObject.setMessage(messages.getInvaliduserid());
		}
		return responseObject;
	}

	public ResponseObject rescheduleAppointment(RescheduleEntity rescheduleEntity) throws ParseException {
		if (userRepository.checkUserIdFromUserDetails(rescheduleEntity.getUserId())) {
			if (userRepository.CheckBookingId(rescheduleEntity.getBookingId())) {
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
					responseObject.setStatus(messages.getSuccessstatus());
					responseObject.setMessage(
							String.format(messages.getReschedulecompleted(), rescheduleEntity.getNewTime()));
				} else {
					responseObject.setStatus(messages.getFailedstatus());
					responseObject.setMessage(messages.getTimenotavailable());
				}
			} else {
				responseObject.setStatus(messages.getFailedstatus());
				responseObject.setMessage(messages.getWrongbookingid());
			}
		} else {
			responseObject.setStatus(messages.getFailedstatus());
			responseObject.setMessage(messages.getInvaliduserid());
		}
		return responseObject;
	}

	public ResponseObject cancelAppointment(CancelAppointmentEntity cancelAppointmentEntity) throws ParseException {
		if (userRepository.checkUserIdFromBookingDetails(cancelAppointmentEntity.getUserId())) {
			String availability = userRepository
					.getDrDetails(
							userRepository.GetBookedUserByUserId(cancelAppointmentEntity.getUserId()).getDoctorId())
					.getAvailability();
			List<String> availabilityList = new ArrayList<String>();
			JSONObject availabilityJson = (JSONObject) parser.parse(availability);
			availabilityList = (List<String>) availabilityJson
					.get(userRepository.GetBookedUserByUserId(cancelAppointmentEntity.getUserId()).getBookedDay());
			if (userRepository.CheckBookingId(cancelAppointmentEntity.getBookingId())) {
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
					userRepository.DeleteAppointmentByUserId(cancelAppointmentEntity.getUserId());
					responseObject.setStatus(messages.getSuccessstatus());
					responseObject.setMessage(String.format(messages.getAppointmentcancelled(), refundAmount));
				} else {
					int paidFee = userRepository.GetBookedUserByUserId(cancelAppointmentEntity.getUserId())
							.getFeePaid();
					int refundAmount = paidFee - ((paidFee * userRepository.getOffers().getBefore4hr()) / 100);
					userRepository.DeleteAppointmentByUserId(cancelAppointmentEntity.getUserId());
					responseObject.setStatus(messages.getSuccessstatus());
					responseObject.setMessage(String.format(messages.getAppointmentcancelled(), refundAmount));
				}
			} else {
				responseObject.setStatus(messages.getFailedstatus());
				responseObject.setMessage(messages.getWrongbookingid());
			}
		} else {
			responseObject.setStatus(messages.getFailedstatus());
			responseObject.setMessage(messages.getInvaliduserid());
		}
		return responseObject;
	}

	public Object showBooking(CancelAppointmentEntity showBookingEntity) {
		if (userRepository.checkUserIdFromUserDetails(showBookingEntity.getUserId())) {
			if (userRepository.CheckBookingId(showBookingEntity.getBookingId())) {
				return userRepository.GetBookedUserByUserId(showBookingEntity.getUserId());
			} else {
				responseObject.setStatus(messages.getFailedstatus());
				responseObject.setMessage(messages.getWrongbookingid());
			}
		} else {
			responseObject.setStatus(messages.getFailedstatus());
			responseObject.setMessage(messages.getInvaliduserid());

		}
		return responseObject;
	}

}
