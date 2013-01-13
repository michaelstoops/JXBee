package com.stoopsartsunlimited.jxbeelib.ip;

import static org.junit.Assert.*;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.stoopsartsunlimited.jxbeelib.ATCommandHelper;

public class XBeeIPConcentrator_SnoopingTest {

	@Test
	public void test() throws Throwable {
		XBeeIPConnection connection = new XBeeIPConnection((Inet4Address) InetAddress.getByName("192.168.1.1"));
		XBeeIPConcentrator concentrator = XBeeIPConcentrator.getInstance();
		NetworkSnooper ns;
		List<InetAddress> hosts;
		Map<String, Object> properties;
		
		
		// initial setup checks
		assertNull(concentrator.getSnooper());
		concentrator.useNetworkSnooper(false);
		assertNull(concentrator.getSnooper());
		concentrator.useNetworkSnooper(true);
		assertNotNull(concentrator.getSnooper());
		ns = concentrator.getSnooper();
		assertEquals(0, ns.getKnownHosts().size());

		// attempt to get XBees talking
		concentrator.pingAllXBees();
		Thread.sleep(1000);
		// make sure that incoming packets are processed
		connection.receivePacket();
		
		hosts = ns.getKnownHosts();
		assertTrue(hosts.size() > 0);
		for (InetAddress host : hosts) {
			System.out.println(host.getHostAddress());
			properties = ns.getHost(host);
			for (String property : properties.keySet()) {
				Object value = properties.get(property);
				if (value instanceof byte[]) {
					System.out.println(String.format("\t%s=%s", property, ATCommandHelper.decodeToString(property, (byte[])value)));					
				} else {
					System.out.println(String.format("\t%s=%s", property, value));
				}
			}
		}
		
	}

}
