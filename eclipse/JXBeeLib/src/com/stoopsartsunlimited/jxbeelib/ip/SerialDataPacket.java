package com.stoopsartsunlimited.jxbeelib.ip;

import java.util.Arrays;

import com.stoopsartsunlimited.jxbeelib.XBeeException;

/**
 * Represents a packet that you send to an XBee module, asking it to send this serial data out its serial port.
 * 
 * @author Michael
 *
 */
public class SerialDataPacket extends Packet {

	protected byte[] packetBytes = null;
	
	public SerialDataPacket(byte[] networkData, int length)
			throws XBeeException {
		super();
		
		// check validity
		if (networkData.length < 2
				|| length < 2) {
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
