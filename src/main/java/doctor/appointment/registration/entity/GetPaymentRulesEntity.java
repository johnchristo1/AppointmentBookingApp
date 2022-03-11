package doctor.appointment.registration.entity;

public class GetPaymentRulesEntity {
	public int id;
	public int porcessingFee;
	public int specialDiscount;
	public int before4hr;
	public int within4hr;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPorcessingFee() {
		return porcessingFee;
	}

	public void setPorcessingFee(int porcessingFee) {
		this.porcessingFee = porcessingFee;
	}

	public int getSpecialDiscount() {
		return specialDiscount;
	}

	public void setSpecialDiscount(int specialDiscount) {
		this.specialDiscount = specialDiscount;
	}

	public int getBefore4hr() {
		return before4hr;
	}

	public void setBefore4hr(int before4hr) {
		this.before4hr = before4hr;
	}

	public int getWithin4hr() {
		return within4hr;
	}

	public void setWithin4hr(int within4hr) {
		this.within4hr = within4hr;
	}
	
}
