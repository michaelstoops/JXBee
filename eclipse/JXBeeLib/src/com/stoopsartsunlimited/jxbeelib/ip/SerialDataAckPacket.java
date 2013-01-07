package com.stoopsartsunlimited.jxbeelib.ip;

import java.util.Arrays;

import com.stoopsartsunlimited.jxbeelib.XBeeException;

/**
 * Represents a packet that an XBee module sends to say that it received your serial data.
 * 
 * @author Michael
 *
 */
public class SerialDataAckPacket extends Packet {

	protected byte[] packetBytes = null;

	public SerialDataAckPacket(byte[] networkData, int length)
			throws XBeeException {
		super();

		// check validity
		if (networkData.length != 2
				|| length != 2) {
			throw new XBeeException("SerialDataAckPacket must be exactly two bytes long.");
		}

		packetBytes = Arrays.copyOfRange(networkData, 0, networkData.length);
	}

	@Override
	public byte[] getBytes() {
		// TODO Auto-generated method stub
		return null;
	}

}
