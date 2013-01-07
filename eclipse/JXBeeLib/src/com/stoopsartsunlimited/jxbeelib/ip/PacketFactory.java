package com.stoopsartsunlimited.jxbeelib.ip;

import java.util.Arrays;

import com.stoopsartsunlimited.jxbeelib.XBeeException;


/**
 * Factory class for producing XBee IP packets.
 * @author Michael
 *
 */
public class PacketFactory {
	public static Packet getPacketFromNetworkBytes(byte[] data) throws XBeeException {
		return getPacketFromNetworkBytes(data, 0, data.length);
	}
	public static Packet getPacketFromNetworkBytes(byte[] data, int offset,
			int length) throws XBeeException {
		try {
			// isolate our packet
			byte[] packetBytes = Arrays.copyOfRange(data, offset, offset + length);
			// inspect the command byte
			switch (Packet.commandByteToEnum(packetBytes[0])) {
			case DATA:
				return new SerialDataPacket(packetBytes, packetBytes.length);
			case REMOTE_COMMAND:
				return new CommandRequestPacket(packetBytes, packetBytes.length);
			case IO_SAMPLE:
				return new IOSamplePacket(packetBytes, packetBytes.length);
			case DATA_ACK:
				return new SerialDataAckPacket(packetBytes, packetBytes.length);
			case REMOTE_COMMAND_RESPONSE:
				return new CommandResponsePacket(packetBytes, packetBytes.length);
			default:
				return new RawPacket(packetBytes, packetBytes.length);
			}
			
		} catch (Exception e) {
			throw new XBeeException(e);
		}
	}
}
