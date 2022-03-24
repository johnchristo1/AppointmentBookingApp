package doctor.appointment.registration.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.validation.Valid;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import doctor.appointment.registration.entity.BookDoctorEntity;
import doctor.appointment.registration.entity.CancelAppointmentEntity;
import doctor.appointment.registration.entity.ConfirmAppointmentEntity;
import doctor.appointment.registration.entity.DoctorDetailsEntity;
import doctor.appointment.registration.entity.GetDrBySpecializationEntity;
import doctor.appointment.registration.entity.RescheduleEntity;
import doctor.appointment.registration.entity.ResponseObject;
import doctor.appointment.registration.entity.RulesEntity;
import doctor.appointment.registration.entity.UserRegistrationEntity;
import doctor.appointment.registration.repository.UserRepository;
import doctor.appointment.registration.services.Services;

/**
 * This application is used to book appointment to consult a doctor, user can
 * reschedule and cancel the booked appointment using this application, This
 * application can be used by Admin and User, Add doctor, add rules and offers
 * and update rule for payment are done by admin. User can do Register user,
 * check availability of the doctor, confirm appointment, reschedule and cancel
 * appointment.
 * 
 * @author John Christo
 * @version 1.0
 * @since 23-03-2022
 */

@RestController
@RequestMapping("/api/v2")
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	Services services;

	/**
	 * This method is used to add details of the doctor to the database table.
	 * 
	 * @param DoctorDetailsEntity
	 * @return ResponseObject which contains message and status
	 */

	@PostMapping("/admin/doctor")
	public ResponseObject addDoctor(@Valid @RequestBody DoctorDetailsEntity doctorDetailsEntity) {
		return services.saveDoctor(doctorDetailsEntity);
	}

	/**
	 * This method is used to delete a doctor by giving doctor id as input.
	 * 
	 * @param doctor id
	 * @return ResponseObject which contains message and status
	 */

	@DeleteMapping("/admin/doctor/{drId}")
	public ResponseObject deleteDr(@PathVariable int drId) {
		return services.deleteDr(drId);
	}

	/**
	 * This method is used to delete a user by giving user id as input.
	 * 
	 * @param user id
	 * @return ResponseObject which contains message and status
	 */
	@DeleteMapping("/admin/deleteUser/{userId}")
	public ResponseObject deleteUser(@PathVariable int userId) {
		return services.deleteUser(userId);
	}

	/**
	 * This method is used to add details of user to the database table.
	 * 
	 * @param UserRegistrationEntity
	 * @return ResponseObject which contains message and status
	 */
	@PostMapping("/user/addUser")
	public ResponseObject addUser(@Valid @RequestBody UserRegistrationEntity userRegistrationEntity) {
		return services.addUser(userRegistrationEntity);
	}

	/**
	 * This method is used to add rules and offers for the user to the database
	 * table.
	 * 
	 * @param RulesEntity
	 * @return ResponseObject which contains message and status
	 */
	@PostMapping("admin/addRules")
	public ResponseObject addRules(@Valid @RequestBody RulesEntity rulesEntity) {
		return services.addRules(rulesEntity);
	}

	/**
	 * This method is used to update rules and offers for the user to the database
	 * table.
	 * 
	 * @param RulesEntity
	 * @return ResponseObject which contains message and status
	 */
	@PostMapping("/admin/updateRules")
	public ResponseObject updateRules(@Valid @RequestBody RulesEntity rulesEntity) {
		return services.updateRules(rulesEntity);
	}

	/**
	 * This method is used to find list of doctors by giving specialization of the
	 * doctor as an input.
	 * 
	 * @param GetDrBySpecializationEntity
	 * @return ResponseObject which contains message and status or list of doctor
	 *         details
	 */
	@GetMapping("/user/findDoctor")
	public Object findDoctor(@RequestBody GetDrBySpecializationEntity getDrBySpecializationEntity) {
		return services.findDoctor(getDrBySpecializationEntity);
	}

	/**
	 * This method is used check if the selected doctor is available for the
	 * selected time and day.
	 * 
	 * @param BookDoctorEntity
	 * @return ResponseObject which contains message and status
	 */
	@GetMapping("/user/bookDoctor")
	public ResponseObject checkAvailability(@Valid @RequestBody BookDoctorEntity bookDoctorEntity) {
		return services.checkDateAvailability(bookDoctorEntity);
	}

	/**
	 * This method is to confirm appointment by giving details of user, doctor to be
	 * booked and amount to be paid for the confirmation.
	 * 
	 * @param ConfirmAppointmentEntity
	 * @return ResponseObject which contains message and status
	 */
	@PutMapping("/user/confirmAppointment")
	public ResponseObject confrimAppointment(@Valid @RequestBody ConfirmAppointmentEntity confirmAppointmentEntity)
			throws ParseException {
		return services.confirmAppointment(confirmAppointmentEntity);
	}

	/**
	 * By using this method user can change booked time, but user cannot change
	 * date.
	 * 
	 * @param RescheduleEntity
	 * @return ResponseObject which contains message and status
	 */
	@PutMapping("/user/rescheduleAppointment")
	public ResponseObject rescheduleAppointment(@Valid @RequestBody RescheduleEntity rescheduleEntity)
			throws ParseException {
		return services.rescheduleAppointment(rescheduleEntity);
	}

	/**
	 * This method is to cancel the confirmed appoinment, user will get refund of
	 * the amount paid according to the time in which the user cancels the
	 * appointment.
	 * 
	 * @param CancelAppointmentEntity
	 * @return ResponseObject which contains message and status
	 */
	@PutMapping("/user/cancelAppointment")
	public ResponseObject rescheduleAppointment(@Valid @RequestBody CancelAppointmentEntity cancelAppointmentEntity)
			throws ParseException {
		return services.cancelAppointment(cancelAppointmentEntity);
	}

	/**
	 * Using this method details of the booked user can be seen.
	 * 
	 * @param CancelAppointmentEntity
	 * @return ResponseObject which contains message and status or details of the
	 *         booked user
	 */
	@GetMapping("/user/showBooking")
	public Object showBooking(@Valid @RequestBody CancelAppointmentEntity showBookingEntity) {
		return services.showBooking(showBookingEntity);
	}

}