package com.stoopsartsunlimited.jxbeelib.ip;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Represents a packet that you send to an XBee module, asking it to send this serial data out its serial port.
 * 
 * Not tested.
 * 
 * @author Michael
 *
 */
public class SerialDataPacket extends Packet {

	/**
	 * Constructs an empty SerialDataPacket.
	 */
	public SerialDataPacket() {
		super();
		packetBytes = new byte[]{ PacketCommand.DATA.getByte(), 0 };
	}
	
	/**
	 * Constructs a SerialDataPacket from an image.
	 * @param networkData
	 * @param offset
	 * @param length
	 */
	public SerialDataPacket(byte[] networkData, int offset, int length) {
		super(networkData, offset, length);
		
		// check validity
		if (networkData.length < 2
				|| length < 2) {
			throw new IllegalArgumentException("networkData is too short to be a serial data packet.");
		}
		if (getPacketCommand() != PacketCommand.DATA) {
			throw new IllegalArgumentException(wrongClassMessage);
		}
	}
	
	
	
	
	
	
	
	
	
	
	// serial data accessors
	
	/**
	 * Sets the packet's payload of serial data to the given byte array.
	 * @param data The new serial data to send.
	 */
	public void setSerialData(byte[] data, int offset, int length) {
		ByteBuffer buffer = ByteBuffer.allocate(data.length + 2);
		buffer.put(packetBytes[0]);
		buffer.put(packetBytes[1]);
		buffer.put(data, offset, length);
		packetBytes = buffer.array();
		
	}

	/**
	 * @return Returns a copy of the serial data that would be sent in this packet. 
	 */
	public byte[] getSerialData() {
		return Arrays.copyOfRange(packetBytes, 2, packetBytes.length);
	}
}
