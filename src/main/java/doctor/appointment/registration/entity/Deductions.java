package doctor.appointment.registration.entity;

import org.hibernate.validator.constraints.Range;

public class Deductions {
	@Range(min = 100, max = 999)
	public int before4hr;
	@Range(min = 100, max = 999)
	public int after4hr;

	public int getBefore4hr() {
		return before4hr;
	}

	public void setBefore4hr(int before4hr) {
		this.before4hr = before4hr;
	}

	public int getAfter4hr() {
		return after4hr;
	}

	public void setAfter4hr(int after4hr) {
		this.after4hr = after4hr;
	}
}
