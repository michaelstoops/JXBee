package com.stoopsartsunlimited.jxbeetool;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import com.stoopsartsunlimited.jxbeelib.ATCommand;
import com.stoopsartsunlimited.jxbeelib.ATCommandHelper;
import com.stoopsartsunlimited.jxbeelib.XBeeClient;
import com.stoopsartsunlimited.jxbeelib.XBeeException;
import com.stoopsartsunlimited.jxbeelib.ip.NetworkSnooper;
import com.stoopsartsunlimited.jxbeelib.ip.XBeeIPConcentrator;

public class DiscoverCommand {

	static final String toolName = "discover";
	
	static String host;
	static XBeeClient client;

	/**
	 * @param args
	 * @throws XBeeException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, XBeeException {
		client = new XBeeClient((Inet4Address) InetAddress.getLocalHost());
		XBeeIPConcentrator concentrator = XBeeIPConcentrator.getInstance();
		System.out.println("Starting snooper...");
		concentrator.useNetworkSnooper(true);
		System.out.println("Broadcasting request...");
		concentrator.pingAllXBees();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) { }
		client.setReadTimeout(1);
		client.sendCommandAndWait(ATCommand.NODE_IDENTIFIER, true, null);
		
		NetworkSnooper snooper = concentrator.getSnooper();
		if (snooper.getKnownHosts().size() == 0) {
			System.out.println("No devices found");
		}
		for (InetAddress host : snooper.getKnownHosts()) {
			Map<String, Object> info = snooper.getHost(host);
			if (!info.containsKey(NetworkSnooper.IS_XBEE)) {
				// not an xbee; ignore.
				continue;
			}
			if (host.equals(InetAddress.getLocalHost())) {
				// don't "discover" myself
				continue;
			}
			System.out.print(String.format("%s:", host.getHostAddress()));
			if (info.containsKey("SH")) {
				System.out.print(String.format(" SH=%s", ATCommandHelper.decodeToString("SH", (byte[])info.get("SH"))));
			}
			if (info.containsKey("SL")) {
				System.out.print(String.format(" SL=%s", ATCommandHelper.decodeToString("SL", (byte[])info.get("SL"))));
			}
			if (info.containsKey("NI")) {
				System.out.print(String.format(" NI=%s", ATCommandHelper.decodeToString("NI", (byte[])info.get("NI"))));
			}
			System.out.println();
		}
		
	}

}
