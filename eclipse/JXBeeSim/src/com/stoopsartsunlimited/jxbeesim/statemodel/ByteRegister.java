package com.stoopsartsunlimited.jxbeesim.statemodel;

import java.nio.ByteBuffer;

public class ByteRegister extends Register {

	public ByteRegister() {
		super();
		super.value = new byte[] { 0 };
	}
	
	public ByteRegister(byte value) {
		this();
		write(value);
	}
	
	@Override
	public Object read() {
		return readByte();
	}
	
	public byte readByte() {
		return value[0];
	}

	@Override
	public void write(Object value) {
		this.write(((Byte)value).byteValue());
	}

	public void write(byte value) {
		ByteBuffer.wrap(super.value).put(value);
	}

}
