package com.geniusver.persistence.example.order.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * EntityLogger
 *
 * @author GeniusV
 */
public class EntityLogger implements MethodInterceptor {
    private final Logger log = LoggerFactory.getLogger(EntityLogger.class);

    private Object originalObj = null;

    public <T> T create(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    public <T> T enhance(T obj) {
        this.originalObj = obj;
        return (T) create(obj.getClass());
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        try {
            result = invokeOriginal(o, objects, methodProxy);
            if (Modifier.isPublic(method.getModifiers()) && log.isDebugEnabled()) {
                log.debug("entity {}.{}, args {}, return {}, object {}",
                        originalObj == null ? o.getClass().getSuperclass().getSimpleName() : originalObj.getClass().getSimpleName(),
                        method.getName(),
                        objects,
                        result,
                        o);
            }
        } catch (Throwable throwable) {
            if (Modifier.isPublic(method.getModifiers()) && log.isDebugEnabled()) {
                log.debug("entity {} {}, args {}, exception {}:{}, object {}",
                        originalObj == null ? o.getClass().getSuperclass().getSimpleName() : originalObj.getClass().getSimpleName(),
                        method.getName(),
                        objects,
                        throwable.getClass().getName(),
                        throwable.getMessage(),
                        o);
            }
            throw throwable;
        }
        return result;
    }

    private Object invokeOriginal(Object o, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (this.originalObj == null) {
            return methodProxy.invokeSuper(o, objects);
        } else {
            return methodProxy.invoke(originalObj, objects);
        }
    }
}
