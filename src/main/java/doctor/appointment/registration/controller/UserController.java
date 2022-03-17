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

@RestController
@RequestMapping("/api/v2")
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	Services services;

// Add doctor details
	@PostMapping("/admin/addDoctors")
	public ResponseObject addDoctor(@Valid @RequestBody DoctorDetailsEntity doctorDetailsEntity) {
		return services.saveDoctor(doctorDetailsEntity);
	}

	@DeleteMapping("/admin/deleteDoctors/{drId}")
	public ResponseObject deleteDr(@PathVariable int drId) {
		return services.deleteDr(drId);
	}
	@DeleteMapping("/admin/deleteUser/{userId}")
	public ResponseObject deleteUser(@PathVariable int userId) {
		return services.deleteUser(userId);
	}

//add user
	@PostMapping("/user/addUser")
	public ResponseObject addUser(@Valid @RequestBody UserRegistrationEntity userRegistrationEntity) {
		return services.addUser(userRegistrationEntity);
	}

	// add rules
	@PostMapping("admin/addRules")
	public ResponseObject addRules(@Valid @RequestBody RulesEntity rulesEntity) {
		return services.addRules(rulesEntity);
	}

// update rules for discount and return
	@PostMapping("/admin/updateRules")
	public ResponseObject updateRules(@Valid @RequestBody RulesEntity rulesEntity) {
		return services.updateRules(rulesEntity);
	}

// get by specialization
	@GetMapping("/user/findDoctor")
	public Object findDoctor(@RequestBody GetDrBySpecializationEntity getDrBySpecializationEntity) {
		return services.findDoctor(getDrBySpecializationEntity.getSpecialization(),
				getDrBySpecializationEntity.getUserId());
	}

// check if the the dr is available in the given time and date
	@GetMapping("/user/bookDoctor")
	public ResponseObject checkAvailability(@Valid @RequestBody BookDoctorEntity bookDoctorEntity) {
		return services.checkDateAvailability(bookDoctorEntity);
	}

	@PutMapping("/user/confirmAppointment")
	public ResponseObject confrimAppointment(@Valid @RequestBody ConfirmAppointmentEntity confirmAppointmentEntity)
			throws ParseException {
		return services.confirmAppointment(confirmAppointmentEntity);
	}

	@PutMapping("/user/rescheduleAppointment")
	public ResponseObject rescheduleAppointment(@Valid @RequestBody RescheduleEntity rescheduleEntity)
			throws ParseException {
		return services.rescheduleAppointment(rescheduleEntity);
	}

	@PutMapping("/user/cancelAppointment")
	public ResponseObject rescheduleAppointment(@Valid @RequestBody CancelAppointmentEntity cancelAppointmentEntity)
			throws ParseException {
		return services.cancelAppointment(cancelAppointmentEntity);
	}

	@GetMapping("/user/showBooking")
	public Object showBooking(@RequestBody CancelAppointmentEntity showBookingEntity) {
		return services.showBooking(showBookingEntity);	
	}
}