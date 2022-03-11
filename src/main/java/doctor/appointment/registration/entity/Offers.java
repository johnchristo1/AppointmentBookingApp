package doctor.appointment.registration.entity;

import org.hibernate.validator.constraints.Range;

public class Offers {
	@Range(min = 100, max=999)
	public int specialOffer;

	public int getSpecialOffer() {
		return specialOffer;
	}

	public void setSpecialOffer(int specialOffer) {
		this.specialOffer = specialOffer;
	}
}
