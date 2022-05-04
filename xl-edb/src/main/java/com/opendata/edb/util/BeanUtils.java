package com.opendata.edb.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自行封装的一个BeanUtils, 提供Spring, Apache的BeanUtils 之外的一些功能. <p/>
 * 访问在当前类声明的private/protected成员变量及private/protected函数的BeanUtils.
 * 注意,因为必须为当前类声明的变量,通过继承获得的protected变量将不能访问, 需要转型到声明该变量的类才能访问. 反射的其他功能请使用Apache
 * Jarkarta Commons BeanUtils
 */
public class BeanUtils
{
	/**
	 * 获取当前类声明的private/protected变量
	 */
	static public Object getPrivateProperty(Object object, String propertyName) throws IllegalAccessException, NoSuchFieldException
	{
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		return field.get(object);
	}

	/**
	 * 设置当前类声明的private/protected变量
	 */
	static public void setPrivateProperty(Object object, String propertyName, Object newValue) throws IllegalAccessException, NoSuchFieldException
	{

		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		field.set(object, newValue);

	}

	/**
	 * 调用当前类声明的private/protected函数
	 */
	static public Object invokePrivateMethod(Object object, String methodName, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++)
		{
			types[i] = params[i].getClass();
		}
		Method method = object.getClass().getDeclaredMethod(methodName, types);
		method.setAccessible(true);
		return method.invoke(object, params);
	}

	/**
	 * 调用当前类声明的private/protected函数
	 */
	static public Object invokeSuperPrivateMethod(Object object, String methodName, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++)
		{
			types[i] = params[i].getClass();
		}
		Method method = object.getClass().getSuperclass().getDeclaredMethod(methodName, types);
		method.setAccessible(true);
		return method.invoke(object, params);
	}
	
	/**
	 * 将一个属性设置指定属性值
	 * @param bean 对象
	 * @param property 属性名称
	 * @param value 值
	 * @throws Exception
	 */
	static public  void setObjectValue(Object bean, String property, Object value) throws Exception
	{
		Method[] methods = bean.getClass().getMethods();
		for (Method method : methods)
		{
			if (method.getName().toLowerCase().equals(("set" + property).toLowerCase()))
			{
				method.invoke(bean, value);
				break;
			}
		}

	}
}
