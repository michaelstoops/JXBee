package com.stoopsartsunlimited.jxbeelib;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.Inet4Address;

public class ATCommandHelper {

	/**
	 * Converts the bytes that were the parameter to an AT command. Puts them into the best data type available for the information they actually represent.
	 * 
	 * @param atCommand
	 * @param parameterBytes
	 * @return Returns the best object it can find to represent the parameter of the AT command.
	 * 
	 */
	public static Object getParameterObject(String atCommand, byte[] v) {
		try {
			// auto-generated code

			if (atCommand.equals(ATCommand.SERIAL_COMM_SERVICE_PORT)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DEVICE_TYPE_IDENTIFIER)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DESTINATION_ENDPOINT)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DESTINATION_ADDRESS_LOW)) return Inet4Address.getByName(toASCII(v));
			if (atCommand.equals(ATCommand.GATEWAY_IP_ADDRESS)) return Inet4Address.getByName(toASCII(v));
			if (atCommand.equals(ATCommand.IP_ADDRESS_MASK)) return Inet4Address.getByName(toASCII(v));
			if (atCommand.equals(ATCommand.NETWORK_ADDRESS)) return Inet4Address.getByName(toASCII(v));
			if (atCommand.equals(ATCommand.NODE_IDENTIFIER)) return toASCII(v);
			if (atCommand.equals(ATCommand.MAX_RF_PAYLOAD_BYTES)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.SERIAL_NUMBER_HIGH)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.SERIAL_NUMBER_LOW)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.NETWORK_TYPE)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.BIT_RATE_OF_IBSS_CREATOR)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.CHANNEL)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.ENCRYPTION_ENABLE)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.SSID)) return toASCII(v);
			if (atCommand.equals(ATCommand.IP_PROTOCOL)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.IP_ADDRESSING_MODE)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.WIFI_SECURITY_KEY)) return toASCII(v);
			if (atCommand.equals(ATCommand.POWER_LEVEL)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.TCP_TIMEOUT)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.API_ENABLE)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.INTERFACE_DATA_RATE)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DIO6_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DIO7_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.FLOW_CONTROL_THRESHOLD)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.SERIAL_PARITY)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.PACKETIZATION_TIMEOUT)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.STOP_BITS)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.IO_DIGITAL_CHANGE_DETECTION)) return new BigInteger(1, v).toString(2);
			if (atCommand.equals(ATCommand.SAMPLE_FROM_SLEEP_RATE)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.IO_SAMPLE_RATE)) return Long.parseLong(new BigInteger(1, v).toString());
			// no conversion for FORCE_SAMPLE
			if (atCommand.equals(ATCommand.PWM0_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DIO10_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DIO11_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DIO12_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DIO13_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DOUT)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DIN)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.AD0_DIO0_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.AD1_DIO1_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.AD2_DIO2_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.AD3_DIO3_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DIO4_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DIO5_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.ANALOG_VOLTAGE_REFERENCE)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DIO8_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.DIO9_CONFIGURATION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.ASSOC_LED_BLINK_TIME)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.PWM0_DUTY_CYCLE)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.PWM1_DUTY_CYCLE)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.PULL_DIRECTION)) return new BigInteger(1, v).toString(2);
			if (atCommand.equals(ATCommand.PULL_UP_RESISTOR)) return new BigInteger(1, v).toString(2);
			if (atCommand.equals(ATCommand.SUPPLY_VOLTAGE)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.ASSOCIATION_INDICATION)) return Long.parseLong(new BigInteger(1, v).toString());
			// no conversion for ACTIVE_SCAN
			if (atCommand.equals(ATCommand.CONFIGURATION_CODE)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.RSSI)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.HARDWARE_VERSION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.TEMPERATURE)) return Integer.parseInt(new BigInteger(v).toString());
			if (atCommand.equals(ATCommand.FIRMWARE_VERSION)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.COMMAND_MODE_CHARACTER)) return toASCII(v);
			// no conversion for EXIT_COMMAND_MODE
			if (atCommand.equals(ATCommand.COMMAND_MODE_TIMEOUT)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.GUARD_TIMES)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.SLEEP_MODE)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.SLEEP_OPTIONS)) return new BigInteger(1, v).toString(2);
			if (atCommand.equals(ATCommand.SLEEP_PERIOD)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.WAKE_TIME)) return Long.parseLong(new BigInteger(1, v).toString());
			if (atCommand.equals(ATCommand.WAKE_HOST)) return Long.parseLong(new BigInteger(1, v).toString());
			// no conversion for APPLY_CHANGES
			// no conversion for SOFTWARE_RESET
			// no conversion for NETWORK_RESET
			// no conversion for RESTORE_DEFAULTS
			// no conversion for WRITE


			// can't figure how to convert the byte array into an object
			// leave it as a byte array. Looks gnarly to print, but doesn't cause a problem.
			return v;




			
			/*
			if (atCommand.equals(ATCommands.TEMPERATURE)) return ByteBuffer.wrap(parameterBytes).getShort();
			return toASCII(parameterBytes);
			*/
			
			/*
			switch (atCommand.hashCode()) {
			// IP addresses
			case ATCommands.DESTINATION_ADDRESS_LOW:
			case ATCommands.GATEWAY_IP_ADDRESS:
			case ATCommands.IP_ADDRESS_MASK:
			case ATCommands.NETWORK_ADDRESS:
				return Inet4Address.getByName(toASCII(parameterBytes));
				
			// enumerations
			// ASCII-coded 32-bit integers
				//return Integer.parseInt(toASCII(parameterBytes), 16);
				
			// ASCII-coded 16-bit integers
				//return Short.parseShort(toASCII(parameterBytes), 16);
				
			// ASCII-coded 8-bit integers
				//return Byte.parseByte(toASCII(parameterBytes), 16);
				
			// ASCII strings
			case ATCommands.SSID:
			case ATCommands.NODE_IDENTIFIER:
			case ATCommands.WIFI_SECURITY_KEY:
				return toASCII(parameterBytes);
	
			// raw binary integer
			case ATCommands.SUPPLY_VOLTAGE:
			case ATCommands.NETWORK_TYPE:
			case ATCommands.ASSOCIATION_INDICATION:
			case ATCommands.API_ENABLE:
			case ATCommands.ANALOG_VOLTAGE_REFERENCE:
			case ATCommands.INTERFACE_DATA_RATE:
			case ATCommands.BIT_RATE_OF_IBSS_CREATOR:
			case ATCommands.SERIAL_COMM_SERVICE_PORT:
			case ATCommands.COMMAND_MODE_CHARACTER:
			case ATCommands.CHANNEL:
			case ATCommands.CONFIGURATION_CODE:
			case ATCommands.COMMAND_MODE_TIMEOUT:
			case ATCommands.AD0_DIO0_CONFIGURATION:
			case ATCommands.AD1_DIO1_CONFIGURATION:
			case ATCommands.AD2_DIO2_CONFIGURATION:
			case ATCommands.AD3_DIO3_CONFIGURATION:
			case ATCommands.DIO4_CONFIGURATION:
			case ATCommands.DIO5_CONFIGURATION:
			case ATCommands.DIO6_CONFIGURATION:
			case ATCommands.DIO7_CONFIGURATION:
			case ATCommands.DIO8_CONFIGURATION:
			case ATCommands.DIO9_CONFIGURATION:
			case ATCommands.RSSI:
			case ATCommands.DEVICE_TYPE_IDENTIFIER:
			case ATCommands.DESTINATION_ENDPOINT:
			case ATCommands.ENCRYPTION_ENABLE:
			case ATCommands.FLOW_CONTROL_THRESHOLD:
			case ATCommands.HARDWARE_VERSION:
			case ATCommands.IO_DIGITAL_CHANGE_DETECTION:
			case ATCommands.SAMPLE_FROM_SLEEP_RATE:
			case ATCommands.IP_PROTOCOL:
			case ATCommands.IO_SAMPLE_RATE:
			case ATCommands.ASSOC_LED_BLINK_TIME:
			case ATCommands.PWM0_DUTY_CYCLE:
			case ATCommands.PWM1_DUTY_CYCLE:
			case ATCommands.IP_ADDRESSING_MODE:
			case ATCommands.SERIAL_PARITY:
			case ATCommands.MAX_RF_PAYLOAD_BYTES:
			case ATCommands.DIO10_CONFIGURATION:
			case ATCommands.DIO11_CONFIGURATION:
			case ATCommands.DIO12_CONFIGURATION:
			case ATCommands.DOUT:
			case ATCommands.DIN:
			case ATCommands.PULL_DIRECTION:
			case ATCommands.POWER_LEVEL:
			case ATCommands.PULL_UP_RESISTOR:
			case ATCommands.PACKETIZATION_TIMEOUT:
			case ATCommands.STOP_BITS:
			case ATCommands.SERIAL_NUMBER_HIGH:
			case ATCommands.SERIAL_NUMBER_LOW:
			case ATCommands.SLEEP_OPTIONS:
			case ATCommands.SLEEP_PERIOD:
			case ATCommands.WAKE_TIME:
			case ATCommands.TCP_TIMEOUT:
			case ATCommands.FIRMWARE_VERSION:
			case ATCommands.WAKE_HOST:
				return toInt(parameterBytes);

			// signed short
			case ATCommands.TEMPERATURE:
				return ByteBuffer.wrap(parameterBytes).getShort();

			default:
				// no conversion known, convert to ASCII and hope for the best.
				return toASCII(parameterBytes);
			}
		*/
		} catch (Exception e) {
			// shouldn't ever happen
			assert(false);
			e.printStackTrace();
			// just return the byte array object
			return v;
		}
	}
	
	/**
	 * Convert a series of bytes to an int, reading the bytes as a binary value, MSB first. 
	 * @param parameterBytes
	 * @return
	 */
	protected static Integer toInt(byte[] parameterBytes) {
		int acc = 0;
		for (int i = 0; i < parameterBytes.length; i++) {
			acc = acc << 8;
			int upcast = parameterBytes[i] & 0xFF; 
			acc += upcast;
		}
		return acc;
	}

	public static String toASCII(byte[] bytes) {
		try {
			return new String(bytes, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			// can't happen.
			assert(false);
			return null;
		}
	}
	
	/**
	 * @return Returns a formatted string that represents the information in the parameter.
	 * An attempt is made at formatting the data so that it is most useful. Sometimes there are units.
	 * @throws IllegalArgumentException if the parameter can't be represented in this data type.
	 */
	public static String getParameterString(String atCommand, byte[] parameterBytes) {
		Object v = getParameterObject(atCommand, parameterBytes);
		
		// the following logic is generated by a script. You can edit it by hand, but it will hurt.

		if (atCommand.equals(ATCommand.SERIAL_COMM_SERVICE_PORT)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.DEVICE_TYPE_IDENTIFIER)) return String.format("0x%08X", v); 
		if (atCommand.equals(ATCommand.DESTINATION_ENDPOINT)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.DESTINATION_ADDRESS_LOW)) return String.format("%s", ((Inet4Address)v).getHostAddress()); 
		if (atCommand.equals(ATCommand.GATEWAY_IP_ADDRESS)) return String.format("%s", ((Inet4Address)v).getHostAddress()); 
		if (atCommand.equals(ATCommand.IP_ADDRESS_MASK)) return String.format("%s", ((Inet4Address)v).getHostAddress()); 
		if (atCommand.equals(ATCommand.NETWORK_ADDRESS)) return String.format("%s", ((Inet4Address)v).getHostAddress()); 
		if (atCommand.equals(ATCommand.NODE_IDENTIFIER)) return String.format("\"%s\"", v); 
		if (atCommand.equals(ATCommand.MAX_RF_PAYLOAD_BYTES)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.SERIAL_NUMBER_HIGH)) return String.format("0x%08X", v); 
		if (atCommand.equals(ATCommand.SERIAL_NUMBER_LOW)) return String.format("0x%08X", v); 
		if (atCommand.equals(ATCommand.NETWORK_TYPE)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.BIT_RATE_OF_IBSS_CREATOR)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.CHANNEL)) return String.format("%s", Integer.parseInt(v.toString()) == 0xFF ? "not associated" : v); 
		if (atCommand.equals(ATCommand.ENCRYPTION_ENABLE)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.SSID)) return String.format("\"%s\"", v); 
		if (atCommand.equals(ATCommand.IP_PROTOCOL)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.IP_ADDRESSING_MODE)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.WIFI_SECURITY_KEY)) return String.format("\"%s\"", v); 
		if (atCommand.equals(ATCommand.POWER_LEVEL)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.TCP_TIMEOUT)) return String.format("%.1f sec", Double.parseDouble(v.toString()) * 0.1); 
		if (atCommand.equals(ATCommand.API_ENABLE)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.INTERFACE_DATA_RATE)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.DIO6_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.DIO7_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.FLOW_CONTROL_THRESHOLD)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.SERIAL_PARITY)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.PACKETIZATION_TIMEOUT)) return String.format("%d character times", v); 
		if (atCommand.equals(ATCommand.STOP_BITS)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.IO_DIGITAL_CHANGE_DETECTION)) return String.format("%s", v); 
		if (atCommand.equals(ATCommand.SAMPLE_FROM_SLEEP_RATE)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.IO_SAMPLE_RATE)) return String.format(Integer.parseInt(v.toString()) == 0 ? "no sampling" : "%f Hz", 1000 / Double.parseDouble(v.toString())); 
		if (atCommand.equals(ATCommand.FORCE_SAMPLE)) return String.format("null"); 
		if (atCommand.equals(ATCommand.PWM0_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.DIO10_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.DIO11_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.DIO12_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.DIO13_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.DOUT)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.DIN)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.AD0_DIO0_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.AD1_DIO1_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.AD2_DIO2_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.AD3_DIO3_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.DIO4_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.DIO5_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.ANALOG_VOLTAGE_REFERENCE)) return String.format("%.2f V", (Double.parseDouble(v.toString()) + 1) * 1.25); 
		if (atCommand.equals(ATCommand.DIO8_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.DIO9_CONFIGURATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.ASSOC_LED_BLINK_TIME)) return String.format("%d ms", Integer.parseInt(v.toString()) == 0 ? 250 : Integer.parseInt(v.toString()) * 10); 
		if (atCommand.equals(ATCommand.PWM0_DUTY_CYCLE)) return String.format("%f%%", Integer.parseInt(v.toString()) / (double)0x3FF); 
		if (atCommand.equals(ATCommand.PWM1_DUTY_CYCLE)) return String.format("%f%%", Integer.parseInt(v.toString()) / (double)0x3FF); 
		if (atCommand.equals(ATCommand.PULL_DIRECTION)) return String.format("%s", v); 
		if (atCommand.equals(ATCommand.PULL_UP_RESISTOR)) return String.format("%s", v); 
		if (atCommand.equals(ATCommand.SUPPLY_VOLTAGE)) return String.format("%.3f V", Double.parseDouble(v.toString()) * 0.001); 
		if (atCommand.equals(ATCommand.ASSOCIATION_INDICATION)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.ACTIVE_SCAN)) return String.format("null"); 
		if (atCommand.equals(ATCommand.CONFIGURATION_CODE)) return String.format("0x%04X", v); 
		if (atCommand.equals(ATCommand.RSSI)) return String.format("%d dBm", Integer.parseInt(v.toString()) * (-1)); 
		if (atCommand.equals(ATCommand.HARDWARE_VERSION)) return String.format("0x%04X", v); 
		if (atCommand.equals(ATCommand.TEMPERATURE)) return String.format("%d C", v); 
		if (atCommand.equals(ATCommand.FIRMWARE_VERSION)) return String.format("0x%X", v); 
		if (atCommand.equals(ATCommand.COMMAND_MODE_CHARACTER)) return String.format("%s", v); 
		if (atCommand.equals(ATCommand.EXIT_COMMAND_MODE)) return String.format("null"); 
		if (atCommand.equals(ATCommand.COMMAND_MODE_TIMEOUT)) return String.format("%.1f sec", Double.parseDouble(v.toString()) * 0.1); 
		if (atCommand.equals(ATCommand.GUARD_TIMES)) return String.format("%.3f", Double.parseDouble(v.toString()) * 0.001); 
		if (atCommand.equals(ATCommand.SLEEP_MODE)) return String.format("%d", v); 
		if (atCommand.equals(ATCommand.SLEEP_OPTIONS)) return String.format("%s", v); 
		if (atCommand.equals(ATCommand.SLEEP_PERIOD)) return String.format("%.3f sec", Double.parseDouble(v.toString()) * 0.01); 
		if (atCommand.equals(ATCommand.WAKE_TIME)) return String.format("%.3f sec", Double.parseDouble(v.toString()) * 0.001); 
		if (atCommand.equals(ATCommand.WAKE_HOST)) return String.format("%d ms", v); 
		if (atCommand.equals(ATCommand.APPLY_CHANGES)) return String.format("null"); 
		if (atCommand.equals(ATCommand.SOFTWARE_RESET)) return String.format("null"); 
		if (atCommand.equals(ATCommand.NETWORK_RESET)) return String.format("null"); 
		if (atCommand.equals(ATCommand.RESTORE_DEFAULTS)) return String.format("null"); 
		if (atCommand.equals(ATCommand.WRITE)) return String.format("null"); 


		// no special formatting
		return v.toString();
	}
}
