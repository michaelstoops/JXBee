package com.stoopsartsunlimited.jxbeetool;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.stoopsartsunlimited.jxbeelib.ATCommand;
import com.stoopsartsunlimited.jxbeelib.ATCommandHelper;
import com.stoopsartsunlimited.jxbeelib.XBeeClient;
import com.stoopsartsunlimited.jxbeelib.ip.NetworkSnooper;
import com.stoopsartsunlimited.jxbeelib.ip.XBeeIPConcentrator;

/**
 * Provides functionality to discover XBee modules within reach of this host.
 *  
 * @author Michael
 *
 */
public class DiscoverTool extends Tool {

	public DiscoverTool(String[] args) {
		// this command doesn't use the remote-host option
		super(args, new String[]{ REMOTE });
		setToolName("discover");
		setDescription("asks an XBee device for information");
	}

	public void main(String[] args) throws Throwable {
		super.main(args);
		
		// open networking and ping everybody
		XBeeClient client = new XBeeClient(InetAddress.getLocalHost());
		XBeeIPConcentrator concentrator = XBeeIPConcentrator.getInstance();
		printlnIfVerbose("Starting snooper...");
		concentrator.useNetworkSnooper(true);
		printlnIfVerbose("Broadcasting request...");
		// send ping
		concentrator.pingAllXBees();
		// give them a chance to respond
		Thread.sleep(100);
		client.setReadTimeout(1);
		// force processing of all inbound packets
		client.sendCommandAndWait(ATCommand.NODE_IDENTIFIER, true, null);
		
		// output data
		NetworkSnooper snooper = concentrator.getSnooper();
		if (snooper.getKnownHosts().size() == 0) {
			printlnIfVerbose("No devices found");
		}
		for (InetAddress host : snooper.getKnownHosts()) {
			Map<String, Object> info = snooper.getHost(host);
			if (!info.containsKey(NetworkSnooper.IS_XBEE)) {
				// not an XBee; ignore.
				continue;
			}
			if (host.equals(InetAddress.getLocalHost())) {
				// don't "discover" myself
				continue;
			}
			
			// output line
			System.out.println(getOutputString(host, info, ATCommand.SERIAL_NUMBER_HIGH, ATCommand.SERIAL_NUMBER_LOW, ATCommand.NODE_IDENTIFIER));
		}
		
	}
	
	private String getOutputString(InetAddress host, Map<String, Object> infoMap, String... properties) {
		// get properties
		List<String> pairs = new LinkedList<String>();
		for (String property : properties) {
			if (infoMap.containsKey(property)) {
				pairs.add(String.format("%s=%s", property, ATCommandHelper.decodeToString(property, (byte[])infoMap.get(property))));
			}
		}
		
		// put it together
		StringBuilder sb = new StringBuilder();
		sb.append(host.getHostAddress());
		if (pairs.size() > 0) {
			sb.append(": ");
			sb.append(StringUtils.join(pairs.toArray(), ", "));
		}
		
		return sb.toString();
	}

}
