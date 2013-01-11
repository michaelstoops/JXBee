package com.stoopsartsunlimited.jxbeelib.ip;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Represents a packet that is a response from an XBee module.
 * 
 * @author Michael
 *
 */
public class CommandResponsePacket extends Packet {

	// constructors
	
	/**
	 * Constructs an empty CommandResponsePacket.
	 */
	public CommandResponsePacket() {
		super();
		packetBytes = new byte[]{ PacketCommand.REMOTE_COMMAND_RESPONSE.getByte(), 0, 0, 0, 0, 0 };
	}

	/**
	 * Constructs a CommandResponsePacket from an image. 
	 * @param networkData
	 * @param offset
	 * @param length
	 */
	public CommandResponsePacket(byte[] networkData, int offset, int length) {
		super(networkData, offset, length);

		// check validity
		if (networkData.length < 6
				|| length < 6) {
			throw new IllegalArgumentException("networkData is too short to be a command response packet.");
		}
		if (getPacketCommand() != PacketCommand.REMOTE_COMMAND_RESPONSE) {
			throw new IllegalArgumentException(wrongClassMessage);
		}
	}
	
	
	
	
	
	
	
	// frame ID accessors
	
	/**
	 * @return Returns the frame ID specified in this packet. If the frame ID is zero, the XBee module will not send any response.
	 */
	public byte getFrameID() {
		return packetBytes[2];
	}
	
	/**
	 * Sets the packet's frame ID.
	 * @param frameID
	 */
	public void setFrameID(byte frameID) {
		packetBytes[2] = frameID;
	}
	
	
	
	
	
	
	
	// atCommand accessors
	
	/**
	 * @return Gets the AT command portion of the packet.
	 */
	public String getATCommand() {
		return Charset.forName("US-ASCII").decode(ByteBuffer.wrap(packetBytes, 3, 2)).toString();
	}
	
	/**
	 * Sets the AT command that should be executed by the XBee module.
	 * @param atCommand
	 */
	public void setATCommand(String atCommand) {
		byte[] bytes = Charset.forName("US-ASCII").encode(atCommand.substring(0, 1)).array();
		packetBytes[3] = bytes[0]; // AT command, first character
		packetBytes[4] = bytes[1]; // AT command, second character
	}
	
	
	
	
	
	
	
	
	
	// status accessors
	
	/**
	 * Status codes specific to the CommandResponsePacket
	 * @author Michael
	 */
	public enum Status {
		OK,
		ERROR,
		INVALID_COMMAND,
		INVALID_PARAMETER;
		
		public byte getByte() {
			switch (this) {
			case OK:
				return (byte)0x00;
			case ERROR:
				return (byte)0x01;
			case INVALID_COMMAND:
				return (byte)0x02;
			case INVALID_PARAMETER:
				return (byte)0x03;
			default:
				throw new IllegalArgumentException("Invalid status: " + this);
			}
		}
		
		public static Status getStatus(byte code) {
			switch (code) {
			case 0x00:
				return Status.OK;
			case 0x01:
				return Status.ERROR;
			case 0x02:
				return Status.INVALID_COMMAND;
			case 0x03:
				return Status.INVALID_PARAMETER;
			default:
				throw new IllegalArgumentException("Invalid status: " + code);
			}
		}
	}
	
	/**
	 * @return Gets the status of the request.
	 */
	public Status getStatus() {
		return Status.getStatus(packetBytes[5]);
	}
	
	/**
	 * Sets the status.
	 * @param status
	 */
	public void setStatus(Status status) {
		packetBytes[5] = status.getByte();
	}
	









	// parameter accessors
	
	/**
	 * @return Returns a copy of the packet parameter as a byte array.
	 */
	public byte[] getParameter() {
		return Arrays.copyOfRange(packetBytes, 6, packetBytes.length);
	}

	/**
	 * @return Returns an ASCII-encoded version of the parameter bytes.
	 */
	public String getParameterAsString() {
		return Charset.forName("US-ASCII").decode(ByteBuffer.wrap(getParameter())).toString();
	}

	/**
	 * Sets the parameter of this packet to the given value.
	 * @param parameter Bytes to send as the parameter
	 */
	public void setParameter(byte[] parameter) {
		// the XBee docs don't specifically call out a limit, but I'm guessing that the limit of the total packet length is 1400 bytes,
		// based on the serial data packet spec.
		int limit = 1394; // 1400 - 6
		if (parameter.length > limit) {
			throw new IllegalArgumentException("Parameter is too large. Limit is " + limit);
		}
		packetBytes = ByteBuffer.allocate(6 + parameter.length).put(packetBytes, 0, 6).put(parameter).array();
	}
	
	/**
	 * Sets the parameter of this packet to an ASCII-encoded version of the given value.
	 * @param parameter String to send as the parameter.
	 */
	public void setParameter(String parameter) {
		packetBytes = ByteBuffer.allocate(6 + parameter.length()).put(packetBytes, 0, 6).put(Charset.forName("US-ASCII").encode((String)parameter)).array();
	}
	
	/**
	 * Clears the parameter from this packet.
	 */
	public void clearParameter() {
		packetBytes = ByteBuffer.allocate(6).put(packetBytes, 0, 6).array();
	}
}
