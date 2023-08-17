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
import java.util.Objects;

/**
 * YamlUtil
 *
 * @author piper
 */
public final class YamlUtil {

    private static final LinkedHashMap<String, Object> LOADED_CONFIG;

    static {
        LOADED_CONFIG = new Yaml().load(ClassLoader.getSystemResourceAsStream("application.yml"));
        String profile = getProfile();
        if (StringUtil.isNotEmpty(profile)) {
            LinkedHashMap<String, Object> profileMap =
                    new Yaml().load(ClassLoader.getSystemResourceAsStream("application-" + profile + ".yml"));
            LOADED_CONFIG.putAll(profileMap);
        }
    }

    /**
     * 获取配置
     */
    public static <T> T getConfig(String key, Class<T> config) {
        try {
            Object o = LOADED_CONFIG.get(key);
            if (o != null) {
                T instance = config.getDeclaredConstructor().newInstance();
                populate((Map) o, instance);
                return instance;
            }
        } catch (InstantiationException | IllegalAccessException
                 | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 属性填充
     */
    public static <T> void populate(Map<String, Object> map, T bean) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
                String name = descriptor.getName();
                if (map.containsKey(name)) {
                    Object writeValue = map.get(name);
                    if (writeValue == null) {
                        continue;
                    }
                    Class<?> propertyType = descriptor.getPropertyType();
                    Method setMethod = descriptor.getWriteMethod();
                    if (String.class.isAssignableFrom(propertyType)) {
                        setMethod.invoke(bean, String.valueOf(writeValue));
                        continue;
                    }
                    if (Number.class.isAssignableFrom(propertyType)) {
                        if (!propertyType.getName().equals(writeValue.getClass().getName())) {
                            Method valueOfMethod = getMethod(propertyType, "valueOf", String.class);
                            if (null != valueOfMethod) {
                                writeValue = valueOfMethod.invoke(null, writeValue.toString());
                                setMethod.invoke(bean, writeValue);
                                continue;
                            }
                        }
                    }
                    setMethod.invoke(bean, writeValue);
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

    public static String getProfile() {
        LinkedHashMap<String, Object> server = (LinkedHashMap) LOADED_CONFIG.get("server");
        if (server != null && !server.isEmpty()) {
            Object profile = server.get("profile");
            if (!Objects.isNull(profile)) {
                return (String) profile;
            }
        }
        return null;
    }
}
