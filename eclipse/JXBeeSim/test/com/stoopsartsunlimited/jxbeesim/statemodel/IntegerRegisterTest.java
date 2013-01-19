package com.stoopsartsunlimited.jxbeesim.statemodel;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntegerRegisterTest {

	@Test
	public void testRead() {
		IntegerRegister r = new IntegerRegister(0x12345678);
		assertEquals(0x12345678, r.read());
	}

	@Test
	public void testWriteObject() {
		IntegerRegister r = new IntegerRegister();
		r.write(Integer.parseInt("12345678", 16));
		assertEquals(0x12345678, r.readInteger());
		
		try {
			r.write("That's a load of crap, Fry!");
			fail();
		} catch (Exception e) {}
		try {
			r.write(new Exception("You make a persuasive argument, Fry!"));
			fail();
		} catch (Exception e) {}
	}

	@Test
	public void testIntegerRegister() {
		IntegerRegister r = new IntegerRegister();
		assertArrayEquals(new byte[]{0,0,0,0}, r.value);
	}

	@Test
	public void testIntegerRegisterInt() {
		IntegerRegister r = new IntegerRegister(0x12345678);
		assertArrayEquals(new byte[]{0x12,0x34,0x56,0x78}, r.value);
	}

	@Test
	public void testReadInteger() {
		IntegerRegister r = new IntegerRegister(0x12345678);
		assertEquals(0x12345678, r.readInteger());
	}

	@Test
	public void testWriteInt() {
		IntegerRegister r = new IntegerRegister();
		r.write(0x12345678);
		assertEquals(0x12345678, r.readInteger());
	}

}
