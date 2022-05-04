package com.xl.es.script.reflection.wrapper;

import com.xl.es.script.reflection.MetaObject;
import com.xl.es.script.reflection.factory.ObjectFactory;
import com.xl.es.script.reflection.property.PropertyTokenizer;

public interface ObjectWrapper {

	Object get(PropertyTokenizer prop);

	void set(PropertyTokenizer prop, Object value);

	String findProperty(String name);

	String[] getGetterNames();

	String[] getSetterNames();

	Class getSetterType(String name);

	Class getGetterType(String name);

	boolean hasSetter(String name);

	boolean hasGetter(String name);

	MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory);

}
