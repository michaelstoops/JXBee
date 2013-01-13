package com.stoopsartsunlimited.jxbeelib.ip;

import java.util.Arrays;


/**
 * Factory class for producing XBee IP packets.
 * @author Michael
 *
 */
public class PacketFactory {
	
	/**
	 * Construct the appropriate packet type from the given bytes.
	 * @param data
	 * @return
	 */
	public static Packet getPacketFromNetworkBytes(byte[] data) {
		return getPacketFromNetworkBytes(data, 0, data.length);
	}
	
	/**
	 * Construct the appropriate packet type from the given bytes. Creates a copy of the packet byte array.
	 * @param data
	 * @param offset
	 * @param length
	 * @return
	 */
	public static Packet getPacketFromNetworkBytes(byte[] data, int offset, int length) {
		
		byte[] dataCopy = Arrays.copyOfRange(data, offset, offset + length);
		
		switch (PacketCommand.getPacketCommand(dataCopy[0])) {
		case DATA:
			return new SerialDataPacket(dataCopy, 0, dataCopy.length);
		case REMOTE_COMMAND:
			return new CommandRequestPacket(dataCopy, 0, dataCopy.length);
		case IO_SAMPLE:
			return new IOSamplePacket(dataCopy, 0, dataCopy.length);
		case DATA_ACK:
			return new SerialDataAckPacket(dataCopy, 0, dataCopy.length);
		case REMOTE_COMMAND_RESPONSE:
			return new CommandResponsePacket(dataCopy, 0, dataCopy.length);
		default:
			return new Packet(dataCopy, 0, dataCopy.length);
		}
	}
	
	public static Packet getCopyOf(Packet packet) {
		return getPacketFromNetworkBytes(packet.getBytes());
	}
}
