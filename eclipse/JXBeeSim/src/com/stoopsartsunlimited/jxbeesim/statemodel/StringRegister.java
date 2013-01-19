package com.stoopsartsunlimited.jxbeesim.statemodel;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class StringRegister extends Register {
	
	public StringRegister() {
		super();
	}
	
	public StringRegister(String value) {
		this();
		write(value);
	}
	
	@Override
	public Object read() {
		return readString();
	}
	
	public String readString() {
		return Charset.forName("US-ASCII").decode(ByteBuffer.wrap(value)).toString();
	}

	@Override
	public void write(Object value) {
		this.write((String)value);
	}

	public void write(String value) {
		super.value = Charset.forName("US-ASCII").encode(value).array();
	}

}
