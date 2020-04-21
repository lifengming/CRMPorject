package test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description: CRMPorject
 * Created by leizhaoyuan on 19/7/30 上午1:59
 */
public class TestDate2 {
    public static void main(String[] args) throws Exception {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-29");
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-30");
        System.out.println(date1.before(date2));
    }
}
