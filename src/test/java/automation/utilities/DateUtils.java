package automation.utilities;
import org.apache.log4j.Logger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
            "Dec" };
    protected static String[] sMonthFullName = { "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December" };

    public static String sMonthNamePostSubtractionOfXdays;
    public static String sYearNamePostSubtractionOfXdays;
    public static String sMonthNamePostAdditionOfXdays;
    public static String sYearNamePostAdditionOfXdays;
    static Logger log = Logger.getLogger("DateUtils");

    /**
     * Returns the System date as String
     * @return system date
     */
    public static String getSystemDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String Date = dateFormat.format(date);
        return Date;
    }

    /**
     * Returns month name with respect to the number of days prior to current day.
     * @param iReducedDays
     * @Example let's say current date is August 2, if given iReducedDays as 4 then
     *          the method returns the month name 'July' as the month changed when
     *          reduced 4 days.
     * @return
     */
    public static String getMonthMinusXdays(int iReducedDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -iReducedDays);
        Date d = calendar.getTime();
        ArrayList<String> sMonthName = new ArrayList<String>(
                Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));
        int iMonthIndex = sMonthName.indexOf(String.valueOf(d).substring(4, 7));
        return String.valueOf(iMonthIndex);
    }

    /**
     * Returns Current day as Integer
     * @return current day
     */
    public static int getCurrentDayAsInteger() {
        Calendar cal = Calendar.getInstance();
        Integer iMonth = cal.get(Calendar.DAY_OF_MONTH);
        return iMonth;
    }

    /**
     * Returns the Current year in String format
     * @return current year in string format
     */
    public static String getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        Integer iYear = cal.get(Calendar.YEAR);
        return iYear.toString();
    }

    /**
     * Returns Current day in String format
     * @return string
     */
    public static String getCurrentDayAsString() {
        Calendar cal = Calendar.getInstance();
        Integer iMonth = cal.get(Calendar.DAY_OF_MONTH);
        String s = iMonth.toString();
        return s;
    }

    /**
     * Returns name of the month after adding N weeks to current month
     * @param noOfWeeks
     * @return name of the month after adding N weeks to current month
     */
    public static String getMonthAfterNWeeks(int noOfWeeks) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_MONTH, noOfWeeks);
        Date d = calendar.getTime();
        System.out.println(d);
        return String.valueOf(d).substring(4, 7);

    }

    /**
     * Returns date after adding N weeks to current date
     * @param noOfWeeks
     * @return date after adding N weeks to current date
     */
    public static String getDateAfterNWeeks(int noOfWeeks) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_MONTH, noOfWeeks);
        Date d = calendar.getTime();
        if (!String.valueOf(d).substring(8, 10).startsWith("0")) {
            return String.valueOf(d).substring(8, 10);
        } else {
            return (String.valueOf(d).substring(8, 10).substring(1));
        }

    }

    /**
     * Returns Date after subtracting X days
     * @param iReducedDays
     * @return date after subtracting X days
     *
     */
    public static String getDatePostSubtractionOfXdays(int iReducedDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -iReducedDays);
        Date d = calendar.getTime();
        sMonthNamePostSubtractionOfXdays = String.valueOf(d).substring(4, 7);
        sYearNamePostSubtractionOfXdays = String.valueOf(d).substring(24, String.valueOf(d).length());
        if (!String.valueOf(d).substring(8, 10).startsWith("0")) {
            return String.valueOf(d).substring(8, 10);
        } else {
            return (String.valueOf(d).substring(8, 10).substring(1));
        }
    }

    /**
     * Returns Month name after subtracting X days
     * @param iReducedDays
     * @return month name after subtracting X days
     */
    public static String getMonthNamePostSubtractionOfXdays(int iReducedDays) {
        sMonthNamePostSubtractionOfXdays = null;
        getDatePostSubtractionOfXdays(iReducedDays);
        return sMonthNamePostSubtractionOfXdays;
    }

    /**
     * Returns year name after subtracting X days
     * @param iReducedDays
     * @return year name after subtracting X days
     */
    public static String getYearNamePostSubtractionOfXdays(int iReducedDays) {
        sYearNamePostSubtractionOfXdays = null;
        getDatePostSubtractionOfXdays(iReducedDays);
        return sYearNamePostSubtractionOfXdays;
    }

    /**
     * Returns current month name as string variable
     * @return current month name
     */
    public static String getCurrentMonthName() {
        Calendar cal = Calendar.getInstance();
        Integer iMonth = cal.get(Calendar.MONTH);
        String month = monthName[iMonth];
        return month;
    }

    /**
     * Returns Index value of Current month in String format
     * @return {@link String}
     */
    public static String getCurrentMonthIndexValueString() {
        Calendar cal = Calendar.getInstance();
        Integer iMonth = cal.get(Calendar.MONTH);

        return iMonth.toString();
    }

    /**
     * Returns index value of current month in integer format
     * @return {@link Integer}
     */
    public static Integer getCurrentMonthIndexValueInt() {
        Calendar cal = Calendar.getInstance();
        Integer iMonth = cal.get(Calendar.MONTH);
        return iMonth;
    }

    /**
     * Returns time stamp
     * @return string
     */
    public static String getTimeStamp() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(cal.getTime());
        return timestamp;
    }

    /**
     * Returns time stamp without spaces
     * @return time stamp without spaces
     */
    public static String getTimeStampNoSpaces() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String timestamp = sdf.format(cal.getTime());
        timestamp = timestamp.replaceAll("[^\\d.]", "");
        return timestamp;
    }

    /**
     * Returns System date suitable to add as image name
     * @return string
     */
    public static String getTimestampforImage() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss_dd-MM-yyyy");
        String timestamp = sdf.format(cal.getTime());
        return timestamp.substring(0, 8) + "_on_" + timestamp.substring(9, timestamp.length());
    }

    /**
     * Converts and returns the date to new date format
     * @param date
     * @param newDateformat
     * @return
     */
    public static String convertFormatOfDate(String date, String newDateformat) {

        final String OLD_FORMAT = "dd/MM/yyyy";
        final String NEW_FORMAT = newDateformat;
        String newDateString = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
            Date d = sdf.parse(date);

            sdf.applyPattern(NEW_FORMAT);
            newDateString = sdf.format(d);
            log.info("Date before conversion : " + date);
            log.info("Date after conversion : " + newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateString;

    }

    /**
     * Converts Date format to new format specified
     * @param date
     * @param sOldFormat
     * @param newDateformat
     *
     */
    public static String convertFormatOfDate(String date, String sOldFormat, String newDateformat) {
        final String OLD_FORMAT = sOldFormat;
        final String NEW_FORMAT = newDateformat;
        String newDateString = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
            log.info("Date before conversion : " + date);
            Date d = sdf.parse(date);

            sdf.applyPattern(NEW_FORMAT);
            newDateString = sdf.format(d);
            log.info("Date after conversion : " + newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateString;

    }

    /**
     * Returns the last day of month
     * @return string
     */
    public static String getLastDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        Integer maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay.toString();
    }

    /**
     * Returns the Previous month name
     * @return get previous month name
     */
    public static String getPreviousMonthName() {
        Calendar cal = Calendar.getInstance();
        Integer iMonth = cal.get(Calendar.MONTH);

        if (iMonth == 0) {
            iMonth = 12;
        }

        Integer nextMonth = iMonth - 1;
        String month = monthName[nextMonth];
        return month;
    }

    /**
     * Returns the next month name
     * @return get next month name
     */
    public static String getNextMonthName() {

        Calendar cal = Calendar.getInstance();
        Integer iMonth = cal.get(Calendar.MONTH);

        if (iMonth == 11) {
            iMonth = -1;
        }

        Integer nextMonth = iMonth + 1;
        String month = monthName[nextMonth];
        return month;
    }

    /**
     * Returns current Date minus specified no of days
     * @param iReducedDays
     * @return String
     */
    public static String getCurrentDateMinusXdays(int iReducedDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -iReducedDays);
        Date d = calendar.getTime();

        if (!String.valueOf(d).substring(8, 10).startsWith("0")) {
            return String.valueOf(d).substring(8, 10);
        } else {
            return (String.valueOf(d).substring(8, 10).substring(1));
        }
    }

    /**
     * Returns day of the current month
     * @param cal
     * @return current day of current month
     */
    public static String getDay(Calendar cal) {
        int date = cal.get(Calendar.DAY_OF_MONTH);
        return Integer.toString(date);
    }

    /**
     * Returns the current month name as String
     * @param cal
     * @return String monthName
     */
    public static String getMonth(Calendar cal) {
        String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

        Integer iMonth = cal.get(Calendar.MONTH);

        String month = monthName[iMonth];
        return month;
    }

    /**
     * Returns date after adding X days to current date
     * @param daysAfter
     * @return the next date
     */
    public static String getDatePostAdditionOfXdays(int daysAfter) {
        DateFormat newFormat = new SimpleDateFormat("MM/dd/yyyy");
        newFormat.setLenient(true);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, daysAfter);
        Date d = cal.getTime();
        sMonthNamePostAdditionOfXdays = String.valueOf(d).substring(4, 7);
        sYearNamePostAdditionOfXdays = String.valueOf(d).substring(24, String.valueOf(d).length());
        if (!String.valueOf(d).substring(8, 10).startsWith("0")) {
            return String.valueOf(d).substring(8, 10);
        } else {
            return (String.valueOf(d).substring(8, 10).substring(1));
        }
    }

    /**
     * Returns Month name after Adding X days to current date
     * @param daysAfter
     * @return gets month name after adding X days to current date
     */
    public static String getMonthNamePostAdditionOfXdays(int daysAfter) {
        sMonthNamePostAdditionOfXdays = null;
        getDatePostAdditionOfXdays(daysAfter);
        return sMonthNamePostAdditionOfXdays;
    }

    /**
     * Returns year after after Adding X days to current date
     * @param daysAfter
     * @return year after adding X days to current date
     */
    public static String getYearPostAdditionOfXdays(int daysAfter) {
        sYearNamePostAdditionOfXdays = null;
        getDatePostAdditionOfXdays(daysAfter);
        return sYearNamePostAdditionOfXdays;
    }

    /**
     * Returns year based on the month
     * @return string
     */
    public static String getYearBasedOnNextMonth() {
        Integer iYear = null;
        Calendar cal = Calendar.getInstance();
        String sMonth = getNextMonthName();
        if (sMonth == "Jan") {
            iYear = cal.get(Calendar.YEAR) + 1;
        } else {
            iYear = cal.get(Calendar.YEAR);
        }
        return iYear.toString();
    }

    /**
     * Returns Full name of the Next month
     * @return string
     */
    public static String getNextMonthFullName() {
        Integer iMonth = getCurrentMonthIndexValueInt();
        if (iMonth == 11) {
            iMonth = -1;
        }
        Integer nextMonth = iMonth + 1;
        String sMonth = sMonthFullName[nextMonth];
        return sMonth;
    }

    /**
     * Returns Full name of the Current month
     * @return string
     */
    public static String getCurrentMonthFullName() {
        Calendar cal = Calendar.getInstance();
        Integer iMonth = cal.get(Calendar.MONTH);
        String sMonth = sMonthFullName[iMonth];
        return sMonth;
    }

    /**
     * Returns Month Full name after subtracting X days
     * @param iReducedDays
     * @return String
     */
    public static String getMonthFullNamePostSubtractionOfXdays(int iReducedDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -iReducedDays);
        Integer iMonth = calendar.get(Calendar.MONTH);
        String sMonth = sMonthFullName[iMonth];
        return sMonth;
    }

    /**
     * Returns Month Full name after adding X days
     * @param idaysAfter
     * @return String
     */
    public static String getMonthFullNamePostAdditionOfXdays(int idaysAfter) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, idaysAfter);
        Integer iMonth = calendar.get(Calendar.MONTH);
        String sMonth = sMonthFullName[iMonth];
        return sMonth;
    }

    /**
     * Returns Month number as Integer
     *
     * @return  month number as integer
     */
    public static int getMonthNumber(String sMonth) {
        int monthNumber = 0;
        try {
            Date date = new SimpleDateFormat("MMM").parse(sMonth);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            monthNumber = cal.get(Calendar.MONTH) + 1;
            return monthNumber;
        } catch (ParseException e) {
            e.printStackTrace();
            return monthNumber;
        }
    }

    /**
     *
     * @param sDate
     * @param sFormat
     * @param iNoOfDays
     * @return date based on addition of X no.of days
     */
    public static String getDateBasedOnNoOfDays(String sDate, String sFormat, int iNoOfDays) {
        SimpleDateFormat format = null;
        Date date;
        Calendar calendar = null;
        try {
            format = new SimpleDateFormat(sFormat);
            date = format.parse(sDate);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, iNoOfDays);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return format.format(calendar.getTime());
    }

    /**
     * Returns last date of current month
     * @param sDate
     * @param sFormat
     * @return last date of current month
     */
    public static String getLastDateOfCurrentMonth(String sDate,String sFormat) {
        String lastDayOfTheMonth = "";

        SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
        try{
            java.util.Date dt= formatter.parse(sDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dt);

            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);

            java.util.Date lastDay = calendar.getTime();

            lastDayOfTheMonth = formatter.format(lastDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lastDayOfTheMonth;
    }

    /**
     *
     * @param sDate
     * @param sFormat
     * @param iNoOfMonth
     * @return last day of the month
     */
    public static String getMonthDateBasedOnDate(String sDate,String sFormat,int iNoOfMonth) {
        String lastDayOfTheMonth = "";

        SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
        try{
            java.util.Date dt= formatter.parse(sDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dt);

            calendar.add(Calendar.MONTH, iNoOfMonth);
            calendar.add(Calendar.DATE, 0);

            java.util.Date lastDay = calendar.getTime();
            lastDayOfTheMonth = formatter.format(lastDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lastDayOfTheMonth;
    }

}
