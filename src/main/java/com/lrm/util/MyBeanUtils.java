package com.lrm.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean处理
 *
 * @author 网络
 */
public class MyBeanUtils {

    /**
     * 获取所有的属性值为空的属性名数组 无法排除嵌套属性
     *
     * @param source 实体对象
     * @return 空属性
     */
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds =  beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            String propertyName = pd.getName();
            Object obj = beanWrapper.getPropertyValue(propertyName);
            if (obj == null) {
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[0]);
    }

    /**
     * @param cls 传入对象的class
     * @param source 源对象
     * @param dest 目标对象
     * @param <T> 泛型类型
     */
    public static <T> void populate(Class<T> cls, T source, T dest) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        //先用类反射对新new的对象设置属性值(必须遵守Java设置规范) 即通过setter方法设置
        //遍历出所有该类声明的属性
        //getDeclaredFields()返回Class中所有的字段，包括私有字段
        Field[] flds = cls.getDeclaredFields();
        for(Field fld : flds){
            //获取该fld对象所代表的属性名
            String fldName = fld.getName();
            //得到方法后缀
            String methodName = fldName.substring(0, 1).toUpperCase() + fldName.substring(1);
            //得到set方法名
            String getMethodName = "get" + methodName;
            //得到get方法名
            String setMethodName = "set" + methodName;

            //根据方法名和参数的数据类型(其实就是属性的类型)，获得Method对象
            Class<?>[] paramTypes = new Class[1];
            paramTypes[0] = fld.getType();

            Method getMethod = cls.getDeclaredMethod(getMethodName);
            Method setMethod = cls.getDeclaredMethod(setMethodName, paramTypes);

            //得到source中对应属性
            Object[] args = new Object[1];
            args[0] = getMethod.invoke(source);
            ArrayList arg = new ArrayList(1);
            arg.add(new Object());
            if (args[0] instanceof ArrayList) {
                arg = (ArrayList) args[0];
            }
            //如果source中不为空 拷贝进去
            if (args[0] != null && arg.size() != 0) {
                setMethod.invoke(dest, args);
            }
        }
    }
}
