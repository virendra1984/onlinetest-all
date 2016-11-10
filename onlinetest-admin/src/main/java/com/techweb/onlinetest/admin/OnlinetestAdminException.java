package com.techweb.onlinetest.admin;

public class OnlinetestAdminException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OnlinetestAdminException() {
		super();
	}

	public OnlinetestAdminException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OnlinetestAdminException(String message, Throwable cause) {
		super(message, cause);
	}

	public OnlinetestAdminException(String message) {
		super(message);
	}

	public OnlinetestAdminException(Throwable cause) {
		super(cause);
	}

}
