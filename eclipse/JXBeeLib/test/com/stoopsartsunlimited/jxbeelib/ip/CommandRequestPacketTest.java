package com.stoopsartsunlimited.jxbeelib.ip;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

public class CommandRequestPacketTest {

	@Test
	public void testCommandRequestPacket() {
		CommandRequestPacket p;
		
		p = new CommandRequestPacket();
		assertEquals(6, p.getBytes().length);
		assertArrayEquals(SamplePackets.commandRequest_default, p.getBytes());

		try {
			p = new CommandRequestPacket(null, 0, 0);
			fail();
		} catch (IllegalArgumentException e) { }
		try {
			p = new CommandRequestPacket(new byte[0], 0, 0);
			fail();
		} catch (IllegalArgumentException e) { }
		try {
			p = new CommandRequestPacket(new byte[5], 0, 5);
			fail();
		} catch (IllegalArgumentException e) { }
		try {
			p = new CommandRequestPacket(SamplePackets.commandResponse_get_ID, 0, SamplePackets.commandResponse_get_ID.length);
			fail();
		} catch (IllegalArgumentException e) { }

	}

	@Test
	public void testCommandRequestPacketByteArrayIntInt() {
		CommandRequestPacket p;
		
		byte[] bytes;
		bytes = SamplePackets. commandRequest_default;
		p = new CommandRequestPacket(bytes, 0, bytes.length);
		assertArrayEquals(bytes, p.getBytes());

		bytes = SamplePackets. commandRequest_get_ID;
		p = new CommandRequestPacket(bytes, 0, bytes.length);
		assertArrayEquals(bytes, p.getBytes());

		bytes = SamplePackets. commandRequest_set_ID;
		p = new CommandRequestPacket(bytes, 0, bytes.length);
		assertArrayEquals(bytes, p.getBytes());
	}

	@Test
	public void testCommandRequestPacketString() {
		CommandRequestPacket p;
		
		p = new CommandRequestPacket("SL");
		assertEquals("SL", p.getATCommand());
		
		p = new CommandRequestPacket("AC");
		assertEquals("AC", p.getATCommand());
	}

	@Test
	public void testCommandRequestPacketStringObject() {
		CommandRequestPacket p;
		
		p = new CommandRequestPacket("NI", SampleValues.testing1_string);
		assertArrayEquals(SamplePackets.commandRequest_set_NI, p.getBytes());
		assertArrayEquals(SampleValues.testing1_ASCII, p.getParameter());
		assertEquals(SampleValues.testing1_string, p.getParameterAsString());

		p = new CommandRequestPacket("NI", SampleValues.testing1_ASCII);
		assertArrayEquals(SamplePackets.commandRequest_set_NI, p.getBytes());
		assertArrayEquals(SampleValues.testing1_ASCII, p.getParameter());
		assertEquals(SampleValues.testing1_string, p.getParameterAsString());
		p.clearParameter();
		assertArrayEquals(new byte[0], p.getParameter());
		assertEquals("", p.getParameterAsString());
		p.setParameter(SampleValues.testing1_ASCII);
		assertArrayEquals(SampleValues.testing1_ASCII, p.getParameter());
		p.clearParameter();
		p.setParameter(SampleValues.testing1_string);
		assertEquals(SampleValues.testing1_string, p.getParameterAsString());
		assertArrayEquals(SampleValues.testing1_ASCII, p.getParameter());

		assertArrayEquals(new CommandRequestPacket("NI", SampleValues.testing1_string).getBytes(), new CommandRequestPacket("NI", SampleValues.testing1_ASCII).getBytes());
		
		try {
			p = new CommandRequestPacket("", "Afdsa");
			fail();
		} catch (IllegalArgumentException e) { }
		try {
			p = new CommandRequestPacket("9", "Afdsa");
			fail();
		} catch (IllegalArgumentException e) { }
		try {
			p = new CommandRequestPacket("654879879", "Afdsa");
			fail();
		} catch (IllegalArgumentException e) { }

		try {
			p.setParameter(new byte[1399]);
			fail();
		} catch (IllegalArgumentException e) { }
		
	}

	@Test
	public void testCommandRequestPacketIntString() {
		CommandRequestPacket p;
		
		p = new CommandRequestPacket((byte)0x55, "98");
		assertArrayEquals(new byte[]{ (byte)0x02, 0x00, 0x55, 0x02, '9', '8' }, p.getBytes());
	}

	@Test
	public void testCommandRequestPacketIntBooleanString() {
		CommandRequestPacket p;
		
		p = new CommandRequestPacket((byte)0x55, false, "98");
		assertArrayEquals(new byte[]{ (byte)0x02, 0x00, 0x55, 0x00, '9', '8' }, p.getBytes());
		p = new CommandRequestPacket((byte)0x55, true, "98");
		assertArrayEquals(new byte[]{ (byte)0x02, 0x00, 0x55, 0x02, '9', '8' }, p.getBytes());
	}

	@Test
	public void testCommandRequestPacketIntBooleanStringObject() throws UnknownHostException {
		CommandRequestPacket p;
		
		p = new CommandRequestPacket(0x35, false, "OO", "noes");
		assertArrayEquals(new byte[]{ (byte)0x02, 0x00, 0x35, 0x00, 'O', 'O', 'n', 'o', 'e', 's' }, p.getBytes());
		
		p = new CommandRequestPacket(0x35, false, "OO", InetAddress.getByName("192.168.1.123"));
		assertArrayEquals(new byte[]{ (byte)0x02, 0x00, 0x35, 0x00, 'O', 'O', '/', '1', '9', '2', '.', '1', '6', '8', '.', '1', '.', '1', '2', '3' }, p.getBytes());
	}

	@Test
	public void testGetFrameID() {
		CommandRequestPacket p;
		
		p = new CommandRequestPacket(SamplePackets.commandRequest_set_NI, 0, SamplePackets.commandRequest_set_NI.length);
		p.setFrameID((byte)123);
		assertEquals(123, p.getFrameID());
		assertFalse(p.getResponseSuppressed());
		p.setFrameID((byte)0);
		assertEquals(0, p.getFrameID());
		assertTrue(p.getResponseSuppressed());
		
		p = new CommandRequestPacket(SamplePackets.commandRequest_set_NI, 0, SamplePackets.commandRequest_set_NI.length);
		p.setFrameID((byte)123);
		p.setResponseSuppressed(false);
		assertEquals(123, p.getFrameID());
		assertFalse(p.getResponseSuppressed());
		p.setResponseSuppressed(true);
		assertEquals(0, p.getFrameID());
		assertTrue(p.getResponseSuppressed());
		p.setResponseSuppressed(false);
		assertFalse(p.getResponseSuppressed());
		
	}

	@Test
	public void testGetApplyNow() {
		CommandRequestPacket p;
		
		p = new CommandRequestPacket();
		p.setApplyNow(false);
		assertFalse(p.getApplyNow());
		p.setApplyNow(true);
		assertTrue(p.getApplyNow());
		p.setApplyNow(false);
		assertFalse(p.getApplyNow());
		
	}

	@Test
	public void testSetATCommand() {
		CommandRequestPacket p;

		p = new CommandRequestPacket();
		p.setATCommand("AC");
		assertEquals("AC", p.getATCommand());
		p.setATCommand("XXXXXXX");
		assertEquals("XX", p.getATCommand());
		try {
			p.setATCommand("");
			fail("not allowed to set AT command to an empty string");
		} catch (Exception e) {}
	}
}
