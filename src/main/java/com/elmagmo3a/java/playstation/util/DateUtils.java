package com.elmagmo3a.java.playstation.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.Date;


/**
 * Util class to provide date utils methods.
 */
public class DateUtils {

	private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

	/**
	 * return new date for current time
	 *
	 * @return Date instance
	 */
	public static Date now() {
		return Date.from(Instant.now());
	}

	/**
	 * convert the passed {@link Long} date to {@link Date}
	 *
	 * @param date long date value
	 * @return {@link Date}
	 */
	public static Date convertLongToDate(Long date) {
		if (date == null) {
			return null;
		}
		return new Date(date);
	}

	/**
	 * convert the passed {@link Long} date to {@link LocalDate}
	 *
	 * @param date long date value
	 * @return {@link LocalDate}
	 */
	public static LocalDate convertLongToLocalDate(Long date) {
		if (date == null) {
			return null;
		}
		return Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * convert the passed {@link Long} date to {@link LocalDateTime}
	 *
	 * @param date long date value
	 * @return {@link LocalDateTime}
	 */
	public static LocalDateTime convertLongToLocalDateTime(Long date) {
		if (date == null) {
			return null;
		}
		return Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/**
	 * convert the passed {@link Date} to {@link Long}
	 *
	 * @param date date value
	 * @return {@link Long}
	 */
	public static Long convertDateToLong(Date date) {
		if (date == null) {
			return null;
		}
		return date.getTime();
	}

	/**
	 * convert the passed {@link LocalDate} to {@link Long}
	 *
	 * @param localDate localDate value
	 * @return {@link Long}
	 */
	public static Long convertLocalDateToLong(LocalDate localDate) {
		if (localDate == null) {
			return null;
		}
//        return localDate.atStartOfDay().atZone(DEFAULT_ZONE_ID).toEpochSecond();
		return localDate.toEpochDay();
	}

	/**
	 * convert the passed {@link LocalDateTime} to {@link Long}
	 *
	 * @param localDateTime localDateTime value
	 * @return {@link Long}
	 */
	public static Long convertLocalDateTimeToLong(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return null;
		}
		return localDateTime.atZone(DEFAULT_ZONE_ID).toEpochSecond();
	}

	/**
	 * Validate the passed dateFromAsLong and dateToAsLong
	 *
	 * @param dateAsLong long date
	 */
	public static void validateDateInTheFuture(Long dateAsLong, String validationErrorMessageKey, Object... params) {
		Date date = convertLongToDate(dateAsLong);
		if (CommonUtils.isBlankOrNull(date)) {
			return;
		}
		validateFirstDateIsBeforeSecondDate(now(), date, validationErrorMessageKey, params);
	}

	/**
	 * Validate the passed dateFromAsLong and dateToAsLong
	 *
	 * @param dateFromAsLong long date
	 * @param dateToAsLong   long date
	 */
	public static void validateDateFromAndDateTo(Long dateFromAsLong, Long dateToAsLong,
			String validationErrorMessageKey, Object... params) {
		Date startDate = convertLongToDate(dateFromAsLong);
		Date endDate = convertLongToDate(dateToAsLong);

		if (CommonUtils.isBlankOrNull(startDate) || CommonUtils.isBlankOrNull(endDate)) {
			return;
		}
		validateFirstDateIsBeforeSecondDate(startDate, endDate, validationErrorMessageKey, params);
	}

	/**
	 * Validate that the passed startDate is before endDate
	 *
	 * @param startDate startDate to be checked
	 * @param endDate   endDate to be checked
	 */
	public static void validateFirstDateIsBeforeSecondDate(Date startDate, Date endDate,
			String validationErrorMessageKey, Object... params) {
		if (CommonUtils.isBlankOrNull(startDate) || CommonUtils.isBlankOrNull(endDate)) {
			throw new IllegalArgumentException("startDate and endDate can not be null");
		}
		if (startDate.equals(endDate) || startDate.after(endDate)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					CommonUtils.resourceBundle(validationErrorMessageKey, params));
		}
	}

	/**
	 * @return The minimum supported long value for The time of midnight at the
	 *         start of the toDay, '00:00'.
	 */
	public static long getTodayMinTime() {
		LocalDateTime now = LocalDateTime.now().with(LocalTime.MIDNIGHT);
		return now.atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
	}

	/**
	 * @param numOfDaysToIncrement : number of days to increment to today
	 * @return The minimum supported long value for The time of midnight after
	 *         adding numOfDaysToIncrement at the start of the time, '00:00'.
	 */
	public static long getMinTimeOfNowIncrementedByNumOfDays(int numOfDaysToIncrement) {
		LocalDateTime now = LocalDateTime.now().plusDays(numOfDaysToIncrement).with(LocalTime.MIDNIGHT);
		return now.atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
	}

	/**
	 * @param numOfDaysToDecrement : number of days to decrement from today
	 * @return The minimum supported long value for The time of midnight after
	 *         adding numOfDaysToIncrement at the start of the time, '00:00'.
	 */
	public static long getMinTimeOfNowDecrementedByNumOfDays(int numOfDaysToDecrement) {
		LocalDateTime now = LocalDateTime.now().minusDays(numOfDaysToDecrement).with(LocalTime.MIDNIGHT);
		return now.atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
	}

	/**
	 * @return The maximum supported long value for today time ,
	 *         '23:59:59.999999999'. This is the time just before midnight at the
	 *         end of the time.
	 */
	public static long getTodayMaxTime() {
		LocalDateTime now = LocalDateTime.now().with(LocalTime.MAX);
		return now.atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
	}

	/**
	 * @param numOfDaysToIncrement : number of days to increment to today
	 * @return The maximum supported long value for today time ,
	 *         '23:59:59.999999999' after adding numOfDaysToIncrement . This is the
	 *         time just before midnight at the end of the time.
	 */
	public static long getMaxTimeOfNowIncrementedByNumOfDays(int numOfDaysToIncrement) {
		LocalDateTime now = LocalDateTime.now().plusDays(numOfDaysToIncrement).with(LocalTime.MAX);
		return now.atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
	}

	/**
	 * @param numOfDaysToDecrement : number of days to decrement from today
	 * @return The maximum supported long value for The time of midnight after
	 *         adding numOfDaysToIncrement at the start of the time,
	 *         '23:59:59.999999999'.
	 */
	public static long getMaxTimeOfNowDecrementedByNumOfDays(int numOfDaysToDecrement) {
		LocalDateTime now = LocalDateTime.now().minusDays(numOfDaysToDecrement).with(LocalTime.MAX);
		return now.atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
	}

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime asLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/**
	 * add the passed milli seconds to now and return nowPlusMillis
	 *
	 * @param milliSeconds milli seconds
	 * @return nowPlusMillis
	 */
	public static Date getCurrentTimePlusMillis(long milliSeconds) {
		LocalDateTime nowPlusMillis = LocalDateTime.now().plusSeconds(milliSeconds / 1000L);
		return asDate(nowPlusMillis);
	}

}
