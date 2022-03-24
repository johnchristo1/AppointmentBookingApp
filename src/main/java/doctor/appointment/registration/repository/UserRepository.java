package doctor.appointment.registration.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

	public void saveDoctor(DoctorDetailsEntity doctorDetailsEntity) {
		jdbcTemplate.update(
				"INSERT INTO doctors_list (name, doctor_id, specialization, consultation_fee,availability,email_id) VALUES (?,?,?,?,?,?)",
				new Object[] { doctorDetailsEntity.getName(), doctorDetailsEntity.getDoctorId(),
						doctorDetailsEntity.getSpecialization(), doctorDetailsEntity.getConsultationFee(),
						doctorDetailsEntity.getAvailability().toString(), doctorDetailsEntity.getEmailId() });
	}

	public void addUser(UserRegistrationEntity userRegistrationEntity) {
		jdbcTemplate.update("INSERT INTO user_details (user_id, mail_id, phone_number, user_name) VALUES (?,?,?,?)",
				new Object[] { userRegistrationEntity.getUserId(), userRegistrationEntity.getMailId(),
						userRegistrationEntity.getPhoneNumber(), userRegistrationEntity.getUserName() });
	}

	public void addRules(RulesEntity rulesEntity) {
		jdbcTemplate.update(
				"INSERT INTO payment_rules (porcessing_fee, special_discount, before4hr,within4hr) VALUES (?,?,?,?)",
				new Object[] { rulesEntity.getFee().getProcessingFee(), rulesEntity.getOffers().getSpecialOffer(),
						rulesEntity.getDeductions().getBefore4hr(), rulesEntity.getDeductions().getAfter4hr() });
	}

	public void updateProcessingFee(RulesEntity rulesEntity) {
		jdbcTemplate.update("UPDATE payment_rules SET porcessing_fee=?",
				new Object[] { rulesEntity.getFee().getProcessingFee() });
	}

	public void updateSpecialDiscount(RulesEntity rulesEntity) {
		jdbcTemplate.update("UPDATE payment_rules SET special_discount=?",
				new Object[] { rulesEntity.getOffers().getSpecialOffer() });
	}

	public void updateBefore4hour(RulesEntity rulesEntity) {
		jdbcTemplate.update("UPDATE payment_rules SET before4hr=?",
				new Object[] { rulesEntity.getDeductions().getBefore4hr() });
	}

	public void updateAfter4hour(RulesEntity rulesEntity) {
		jdbcTemplate.update("UPDATE payment_rules SET within4hr=?",
				new Object[] { rulesEntity.getDeductions().getAfter4hr() });
	}

	public List<GetDoctorDetailsEntity> findBySpesialization(String specialization) {
		return jdbcTemplate.query("SELECT * FROM appoitnmentapptables.doctors_list WHERE specialization=?;",
				new BeanPropertyRowMapper<GetDoctorDetailsEntity>(GetDoctorDetailsEntity.class), specialization);

	}

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

	public List<Integer> GetDrIdList() {
		return jdbcTemplate.queryForList("SELECT doctor_id FROM appoitnmentapptables.doctors_list;", Integer.class);
	}

	public int GetUserId(String mailId) {
		return Integer.parseInt(jdbcTemplate.queryForObject(
				"SELECT user_id FROM appoitnmentapptables.user_details where mail_id=?;", String.class, mailId));
	}

	public int DeleteDrbyDrId(int drId) {
		return jdbcTemplate.update("DELETE FROM doctors_list WHERE doctor_id=?;", drId);
	}

	public int DeleteUserById(int userId) {
		return jdbcTemplate.update("DELETE FROM appoitnmentapptables.user_details WHERE user_id=?;", userId);
	}
	
	public void DeleteAppointmentByUserId(int userId) {
		jdbcTemplate.update("DELETE FROM appoitnmentapptables.booking_details WHERE user_id=?;", userId);
	}
	
	public boolean checkDoctorId(String emailId) {
		try {
			jdbcTemplate.queryForObject("select doctor_id from appoitnmentapptables.doctors_list where email_id = ?;",
					Integer.class, emailId);
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}

	}

	public boolean checkDoctorId(int doctorId) {
		try {
			jdbcTemplate.queryForObject("SELECT doctor_id from appoitnmentapptables.doctors_list where doctor_id =?;",
					Integer.class, doctorId);
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}

	}

	public boolean checkUserMailId(String emailId) {
		try {
			jdbcTemplate.queryForObject("SELECT user_id FROM appoitnmentapptables.user_details where mail_id=?;",
					Integer.class, emailId);
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}

	}

	public boolean checkUserPhoneNumber(String phoneNumber) {
		try {
			jdbcTemplate.queryForObject("SELECT user_id FROM appoitnmentapptables.user_details where phone_number=?;",
					Integer.class, phoneNumber);
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}

	}

	public boolean checkUserIdFromUserDetails(int userId) {
		try {
			jdbcTemplate.queryForObject("SELECT user_id FROM appoitnmentapptables.user_details where user_id =?;",
					Integer.class, userId);
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}

	}

	public boolean CheckBookingId(int bookingId) {
		try {
			jdbcTemplate.queryForObject(
					"SELECT booking_id FROM appoitnmentapptables.booking_details where booking_id =?;", Integer.class,
					bookingId);
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}

	public boolean checkUserIdFromBookingDetails(int userId) {
		try {
			jdbcTemplate.queryForObject("SELECT user_id FROM appoitnmentapptables.booking_details where user_id =?;",
					Integer.class, userId);
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}

	}

	public boolean checkUserRules() {
		try {
			jdbcTemplate.queryForObject("SELECT id FROM appoitnmentapptables.payment_rules;", Integer.class);
			return false;
		} catch (EmptyResultDataAccessException e) {
			return true;
		}

	}

}
