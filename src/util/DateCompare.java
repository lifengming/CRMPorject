package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/30 上午2:06
 */
public class DateCompare {
    /**
     * 将传递进来的日期与当前日期进行比较，在进行比较的过程中，是以当前日期为主，不包括时间
     *
     * @param date
     * @return
     */
    public static boolean compare(Date date) {
        return getCurrentDate().getTime() <= date.getTime();
    }

    public static Date getCurrentDate() {
        String currDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 00:00:00.000 ";
        Date cDate = null;
        try {
            cDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(currDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cDate;
    }
}

