package util.validator;

import util.ResourceUtils;
import util.servlet.DispatcherServlet;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: DispatcherProject
 * Created by leizhaoyuan on 19/7/18 下午10:14
 */


public class Validation {
    private Validation(){}
    /**
     *  进行数据的验证处理操作，有错误出现（不满足验证规则），将错误信息保存在Map集合之中:<br>
     *      Map集合保存有两个内容:<br>
     *          <li>key=参数的名称</li>
     *          <li>value=错误的完整信息</li>
     * @param servlet 因为整个的处理之中，需要Messages,Pages,getXxx()等相关参数操作
     * @return
     */
    public static Map<String, String> validate(DispatcherServlet servlet) {
        Map<String, String> errors = new HashMap<>();
        //1,取出验证规则
        String ruleKey = servlet.getClass().getSimpleName() + "." + servlet.getStatus() + ".rules";
        try {
            String rule = ResourceUtils.get("Validations", ruleKey);
            if (rule != null) {
                String result[] = rule.split("\\|");
                for (int x = 0; x < result.length; x++) {//开始取出每一个规则进行检测
                    String temp[] = result[x].split(":");
                    String val = servlet.getStringParameter(temp[0]);
                    switch (temp[1]) {
                        case "string": {
                            if (!Validation.validateEmpty(val)) {
                                errors.put(temp[0], servlet.getMessageValue("validation.string.msg"));
                            }
                            break;
                        }
                        case "int": {
                            if (!Validation.validateInt(val)) {
                                errors.put(temp[0], servlet.getMessageValue("validation.int.msg"));
                            }
                            break;
                        }
                        case "double": {
                            if (!Validation.validateDouble(val)) {
                                errors.put(temp[0], servlet.getMessageValue("validation.double.msg"));
                            }
                            break;
                        }
                        case "date": {
                            if (!Validation.validateDate(val)) {
                                errors.put(temp[0], servlet.getMessageValue("validation.date.msg"));
                            }
                            break;
                        }
                        case "datetime": {
                            if (!Validation.validateDatetime(val)) {
                                errors.put(temp[0], servlet.getMessageValue("validation.datetime.msg"));
                            }
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {//出现了错误，表示没有规则
            e.printStackTrace();
        }
        return errors;
    }

    /**
     * 验证数据是否为日期
     * @param val
     * @return
     */
    public static boolean validateDatetime(String val) {
        if (validateEmpty(val)) {
            return val.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
        }
        return false;
    }

    /**
     * 验证数据是否为日期
     * @param val
     * @return
     */
    public static boolean validateDate(String val) {
        if (validateEmpty(val)) {
            return val.matches("\\d{4}-\\d{2}-\\d{2}");
        }
        return false;
    }

    /**
     * 验证数据是否为小数
     * @param val
     * @return
     */
    public static boolean validateDouble(String val) {
        if (validateEmpty(val)) {
            return val.matches("\\d+(\\.\\d+)?");
        }
        return false;
    }
    /**
     * 验证数据是否为整数
     * @param val
     * @return
     */
    public static boolean validateInt(String val) {
        if (validateEmpty(val)) {
            return val.matches("\\d+");
        }
        return false;
    }
    /**
     * 验证制动数据是否为空，或者为空字符串
     *
     * @return 为空字符串，返回false
     */
    public static boolean validateEmpty(String val) {
        if (val == null || "".equals(val)) {
            return false;
        }
        return true;
    }
}
