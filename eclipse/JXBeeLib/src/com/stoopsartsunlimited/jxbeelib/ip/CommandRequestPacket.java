package com.stoopsartsunlimited.jxbeelib.ip;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.stoopsartsunlimited.jxbeelib.XBeeException;

/**
 * Represents a packet that sends a command to an XBee module over TCP/IP.
 * 
 * You should probably treat this object as immutable.
 * 
 * @author Michael
 *
 */
public class CommandRequestPacket extends Packet {
	
	protected byte[] packetBytes = null;

	public CommandRequestPacket(byte[] networkData, int length) throws XBeeException {
		super();
		
		// check validity
		if (networkData.length < 6
				|| length < 6) {
			throw new XBeeException("networkData is too short to be a packet.");
		}

		packetBytes = Arrays.copyOfRange(networkData, 0, networkData.length);
	}
	
	/**
	 * Creates a packet that requests to execute a command on a remote XBee device.
	 * @param frameIDByte Identifying byte for this request. If set to zero, any response is suppressed. Otherwise, the same value will come back in the response.
	 * @param applyNow True iff this and all previous changes should be applied now.
	 * @param atCommand Two-character string of the AT command code.
	 * @return
	 * @throws XBeeException
	 */
	public CommandRequestPacket(
			int frameIDByte,
			boolean applyNow,
			String atCommand
			) throws XBeeException {
		this(frameIDByte, applyNow, atCommand, null);
	}

	/**
	 * Creates a packet that requests to execute a command on a remote XBee device.
	 * @param frameIDByte Identifying byte for this request. If set to zero, any response is suppressed. Otherwise, the same value will come back in the response.
	 * @param applyNow True iff this and all previous changes should be applied now.
	 * @param atCommand Two-character string of the AT command code.
	 * @param parameter Data to go with the AT command.
	 * @return
	 * @throws XBeeException
	 */
	public CommandRequestPacket(
			int frameIDByte,
			boolean applyNow,
			String atCommand,
			Object parameterObject
			) {
		
		// validity checks
		// AT Command must be exactly two characters long.
		if (atCommand.length() != 2) {
			throw new IllegalArgumentException("AT Command is an incorrect length. It must be exactly 2 characters long.");
		}
		// parameter can't be unreasonably large
		if ((parameterObject instanceof byte[] && ((byte[])parameterObject).length > 4000)
				|| (parameterObject instanceof String && ((String)parameterObject).length() > 4000)) {
			throw new IllegalArgumentException("Parameter is too large.");
		}
		
		// convert parameter to bytes
		byte[] parameterBytes = parameterObjectToBytes(parameterObject);

		// compose the packet
		packetBytes = new byte[6 + parameterBytes.length];
		packetBytes[0] = commandEnumToByte(PacketCommands.REMOTE_COMMAND);
		packetBytes[1] = 0; // command options is always 0 for AT commands 
		packetBytes[2] = (byte)frameIDByte;
		packetBytes[3] = applyNow ? (byte) 0x02 : (byte) 0x00; // Configuration options
		packetBytes[4] = atCommand.getBytes()[0]; // AT command, first character
		packetBytes[5] = atCommand.getBytes()[1]; // AT command, second character
		System.arraycopy(parameterBytes, 0, packetBytes, 6, parameterBytes.length);
	}
	
	public PacketCommands getCommand() {
		return commandByteToEnum(packetBytes[0]);
	}
	
	public byte getFrameID() {
		return packetBytes[2];
	}
	
	public boolean getResponseSuppressed() {
		// any frame with frameID of zero will be suppress any response from the receiving device.
		return getFrameID() == 0;
	}
	
	public void setResponseSuppressed(boolean suppress) {
		if (suppress) {
			// response is to be suppressed, set the frame ID to 0
			packetBytes[2] = 0;
		}
		// don't suppress the response, leave the frame ID.
	}
	
	public boolean getApplyNow() {
		if ((packetBytes[3] & 0x02) == 0x02) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * gets the AT command portion of the packet
	 * @return 
	 */
	public String getATCommand() {
		try {
			return new String(new byte[]{ packetBytes[4], packetBytes[5] }, "US-ASCII");
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @return the parameter
	 * @throws XBeeException 
	 */
	public String getParameterString() {
		try {
			return new String(getParameterBytes(), "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			// can't happen
			assert(false);
			return null;
		}
	}
	
	public byte[] getParameterBytes() {
		return Arrays.copyOfRange(packetBytes, 6, packetBytes.length);
	}

	@Override
	public byte[] getBytes() {
		return packetBytes;
	}

	/**
	 * Converts an object to bytes that we can send across the network.
	 * If the argument isn't one of the expected types, this method calls parameter.toString() and uses that string.
	 * @param parameter null, a byte array, a string.
	 * @return a 
	 */
	protected static byte[] parameterObjectToBytes(Object parameter) {
		if (parameter == null) {
			// parameter is null, send nothing
			return new byte[0];
		} else if (parameter instanceof byte[]) {
			// already is a byte array, send as-is
			return (byte[])parameter;
		} else if (parameter instanceof String) {
			// is a string, convert to bytes.
			return ((String) parameter).getBytes();
		} else {
			// unknown type. convert to string then to bytes
			return parameter.toString().getBytes();
		}		
	}

}
