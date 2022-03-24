package UtilityEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import doctor.appointment.registration.entity.BookDoctorEntity;
import doctor.appointment.registration.repository.UserRepository;

@Component
public class FeeCalculations {
	@Autowired
	UserRepository userRepository;

	public long feeAfterDiscount(int doctorId) {
		int specialDiscount = userRepository.getOffers().getSpecialDiscount();
		int processingFee = userRepository.getOffers().getPorcessingFee();
		int consultationFee = userRepository.getDrDetails(doctorId).getConsultationFee();
		long discount = (long) (((consultationFee + processingFee) * specialDiscount) / 100);
		long feeAfterDiscount = (long) (consultationFee + processingFee) - discount;
		System.out.println(
				"specialDiscount" + specialDiscount + " processingFee  : " + processingFee + " consultationFee : "
						+ consultationFee + "discount ;" + discount + "feeAfterDiscount : " + feeAfterDiscount);
		return feeAfterDiscount;
	}

	public int feeWithin4hr(int userId) {
		int paidFee = userRepository.GetBookedUserByUserId(userId).getFeePaid();
		int refundAmount = paidFee - ((paidFee * userRepository.getOffers().getWithin4hr()) / 100);
		return refundAmount;
	}

	public int feeAfter4hr(int userId) {
		int paidFee = userRepository.GetBookedUserByUserId(userId).getFeePaid();
		int refundAmount = paidFee - ((paidFee * userRepository.getOffers().getBefore4hr()) / 100);
		return refundAmount;
	}
}
