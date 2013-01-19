package com.stoopsartsunlimited.jxbeesim.statemodel;

import java.nio.ByteBuffer;
import java.util.BitSet;

public class BitSetRegister extends Register {

	public BitSetRegister() {
		super();
		write(new BitSet());
	}
	
	public BitSetRegister(BitSet value) {
		this();
		write(value);
	}
	
	public BitSetRegister(int value) {
		this();
		write(BitSet.valueOf(ByteBuffer.allocate(4).putInt(value).array()));
	}
	
	@Override
	public Object read() {
		return readBitSet();
	}
	
	public BitSet readBitSet() {
		return BitSet.valueOf(value);
	}

	@Override
	public void write(Object value) {
		this.write((BitSet)value);
	}
	
	public void write(BitSet value) {
		this.value = value.toByteArray();
	}
	
}
