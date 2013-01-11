package com.stoopsartsunlimited.jxbeelib.ip;

import java.nio.ByteBuffer;
import java.util.Arrays;


/**
 * Represents a packet that contains IO samples from an XBee module.
 * 
 * Not tested.
 * 
 * @author Michael
 *
 */
public class IOSamplePacket extends Packet {

	/**
	 * Constructs an empty IOSamplePacket.
	 */
	public IOSamplePacket() {
		super();
		packetBytes = new byte[]{ PacketCommand.IO_SAMPLE.getByte(), 0, 0, 0, 0, 0 };
	}

	/**
	 * Constructs a IOSamplePacket from an image. 
	 * @param networkData
	 * @param offset
	 * @param length
	 */
	public IOSamplePacket(byte[] networkData, int offset, int length) {
		super(networkData, offset, length);
		
		// check validity
		if (networkData.length < 6
				|| length < 6) {
			throw new IllegalArgumentException("networkData is too short to be am IO sample packet.");
		}
		if (getPacketCommand() != PacketCommand.IO_SAMPLE) {
			throw new IllegalArgumentException(wrongClassMessage);
		}
		
		int expectedLength = 6 + (containsDigitalSample() ? 2 : 0) + getNumberOfAnalogSamples();
		if (networkData.length < expectedLength
				|| length < expectedLength) {
			throw new IllegalArgumentException("networkData is too short to contain the indicated number of samples.");
		}
	}
	
	
	
	
	
	
	
	
	
	// number of samples accessors
	
	/**
	 * Gets the number of samples in this packet. Per the Digi documentation, this should always be 0x01, but this library doesn't enforce it.
	 * @return
	 */
	public byte getNumberOfSamples() {
		return packetBytes[3];
	}
	
	/**
	 * Sets the number of samples in this packet. Per the Digi documentation, this should always be 0x01, but this library doesn't enforce it.
	 * 
	 * This method is protected because if you change the mask without adding or removing the samples,
	 * the object will become inconsistent. We don't want consumers of the class doing that to themselves.
	 */
	protected void setNumberOfSamples(byte samples) {
		packetBytes[3] = samples;
	}
	
	
	
	
	
	
	
	
	// digital mask accessors
	
	/**
	 * Gets a bitmask indicating which digital I/O lines are enabled. The order of the bytes in the argument are as in the packet, MSB first.
	 * @return
	 */
	public byte[] getDigitalMask() {
		return Arrays.copyOfRange(packetBytes, 4, 5);
	}
	
	/**
	 * Sets a bitmask indicating which digital I/O lines are enabled. The order of the bytes in the argument are as in the packet, MSB first.
	 * 
	 * This method is protected because if you change the mask without adding or removing the samples,
	 * the object will become inconsistent. We don't want consumers of the class doing that to themselves.
	 */
	protected void setDigitalMask(byte[] mask) {
		packetBytes[4] = mask[0];
		packetBytes[5] = mask[1];
	}
	
	/**
	 * 
	 * @return Returns true iff this packet contains any digital IO samples
	 */
	public boolean containsDigitalSample() {
		if (packetBytes[5] != 0
			|| packetBytes[4] != 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true iff this packet contains a sample for the given IO line
	 * @param dioPinNumber
	 * @return
	 */
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
	
	
	
	
	
	
	
	
	
	
	
	// analog mask accessors
	
	/**
	 * Gets a bitmask indicating which analog I/O lines are enabled.
	 * @return
	 */
	public byte getAnalogMask() {
		return packetBytes[6];
	}
	
	/**
	 * Sets a bitmask indicating which digital I/O lines are enabled.
	 * 
	 * This method is protected because if you change the mask without adding or removing the samples,
	 * the object will become inconsistent. We don't want consumers of the class doing that to themselves.
	 * @param mask
	 */
	protected void setAnalogMask(byte mask) {
		packetBytes[6] = mask;
	}
	
	/**
	 * Returns true iff this packet contains a sample for the given IO line
	 * @param adPinNumber
	 * @return
	 */
	public boolean containsAnalogSample(int adPinNumber) {
		switch (adPinNumber) {
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
	
	/**
	 * Gets the number of analog samples in this packet.
	 * @return
	 */
	public int getNumberOfAnalogSamples() {
		int count = 0;
		for (int i = 0; i < 8; i++) {
			if (containsAnalogSample(i)) {
				count++;
			}
		}
		return count;
	}
	
	

	
	

	
	
	
	
	
	
	
	// digital IO sample accessors

	// there is no set- method because you would want an insert or delete operation instead.
	
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

	
	
	
	
	
	
	
	
	
	// analog IO sample accessors
	
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
	
}
