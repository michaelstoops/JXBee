package com.stoopsartsunlimited.jxbeesim.statemodel;

import static org.junit.Assert.*;

import org.junit.Test;

public class ShortRegisterTest {

	private static short a = (short)0x1234;
	private static short b = (short)0x9753;

	@Test
	public void testRead() {
		ShortRegister r = new ShortRegister(a);
		assertEquals(a, r.read());
	}

	@Test
	public void testWriteObject() {
		ShortRegister r = new ShortRegister();
		r.write(Short.parseShort("1234", 16));
		assertEquals(a, r.readShort());
		
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
	public void testShortRegister() {
		ShortRegister r = new ShortRegister();
		assertArrayEquals(new byte[]{0,0}, r.value);
	}

	@Test
	public void testShortRegisterShort() {
		ShortRegister r = new ShortRegister(a);
		assertArrayEquals(new byte[]{0x12,0x34}, r.value);
	}

	@Test
	public void testReadShort() {
		ShortRegister r = new ShortRegister(a);
		assertEquals(0x1234, r.readShort());
	}

	@Test
	public void testWriteShort() {
		ShortRegister r = new ShortRegister();
		r.write(b);
		assertEquals(b, r.readShort());
	}

}
