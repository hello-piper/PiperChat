package piper.im.common.util;

import org.yaml.snakeyaml.Yaml;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * yml文件读取工具
 *
 * @author piper
 */
public class YamlUtil {

    private static final LinkedHashMap<String, Object> LOADED_CONFIG;

    static {
        LOADED_CONFIG = new Yaml().load(ClassLoader.getSystemResourceAsStream("application.yml"));
    }

    /**
     * 获取配置
     *
     * @param key
     * @param config
     * @param <T>
     * @return
     */
    public static <T> T getConfig(String key, Class<T> config) {
        try {
            T instance = config.newInstance();
            populate((Map) LOADED_CONFIG.get(key), instance);
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 属性填充
     *
     * @param map
     * @param bean
     * @param <T>
     */
    public static <T> void populate(Map<String, Object> map, T bean) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
                String name = descriptor.getName();
                if (map.containsKey(name)) {
                    Method writeMethod = descriptor.getWriteMethod();
                    writeMethod.invoke(bean, map.get(name));
                }
            }
        } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
