package com.omapslab.andromaps.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * View Helpers
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 * -------------------------------------------------------------
 */
public class DateTimeHelper {

    public String DATE_DAY_NAME = "EEEE,dd MMM yyyy";
    public String DATE_STANDART = "dd MMM yyyy";

    /**
     * ConvertDate from default Sql output Datetime yyyy/mm/dd HH:MM:SS
     *
     * @param type
     * @return
     */
    public String convertTo(String setDate, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat formatter = null;
        Date testDate = null;
        try {
            testDate = sdf.parse(setDate);
            formatter = new SimpleDateFormat(type);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatter.format(testDate);
    }

    public String convertTo(String setDate, String fromType, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromType, new Locale("in"));
        SimpleDateFormat formatter = null;
        Date testDate = null;
        try {
            testDate = sdf.parse(setDate);
            formatter = new SimpleDateFormat(type);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatter.format(testDate);
    }

    /**
     * Create New Format DateTime
     *
     * @param year
     * @param month
     * @param day
     * @param format
     * @return
     */
    public String newFormat(int year, int month, int day, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * Create New Format DateTime in Indonesian (Ex: 03 Februai 1991)
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public String indonesianFormat(int year, int month, int day) {
        String[] indoMonth = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

        String newDay = String.valueOf(day);
        newDay = (newDay.length() == 1) ? "0" + newDay : newDay;
        String newMonth = indoMonth[month];
        String newYear = String.valueOf(year);
        return newDay + " " + newMonth + " " + newYear;
    }

    public String indonesianFormat(String sDate, String format) {
        String[] indoMonth = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        String[] days = new String[]{"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = df.parse(sDate);

            c.setTime(date);

            String newDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
            newDay = (newDay.length() == 1) ? "0" + newDay : newDay;
            String newMonth = indoMonth[c.get(Calendar.MONTH)];
            newMonth = (newMonth.length() == 1) ? "0" + newMonth : newMonth;
            String newYear = String.valueOf(c.get(Calendar.YEAR));

            if (format.equals(DATE_DAY_NAME)) {
                String dayName = days[c.get(Calendar.DAY_OF_WEEK) - 1];
                return dayName + ", " + newDay + " " + newMonth + " " + newYear;
            } else if (format.equals(DATE_STANDART)) {
                return newDay + " " + newMonth + " " + newYear;
            } else
                return newDay + " " + newMonth + " " + newYear;

        } catch (ParseException e) {
            e.printStackTrace();
            return sDate;
        }
    }

    public String convertToStandart(String date) {
        String sResult = date;
        String[] indoMonth = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        String[] newDate = date.split(" ");
        if (newDate.length == 3) {
            String sMonth = newDate[1];
            int i = 0;
            for (String m : indoMonth) {
                if (m.equalsIgnoreCase(newDate[1])) {
                    sMonth = String.valueOf(i + 1);
                    break;
                }
                i++;
            }
            sResult = newDate[2] + "-" + ((sMonth.length() == 1) ? "0" + sMonth : sMonth) + "-" + ((newDate[0].length() == 1) ? "0" + newDate[0] : newDate[0]);
        }
        return sResult;
    }

    public String addDayToDate(String date, int day, String format) {
        String result = date;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
            c.add(Calendar.DATE, day);
            result = sdf.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String addDayToDate(Date date, int day, String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        result = sdf.format(c.getTime());
        return result;
    }

}
