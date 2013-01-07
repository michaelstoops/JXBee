package com.stoopsartsunlimited.jxbeelib.ip;

import com.stoopsartsunlimited.jxbeelib.XBeeException;

/**
 * Abstract class for all XBee packets that are to travel over IP.
 * @author Michael
 *
 */
public abstract class Packet {
	public Packet() {
		// just a packet object, no value
	}
	
	public Packet(byte[] networkData, int length) throws XBeeException {
		
	}
	
	/**
	 * Gets an array of bytes that represent the network form of this packet. Suitable for transmission.
	 * @return
	 */
	public abstract byte[] getBytes();
	
	/**
	 * Converts a packet command enum value to the corresponding code byte.
	 * @param command
	 * @return
	 * @throws XBeeException
	 */
	static byte commandEnumToByte(PacketCommands command) throws IllegalArgumentException {
		switch (command) {
		case DATA:
			return 0x00;
		case REMOTE_COMMAND:
			return 0x02;
		case IO_SAMPLE:
			return 0x04;
		case DATA_ACK:
			return (byte) 0x80;
		case REMOTE_COMMAND_RESPONSE:
			return (byte) 0x82;
		default:
			throw new IllegalArgumentException("Invalid command: " + command);
		}
	}

	/**
	 * Converts a packet command code byte to the corresponding enum value.
	 * @param command
	 * @return
	 * @throws XBeeException
	 */
	static PacketCommands commandByteToEnum(byte command) throws IllegalArgumentException {
		switch (command) {
		case 0x00:
			return PacketCommands.DATA;
		case 0x02:
			return PacketCommands.REMOTE_COMMAND;
		case 0x04:
			return PacketCommands.IO_SAMPLE;
		case (byte)0x80:
			return PacketCommands.DATA_ACK;
		case (byte)0x82:
			return PacketCommands.REMOTE_COMMAND_RESPONSE;
		default:
			throw new IllegalArgumentException("Invalid command: " + command);
		}
	}
	
}
