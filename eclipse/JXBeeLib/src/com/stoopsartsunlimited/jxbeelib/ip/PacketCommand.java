package com.stoopsartsunlimited.jxbeelib.ip;

import com.stoopsartsunlimited.jxbeelib.XBeeException;

/**
 * Represents the allowable values of the command byte in an XBee IP packet.
 * 
 * @author Michael
 *
 */
public enum PacketCommand {
	DATA,
	REMOTE_COMMAND,
	IO_SAMPLE,
	DATA_ACK,
	REMOTE_COMMAND_RESPONSE;
	
	/**
	 * Converts a packet command enum value to the corresponding code byte.
	 * @param command
	 * @return
	 * @throws XBeeException
	 */
	byte getByte() throws IllegalArgumentException {
		switch (this) {
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
			throw new IllegalArgumentException("Invalid command: " + this);
		}
	}

	/**
	 * Converts a packet command code byte to the corresponding enum value.
	 * @param command
	 * @return
	 * @throws XBeeException
	 */
	static PacketCommand getPacketCommand(byte commandByte) throws IllegalArgumentException {
		switch (commandByte) {
		case 0x00:
			return DATA;
		case 0x02:
			return REMOTE_COMMAND;
		case 0x04:
			return IO_SAMPLE;
		case (byte)0x80:
			return DATA_ACK;
		case (byte)0x82:
			return REMOTE_COMMAND_RESPONSE;
		default:
			throw new IllegalArgumentException("Invalid command: " + commandByte);
		}
	}
}
