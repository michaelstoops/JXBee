package com.stoopsartsunlimited.jxbeelib;

@SuppressWarnings("serial")
public class XBeeException extends Exception {

	public XBeeException() {
	}

	public XBeeException(String msg) {
		super(msg);
	}

	public XBeeException(Throwable e) {
		super(e);
	}

	public XBeeException(String msg, Throwable e) {
		super(msg, e);
	}

}
