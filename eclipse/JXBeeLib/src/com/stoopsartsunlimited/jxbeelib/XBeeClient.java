package com.stoopsartsunlimited.jxbeelib;

import java.net.Inet4Address;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.stoopsartsunlimited.jxbeelib.ip.CommandRequestPacket;
import com.stoopsartsunlimited.jxbeelib.ip.CommandResponsePacket;
import com.stoopsartsunlimited.jxbeelib.ip.CommandResponsePacket.Status;
import com.stoopsartsunlimited.jxbeelib.ip.Packet;
import com.stoopsartsunlimited.jxbeelib.ip.XBeeIPConnection;

/**
 * This class models an XBee module, what we know about it and provides ways to do stuff to it.
 * 
 * This class isn't intended to be a complete state representation of the device. It just does
 * enough to be useful.
 * 
 * @author Michael
 */
public class XBeeClient {
	
	public XBeeClient(Inet4Address remoteAddress) throws XBeeException {
		connection = new XBeeIPConnection(remoteAddress);
	}
	
	XBeeIPConnection connection;
	
	/**
	 * Timeout in milliseconds. If a blocking function call waits for a response longer than this time
	 * it will give up.
	 */
	protected int readTimeout = 10000;
	
	/**
	 * Sends a command and waits for a response. Blocks until timeout is reached or response is received.
	 * If the response contains device state information, the state is stored.
	 * 
	 * @return Returns true iff a response was received, false if none was seen.
	 * @throws XBeeException Throws an XBeeException if the operation couldn't be completed.
	 * 
	 */
	public boolean sendCommandAndWait(String command, boolean applyNow, Object parameter) throws XBeeException {
		
		// generate the request
		CommandRequestPacket requestPacket =
				new CommandRequestPacket(
						getRandomFrameID(),
						applyNow,
						command,
						parameter);
		connection.sendPacket(requestPacket);
		
		Packet receivedPacket;
		
		// polling loop
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MILLISECOND, readTimeout);
		Date deadline = cal.getTime();
		
		while (new Date().before(deadline)) {
			receivedPacket = connection.receivePacket();
			
			// save any useful data in the packet
			gleanStateData(receivedPacket);
			
			// filter for the packet we expect
			if (receivedPacket != null
				&& receivedPacket instanceof CommandResponsePacket) {
				// got a command response packet
				// filter for AT command and frame ID matches
				CommandResponsePacket response = (CommandResponsePacket)receivedPacket;
				if (response.getATCommand().equals(command)
						&& response.getFrameID() == requestPacket.getFrameID()) {
					// match!
					// attempt to glean from this request-response pair.
					gleanStateData(requestPacket, receivedPacket);
					return true;
				}
			}
		}
		// never got the response
		return false;
	}
	
	/**
	 * Sends a command and doesn't wait for any response. As an optimization, it also tells the
	 * XBee device not to send a response.
	 * 
	 * @param command
	 * @throws XBeeException 
	 */
	public void sendCommandNoResponse(String command, boolean applyNow, Object parameter) throws XBeeException {
		// generate the request
		CommandRequestPacket requestPacket =
				new CommandRequestPacket(
						0,
						applyNow,
						command,
						parameter);
		connection.sendPacket(requestPacket);
	}
	
	
	protected Map<String, byte[]> stateMap = new HashMap<String, byte[]>();
	
	/**
	 * @param key
	 * @return Returns true iff the requested state information is available. 
	 */
	public boolean containsState(String key) {
		return stateMap.containsKey(key);
	}

	/**
	 * @param key
	 * @return Returns bytes previously captured.
	 */
	public byte[] getState(String key) {
		return stateMap.get(key);
	}
	
	protected void setState(String key, byte[] value) {
		stateMap.put(key, value);
	}
	
	/**
	 * Inspects the packet for state data. If it contains state data for the remote XBee device,
	 * stores it.
	 * 
	 * Does not modify the packet.
	 * 
	 * @param packet
	 */
	protected void gleanStateData(Packet packet) {
		try {
			// glean only from AT response packets that show a key, a value and Status=OK
			if (packet instanceof CommandResponsePacket) {
				CommandResponsePacket response = (CommandResponsePacket)packet;
				if (response.getStatus() == Status.OK
						&& response.getParameterBytes() != null) {
					setState(response.getATCommand(), response.getParameterBytes());
				}
			}
		} catch (Exception e) {
			// if anything went wrong, just assume that there's nothing to glean.
		}
	}
	
	/**
	 * Inspects a pair of AT command packets for evidence that the XBee device changed state.
	 * If they contain evidence of the state of the remote XBee device, the information is stored.
	 * 
	 * Does not modify the packets.
	 * 
	 * @param packet
	 */
	protected void gleanStateData(Packet request, Packet response) {
		
	}
	
	/**
	 * @return Returns a random, non-zero frame ID 
	 */
	protected byte getRandomFrameID() {
		return (byte) ((byte)(Math.random() * 0xFE) + 1);
	}

	/**
	 * Sets the timeout for reading from the xbee connection. 
	 * @param timeoutMS
	 */
	public void setReadTimeout(int timeout) {
		connection.setTimeout(timeout);
	}
}
