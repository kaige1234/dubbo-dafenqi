package com.xl.es.script.reflection.wrapper;

import com.xl.es.script.reflection.MetaObject;

public interface ObjectWrapperFactory
{

	boolean hasWrapperFor(Object object);

	ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);

}
