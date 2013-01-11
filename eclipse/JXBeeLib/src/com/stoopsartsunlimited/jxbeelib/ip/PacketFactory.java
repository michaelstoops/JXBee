package com.stoopsartsunlimited.jxbeelib.ip;


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
	 * Construct the appropriate packet type from the given bytes.
	 * @param data
	 * @param offset
	 * @param length
	 * @return
	 */
	public static Packet getPacketFromNetworkBytes(byte[] data, int offset, int length) {
		switch (PacketCommand.getPacketCommand(data[offset])) {
		case DATA:
			return new SerialDataPacket(data, offset, length);
		case REMOTE_COMMAND:
			return new CommandRequestPacket(data, offset, length);
		case IO_SAMPLE:
			return new IOSamplePacket(data, offset, length);
		case DATA_ACK:
			return new SerialDataAckPacket(data, offset, length);
		case REMOTE_COMMAND_RESPONSE:
			return new CommandResponsePacket(data, offset, length);
		default:
			return new Packet(data, offset, length);
		}
	}
}
