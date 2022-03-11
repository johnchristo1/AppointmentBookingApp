package doctor.appointment.registration.controller;

import java.util.List;

import javax.validation.Valid;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import doctor.appointment.registration.entity.BookDoctorEntity;
import doctor.appointment.registration.entity.CancelAppointmentEntity;
import doctor.appointment.registration.entity.ConfirmAppointmentEntity;
import doctor.appointment.registration.entity.DoctorDetailsEntity;
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
	public Object findDoctor(@RequestBody DoctorDetailsEntity doctorDetailsEntity) {
		return services.findByDoctor(doctorDetailsEntity.getSpecialization());
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

	@PostMapping("/test")
	public void test(@RequestBody DoctorDetailsEntity doctorDetailsEntity) {

//		System.out.println("out : " + userRepository.GetBookedUserByTockenId(11).getUser_name());
	}
}