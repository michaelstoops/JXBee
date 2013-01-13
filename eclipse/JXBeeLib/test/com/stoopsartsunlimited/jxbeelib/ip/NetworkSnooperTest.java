package com.stoopsartsunlimited.jxbeelib.ip;

import static org.junit.Assert.*;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.junit.Test;

public class NetworkSnooperTest {

	@Test
	public void testGetKnownHosts() throws UnknownHostException {
		NetworkSnooper ns;
				
		ns = new NetworkSnooper();
		assertEquals(0, ns.getKnownHosts().size());
		DatagramPacket dp = new DatagramPacket(SamplePackets.commandResponse_get_ID, SamplePackets.commandResponse_get_ID.length);
		InetAddress host = InetAddress.getByName("192.168.1.1");
		dp.setAddress(host);
		ns.update(PacketFactory.getPacketFromNetworkBytes(SamplePackets.commandResponse_get_ID), dp);
		assertEquals(1, ns.getKnownHosts().size());
	}

	@Test
	public void testGetHost() throws UnknownHostException {
		NetworkSnooper ns = new NetworkSnooper();
		InetAddress host;
		DatagramPacket dp;
		Map<String, Object> properties;
		
		ns = new NetworkSnooper();
		host = InetAddress.getByName("192.168.1.1");
		dp = new DatagramPacket(SamplePackets.commandResponse_get_ID, SamplePackets.commandResponse_get_ID.length, host, 0xBEE);
		ns.update(PacketFactory.getPacketFromNetworkBytes(SamplePackets.commandResponse_get_ID), dp);
		assertNull(ns.getHost(InetAddress.getByName("192.168.1.254")));
		properties = ns.getHost(host);
		assertNotNull(properties);
		assertEquals(true, properties.get(NetworkSnooper.IS_XBEE));
		assertArrayEquals(new byte[]{ 'm', 'y', 's', 's', 'i', 'd' }, (byte[])properties.get("ID"));
		assertNull(properties.get("NOT A PROPERTY"));

	
		ns = new NetworkSnooper();
		host = InetAddress.getByName("192.168.1.1");
		dp = new DatagramPacket(SamplePackets.commandResponse_get_ID, SamplePackets.commandResponse_get_ID.length, host, 0xBEE);
		ns.update(PacketFactory.getPacketFromNetworkBytes(SamplePackets.commandResponse_get_ID_failed), dp);
		properties = ns.getHost(host);
		assertNull(properties.get("ID"));
	}
}
