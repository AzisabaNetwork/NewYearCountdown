package net.azisaba.silo.newyear;

import java.util.Calendar;
import java.util.Date;

/**
 * 日付のフォーマットを行うクラス
 *
 * @author siloneco
 *
 */
public class DateFormatUtils {

//    public static String format(long l) {
//
//    }

    public static String format(Calendar cal) {
        if ( cal.getTimeInMillis() >= 0 ) {
            cal.add(Calendar.YEAR, -1970);
            cal.add(Calendar.HOUR, -9);
        }
        StringBuilder builder = new StringBuilder();

        if ( cal.get(Calendar.YEAR) > 1 ) {
            builder.append(cal.get(Calendar.YEAR) - 1).append("年");
        }
        if ( cal.get(Calendar.MONTH) > 0 ) {
            builder.append(cal.get(Calendar.MONTH) + 1).append("ヵ月");
        }
        if ( cal.get(Calendar.DATE) > 1 ) {
            builder.append(cal.get(Calendar.DATE) - 1).append("日");
        }

        if ( cal.get(Calendar.HOUR_OF_DAY) > 0 ) {
            builder.append(cal.get(Calendar.HOUR_OF_DAY)).append("時間");
        }
        if ( cal.get(Calendar.MINUTE) > 0 ) {
            builder.append(cal.get(Calendar.MINUTE)).append("分");
        }
        if ( cal.get(Calendar.SECOND) > 0 ) {
            builder.append(cal.get(Calendar.SECOND)).append("秒");
        }

        return builder.toString();
    }

    public static String format(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return format(cal);
    }

    public static String format(Date date1, Date date2) {
        Date max, min;

        if ( date1.after(date2) ) {
            max = date1;
            min = date2;
        } else {
            max = date2;
            min = date1;
        }

        return format(new Date(max.getTime() - min.getTime()));
    }

    public static String format(Calendar cal1, Calendar cal2) {
        return format(cal1.getTime(), cal2.getTime());
    }
}
