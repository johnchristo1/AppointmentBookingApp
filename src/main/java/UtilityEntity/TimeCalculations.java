package UtilityEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import doctor.appointment.registration.repository.UserRepository;

@Component
public class TimeCalculations {
	@Autowired
	UserRepository userRepository;

	public String CurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("HH-mm");
		Calendar calendar = Calendar.getInstance();
		String currentTime = dateFormat.format(calendar.getTime());
		return currentTime;
	}

	public int TimeOfBookingInMinutes(int userId) {
		String timeOfBooking = userRepository.GetBookedUserByUserId(userId).getTimeOfBooking();
		String[] timeOfBookingList = timeOfBooking.split("-");
		int timeOfBookingInMinutes = 60 * Integer.parseInt(timeOfBookingList[0])
				+ Integer.parseInt(timeOfBookingList[1]);
		return timeOfBookingInMinutes;
	}

	public int CurrentTimeInMinutes() {
		DateFormat dateFormat = new SimpleDateFormat("HH-mm");
		Calendar calendar = Calendar.getInstance();
		String currentTime = dateFormat.format(calendar.getTime());
		String[] currentTimeList = currentTime.split("-");
		int currentTimeInMinutes = 60 * Integer.parseInt(currentTimeList[0]) + Integer.parseInt(currentTimeList[1]);
		return currentTimeInMinutes;

	}

}
