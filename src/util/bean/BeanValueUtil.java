package util.bean;

import util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Description: DispatcherProject
 * Created by leizhaoyuan on 19/7/11 下午10:00
 */

/**
 * 本类功能根据当前Servlet对象，以及参数名称和内容，实现自动化vo属性的设置
 * 前提;操作的vo对象一定在Servlet中被实例化，且提供有相应的getter方法，
 */
public class BeanValueUtil {
    private Object servletObject;
    private String attributeName;//带有"."的属性名称
    private String attributeValue;//通过外部request.getParameter()接收的！

    /**
     * 传入所需要的对象
     * @param servletObject  触发此操作的Servlet对象
     * @param attributeName 传入参数名称
     * @param attributeValue 传入参数内容
     */
    public BeanValueUtil(Object servletObject, String attributeName, String attributeValue) {
        this.servletObject = servletObject;
        this.attributeName = attributeName.trim();//trim()去掉字符串两端多余的空格！
        this.attributeValue = attributeValue.trim();
    }

    /**
     * 实现属性内容的设置,将参数的内容，设置给指定VO类的属性
     */
    public void setObjectValue() {
        Object currentObject = null;//描述当前vo类对象
        if (this.attributeName.contains(".")) {//若属性名称含有".",则可以处理操作
            String result[] = this.attributeName.split("\\.");
            try {
                Method getMethod = this.servletObject.getClass().getMethod("get" + StringUtils.initcap(result[0]));
                currentObject = getMethod.invoke(this.servletObject);
                if (result.length == 2) {//单级控制
                    //有了参数名称，有参数内容，有当前对象，可以实现反射调用
                    Field field = currentObject.getClass().getDeclaredField(result[1]);//取得指定属性的对象
                    Method setMethod = currentObject.getClass().getMethod("set" + StringUtils.initcap(result[1]), field.getType());
                    setMethod.invoke(currentObject, this.convertValue(field.getType().getSimpleName()));//实现了setter调用
                } else {//多级控制
                    for (int x = 1; x < result.length - 1; x++) {
                        Field subField = currentObject.getClass().getDeclaredField(result[x]);
                        Method getSubMethod = currentObject.getClass().getMethod("get" + StringUtils.initcap(result[x]));
                       // currentObject = getSubMethod.invoke(currentObject);//改变currentObject的值
                        if (getSubMethod.invoke(currentObject) == null) {
                            currentObject = this.objectNewInstance(currentObject, result[x], subField);//通过实例化变更当前对象
                        } else {
                            currentObject = getSubMethod.invoke(currentObject);//通过get()调用变更当前对象！
                        }
                    }
                    //开始对象的多级控制
                    Field attField = currentObject.getClass().getDeclaredField(result[result.length - 1]);
                    Method attMethod = currentObject.getClass().getMethod("set" + StringUtils.initcap(result[result.length - 1]), attField.getType());
                    attMethod.invoke(currentObject, this.convertValue(attField.getType().getSimpleName()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 实现对象的实例化 反射调用setter实现对象的实例化控制
     *
     * @param field         对象所属类型，只有知道类型，才可以反射实例化对象
     * @param currentObject 当前对象
     * @param attr          属性名称
     * @return 一个实例化好的对象
     */
    private Object objectNewInstance(Object currentObject, String attr, Field field) throws Exception {
        //System.out.println("********"+field.getType());
        //System.out.println("********" + currentObject.getClass());
        Object newObject = field.getType().newInstance();//只能根据属性对象，反射实例化对象
        Method setMethod = currentObject.getClass().getMethod("set" + StringUtils.initcap(attr), field.getType());
        setMethod.invoke(currentObject, newObject);
        return newObject;
    }

    /**
     * 因为传入数据内容都是String,而调用setter设置值的时候，是设置相应类型的值，有Integer,Double,Date,String型值
     * 所以需要将String转换为对应类型的值！
     *
     * @return
     */
    private Object convertValue(String type) {
        if ("int".equals(type) || "Integer".equals(type)) {
           return Integer.parseInt(this.attributeValue);//将内容变为整形返回
        }
        if ("double".equalsIgnoreCase(type)) {
            return Double.parseDouble(this.attributeValue);
        }
        if ("date".equalsIgnoreCase(type)) {
            try {
                if (this.attributeValue.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        return new SimpleDateFormat("yyyy-MM-dd").parse(this.attributeValue);
                }
                if (this.attributeValue.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
                        return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").parse(this.attributeValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        return this.attributeValue;
    }
}
