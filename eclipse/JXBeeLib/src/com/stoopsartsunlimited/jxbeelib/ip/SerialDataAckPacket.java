package com.stoopsartsunlimited.jxbeelib.ip;


/**
 * Represents a packet that an XBee module sends to say that it received your serial data.
 * 
 * Not tested.

 * @author Michael
 *
 */
public class SerialDataAckPacket extends Packet {

	/**
	 * Constructs an empty SerialDataAckPacket.
	 */
	public SerialDataAckPacket() {
		super();
		packetBytes = new byte[]{ PacketCommand.DATA_ACK.getByte(), 0 };
	}

	/**
	 * Constructs a SerialDataAckPacket from an image.
	 * @param networkData
	 * @param offset
	 * @param length
	 */
	public SerialDataAckPacket(byte[] networkData, int offset, int length) {
		super(networkData, offset, length);

		// check validity
		if (networkData.length != 2
				|| length != 2) {
			throw new IllegalArgumentException("Serial data ack packets must be exactly two bytes long. This packet image is " + length);
		}
		if (getPacketCommand() != PacketCommand.DATA_ACK) {
			throw new IllegalArgumentException(wrongClassMessage);
		}
	}
	
	// there really isn't any more to this packet type

}
