package com.stoopsartsunlimited.jxbeelib.ip;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stoopsartsunlimited.jxbeelib.ip.CommandResponsePacket.Status;

/**
 * Represents a class that tends to know what's going on in a network.
 * @author Michael
 *
 */
public class NetworkSnooper implements PacketSubscriber {

	/**
	 * Map of known hosts and what we know about them
	 */
	private HashMap<InetAddress, HashMap<String, Object> > knownHosts = new HashMap<InetAddress, HashMap<String, Object> >();
	
	// fixed property names
	public static String IS_XBEE = "IS_XBEE";
	
	
	@Override
	public void update(Packet packet, Object... context) {
		// find the host 
		InetAddress host = null;
		
		for (Object object : context) {
			if (object instanceof DatagramPacket) {
				host = ((DatagramPacket)object).getAddress();
			}
		}
		
		if (host == null) {
			// no host found
			return;
		}
		
		// find existing records
		HashMap<String, Object> knownProperties = null;
		if (knownHosts.containsKey(host)) {
			knownProperties = knownHosts.get(host);
		} else {
			knownProperties = new HashMap<String, Object>();
		}
		
		// record info
		// is it an xbee?
		if (packet instanceof CommandRequestPacket
				|| packet instanceof CommandResponsePacket
				|| packet instanceof IOSamplePacket
				|| packet instanceof SerialDataPacket
				|| packet instanceof SerialDataAckPacket) {
			// if we got a known packet type, the sender is definitely an XBee device
			knownProperties.put(IS_XBEE, true);
		}
		// glean from CommandResponsePacket
		if (packet instanceof CommandResponsePacket
				&& ((CommandResponsePacket)packet).getStatus() == Status.OK) {
			CommandResponsePacket commandResponsePacket = (CommandResponsePacket)packet;
			knownProperties.put(commandResponsePacket.getATCommand(), commandResponsePacket.getParameter());
		}
		
		// save info
		knownHosts.put(host, knownProperties);
	}
	
	/**
	 * gets a list of all the hosts known
	 * @return
	 */
	public List<InetAddress> getKnownHosts() {
		return new ArrayList<InetAddress>(knownHosts.keySet());
	}
	
	/**
	 * Returns a map containing what we know about the given host.
	 * @param host
	 * @return
	 */
	public Map<String, Object> getHost(InetAddress host) {
		return knownHosts.get(host);
	}
	
}
