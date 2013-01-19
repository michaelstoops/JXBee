package com.stoopsartsunlimited.jxbeesim.statemodel;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegisterTest {

	@Test
	public void testRegister() {
		Register r = new Register();
		assertNotNull(r.value);
		assertEquals(0, r.value.length);
		
	}

	@Test
	public void testRegisterByteArray() {
		byte[] bytes = new byte[]{1,2,3,4};
		Register r = new Register(bytes);
		assertEquals(bytes, r.value);
		assertArrayEquals(bytes, r.value);
		
	}

	@Test
	public void testRead() {
		byte[] bytes = new byte[]{1,2,3,4};
		Register r = new Register(bytes);
		assertEquals(bytes, r.read());
		assertArrayEquals(bytes, (byte[])r.read());
	}

	@Test
	public void testReadBytes() {
		byte[] bytes = new byte[]{1,2,3,4};
		Register r = new Register(bytes);
		assertEquals(bytes, r.readBytes());
		assertArrayEquals(bytes, r.readBytes());
		assertEquals((byte[])r.read(), r.readBytes());
	}

	@Test
	public void testWriteObject() {
		byte[] bytes = new byte[]{1,2,3,4};
		Register r = new Register();
		r.write(bytes);
		assertEquals(r.value, r.readBytes());
		
		
		r = new Register(new byte[]{5,6,7});
		r.write((Object)bytes);
		assertEquals(bytes, r.read());
		
		r = new Register(bytes);
		try {
			r.write("dude");
			fail();
		} catch (Exception e) { }

		
	
	}

	@Test
	public void testWriteByteArray() {
		byte[] bytes = new byte[]{1,2,3,4};
		byte[] moreBytes = new byte[]{5,6,7};
		Register r = new Register(bytes);

		r.write(moreBytes);
		assertEquals(moreBytes, r.read());
	
	}

}
