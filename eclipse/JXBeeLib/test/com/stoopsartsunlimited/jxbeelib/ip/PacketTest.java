package com.stoopsartsunlimited.jxbeelib.ip;

import static org.junit.Assert.*;

import org.junit.Test;

public class PacketTest {

	@Test
	public void testPacket() {
		Packet p;
		p = new Packet();
		assertEquals(2, p.getBytes().length);
		
		p.setPacketCommand(PacketCommand.DATA_ACK);
		assertEquals(PacketCommand.DATA_ACK, p.getPacketCommand());
		
		try {
			p = new Packet(null, 0, 0);
			fail();
		} catch (IllegalArgumentException e) { }
		try {
			p = new Packet(new byte[0], 0, 0);
			fail();
		} catch (IllegalArgumentException e) { }
				
	}

	@Test
	public void testPacketByteArrayIntInt() {
		Packet p;
		byte[] bytes;
		
		bytes = SamplePackets.commandRequest_get_ID;
		p = new Packet(bytes, 0, bytes.length);
		assertArrayEquals(bytes, p.getBytes());
		
		bytes = SamplePackets.commandRequest_set_ID;
		p = new Packet(bytes, 0, bytes.length);
		assertArrayEquals(bytes, p.getBytes());
		
		bytes = SamplePackets.commandResponse_get_ID;
		p = new Packet(bytes, 0, bytes.length);
		assertArrayEquals(bytes, p.getBytes());
		
		bytes = SamplePackets.commandResponse_set_ID;
		p = new Packet(bytes, 0, bytes.length);
		assertArrayEquals(bytes, p.getBytes());
		
		p.setPacketCommand(PacketCommand.DATA_ACK);
		assertEquals(PacketCommand.DATA_ACK, p.getPacketCommand());
	}
}
