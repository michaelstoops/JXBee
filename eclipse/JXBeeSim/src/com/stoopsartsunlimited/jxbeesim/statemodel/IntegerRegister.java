package com.stoopsartsunlimited.jxbeesim.statemodel;

import java.nio.ByteBuffer;

public class IntegerRegister extends Register {

	public IntegerRegister() {
		super();
		super.value = new byte[] { 0, 0, 0, 0 };
	}
	
	public IntegerRegister(int value) {
		this();
		write(value);
	}
	
	@Override
	public Object read() {
		return readInteger();
	}
	
	public int readInteger() {
		return ByteBuffer.wrap(value).getInt();
	}

	@Override
	public void write(Object value) {
		this.write(((Integer)value).intValue());
	}

	public void write(int value) {
		ByteBuffer.wrap(super.value).putInt(value);
	}

}
