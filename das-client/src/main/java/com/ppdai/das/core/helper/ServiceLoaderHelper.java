package com.ppdai.das.core.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

public class ServiceLoaderHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLoaderHelper.class);

    private static ApplicationContext applicationContext;

    public ServiceLoaderHelper(ApplicationContext applicationContext) {
        ServiceLoaderHelper.applicationContext = applicationContext;
    }

    public static <T> T getInstance(Class<T> clazz, Class<?> defaultImplClass) {
        try {
            LOGGER.info("获取bean:{}", clazz.getName());
            T bean = applicationContext.getBean(clazz);
            LOGGER.info("获取bean成功:" + clazz.getName());
            return bean;
            //多余一个bean
        } catch (NoUniqueBeanDefinitionException e1) {
            LOGGER.error("有多个bean,class:{}", clazz.getName());
            //没有bean
        } catch (NoSuchBeanDefinitionException e) {
            LOGGER.error("bean不存在:{}", clazz.getName());
            //其他异常
        } catch (Exception e2) {
            LOGGER.error("获取bean错误", e2);
            throw e2;
        }

        return getInstancePrivate(clazz, defaultImplClass);
    }

    private static <T> T getInstancePrivate(Class<T> clazz, Class<?> defaultImplClass) {
        LOGGER.info("从配置文件获取实现:{}", clazz.getName());
        T inst = getInstance(clazz);
        if (inst != null) {
            return inst;
        }

        LOGGER.warn("No customeized implementation found for: " + clazz);
        try {
            return (T) defaultImplClass.newInstance();
        } catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can not create default implementation: " + defaultImplClass);
        }
    }

    public static <T> T getInstance(Class<T> clazz) {
        T instance = null;
        try {
            Iterator<T> iterator = getIterator(clazz);

            if (!Ordered.class.isAssignableFrom(clazz)) {
                if (iterator.hasNext()) {
                    return iterator.next();
                }
            } else {
                List<T> sortServices = new LinkedList<>();
                while (iterator.hasNext()) {
                    T service = iterator.next();
                    sortServices.add(service);
                }
                if (sortServices.size() == 0) {
                    return null;
                }
                Collections.sort(sortServices, (Comparator<? super T>) new OrderedComparator());
                return sortServices.get(0);
            }
        } catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException("Can not locate implementation of: " + clazz);
        }
        return instance;
    }

    private static <T> Iterator<T> getIterator(Class<T> clazz) {
        ServiceLoader<T> loader = ServiceLoader.load(clazz);
        return loader.iterator();
    }

}
