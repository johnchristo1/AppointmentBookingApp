package doctor.appointment.registration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Component;

@Component
public class ConfirmAppointmentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id;
	public BookingInfoEntity bookingInfo;
	public PaymentInfoEntity paymentInfo;

	public BookingInfoEntity getBookingInfo() {
		return bookingInfo;
	}

	public void setBookingInfo(BookingInfoEntity bookingInfo) {
		this.bookingInfo = bookingInfo;
	}

	public PaymentInfoEntity getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfoEntity paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

}
