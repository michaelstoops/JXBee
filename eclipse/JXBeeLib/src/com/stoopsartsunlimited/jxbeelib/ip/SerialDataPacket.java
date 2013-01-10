package com.stoopsartsunlimited.jxbeelib.ip;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.stoopsartsunlimited.jxbeelib.XBeeException;

/**
 * Represents a packet that you send to an XBee module, asking it to send this serial data out its serial port.
 * 
 * Not tested.
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
	
	/**
	 * Create an minimum-length serial data packet.
	 * @throws XBeeException 
	 */
	public SerialDataPacket() throws XBeeException {
		this(new byte[]{ 0x04, 0 }, 2);
	}

	public PacketCommands getCommand() {
		return commandByteToEnum(packetBytes[0]);
	}
	
	/**
	 * Sets the packet's payload of serial data to the given byte array.
	 * @param data The new serial data to send.
	 */
	public void setSerialData(byte[] data) {
		ByteBuffer buffer = ByteBuffer.allocate(data.length + 2);
		buffer.put(packetBytes[0]);
		buffer.put(packetBytes[1]);
		buffer.put(data);
		packetBytes = buffer.array();
		
	}

	/**
	 * @return Returns a copy of the serial data that would be sent in this packet. 
	 */
	public byte[] getSerialData() {
		return Arrays.copyOfRange(packetBytes, 2, packetBytes.length);
	}
	
	@Override
	public byte[] getBytes() {
		return packetBytes;
	}


}
