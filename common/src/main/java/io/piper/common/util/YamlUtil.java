/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.common.util;

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
        T instance = null;
        try {
            instance = config.getDeclaredConstructor().newInstance();
            populate((Map) LOADED_CONFIG.get(key), instance);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return instance;
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
                    Object writeValue = map.get(name);
                    Class<?> propertyType = descriptor.getPropertyType();
                    if (Number.class.isAssignableFrom(propertyType)) {
                        if (!propertyType.getName().equals(writeValue.getClass().getName())) {
                            Method valueOfMethod = getMethod(propertyType, "valueOf", String.class);
                            if (null != valueOfMethod) {
                                valueOfMethod.invoke(null, writeValue.toString());
                                continue;
                            }
                        }
                    }
                    Method writeMethod = descriptor.getWriteMethod();
                    writeMethod.invoke(bean, writeValue);
                }
            }
        } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Method getMethod(Class<?> clz, String name, Class paramClz) {
        if (Number.class.isAssignableFrom(clz)) {
            try {
                return clz.getMethod(name, paramClz);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
