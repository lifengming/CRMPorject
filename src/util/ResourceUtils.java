package util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Description: DispatcherProject
 * Created by leizhaoyuan on 19/7/18 下午11:30
 */
public class ResourceUtils {
    /**
     * 根据指定资源名称和指定key取得对应的value内容
     * @param baseName 指定资源名称
     * @param key 对用的key
     * @return
     */
    public static String get(String baseName, String key) {
        return ResourceBundle.getBundle(baseName, Locale.getDefault()).getString(key);
    }
}

