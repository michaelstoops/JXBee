package com.stoopsartsunlimited.jxbeelib.ip;

/**
 * Represents the allowable values of the command byte in an XBee IP packet.
 * 
 * @author Michael
 *
 */
public enum PacketCommands {
	DATA,
	REMOTE_COMMAND,
	IO_SAMPLE,
	DATA_ACK,
	REMOTE_COMMAND_RESPONSE
}
