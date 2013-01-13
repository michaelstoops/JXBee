package com.stoopsartsunlimited.jxbeelib.ip;

import java.util.Arrays;

/**
 * Abstract class for all XBee packets that are to travel over IP.
 * @author Michael
 *
 */
public class Packet {
	
	// fields
	
	protected byte[] packetBytes = null;
	
	protected static String wrongClassMessage = "This packet image does not contain a packet of the expected type. Use a different Packet class."; 

	// constructors
	
	/**
	 * Creates a blank packet of the minimum length
	 */
	public Packet() {
		// just a blank packet
		packetBytes = new byte[]{ 0, 0 };
	}
	
	/**
	 * Constructs a packet object from the given bytes
	 * @param networkData bytes as taken from a UDP payload
	 * @param offset
	 * @param length
	 */
	public Packet(byte[] networkData, int offset, int length) {
		if (networkData == null
			|| length < 2) {
			throw new IllegalArgumentException();
		}
		packetBytes = Arrays.copyOfRange(networkData, offset, length);
	}
		
	
	
	
	
	
	// packetBytes accessors
	
	// no setter because you shoud really just make a new packet object instead.
	
	/**
	 * Gets an array of bytes that represent the network form of this packet. Suitable for transmission.
	 * @return
	 */
	public byte[] getBytes() {
		return packetBytes; 
	}

	
	
	
	
	
	
	// packet command accessors
	
	/**
	 * Get the packet command type of this packet. This value distinguishes between major types of packets
	 * such as command request and response packets, etc.
	 * @return
	 */
	public PacketCommand getPacketCommand() {
		return PacketCommand.getPacketCommand(packetBytes[0]);
	}
	
	/**
	 * Sets the packet command of this packet. This is what distinguishes between major types of packets
	 * such as command request and response packets, etc.
	 * @param packetCommand
	 */
	public void setPacketCommand(PacketCommand packetCommand) {
		packetBytes[0] = packetCommand.getByte();
	}
}
