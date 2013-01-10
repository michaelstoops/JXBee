package com.stoopsartsunlimited.jxbeelib.ip;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.stoopsartsunlimited.jxbeelib.XBeeException;


/**
 * Represents a packet that contains IO samples from an XBee module.
 * 
 * Not tested.
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
	
	public PacketCommands getCommand() {
		return commandByteToEnum(packetBytes[0]);
	}
	
	public byte getNumberOfSamples() {
		return packetBytes[3];
	}
	
	public byte[] getDigitalMask() {
		return Arrays.copyOfRange(packetBytes, 4, 5);
	}
	
	public boolean containsDigitalSample() {
		if (packetBytes[5] != 0
			|| packetBytes[4] != 0) {
			return true;
		}
		return false;
	}

	public boolean containsDigitalSample(int dioPinNumber) {
		switch (dioPinNumber) {
		case 0:
			return (packetBytes[5] & 0x01) == 0x01;
		case 1:
			return (packetBytes[5] & 0x02) == 0x02;
		case 2:
			return (packetBytes[5] & 0x04) == 0x04;
		case 3:
			return (packetBytes[5] & 0x08) == 0x08;
		case 4:
			return (packetBytes[5] & 0x01) == 0x01;
		case 5:
			return (packetBytes[5] & 0x02) == 0x02;
		case 6:
			return (packetBytes[5] & 0x04) == 0x04;
		case 7:
			return (packetBytes[5] & 0x08) == 0x08;
		case 8:
			return (packetBytes[4] & 0x01) == 0x01;
		case 9:
			return (packetBytes[4] & 0x02) == 0x02;
		case 10:
			return (packetBytes[4] & 0x04) == 0x04;
		case 11:
			return (packetBytes[4] & 0x08) == 0x08;
		case 12:
			return (packetBytes[4] & 0x01) == 0x01;
		case 13:
			return (packetBytes[4] & 0x02) == 0x02;
		case 14:
			return (packetBytes[4] & 0x04) == 0x04;
		case 15:
			return (packetBytes[4] & 0x08) == 0x08;
		default:
			return false;
		}
	}
	
	public byte getAnalogMask() {
		return packetBytes[6];
	}
	
	/**
	 * @return returns 0 if the pin is low, 1 if it is high and throws an exception if the pin isn't sampled in this packet 
	 */
	public int getDigitalSample(int dioPinNumber) {
		switch (dioPinNumber) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			return packetBytes[5] >> dioPinNumber & 0x01;
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
			return packetBytes[4] >> dioPinNumber & 0x01;
		default:
			throw new IllegalArgumentException("This packet does not contain a sample for DIO pin " + dioPinNumber);
		}
	}
	
	public boolean containsAnalogSample(int dioPinNumber) {
		switch (dioPinNumber) {
		case 0:
			return (packetBytes[6] & 0x01) == 0x01;
		case 1:
			return (packetBytes[6] & 0x02) == 0x02;
		case 2:
			return (packetBytes[6] & 0x04) == 0x04;
		case 3:
			return (packetBytes[6] & 0x08) == 0x08;
		case 4:
			return (packetBytes[6] & 0x01) == 0x01;
		case 5:
			return (packetBytes[6] & 0x02) == 0x02;
		case 6:
			return (packetBytes[6] & 0x04) == 0x04;
		case 7:
			return (packetBytes[6] & 0x08) == 0x08;
		default:
			return false;
		}
	}
	
	public int getNumberOfAnalogSamples() {
		int count = 0;
		for (int i = 0; i < 8; i++) {
			if (containsAnalogSample(i)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * @return Returns the value of the analog sample if it is present. If not, throws exception.
	 */
	public short getAnalogSample(int adPinNumber) {
		int offset = 7;
		if (containsDigitalSample()) {
			offset += 2;
		}
		for (int i = 0; i < 8; i++) {
			if (containsAnalogSample(i)) {
				offset += 2;
			}
			if (i == adPinNumber) {
				return ByteBuffer.wrap(packetBytes).getShort(offset);
			}
		}
		// no sample for that pin
		throw new IllegalArgumentException("This packet does not contain a sample for analog pin " + adPinNumber);
	}
	

	@Override
	public byte[] getBytes() {
		return packetBytes;
	}

}
