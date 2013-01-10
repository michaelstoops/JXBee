package com.stoopsartsunlimited.jxbeelib.ip;

import com.stoopsartsunlimited.jxbeelib.XBeeException;

/**
 * Represents an XBee TCP/IP packet. Doesn't represent any meaning of the packet, just the raw bytes.  
 * 
 * @author Michael
 *
 */
public class RawPacket extends Packet {

	protected byte[] packetBytes = null;

	public RawPacket(byte[] networkData, int length) throws XBeeException {
		super(networkData, length);
	}

	@Override
	public byte[] getBytes() {
		return packetBytes;
	}

}
