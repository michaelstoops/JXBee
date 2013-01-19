package com.stoopsartsunlimited.jxbeesim.statemodel;

import static org.junit.Assert.*;

import org.junit.Test;

public class ByteRegisterTest {

	private static byte a = (byte)0xaa;
	private static byte b = (byte)0xbb;

	@Test
	public void testRead() {
		ByteRegister r = new ByteRegister(a);
		assertEquals(a, r.read());
	}

	@Test
	public void testWriteObject() {
		ByteRegister r = new ByteRegister();
		r.write(Byte.parseByte("1a", 16));
		assertEquals(0x1a, r.readByte());
		
		try {
			r.write("That's a load of crap, Fry!");
			fail();
		} catch (Exception e) {}
		try {
			r.write(new Exception("You make a persuasive argument, Fry!"));
			fail();
		} catch (Exception e) {}	}

	@Test
	public void testByteRegister() {
		ByteRegister r = new ByteRegister();
		assertArrayEquals(new byte[]{0}, r.value);
	}

	@Test
	public void testByteRegisterByte() {
		ByteRegister r = new ByteRegister(a);
		assertArrayEquals(new byte[]{(byte)0xaa}, r.value);
	}

	@Test
	public void testReadByte() {
		ByteRegister r = new ByteRegister((byte)0x65);
		assertEquals(0x65, r.readByte());
	}

	@Test
	public void testWriteByte() {
		ByteRegister r = new ByteRegister();
		r.write(b);
		assertEquals(b, r.readByte());
	}
}
