package util;

/**
 * Description: DispatcherProject
 * Created by leizhaoyuan on 19/7/11 下午10:32
 */

/**
 * 实现字符串首字母大写操作
 */
public class StringUtils {
     public static String initcap(String str) {
         if (str == null || "".equals(str)) {
             return str;
         }
         if (str.length() == 1) {//一个字符
             return str.toUpperCase();
         }
         return str.substring(0, 1).toUpperCase() + str.substring(1);
     }
}
