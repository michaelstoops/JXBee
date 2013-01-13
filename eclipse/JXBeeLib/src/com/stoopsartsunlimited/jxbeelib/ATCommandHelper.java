package com.stoopsartsunlimited.jxbeelib;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ATCommandHelper {

	/**
	 * Takes some bytes as they would be seen in an XBee Wi-Fi AT Command packet, and returns the same data represented in the preferred Java type for that data.
	 * @param atCommand The AT command associated with this data. Is used to properly format the data for its purpose.
	 * @param v The data to be converted.
	 * @return Returns the preferred Java representation of the data.
	 */
	public static Object decodeAT(String atCommand, byte[] v) {
		
		try {
			
		// AUTO-GENERATED CODE. DON'T EDIT.
		
			if (atCommand.equals(ATCommand.DEVICE_TYPE_IDENTIFIER)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.SERIAL_NUMBER_HIGH)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.SERIAL_NUMBER_LOW)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.SERIAL_COMM_SERVICE_PORT)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.DESTINATION_ENDPOINT)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.MAX_RF_PAYLOAD_BYTES)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.DESTINATION_ADDRESS_LOW)) return InetAddress.getByName(Charset.forName("US-ASCII").decode(ByteBuffer.wrap(v)).toString());
			if (atCommand.equals(ATCommand.GATEWAY_IP_ADDRESS)) return InetAddress.getByName(Charset.forName("US-ASCII").decode(ByteBuffer.wrap(v)).toString());
			if (atCommand.equals(ATCommand.IP_ADDRESS_MASK)) return InetAddress.getByName(Charset.forName("US-ASCII").decode(ByteBuffer.wrap(v)).toString());
			if (atCommand.equals(ATCommand.NETWORK_ADDRESS)) return InetAddress.getByName(Charset.forName("US-ASCII").decode(ByteBuffer.wrap(v)).toString());
			if (atCommand.equals(ATCommand.NODE_IDENTIFIER)) return Charset.forName("US-ASCII").decode(ByteBuffer.wrap(v)).toString();
			if (atCommand.equals(ATCommand.CHANNEL)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.TCP_TIMEOUT)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.NETWORK_TYPE)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.BIT_RATE_OF_IBSS_CREATOR)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.ENCRYPTION_ENABLE)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.IP_PROTOCOL)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.IP_ADDRESSING_MODE)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.POWER_LEVEL)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.SSID)) return Charset.forName("US-ASCII").decode(ByteBuffer.wrap(v)).toString();
			if (atCommand.equals(ATCommand.WIFI_SECURITY_KEY)) return Charset.forName("US-ASCII").decode(ByteBuffer.wrap(v)).toString();
			if (atCommand.equals(ATCommand.PACKETIZATION_TIMEOUT)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.API_ENABLE)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.INTERFACE_DATA_RATE)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.DIO6_CONFIGURATION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.DIO7_CONFIGURATION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.SERIAL_PARITY)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.STOP_BITS)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.FLOW_CONTROL_THRESHOLD)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.IO_DIGITAL_CHANGE_DETECTION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.SAMPLE_FROM_SLEEP_RATE)) return new BigInteger(1, v).intValue();
			// no conversion for FORCE_SAMPLE
			if (atCommand.equals(ATCommand.DIO10_CONFIGURATION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.DIO11_CONFIGURATION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.DIO12_CONFIGURATION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.DOUT)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.DIN)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.IO_SAMPLE_RATE)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.AD0_DIO0_CONFIGURATION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.AD1_DIO1_CONFIGURATION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.AD2_DIO2_CONFIGURATION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.AD3_DIO3_CONFIGURATION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.DIO4_CONFIGURATION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.DIO5_CONFIGURATION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.PULL_DIRECTION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.PULL_UP_RESISTOR)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.ASSOC_LED_BLINK_TIME)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.ANALOG_VOLTAGE_REFERENCE)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.DIO8_CONFIGURATION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.DIO9_CONFIGURATION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.PWM0_DUTY_CYCLE)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.PWM1_DUTY_CYCLE)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.RSSI)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.ASSOCIATION_INDICATION)) return new BigInteger(1, v).intValue();
			// no conversion for ACTIVE_SCAN
			if (atCommand.equals(ATCommand.SUPPLY_VOLTAGE)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.CONFIGURATION_CODE)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.HARDWARE_VERSION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.TEMPERATURE)) return new BigInteger(v).intValue();
			if (atCommand.equals(ATCommand.FIRMWARE_VERSION)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.SLEEP_OPTIONS)) return new BigInteger(1, v).intValue();
			// no conversion for EXIT_COMMAND_MODE
			if (atCommand.equals(ATCommand.SLEEP_MODE)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.SLEEP_PERIOD)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.WAKE_TIME)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.COMMAND_MODE_TIMEOUT)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.GUARD_TIMES)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.WAKE_HOST)) return new BigInteger(1, v).intValue();
			if (atCommand.equals(ATCommand.COMMAND_MODE_CHARACTER)) return Charset.forName("US-ASCII").decode(ByteBuffer.wrap(v)).toString();
			// no conversion for APPLY_CHANGES
			// no conversion for SOFTWARE_RESET
			// no conversion for NETWORK_RESET
			// no conversion for RESTORE_DEFAULTS
			// no conversion for WRITE
			
		} catch (UnknownHostException e) {
			throw new IllegalArgumentException(e);
		}
		
		// don't know what else to do, return the byte array
		throw new IllegalArgumentException("No conversion available for AT command " + atCommand);
	}
	
	
	/**
	 * Takes an object that represents some state information in an XBee module. Represents that data as a string.
	 * @param atCommand The AT command associated with this data. Is used to properly format the data for its purpose.
	 * @param v The object to be converted
	 * @return Returns a string representation of the state data.
	 */
	public static String toString(String atCommand, Object v) {
		
		// AUTO-GENERATED CODE. DON'T EDIT.
		
		if (atCommand.equals(ATCommand.DEVICE_TYPE_IDENTIFIER)) return String.format("0x%08X", v);
		if (atCommand.equals(ATCommand.SERIAL_NUMBER_HIGH)) return String.format("0x%08X", v);
		if (atCommand.equals(ATCommand.SERIAL_NUMBER_LOW)) return String.format("0x%08X", v);
		if (atCommand.equals(ATCommand.SERIAL_COMM_SERVICE_PORT)) return String.format("0x%04X", v);
		if (atCommand.equals(ATCommand.DESTINATION_ENDPOINT)) return String.format("0x%04X", v);
		if (atCommand.equals(ATCommand.MAX_RF_PAYLOAD_BYTES)) return String.format("%d", v);
		if (atCommand.equals(ATCommand.DESTINATION_ADDRESS_LOW)) return String.format("%s", ((InetAddress)v).getHostAddress());
		if (atCommand.equals(ATCommand.GATEWAY_IP_ADDRESS)) return String.format("%s", ((InetAddress)v).getHostAddress());
		if (atCommand.equals(ATCommand.IP_ADDRESS_MASK)) return String.format("%s", ((InetAddress)v).getHostAddress());
		if (atCommand.equals(ATCommand.NETWORK_ADDRESS)) return String.format("%s", ((InetAddress)v).getHostAddress());
		if (atCommand.equals(ATCommand.NODE_IDENTIFIER)) return String.format("\"%s\"", v);
		if (atCommand.equals(ATCommand.CHANNEL)) return String.format("%d", v);
		if (atCommand.equals(ATCommand.TCP_TIMEOUT)) return String.format("%d", v);
		if (atCommand.equals(ATCommand.NETWORK_TYPE)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.BIT_RATE_OF_IBSS_CREATOR)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.ENCRYPTION_ENABLE)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.IP_PROTOCOL)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.IP_ADDRESSING_MODE)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.POWER_LEVEL)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.SSID)) return String.format("\"%s\"", v);
		if (atCommand.equals(ATCommand.WIFI_SECURITY_KEY)) return String.format("\"%s\"", v);
		if (atCommand.equals(ATCommand.PACKETIZATION_TIMEOUT)) return String.format("%d", v);
		if (atCommand.equals(ATCommand.API_ENABLE)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.INTERFACE_DATA_RATE)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.DIO6_CONFIGURATION)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.DIO7_CONFIGURATION)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.SERIAL_PARITY)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.STOP_BITS)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.FLOW_CONTROL_THRESHOLD)) return String.format("%d", v);
		if (atCommand.equals(ATCommand.IO_DIGITAL_CHANGE_DETECTION)) return String.format("%d", v);
		if (atCommand.equals(ATCommand.SAMPLE_FROM_SLEEP_RATE)) return String.format("%d", v);
		// no conversion for FORCE_SAMPLE
		if (atCommand.equals(ATCommand.DIO10_CONFIGURATION)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.DIO11_CONFIGURATION)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.DIO12_CONFIGURATION)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.DOUT)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.DIN)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.IO_SAMPLE_RATE)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.AD0_DIO0_CONFIGURATION)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.AD1_DIO1_CONFIGURATION)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.AD2_DIO2_CONFIGURATION)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.AD3_DIO3_CONFIGURATION)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.DIO4_CONFIGURATION)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.DIO5_CONFIGURATION)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.PULL_DIRECTION)) return String.format("0x%X", v);
		if (atCommand.equals(ATCommand.PULL_UP_RESISTOR)) return String.format("0x%X", v);
		if (atCommand.equals(ATCommand.ASSOC_LED_BLINK_TIME)) return String.format("%d", v);
		if (atCommand.equals(ATCommand.ANALOG_VOLTAGE_REFERENCE)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.DIO8_CONFIGURATION)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.DIO9_CONFIGURATION)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.PWM0_DUTY_CYCLE)) return String.format("%.3f%%", 100 * (Integer)v / (double)0x3FF);
		if (atCommand.equals(ATCommand.PWM1_DUTY_CYCLE)) return String.format("%.3f%%", 100 * (Integer)v / (double)0x3FF);
		if (atCommand.equals(ATCommand.RSSI)) return String.format("%d dBm", (Integer)v*(-1));
		if (atCommand.equals(ATCommand.ASSOCIATION_INDICATION)) return String.format("%s", v);
		// no conversion for ACTIVE_SCAN
		if (atCommand.equals(ATCommand.SUPPLY_VOLTAGE)) return String.format("%.3f V", (Integer)v * 0.001);
		if (atCommand.equals(ATCommand.CONFIGURATION_CODE)) return String.format("0x%04X", v);
		if (atCommand.equals(ATCommand.HARDWARE_VERSION)) return String.format("0x%04X", v);
		if (atCommand.equals(ATCommand.TEMPERATURE)) return String.format("%d C", v);
		if (atCommand.equals(ATCommand.FIRMWARE_VERSION)) return String.format("0x%04X", v);
		if (atCommand.equals(ATCommand.SLEEP_OPTIONS)) return String.format("0x%04X", v);
		// no conversion for EXIT_COMMAND_MODE
		if (atCommand.equals(ATCommand.SLEEP_MODE)) return String.format("%s", v);
		if (atCommand.equals(ATCommand.SLEEP_PERIOD)) return String.format("%.3f sec", (Integer)v * 0.01);
		if (atCommand.equals(ATCommand.WAKE_TIME)) return String.format("%.3f sec", (Integer)v * 0.001);
		if (atCommand.equals(ATCommand.COMMAND_MODE_TIMEOUT)) return String.format("%.1f sec", (Integer)v * 0.1);
		if (atCommand.equals(ATCommand.GUARD_TIMES)) return String.format("%.3f sec", (Integer)v * 0.001);
		if (atCommand.equals(ATCommand.WAKE_HOST)) return String.format("%d ms", v);
		if (atCommand.equals(ATCommand.COMMAND_MODE_CHARACTER)) return String.format("\"%s\"", v);
		// no conversion for APPLY_CHANGES
		// no conversion for SOFTWARE_RESET
		// no conversion for NETWORK_RESET
		// no conversion for RESTORE_DEFAULTS
		// no conversion for WRITE

		
		// don't know what else to do, return a generic toString()
		throw new IllegalArgumentException("No conversion available for AT command " + atCommand);
		
	}

	/**
	 * Takes an object that represents the data of an XBee register, and converts it to bytes so that it can be sent in an AT Command packet.
	 * @param atCommand The AT command associated with this data. Is used to properly encode the data for its purpose.
	 * @param v The data to be converted.
	 * @return Returns bytes to be sent as the parameter of an AT Command packet.
	 */
	public static byte[] encodeAT(String atCommand, Object v) {
		
		// AUTO-GENERATED CODE. DON'T EDIT.
		
		if (atCommand.equals(ATCommand.DEVICE_TYPE_IDENTIFIER)) return ByteBuffer.allocate(4).putInt(((Integer)v).intValue()).array();
		if (atCommand.equals(ATCommand.SERIAL_NUMBER_HIGH)) return ByteBuffer.allocate(4).putInt(((Integer)v).intValue()).array();
		if (atCommand.equals(ATCommand.SERIAL_NUMBER_LOW)) return ByteBuffer.allocate(4).putInt(((Integer)v).intValue()).array();
		if (atCommand.equals(ATCommand.SERIAL_COMM_SERVICE_PORT)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.DESTINATION_ENDPOINT)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.MAX_RF_PAYLOAD_BYTES)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.DESTINATION_ADDRESS_LOW)) return Charset.forName("US-ASCII").encode((((InetAddress)v).getHostAddress()).toString()).array();
		if (atCommand.equals(ATCommand.GATEWAY_IP_ADDRESS)) return Charset.forName("US-ASCII").encode((((InetAddress)v).getHostAddress()).toString()).array();
		if (atCommand.equals(ATCommand.IP_ADDRESS_MASK)) return Charset.forName("US-ASCII").encode((((InetAddress)v).getHostAddress()).toString()).array();
		if (atCommand.equals(ATCommand.NETWORK_ADDRESS)) return Charset.forName("US-ASCII").encode((((InetAddress)v).getHostAddress()).toString()).array();
		if (atCommand.equals(ATCommand.NODE_IDENTIFIER)) return Charset.forName("US-ASCII").encode((String)v).array();
		if (atCommand.equals(ATCommand.CHANNEL)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.TCP_TIMEOUT)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.NETWORK_TYPE)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.BIT_RATE_OF_IBSS_CREATOR)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.ENCRYPTION_ENABLE)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.IP_PROTOCOL)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.IP_ADDRESSING_MODE)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.POWER_LEVEL)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.SSID)) return Charset.forName("US-ASCII").encode((String)v).array();
		if (atCommand.equals(ATCommand.WIFI_SECURITY_KEY)) return Charset.forName("US-ASCII").encode((String)v).array();
		if (atCommand.equals(ATCommand.PACKETIZATION_TIMEOUT)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.API_ENABLE)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.INTERFACE_DATA_RATE)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.DIO6_CONFIGURATION)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.DIO7_CONFIGURATION)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.SERIAL_PARITY)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.STOP_BITS)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.FLOW_CONTROL_THRESHOLD)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.IO_DIGITAL_CHANGE_DETECTION)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.SAMPLE_FROM_SLEEP_RATE)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		// no conversion for FORCE_SAMPLE
		if (atCommand.equals(ATCommand.DIO10_CONFIGURATION)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.DIO11_CONFIGURATION)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.DIO12_CONFIGURATION)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.DOUT)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.DIN)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.IO_SAMPLE_RATE)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.AD0_DIO0_CONFIGURATION)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.AD1_DIO1_CONFIGURATION)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.AD2_DIO2_CONFIGURATION)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.AD3_DIO3_CONFIGURATION)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.DIO4_CONFIGURATION)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.DIO5_CONFIGURATION)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.PULL_DIRECTION)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.PULL_UP_RESISTOR)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.ASSOC_LED_BLINK_TIME)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.ANALOG_VOLTAGE_REFERENCE)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.DIO8_CONFIGURATION)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.DIO9_CONFIGURATION)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.PWM0_DUTY_CYCLE)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.PWM1_DUTY_CYCLE)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.RSSI)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.ASSOCIATION_INDICATION)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		// no conversion for ACTIVE_SCAN
		if (atCommand.equals(ATCommand.SUPPLY_VOLTAGE)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.CONFIGURATION_CODE)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.HARDWARE_VERSION)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.TEMPERATURE)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.FIRMWARE_VERSION)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.SLEEP_OPTIONS)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		// no conversion for EXIT_COMMAND_MODE
		if (atCommand.equals(ATCommand.SLEEP_MODE)) return ByteBuffer.allocate(1).put(((Integer)v).byteValue()).array();
		if (atCommand.equals(ATCommand.SLEEP_PERIOD)) return ByteBuffer.allocate(4).putInt(((Integer)v).intValue()).array();
		if (atCommand.equals(ATCommand.WAKE_TIME)) return ByteBuffer.allocate(4).putInt(((Integer)v).intValue()).array();
		if (atCommand.equals(ATCommand.COMMAND_MODE_TIMEOUT)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.GUARD_TIMES)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.WAKE_HOST)) return ByteBuffer.allocate(2).putShort(((Integer)v).shortValue()).array();
		if (atCommand.equals(ATCommand.COMMAND_MODE_CHARACTER)) return Charset.forName("US-ASCII").encode((String)v).array();
		// no conversion for APPLY_CHANGES
		// no conversion for SOFTWARE_RESET
		// no conversion for NETWORK_RESET
		// no conversion for RESTORE_DEFAULTS
		// no conversion for WRITE


		
		// don't know what else to do
		throw new IllegalArgumentException("No conversion available for AT command " + atCommand);
	}


	public static String decodeToString(String atCommand, byte[] v) {
		Object o = decodeAT(atCommand, v);
		return toString(atCommand, o); 
	}
	
	
	public static boolean canGet(String atCommand) {
		
		// AUTO-GENERATED CODE. DON'T EDIT.
		
		if (atCommand.equals(ATCommand.DESTINATION_ADDRESS_LOW)) return true;
		if (atCommand.equals(ATCommand.GATEWAY_IP_ADDRESS)) return true;
		if (atCommand.equals(ATCommand.IP_ADDRESS_MASK)) return true;
		if (atCommand.equals(ATCommand.NETWORK_ADDRESS)) return true;
		if (atCommand.equals(ATCommand.DEVICE_TYPE_IDENTIFIER)) return true;
		if (atCommand.equals(ATCommand.SERIAL_NUMBER_HIGH)) return true;
		if (atCommand.equals(ATCommand.SERIAL_NUMBER_LOW)) return true;
		if (atCommand.equals(ATCommand.SERIAL_COMM_SERVICE_PORT)) return true;
		if (atCommand.equals(ATCommand.DESTINATION_ENDPOINT)) return true;
		if (atCommand.equals(ATCommand.MAX_RF_PAYLOAD_BYTES)) return true;
		if (atCommand.equals(ATCommand.NODE_IDENTIFIER)) return true;
		if (atCommand.equals(ATCommand.CHANNEL)) return true;
		if (atCommand.equals(ATCommand.TCP_TIMEOUT)) return true;
		if (atCommand.equals(ATCommand.NETWORK_TYPE)) return true;
		if (atCommand.equals(ATCommand.BIT_RATE_OF_IBSS_CREATOR)) return true;
		if (atCommand.equals(ATCommand.ENCRYPTION_ENABLE)) return true;
		if (atCommand.equals(ATCommand.IP_PROTOCOL)) return true;
		if (atCommand.equals(ATCommand.IP_ADDRESSING_MODE)) return true;
		if (atCommand.equals(ATCommand.POWER_LEVEL)) return true;
		if (atCommand.equals(ATCommand.SSID)) return true;
		if (atCommand.equals(ATCommand.WIFI_SECURITY_KEY)) return true;
		if (atCommand.equals(ATCommand.PACKETIZATION_TIMEOUT)) return true;
		if (atCommand.equals(ATCommand.API_ENABLE)) return true;
		if (atCommand.equals(ATCommand.INTERFACE_DATA_RATE)) return true;
		if (atCommand.equals(ATCommand.DIO6_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.DIO7_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.SERIAL_PARITY)) return true;
		if (atCommand.equals(ATCommand.STOP_BITS)) return true;
		if (atCommand.equals(ATCommand.FLOW_CONTROL_THRESHOLD)) return true;
		if (atCommand.equals(ATCommand.IO_DIGITAL_CHANGE_DETECTION)) return true;
		if (atCommand.equals(ATCommand.SAMPLE_FROM_SLEEP_RATE)) return true;
		if (atCommand.equals(ATCommand.DIO10_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.DIO11_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.DIO12_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.DOUT)) return true;
		if (atCommand.equals(ATCommand.DIN)) return true;
		if (atCommand.equals(ATCommand.IO_SAMPLE_RATE)) return true;
		if (atCommand.equals(ATCommand.FORCE_SAMPLE)) return false;
		if (atCommand.equals(ATCommand.AD0_DIO0_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.AD1_DIO1_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.AD2_DIO2_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.AD3_DIO3_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.DIO4_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.DIO5_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.PULL_DIRECTION)) return true;
		if (atCommand.equals(ATCommand.PULL_UP_RESISTOR)) return true;
		if (atCommand.equals(ATCommand.ASSOC_LED_BLINK_TIME)) return true;
		if (atCommand.equals(ATCommand.ANALOG_VOLTAGE_REFERENCE)) return true;
		if (atCommand.equals(ATCommand.DIO8_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.DIO9_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.PWM0_DUTY_CYCLE)) return true;
		if (atCommand.equals(ATCommand.PWM1_DUTY_CYCLE)) return true;
		if (atCommand.equals(ATCommand.RSSI)) return true;
		if (atCommand.equals(ATCommand.ASSOCIATION_INDICATION)) return true;
		if (atCommand.equals(ATCommand.SUPPLY_VOLTAGE)) return true;
		if (atCommand.equals(ATCommand.CONFIGURATION_CODE)) return true;
		if (atCommand.equals(ATCommand.HARDWARE_VERSION)) return true;
		if (atCommand.equals(ATCommand.TEMPERATURE)) return true;
		if (atCommand.equals(ATCommand.FIRMWARE_VERSION)) return true;
		if (atCommand.equals(ATCommand.ACTIVE_SCAN)) return false;
		if (atCommand.equals(ATCommand.SLEEP_OPTIONS)) return true;
		if (atCommand.equals(ATCommand.SLEEP_MODE)) return true;
		if (atCommand.equals(ATCommand.SLEEP_PERIOD)) return true;
		if (atCommand.equals(ATCommand.WAKE_TIME)) return true;
		if (atCommand.equals(ATCommand.COMMAND_MODE_TIMEOUT)) return true;
		if (atCommand.equals(ATCommand.GUARD_TIMES)) return true;
		if (atCommand.equals(ATCommand.WAKE_HOST)) return true;
		if (atCommand.equals(ATCommand.COMMAND_MODE_CHARACTER)) return true;
		if (atCommand.equals(ATCommand.EXIT_COMMAND_MODE)) return false;
		if (atCommand.equals(ATCommand.APPLY_CHANGES)) return false;
		if (atCommand.equals(ATCommand.SOFTWARE_RESET)) return false;
		if (atCommand.equals(ATCommand.NETWORK_RESET)) return false;
		if (atCommand.equals(ATCommand.RESTORE_DEFAULTS)) return false;
		if (atCommand.equals(ATCommand.WRITE)) return false;
		
		// ummmmm... unknown register, so it's not readable
		return false;
	}
	
	public static boolean canSet(String atCommand) {
		
		// AUTO-GENERATED CODE. DON'T EDIT.
		
		if (atCommand.equals(ATCommand.DESTINATION_ADDRESS_LOW)) return true;
		if (atCommand.equals(ATCommand.GATEWAY_IP_ADDRESS)) return true;
		if (atCommand.equals(ATCommand.IP_ADDRESS_MASK)) return true;
		if (atCommand.equals(ATCommand.NETWORK_ADDRESS)) return true;
		if (atCommand.equals(ATCommand.DEVICE_TYPE_IDENTIFIER)) return true;
		if (atCommand.equals(ATCommand.SERIAL_NUMBER_HIGH)) return false;
		if (atCommand.equals(ATCommand.SERIAL_NUMBER_LOW)) return false;
		if (atCommand.equals(ATCommand.SERIAL_COMM_SERVICE_PORT)) return true;
		if (atCommand.equals(ATCommand.DESTINATION_ENDPOINT)) return true;
		if (atCommand.equals(ATCommand.MAX_RF_PAYLOAD_BYTES)) return false;
		if (atCommand.equals(ATCommand.NODE_IDENTIFIER)) return true;
		if (atCommand.equals(ATCommand.CHANNEL)) return false;
		if (atCommand.equals(ATCommand.TCP_TIMEOUT)) return true;
		if (atCommand.equals(ATCommand.NETWORK_TYPE)) return true;
		if (atCommand.equals(ATCommand.BIT_RATE_OF_IBSS_CREATOR)) return true;
		if (atCommand.equals(ATCommand.ENCRYPTION_ENABLE)) return true;
		if (atCommand.equals(ATCommand.IP_PROTOCOL)) return true;
		if (atCommand.equals(ATCommand.IP_ADDRESSING_MODE)) return true;
		if (atCommand.equals(ATCommand.POWER_LEVEL)) return true;
		if (atCommand.equals(ATCommand.SSID)) return true;
		if (atCommand.equals(ATCommand.WIFI_SECURITY_KEY)) return true;
		if (atCommand.equals(ATCommand.PACKETIZATION_TIMEOUT)) return true;
		if (atCommand.equals(ATCommand.API_ENABLE)) return true;
		if (atCommand.equals(ATCommand.INTERFACE_DATA_RATE)) return true;
		if (atCommand.equals(ATCommand.DIO6_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.DIO7_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.SERIAL_PARITY)) return true;
		if (atCommand.equals(ATCommand.STOP_BITS)) return true;
		if (atCommand.equals(ATCommand.FLOW_CONTROL_THRESHOLD)) return true;
		if (atCommand.equals(ATCommand.IO_DIGITAL_CHANGE_DETECTION)) return true;
		if (atCommand.equals(ATCommand.SAMPLE_FROM_SLEEP_RATE)) return true;
		if (atCommand.equals(ATCommand.DIO10_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.DIO11_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.DIO12_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.DOUT)) return true;
		if (atCommand.equals(ATCommand.DIN)) return true;
		if (atCommand.equals(ATCommand.IO_SAMPLE_RATE)) return true;
		if (atCommand.equals(ATCommand.FORCE_SAMPLE)) return true;
		if (atCommand.equals(ATCommand.AD0_DIO0_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.AD1_DIO1_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.AD2_DIO2_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.AD3_DIO3_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.DIO4_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.DIO5_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.PULL_DIRECTION)) return true;
		if (atCommand.equals(ATCommand.PULL_UP_RESISTOR)) return true;
		if (atCommand.equals(ATCommand.ASSOC_LED_BLINK_TIME)) return true;
		if (atCommand.equals(ATCommand.ANALOG_VOLTAGE_REFERENCE)) return true;
		if (atCommand.equals(ATCommand.DIO8_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.DIO9_CONFIGURATION)) return true;
		if (atCommand.equals(ATCommand.PWM0_DUTY_CYCLE)) return true;
		if (atCommand.equals(ATCommand.PWM1_DUTY_CYCLE)) return true;
		if (atCommand.equals(ATCommand.RSSI)) return false;
		if (atCommand.equals(ATCommand.ASSOCIATION_INDICATION)) return false;
		if (atCommand.equals(ATCommand.SUPPLY_VOLTAGE)) return false;
		if (atCommand.equals(ATCommand.CONFIGURATION_CODE)) return false;
		if (atCommand.equals(ATCommand.HARDWARE_VERSION)) return false;
		if (atCommand.equals(ATCommand.TEMPERATURE)) return false;
		if (atCommand.equals(ATCommand.FIRMWARE_VERSION)) return false;
		if (atCommand.equals(ATCommand.ACTIVE_SCAN)) return true;
		if (atCommand.equals(ATCommand.SLEEP_OPTIONS)) return true;
		if (atCommand.equals(ATCommand.SLEEP_MODE)) return true;
		if (atCommand.equals(ATCommand.SLEEP_PERIOD)) return true;
		if (atCommand.equals(ATCommand.WAKE_TIME)) return true;
		if (atCommand.equals(ATCommand.COMMAND_MODE_TIMEOUT)) return true;
		if (atCommand.equals(ATCommand.GUARD_TIMES)) return true;
		if (atCommand.equals(ATCommand.WAKE_HOST)) return true;
		if (atCommand.equals(ATCommand.COMMAND_MODE_CHARACTER)) return true;
		if (atCommand.equals(ATCommand.EXIT_COMMAND_MODE)) return true;
		if (atCommand.equals(ATCommand.APPLY_CHANGES)) return true;
		if (atCommand.equals(ATCommand.SOFTWARE_RESET)) return true;
		if (atCommand.equals(ATCommand.NETWORK_RESET)) return true;
		if (atCommand.equals(ATCommand.RESTORE_DEFAULTS)) return true;
		if (atCommand.equals(ATCommand.WRITE)) return true;

		
		// ummmmm... unknown register, so it's not writeable
		return false;
	}
	
	public static Object parse(String atCommand, String v) {
		
		try {
			// AUTO-GENERATED CODE. DON'T EDIT.

			if (atCommand.equals(ATCommand.DESTINATION_ADDRESS_LOW)) return InetAddress.getByName(v);
			if (atCommand.equals(ATCommand.GATEWAY_IP_ADDRESS)) return InetAddress.getByName(v);
			if (atCommand.equals(ATCommand.IP_ADDRESS_MASK)) return InetAddress.getByName(v);
			if (atCommand.equals(ATCommand.NETWORK_ADDRESS)) return InetAddress.getByName(v);
			if (atCommand.equals(ATCommand.DEVICE_TYPE_IDENTIFIER)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.SERIAL_NUMBER_HIGH)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.SERIAL_NUMBER_LOW)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.SERIAL_COMM_SERVICE_PORT)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.DESTINATION_ENDPOINT)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.MAX_RF_PAYLOAD_BYTES)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.CHANNEL)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.TCP_TIMEOUT)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.NETWORK_TYPE)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.BIT_RATE_OF_IBSS_CREATOR)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.ENCRYPTION_ENABLE)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.IP_PROTOCOL)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.IP_ADDRESSING_MODE)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.POWER_LEVEL)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.PACKETIZATION_TIMEOUT)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.API_ENABLE)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.INTERFACE_DATA_RATE)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.DIO6_CONFIGURATION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.DIO7_CONFIGURATION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.SERIAL_PARITY)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.STOP_BITS)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.FLOW_CONTROL_THRESHOLD)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.IO_DIGITAL_CHANGE_DETECTION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.SAMPLE_FROM_SLEEP_RATE)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.DIO10_CONFIGURATION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.DIO11_CONFIGURATION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.DIO12_CONFIGURATION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.DOUT)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.DIN)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.IO_SAMPLE_RATE)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.AD0_DIO0_CONFIGURATION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.AD1_DIO1_CONFIGURATION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.AD2_DIO2_CONFIGURATION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.AD3_DIO3_CONFIGURATION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.DIO4_CONFIGURATION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.DIO5_CONFIGURATION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.PULL_DIRECTION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.PULL_UP_RESISTOR)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.ASSOC_LED_BLINK_TIME)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.ANALOG_VOLTAGE_REFERENCE)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.DIO8_CONFIGURATION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.DIO9_CONFIGURATION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.PWM0_DUTY_CYCLE)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.PWM1_DUTY_CYCLE)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.RSSI)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.ASSOCIATION_INDICATION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.SUPPLY_VOLTAGE)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.CONFIGURATION_CODE)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.HARDWARE_VERSION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.TEMPERATURE)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.FIRMWARE_VERSION)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.SLEEP_OPTIONS)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.SLEEP_MODE)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.SLEEP_PERIOD)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.WAKE_TIME)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.COMMAND_MODE_TIMEOUT)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.GUARD_TIMES)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.WAKE_HOST)) return Integer.parseInt(v);
			if (atCommand.equals(ATCommand.NODE_IDENTIFIER)) return v.substring(0, Math.min(20, v.length()));
			if (atCommand.equals(ATCommand.SSID)) return v.substring(0, Math.min(31, v.length()));
			if (atCommand.equals(ATCommand.WIFI_SECURITY_KEY)) return v.substring(0, Math.min(31, v.length()));
			if (atCommand.equals(ATCommand.COMMAND_MODE_CHARACTER)) return v.substring(0, Math.min(1, v.length()));

		} catch (UnknownHostException e) {
			throw new IllegalArgumentException(e);
		}

		// don't know what else to do
		throw new IllegalArgumentException("No conversion available for AT command " + atCommand);
		
	}
}
