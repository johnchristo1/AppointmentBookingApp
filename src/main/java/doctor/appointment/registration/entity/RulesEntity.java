package doctor.appointment.registration.entity;

import javax.validation.constraints.NotEmpty;

public class RulesEntity {
	public Fee fee;
	public Offers offers;
	public Deductions deductions;

	public Fee getFee() {
		return fee;
	}

	public void setFee(Fee fee) {
		this.fee = fee;
	}

	public Offers getOffers() {
		return offers;
	}

	public void setOffers(Offers offers) {
		this.offers = offers;
	}

	public Deductions getDeductions() {
		return deductions;
	}

	public void setDeductions(Deductions deductions) {
		this.deductions = deductions;
	}
}
