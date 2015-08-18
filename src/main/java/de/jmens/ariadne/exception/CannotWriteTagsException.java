package de.jmens.ariadne.exception;

public class CannotWriteTagsException extends RuntimeException
{
	public CannotWriteTagsException()
	{
		super();
	}

	public CannotWriteTagsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CannotWriteTagsException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public CannotWriteTagsException(String message)
	{
		super(message);
	}

	public CannotWriteTagsException(Throwable cause)
	{
		super(cause);
	}

}
