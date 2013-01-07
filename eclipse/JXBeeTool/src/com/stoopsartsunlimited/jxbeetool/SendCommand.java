package com.stoopsartsunlimited.jxbeetool;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import com.stoopsartsunlimited.jxbeelib.ATCommandHelper;
import com.stoopsartsunlimited.jxbeelib.ATCommands;
import com.stoopsartsunlimited.jxbeelib.XBeeClient;
import com.stoopsartsunlimited.jxbeelib.XBeeException;

public class SendCommand {

	static final String toolName = "send";
	
	static String host;
	static XBeeClient client;

	static String atCommand;
	static String dataArg;
	static boolean useHexParameter = false;
	static boolean useHexOutput = false;
	static byte[] parameter;

	static final String USAGE = "Usage:\n" +
			"  " + JXBeeTool.programName + " " + toolName + " [OPTIONS] HOSTNAME ATCOMMAND [DATA]\n" +
			"OPTIONS:\n" +
			"  -h        view this help message\n" +
			"  -ix       the DATA is encoded as hex string. Useful for sending unusual values.\n" +
			"  -ox       if any data is returned by the device, print it as a hex string.\n" +
			"HOSTNAME:\n" +
			"  The dotted IPV4 address or DNS name of the remote device you want to poll.\n" +
			"ATCOMMAND\n" +
			"  The two-character AT commands to be set.";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			int i;
			
			// process arguments
			try {
				for (i = 0; i < args.length; i++) {
					String arg = args[i];
					try {
						if (arg.equals("-h")) {
							System.out.println(USAGE);
							return;
						} else if (arg.equals("-ix")) {
							useHexParameter = true;
						} else {
							break;
						}
					} catch (Exception e) {
						System.err.println("error processing argument: " + arg);
						System.err.println(USAGE);
						return;
					}
				}
				try {
					host = args[i++];
				} catch (Exception e) {
					System.err.println("error while looking for the HOSTNAME argument");
					System.err.println(USAGE);
					return;
				}
				try {
					atCommand = args[i++];
					if (!isCommandOK(atCommand)) {
						System.err.println("the AT command \"" + atCommand + "\" is not allowable. See your device's documentation for available commands.");
						System.err.println(USAGE);
						return;
					}
				} catch (IllegalArgumentException e) {
					System.err.println(e.getMessage());
					return;
				} catch (Exception e) {
					System.err.println("error while looking for the ATCOMMANDS argument: " + e);
					System.err.println(USAGE);
					return;
				}
				try {
					// DATA isn't required -- check first
					if (i < args.length) {
						dataArg = args[i++];
					}
				} catch (IllegalArgumentException e) {
					System.err.println(e.getMessage());
					return;
				} catch (Exception e) {
					System.err.println("error while reading the DATA argument: " + e);
					System.err.println(USAGE);
					return;
				}
			} catch (Exception e) {
				System.err.println("error while processing arguments");
				System.err.println(USAGE);
				return;
			}
			
			client = new XBeeClient((Inet4Address)Inet4Address.getByName(host));
			client.setReadTimeout(50);
			
			client.sendCommandAndWait(atCommand, false, parameter);
			if (client.containsState(atCommand)) {
				byte[] state = client.getState(atCommand);
				if (useHexOutput) {
					System.out.println(new BigInteger(1, state).toString(16));
				} else {
					System.out.println(ATCommandHelper.getParameterString(atCommand, state));
				}
			}
			
		} catch (UnknownHostException e) {
			System.err.println("unknown host: " + host);
			return;
		} catch (XBeeException e) {
			System.err.println(e);
			return;
		}
		
	}
	
	private static boolean isCommandOK(String command) {
		if (command.length() != 2) {
			throw new IllegalArgumentException();
		}
		if (
				// auto-generated code. See "AT Commands.xlsx"
				!command.equals(ATCommands.SERIAL_COMM_SERVICE_PORT)
				&& !command.equals(ATCommands.DEVICE_TYPE_IDENTIFIER)
				&& !command.equals(ATCommands.DESTINATION_ENDPOINT)
				&& !command.equals(ATCommands.DESTINATION_ADDRESS_LOW)
				&& !command.equals(ATCommands.GATEWAY_IP_ADDRESS)
				&& !command.equals(ATCommands.IP_ADDRESS_MASK)
				&& !command.equals(ATCommands.NETWORK_ADDRESS)
				&& !command.equals(ATCommands.NODE_IDENTIFIER)
				&& !command.equals(ATCommands.MAX_RF_PAYLOAD_BYTES)
				&& !command.equals(ATCommands.SERIAL_NUMBER_HIGH)
				&& !command.equals(ATCommands.SERIAL_NUMBER_LOW)
				&& !command.equals(ATCommands.NETWORK_TYPE)
				&& !command.equals(ATCommands.BIT_RATE_OF_IBSS_CREATOR)
				&& !command.equals(ATCommands.CHANNEL)
				&& !command.equals(ATCommands.ENCRYPTION_ENABLE)
				&& !command.equals(ATCommands.SSID)
				&& !command.equals(ATCommands.IP_PROTOCOL)
				&& !command.equals(ATCommands.IP_ADDRESSING_MODE)
				&& !command.equals(ATCommands.WIFI_SECURITY_KEY)
				&& !command.equals(ATCommands.POWER_LEVEL)
				&& !command.equals(ATCommands.TCP_TIMEOUT)
				&& !command.equals(ATCommands.API_ENABLE)
				&& !command.equals(ATCommands.INTERFACE_DATA_RATE)
				&& !command.equals(ATCommands.DIO6_CONFIGURATION)
				&& !command.equals(ATCommands.DIO7_CONFIGURATION)
				&& !command.equals(ATCommands.FLOW_CONTROL_THRESHOLD)
				&& !command.equals(ATCommands.SERIAL_PARITY)
				&& !command.equals(ATCommands.PACKETIZATION_TIMEOUT)
				&& !command.equals(ATCommands.STOP_BITS)
				&& !command.equals(ATCommands.IO_DIGITAL_CHANGE_DETECTION)
				&& !command.equals(ATCommands.SAMPLE_FROM_SLEEP_RATE)
				&& !command.equals(ATCommands.IO_SAMPLE_RATE)
				&& !command.equals(ATCommands.PWM0_CONFIGURATION)
				&& !command.equals(ATCommands.DIO10_CONFIGURATION)
				&& !command.equals(ATCommands.DIO11_CONFIGURATION)
				&& !command.equals(ATCommands.DIO12_CONFIGURATION)
				&& !command.equals(ATCommands.DIO13_CONFIGURATION)
				&& !command.equals(ATCommands.DOUT)
				&& !command.equals(ATCommands.DIN)
				&& !command.equals(ATCommands.AD0_DIO0_CONFIGURATION)
				&& !command.equals(ATCommands.AD1_DIO1_CONFIGURATION)
				&& !command.equals(ATCommands.AD2_DIO2_CONFIGURATION)
				&& !command.equals(ATCommands.AD3_DIO3_CONFIGURATION)
				&& !command.equals(ATCommands.DIO4_CONFIGURATION)
				&& !command.equals(ATCommands.DIO5_CONFIGURATION)
				&& !command.equals(ATCommands.ANALOG_VOLTAGE_REFERENCE)
				&& !command.equals(ATCommands.DIO8_CONFIGURATION)
				&& !command.equals(ATCommands.DIO9_CONFIGURATION)
				&& !command.equals(ATCommands.ASSOC_LED_BLINK_TIME)
				&& !command.equals(ATCommands.PWM0_DUTY_CYCLE)
				&& !command.equals(ATCommands.PWM1_DUTY_CYCLE)
				&& !command.equals(ATCommands.PULL_DIRECTION)
				&& !command.equals(ATCommands.PULL_UP_RESISTOR)
				&& !command.equals(ATCommands.SUPPLY_VOLTAGE)
				&& !command.equals(ATCommands.ASSOCIATION_INDICATION)
				&& !command.equals(ATCommands.CONFIGURATION_CODE)
				&& !command.equals(ATCommands.RSSI)
				&& !command.equals(ATCommands.HARDWARE_VERSION)
				&& !command.equals(ATCommands.TEMPERATURE)
				&& !command.equals(ATCommands.FIRMWARE_VERSION)
				&& !command.equals(ATCommands.COMMAND_MODE_CHARACTER)
				&& !command.equals(ATCommands.COMMAND_MODE_TIMEOUT)
				&& !command.equals(ATCommands.GUARD_TIMES)
				&& !command.equals(ATCommands.SLEEP_MODE)
				&& !command.equals(ATCommands.SLEEP_OPTIONS)
				&& !command.equals(ATCommands.SLEEP_PERIOD)
				&& !command.equals(ATCommands.WAKE_TIME)
				&& !command.equals(ATCommands.WAKE_HOST)) {
			throw new IllegalArgumentException("error: " + command + "is not a supported command.");
		}
		// no problem found
		return true;
	}
}
