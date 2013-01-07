package com.stoopsartsunlimited.jxbeelib;

import java.nio.ByteBuffer;
import java.util.Arrays;


/**
 * Represents the unique identity of any XBee device
 * @author Michael
 *
 */
public class DeviceID {
	/**
	 * Array of six bytes that make up the device serial number. Usually derived from a MAC address
	 * for whatever network the device uses.
	 * 
	 * Wi-Fi devices have 48-bit MAC addresses, padded with zeroes starting with the MSB.
	 * 
	 * Byte-order is MSB first.
	 */
	protected byte[] serialNumber;
	
	public DeviceID() {
		serialNumber = new byte[]{ 0,0,0,0,0,0,0,0 };
	}
	public DeviceID(byte[] bytes) {
		serialNumber = Arrays.copyOf(bytes, 8);		
	}
	public DeviceID(long serialNumber) {
		this.serialNumber = ByteBuffer.allocate(8).putLong(serialNumber).array();
	}
	public DeviceID(int high, int low) {
		this.serialNumber = ByteBuffer.allocate(8).putInt(high).putInt(low).array();
	}
	
	public byte[] getBytes() {
		return Arrays.copyOf(serialNumber, 8);
	}
	public long getLong() {
		return ByteBuffer.wrap(serialNumber).getLong();
	}
	public long getHighInt() {
		return ByteBuffer.wrap(serialNumber).getInt();
	}
	public long getLowInt() {
		return ByteBuffer.wrap(serialNumber).getInt(4);
	}

}
