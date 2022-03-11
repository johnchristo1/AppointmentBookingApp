package doctor.appointment.registration.entity;

import org.hibernate.validator.constraints.Range;

public class Fee {
	@Range(min = 100, max=999)
	public int processingFee;

	public int getProcessingFee() {
		return processingFee;
	}

	public void setProcessingFee(int processingFee) {
		this.processingFee = processingFee;
	}
}
