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
				"INSERT INTO doctors_list (name, doctor_id, specialization, consultation_fee,availability,email_id) VALUES (?,?,?,?,?,?)",
				new Object[] { doctorDetailsEntity.getName(), doctorDetailsEntity.getDoctorId(),
						doctorDetailsEntity.getSpecialization(), doctorDetailsEntity.getConsultationFee(),
						doctorDetailsEntity.getAvailability().toString(), doctorDetailsEntity.getEmailId() });
	}

//save user
	public void addUser(UserRegistrationEntity userRegistrationEntity) {
		jdbcTemplate.update("INSERT INTO user_details (user_id, mail_id, phone_number, user_name) VALUES (?,?,?,?)",
				new Object[] { userRegistrationEntity.getUserId(), userRegistrationEntity.getMailId(),
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
	// take rules to an entity
	// if the incoming req is null then save old value to a variable
	// then use that data to update

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
				"INSERT INTO booking_details (user_name, doctor_id, fee_paid, booked_time, booked_day,booked_doctor,user_id,time_of_booking) VALUES (?,?,?,?,?,?,?,?)",
				new Object[] { confirmAppointmentEntity.getPaymentInfo().getNameAsInTheCard(),
						confirmAppointmentEntity.getBookingInfo().getDoctorId(),
						confirmAppointmentEntity.getPaymentInfo().getAmount(),
						confirmAppointmentEntity.getBookingInfo().getTime(),
						confirmAppointmentEntity.getBookingInfo().getDay(),
						confirmAppointmentEntity.getBookingInfo().getNameOfDoctor(),
						confirmAppointmentEntity.getBookingInfo().getUserId(),
						confirmAppointmentEntity.getTimeOfBooking() });
	}

	public GetBookedUser GetBookedUserByUserId(int userId) {
		return jdbcTemplate.queryForObject("SELECT * FROM appoitnmentapptables.booking_details where user_id =?;",
				new BeanPropertyRowMapper<GetBookedUser>(GetBookedUser.class), userId);
	}

	public void UpdateUserDetails(String newBookedTime, int userId) {
		jdbcTemplate.update("UPDATE booking_details SET booked_time=? WHERE user_id =?;", newBookedTime, userId);

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

	public int GetUserId(String mailId) {
		return Integer.parseInt(jdbcTemplate.queryForObject(
				"SELECT user_id FROM appoitnmentapptables.user_details where mail_id=?;", String.class, mailId));
	}

	public List<Integer> GetUserIdList() {
		return jdbcTemplate.queryForList("SELECT user_id FROM appoitnmentapptables.booking_details;", Integer.class);
	}

	public List<String> GetDrRegisteredMailIdList() {
		return jdbcTemplate.queryForList("SELECT email_id FROM appoitnmentapptables.doctors_list;", String.class);
	}

	public int DeleteDrbyDrId(int drId) {
		return jdbcTemplate.update("DELETE FROM doctors_list WHERE doctor_id=?;", drId);
	}

	public int DeleteUserById(int userId) {
		return jdbcTemplate.update("DELETE FROM appoitnmentapptables.booking_details WHERE user_id=?;", userId);
	}

	// get user id list from user details
	public List<Integer> GetUserIdListFromUserDetails() {
		return jdbcTemplate.queryForList("SELECT user_id FROM appoitnmentapptables.user_details;", Integer.class);
	}

}
