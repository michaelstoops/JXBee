package com.stoopsartsunlimited.jxbeesim.statemodel;

public class Register {

	protected byte[] value;
	
	public Register() {
		value = new byte[0];
	}
	
	public Register(byte[] value) {
		this.value = value;
	}
	
	public Object read() {
		return value;
	}
	
	public byte[] readBytes() {
		return value;
	}
	
	public void write(Object value) {
		this.write((byte[])value);
	}
	
	public void write(byte[] value) {
		this.value = value; 
	}
}
