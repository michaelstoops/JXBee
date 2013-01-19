package com.stoopsartsunlimited.jxbeesim.statemodel;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringRegisterTest {

	@Test
	public void testRead() {
		StringRegister r = new StringRegister("onetwo");
		assertEquals("onetwo", r.read());
	}
	

	@Test
	public void testWriteObject() {
		StringRegister r = new StringRegister();
		r.write("That's a load of crap, Fry!");
		assertEquals("That's a load of crap, Fry!", r.readString());
		
		try {
			r.write(Integer.parseInt("12345678", 16));
			fail();
		} catch (Exception e) {}
		try {
			r.write(new Exception("You make a persuasive argument, Fry!"));
			fail();
		} catch (Exception e) {}
	}

	@Test
	public void testStringRegister() {
		StringRegister r = new StringRegister();
		assertArrayEquals(new byte[0], r.value);
	}

	@Test
	public void testStringRegisterString() {
		StringRegister r = new StringRegister("hello");
		assertArrayEquals(new byte[]{'h','e','l','l','o'}, r.value);
	}

	@Test
	public void testReadString() {
		StringRegister r = new StringRegister("world");
		assertEquals("world", r.readString());
	}

	@Test
	public void testWriteString() {
		StringRegister r = new StringRegister();
		r.write("kitteh");
		assertEquals("kitteh", r.readString());
	}

}
