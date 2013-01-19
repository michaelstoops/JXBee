package com.stoopsartsunlimited.jxbeelib.ip;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import com.stoopsartsunlimited.jxbeelib.ATCommandHelper;

/**
 * Represents a packet that sends a command to an XBee module over TCP/IP.
 * 
 * @author Michael
 *
 */
public class CommandRequestPacket extends Packet {

	/**
	 * default non-zero/non-suppressed frame id.
	 */
	protected static byte DEFAULT_FRAME_ID = 0x01;
	
	// fields
	
	
	
	// constructors
	
	/**
	 * Default constructor, creates a blank CommandRequestPacket
	 */
	public CommandRequestPacket() {
		super();
		packetBytes = new byte[]{ PacketCommand.REMOTE_COMMAND.getByte(), 0, 0, 0, 0, 0 };
	}
	
	/**
	 * Creates a CommandRequestPacket from an image.
	 * @param networkData image of this packet. Described in the XBee Wi-Fi docs as "Client Packet Data"
	 * @param offset
	 * @param length
	 */
	public CommandRequestPacket(byte[] networkData, int offset, int length) {
		super(networkData, offset, length);
		
		// check validity
		if (networkData.length < 6
				|| length < 6) {
			throw new IllegalArgumentException("networkData is too short to be a command request packet.");
		}
		if (getPacketCommand() != PacketCommand.REMOTE_COMMAND) {
			throw new IllegalArgumentException(wrongClassMessage);
		}
	}
	
	/**
	 * Constructs a CommandRequestPacket with the given parameters.
	 * @param atCommand
	 */
	public CommandRequestPacket(
			String atCommand
			) {
		this(DEFAULT_FRAME_ID, true, atCommand, null);
	}

	/**
	 * Constructs a CommandRequestPacket with the given parameters.
	 * @param atCommand
	 */
	public CommandRequestPacket(
			String atCommand,
			Object parameterObject
			) {
		this(DEFAULT_FRAME_ID, true, atCommand, parameterObject);
	}

	/**
	 * Constructs a CommandRequestPacket with the given parameters
	 * @param frameIDByte
	 * @param atCommand
	 */
	public CommandRequestPacket(
			int frameIDByte,
			String atCommand
			) {
		this(frameIDByte, true, atCommand, null);
	}

	/**
	 * Constructs a CommandRequestPacket with the given parameters
	 * @param frameIDByte
	 * @param applyNow
	 * @param atCommand
	 */
	public CommandRequestPacket(
			int frameIDByte,
			boolean applyNow,
			String atCommand
			) {
		this(frameIDByte, applyNow, atCommand, null);
	}

	/**
	 * Constructs a CommandRequestPacket with the given parameters
	 * @param frameIDByte
	 * @param applyNow
	 * @param atCommand
	 * @param parameterObject
	 */
	public CommandRequestPacket(
			int frameIDByte,
			boolean applyNow,
			String atCommand,
			Object parameterObject
			) {
		this();
		
		// validity checks
		// AT Command must be exactly two characters long.
		if (atCommand.length() != 2) {
			throw new IllegalArgumentException("AT Command is an incorrect length. It must be exactly 2 characters long.");
		}
		
		setFrameID((byte)frameIDByte);
		setApplyNow(applyNow);
		setATCommand(atCommand);

		// set the parameter
		if (parameterObject == null) {
			setParameter(new byte[0]);
		} else if (parameterObject instanceof byte[]) {
			setParameter((byte[])parameterObject);
		} else if (parameterObject instanceof String) {
			setParameter((String)parameterObject);
		} else {
			// not quite the type expected. convert to a string, then to bytes.
			setParameter(parameterObject.toString());
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
	 * @return Returns true iff the packet requests that the XBee module not send a response. 
	 */
	public boolean getResponseSuppressed() {
		// any frame with frameID of zero will be suppress any response from the receiving device.
		return getFrameID() == 0;
	}
	
	/**
	 * Sets the packet's frame ID. If the frame ID is 0, the XBee module will not send any response.
	 * @param frameID
	 */
	public void setFrameID(byte frameID) {
		packetBytes[2] = frameID;
	}
	
	/**
	 * Sets whether the XBee module should suppress any response.
	 * @param suppress
	 */
	public void setResponseSuppressed(boolean suppress) {
		if (suppress) {
			// response is to be suppressed, set the frame ID to 0
			setFrameID((byte)0);
		} else {
			// don't suppress the response
			// if the response was previously suppressed, set frameID = DEFAULT_FRAME_ID so it won't be suppressed
			if (packetBytes[2] == 0) {
				packetBytes[2] = DEFAULT_FRAME_ID;
			}
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	// configuration options accessors (see XBee docs)

	/**
	 * @return Gets whether the receiving XBee device should apply this and all other queued any changes immediately.
	 */
	public boolean getApplyNow() {
		if ((packetBytes[3] & 0x02) == 0x02) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets whether the receiving XBee device should apply this and all other queued any changes immediately. You can apply changes later with an "AC" command or another command with applyNow set.
	 * @return
	 */
	public void setApplyNow(boolean applyNow) {
		if (applyNow) {
			packetBytes[3] |= 0x02;
		} else {
			packetBytes[3] &= ~0x02;
		}
	}
	

	
	
	
	
	
	
	
	// atCommand accessors
	
	/**
	 * @return Gets the AT command portion of the packet.
	 */
	public String getATCommand() {
		return Charset.forName("US-ASCII").decode(ByteBuffer.wrap(packetBytes, 4, 2)).toString();
	}
	
	/**
	 * Sets the AT command that should be executed by the XBee module.
	 * @param atCommand
	 */
	public void setATCommand(String atCommand) {
		byte[] bytes = Charset.forName("US-ASCII").encode(atCommand.substring(0, 2)).array();
		packetBytes[4] = bytes[0]; // AT command, first character
		packetBytes[5] = bytes[1]; // AT command, second character
	}
	
	
	
	
	
	
	
	// parameter accessors
	
	/**
	 * @return Returns a copy of the packet parameter as a byte array. If there is no parameter, the return value is a byte array of length zero.
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
		// the XBee docs don'e specifically call out a limit, but I'm guessing that the limit of the total packet length is 1400 bytes,
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
	
	
	
	
	
	
	
	
	
	@Override
	public String toString() {
		return String.format("%s: ID=0x%02X %s %s%s",
				getClass().getSimpleName(),
				getFrameID(),
				getParameter().length == 0 ? "get" : "set",
				getATCommand(),
				getParameter().length == 0 ? "" : "=" + ATCommandHelper.decodeToString(getATCommand(), getParameter()));
	}
	
}
