package com.xl.es.script.type;

import com.xl.es.script.exception.PersistenceException;

public class TypeException extends PersistenceException
{

	public TypeException()
	{
		super();
	}

	public TypeException(String message)
	{
		super(message);
	}

	public TypeException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public TypeException(Throwable cause)
	{
		super(cause);
	}

}
