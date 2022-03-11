package doctor.appointment.registration.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import doctor.appointment.registration.entity.ConfirmAppointmentEntity;
import doctor.appointment.registration.entity.DoctorDetailsEntity;
import doctor.appointment.registration.entity.GetBookedUser;
import doctor.appointment.registration.entity.GetDoctorDetailsEntity;
import doctor.appointment.registration.entity.GetPaymentRulesEntity;
import doctor.appointment.registration.entity.GetUserDetailsEntity;
import doctor.appointment.registration.entity.Messages;
import doctor.appointment.registration.entity.ResponseObject;
import doctor.appointment.registration.entity.RulesEntity;
import doctor.appointment.registration.entity.UserRegistrationEntity;

@Repository
public class UserRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	ResponseObject responseObject;
	@Autowired
	Messages messages;
	@Autowired
	ConfirmAppointmentEntity confirmAppointmentEntity;

//admin - save doctors
	public void saveDoctor(DoctorDetailsEntity doctorDetailsEntity) {
		jdbcTemplate.update(
				"INSERT INTO doctors_list (name, doctor_id, specialization, consultation_fee,availability) VALUES (?,?,?,?,?)",
				new Object[] { doctorDetailsEntity.getName(), doctorDetailsEntity.getDoctorId(),
						doctorDetailsEntity.getSpecialization(), doctorDetailsEntity.getConsultationFee(),
						doctorDetailsEntity.getAvailability().toString() });
	}

//save user
	public void addUser(UserRegistrationEntity userRegistrationEntity) {
		jdbcTemplate.update("INSERT INTO user_details (tocken_id, mail_id, phone_number, user_name) VALUES (?,?,?,?)",
				new Object[] { userRegistrationEntity.getTockenId(), userRegistrationEntity.getMailId(),
						userRegistrationEntity.getPhoneNumber(), userRegistrationEntity.getUserName() });
	}

//admin - add rules 
	public void addRules(RulesEntity rulesEntity) {
		jdbcTemplate.update(
				"INSERT INTO payment_rules (porcessing_fee, special_discount, before4hr,within4hr) VALUES (?,?,?,?)",
				new Object[] { rulesEntity.getFee().getProcessingFee(), rulesEntity.getOffers().getSpecialOffer(),
						rulesEntity.getDeductions().getBefore4hr(), rulesEntity.getDeductions().getAfter4hr() });
	}

//admin - update rules 
	public void updateRules(RulesEntity rulesEntity) {
		jdbcTemplate.update("UPDATE payment_rules SET porcessing_fee=?, special_discount=?, before4hr=?,within4hr=?",
				new Object[] { rulesEntity.getFee().getProcessingFee(), rulesEntity.getOffers().getSpecialOffer(),
						rulesEntity.getDeductions().getBefore4hr(), rulesEntity.getDeductions().getAfter4hr() });
	}

// user-find doctor details by specialization
	public List<GetDoctorDetailsEntity> findBySpesialization(String specialization) {
		return jdbcTemplate.query("SELECT * FROM appoitnmentapptables.doctors_list WHERE specialization=?;",
				new BeanPropertyRowMapper<GetDoctorDetailsEntity>(GetDoctorDetailsEntity.class), specialization);

	}

	// select doctor detail in giving id
	public GetDoctorDetailsEntity getDrDetails(int doctorId) {
		return jdbcTemplate.queryForObject("SELECT * FROM appoitnmentapptables.doctors_list where doctor_id =?;",
				new BeanPropertyRowMapper<GetDoctorDetailsEntity>(GetDoctorDetailsEntity.class), doctorId);
	}

	public void updateDrAvailability(String newAvailability, int doctorId) {
		jdbcTemplate.update("UPDATE doctors_list SET availability=? WHERE doctor_id =?;", newAvailability, doctorId);
	}

	public GetPaymentRulesEntity getOffers() {
		return jdbcTemplate.queryForObject("SELECT * FROM appoitnmentapptables.payment_rules;",
				new BeanPropertyRowMapper<GetPaymentRulesEntity>(GetPaymentRulesEntity.class));
	}

	public void saveUser(ConfirmAppointmentEntity confirmAppointmentEntity) {
		jdbcTemplate.update(
				"INSERT INTO booking_details (user_name, doctor_id, fee_paid, booked_time, booked_day,booked_doctor,tocken_id) VALUES (?,?,?,?,?,?,?)",
				new Object[] { confirmAppointmentEntity.getPaymentInfo().getNameAsInTheCard(),
						confirmAppointmentEntity.getBookingInfo().getDoctorId(),
						confirmAppointmentEntity.getPaymentInfo().getAmount(),
						confirmAppointmentEntity.getBookingInfo().getTime(),
						confirmAppointmentEntity.getBookingInfo().getDay(),
						confirmAppointmentEntity.getBookingInfo().getNameOfDoctor(),
						confirmAppointmentEntity.getBookingInfo().getTockenId() });
	}

//get paid fee, name etc
	public GetUserDetailsEntity getUserDetails(int id) {
		return jdbcTemplate.queryForObject("SELECT * FROM appoitnmentapptables.booking_details where id=?;",
				new BeanPropertyRowMapper<GetUserDetailsEntity>(GetUserDetailsEntity.class), id);
	}

	public int GetUserId(String userName) {
		return Integer.parseInt(jdbcTemplate.queryForObject(
				"SELECT id FROM appoitnmentapptables.booking_details where user_name =?;", String.class, userName));
	}

	public void UpdateUserDetails(String newBookedTime, int tockenId) {
		jdbcTemplate.update("UPDATE booking_details SET booked_time=? WHERE tocken_id =?;", newBookedTime, tockenId);

	}

	public List<String> UserRegisteredMailIdList() {
		return jdbcTemplate.queryForList("SELECT mail_id FROM appoitnmentapptables.user_details;", String.class);
	}

	public List<String> UserRegisteredPhNoList() {
		return jdbcTemplate.queryForList("SELECT phone_number FROM appoitnmentapptables.user_details;", String.class);
	}

	public List<Integer> GetDrIdList() {
		return jdbcTemplate.queryForList("SELECT doctor_id FROM appoitnmentapptables.doctors_list;", Integer.class);
	}

	public int GetTockenId(String mailId) {
		return Integer.parseInt(jdbcTemplate.queryForObject(
				"SELECT tocken_id FROM appoitnmentapptables.user_details where mail_id=?;", String.class, mailId));
	}

	public List<Integer> GetTockenIdList() {
		return jdbcTemplate.queryForList("SELECT tocken_id FROM appoitnmentapptables.booking_details;", Integer.class);
	}
	
	public GetBookedUser GetBookedUserByTockenId(int tockenId) {
		return jdbcTemplate.queryForObject("SELECT * FROM appoitnmentapptables.booking_details where tocken_id =?;",
				new BeanPropertyRowMapper<GetBookedUser>(GetBookedUser.class), tockenId);
	}
}
