package com.techweb.onlinetest.exception;

public class SCBException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SCBException() {
	}

	public SCBException(String message) {
		super(message);
	}

	public SCBException(Throwable cause) {
		super(cause);
	}

	public SCBException(String message, Throwable cause) {
		super(message, cause);
	}

	public SCBException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
