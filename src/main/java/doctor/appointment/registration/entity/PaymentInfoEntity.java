package doctor.appointment.registration.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

public class PaymentInfoEntity {
	@Range(min = 100, max = 999)
	public int amount;

	@NotEmpty(message = "Name in debit card is not given")
	public String nameAsInTheCard;

	@NotEmpty(message = "Card Number is not given")
	public String cardNumber;

	@NotEmpty(message = "Cvv number is not given")
	public String cvv;

	@NotEmpty(message = "ExpiryDate is not given")
	public String expiryDate;
	
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getNameAsInTheCard() {
		return nameAsInTheCard;
	}

	public void setNameAsInTheCard(String nameAsInTheCard) {
		this.nameAsInTheCard = nameAsInTheCard;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
