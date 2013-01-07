package com.stoopsartsunlimited.jxbeelib.ip;

import java.util.Arrays;

import com.stoopsartsunlimited.jxbeelib.XBeeException;


/**
 * Represents a packet that contains IO samples from an XBee module.
 * Not actually implemented.
 * 
 * @author Michael
 *
 */
public class IOSamplePacket extends Packet {

	protected byte[] packetBytes = null;

	public IOSamplePacket(byte[] networkData, int length) throws XBeeException {
		super();
		
		// check validity
		if (networkData.length < 6
				|| length < 6) {
			throw new XBeeException("networkData is too short to be a packet.");
		}

		packetBytes = Arrays.copyOfRange(networkData, 0, networkData.length);
	}

	@Override
	public byte[] getBytes() {
		// TODO Auto-generated method stub
		return null;
	}

}
