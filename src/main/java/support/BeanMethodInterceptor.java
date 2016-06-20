package support;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import annotation.PropertyListener;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Created by Shannon,chen on 16/6/17.
 * <p>
 * 使用Enhancer代理对应的Bean,同时对对应setXXX()方法含有PropertyListener注解的进行代理,记录变化之前和之后的值
 */
public class BeanMethodInterceptor<T> implements MethodInterceptor {
    private T target; // 监控的目标对象
    private Object oldValue; // 监控的目标对象某个属性变化之前的值
    private Object newValue; // 监控的目标对象某个属性变化之后的值

    private PropertyChangeListener[] listeners; // 属性变化监听器

    public BeanMethodInterceptor(T target, PropertyChangeListener... listeners) {
        this.target = target;
        this.listeners = listeners;
    }

    private Enhancer enhance = new Enhancer();

    public Object createProxy(Class<?> clazz) {
        enhance.setSuperclass(clazz);
        enhance.setCallback(this);
        return enhance.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Class clazz = target.getClass();
        PropertyListener propertyListener = method.getAnnotation(PropertyListener.class);
        if (propertyListener != null) {
            String propertyName = propertyListener.value();
            Field field = clazz.getDeclaredField(propertyName);
            field.setAccessible(Boolean.TRUE);
            oldValue = field.get(target);
            newValue = objects[0];

            PropertyChangeEvent event = new PropertyChangeEvent(target, propertyName, oldValue, newValue);
            Arrays.stream(listeners).forEach(listener -> listener.propertyChange(event));

            return methodProxy.invokeSuper(obj, objects);
        }

        return methodProxy.invokeSuper(obj, objects);

    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

}
