package com.xl.es.script.builder.Exception;

public class ScriptMapperException extends RuntimeException
{
	private static final long serialVersionUID = 6805253141674575364L;

	public ScriptMapperException(Throwable cause)
	{
		super(cause);
	}

	public ScriptMapperException(String message)
	{
		super(message);
	}
}
