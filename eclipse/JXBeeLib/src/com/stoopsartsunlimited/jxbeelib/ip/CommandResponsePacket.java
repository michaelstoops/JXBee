package com.stoopsartsunlimited.jxbeelib.ip;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.stoopsartsunlimited.jxbeelib.XBeeException;

/**
 * Represents a packet that is a response from an XBee module.
 * 
 * @author Michael
 *
 */
public class CommandResponsePacket extends Packet {
	
	protected byte[] packetBytes = null;

	public enum Status {
		OK,
		ERROR,
		INVALID_COMMAND,
		INVALID_PARAMETER
	}
	
	public CommandResponsePacket(byte[] networkData, int length) throws XBeeException {
		super();

		// check validity
		if (networkData.length < 6
				|| length < 6) {
			throw new XBeeException("networkData is too short to be a packet.");
		}

		packetBytes = Arrays.copyOfRange(networkData, 0, networkData.length);

	}
	
	@Override
	public String toString() {
		String status;
		try {
			status = getStatus().toString();
		} catch (Exception e) {
			status = "unknown";
		}
		return String.format("%s(%s=\"%s\", status=%s)", CommandResponsePacket.class.getSimpleName(), getATCommand(), getParameterString(), status);
	}
	
	public PacketCommands getCommand() {
		return commandByteToEnum(packetBytes[0]);
	}
	
	public byte getFrameID() {
		return packetBytes[2];
	}
	
	/**
	 * gets the AT command portion of the packet
	 * @return 
	 */
	public String getATCommand() {
		try {
			return new String(new byte[]{ packetBytes[3], packetBytes[4] }, "US-ASCII");
		} catch (Exception e) {
			return null;
		}
	}
	
	public Status getStatus() throws XBeeException {
		switch (packetBytes[5]) {
		case 0x00:
			return Status.OK;
		case 0x01:
			return Status.ERROR;
		case 0x02:
			return Status.INVALID_COMMAND;
		case 0x03:
			return Status.INVALID_PARAMETER;
		default:
			throw new XBeeException("Invalid status: " + packetBytes[5]);
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
			// can't happen, don't propagate exception
			return null;
		}
	}
	
	public byte[] getParameterBytes() {
		return Arrays.copyOfRange(packetBytes, 6, packetBytes.length);
	}

	@Override
	public byte[] getBytes() {
		// TODO Auto-generated method stub
		return null;
	}

}
