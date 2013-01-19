package com.stoopsartsunlimited.jxbeesim.statemodel;

import java.nio.ByteBuffer;

public class ShortRegister extends Register {

	public ShortRegister() {
		super();
		super.value = new byte[] { 0, 0 };
	}
	
	public ShortRegister(short value) {
		this();
		write(value);
	}
	
	@Override
	public Object read() {
		return readShort();
	}
	
	public short readShort() {
		return ByteBuffer.wrap(value).getShort();
	}

	@Override
	public void write(Object value) {
		this.write(((Short)value).shortValue());
	}

	public void write(short value) {
		ByteBuffer.wrap(super.value).putShort(value);
	}

}
