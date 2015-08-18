package de.jmens.ariadne.exception;

public class CannotReadFilesException extends RuntimeException
{

	public CannotReadFilesException()
	{
		super();
	}

	public CannotReadFilesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CannotReadFilesException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public CannotReadFilesException(String message)
	{
		super(message);
	}

	public CannotReadFilesException(Throwable cause)
	{
		super(cause);
	}

}
