package com.stoopsartsunlimited.jxbeesim.statemodel;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

public class InetAddressRegisterTest {
	
	
	
	@Test
	public void testRead() throws UnknownHostException {
		InetAddressRegister r = new InetAddressRegister(InetAddress.getByName("192.168.1.254"));
		assertEquals(InetAddress.getByName("192.168.1.254"), r.read());
	}

	@Test
	public void testWriteObject() throws UnknownHostException {
		InetAddressRegister r = new InetAddressRegister();
		r.write((Object)InetAddress.getByName("192.168.1.254"));
		assertEquals(InetAddress.getByName("192.168.1.254"), r.readInetAddress());
		
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
	public void testInetAddressRegister() {
		InetAddressRegister r = new InetAddressRegister();
		assertArrayEquals(new byte[0], r.value);
	}

	@Test
	public void testInetAddressRegisterInetAddress() throws UnknownHostException {
		InetAddressRegister r = new InetAddressRegister(InetAddress.getByName("192.168.1.254"));
		assertArrayEquals(new byte[]{ (byte)0xC0,(byte)0xA8,(byte)0x01,(byte)0xFE }, r.value);
	}

	@Test
	public void testInetAddressRegisterString() throws UnknownHostException {
		InetAddressRegister r = new InetAddressRegister("192.168.1.254");
		assertArrayEquals(new byte[]{ (byte)0xC0,(byte)0xA8,(byte)0x01,(byte)0xFE }, r.value);
	}

	@Test
	public void testReadInetAddress() throws UnknownHostException {
		InetAddressRegister r = new InetAddressRegister(InetAddress.getByName("192.168.1.254"));
		assertEquals(InetAddress.getByName("192.168.1.254"), r.readInetAddress());
	}

	@Test
	public void testWriteInetAddress() throws UnknownHostException {
		InetAddressRegister r = new InetAddressRegister();
		r.write(InetAddress.getByName("192.168.1.254"));
		assertEquals(InetAddress.getByName("192.168.1.254"), r.readInetAddress());
	}

	@Test
	public void testWriteString() throws UnknownHostException {
		InetAddressRegister r = new InetAddressRegister();
		r.write("192.168.1.254");
		assertEquals(InetAddress.getByName("192.168.1.254"), r.readInetAddress());
	}

}
