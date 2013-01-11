package com.stoopsartsunlimited.jxbeetool;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import com.stoopsartsunlimited.jxbeelib.ATCommandHelper;
import com.stoopsartsunlimited.jxbeelib.ATCommand;
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
				!command.equals(ATCommand.SERIAL_COMM_SERVICE_PORT)
				&& !command.equals(ATCommand.DEVICE_TYPE_IDENTIFIER)
				&& !command.equals(ATCommand.DESTINATION_ENDPOINT)
				&& !command.equals(ATCommand.DESTINATION_ADDRESS_LOW)
				&& !command.equals(ATCommand.GATEWAY_IP_ADDRESS)
				&& !command.equals(ATCommand.IP_ADDRESS_MASK)
				&& !command.equals(ATCommand.NETWORK_ADDRESS)
				&& !command.equals(ATCommand.NODE_IDENTIFIER)
				&& !command.equals(ATCommand.MAX_RF_PAYLOAD_BYTES)
				&& !command.equals(ATCommand.SERIAL_NUMBER_HIGH)
				&& !command.equals(ATCommand.SERIAL_NUMBER_LOW)
				&& !command.equals(ATCommand.NETWORK_TYPE)
				&& !command.equals(ATCommand.BIT_RATE_OF_IBSS_CREATOR)
				&& !command.equals(ATCommand.CHANNEL)
				&& !command.equals(ATCommand.ENCRYPTION_ENABLE)
				&& !command.equals(ATCommand.SSID)
				&& !command.equals(ATCommand.IP_PROTOCOL)
				&& !command.equals(ATCommand.IP_ADDRESSING_MODE)
				&& !command.equals(ATCommand.WIFI_SECURITY_KEY)
				&& !command.equals(ATCommand.POWER_LEVEL)
				&& !command.equals(ATCommand.TCP_TIMEOUT)
				&& !command.equals(ATCommand.API_ENABLE)
				&& !command.equals(ATCommand.INTERFACE_DATA_RATE)
				&& !command.equals(ATCommand.DIO6_CONFIGURATION)
				&& !command.equals(ATCommand.DIO7_CONFIGURATION)
				&& !command.equals(ATCommand.FLOW_CONTROL_THRESHOLD)
				&& !command.equals(ATCommand.SERIAL_PARITY)
				&& !command.equals(ATCommand.PACKETIZATION_TIMEOUT)
				&& !command.equals(ATCommand.STOP_BITS)
				&& !command.equals(ATCommand.IO_DIGITAL_CHANGE_DETECTION)
				&& !command.equals(ATCommand.SAMPLE_FROM_SLEEP_RATE)
				&& !command.equals(ATCommand.IO_SAMPLE_RATE)
				&& !command.equals(ATCommand.PWM0_CONFIGURATION)
				&& !command.equals(ATCommand.DIO10_CONFIGURATION)
				&& !command.equals(ATCommand.DIO11_CONFIGURATION)
				&& !command.equals(ATCommand.DIO12_CONFIGURATION)
				&& !command.equals(ATCommand.DIO13_CONFIGURATION)
				&& !command.equals(ATCommand.DOUT)
				&& !command.equals(ATCommand.DIN)
				&& !command.equals(ATCommand.AD0_DIO0_CONFIGURATION)
				&& !command.equals(ATCommand.AD1_DIO1_CONFIGURATION)
				&& !command.equals(ATCommand.AD2_DIO2_CONFIGURATION)
				&& !command.equals(ATCommand.AD3_DIO3_CONFIGURATION)
				&& !command.equals(ATCommand.DIO4_CONFIGURATION)
				&& !command.equals(ATCommand.DIO5_CONFIGURATION)
				&& !command.equals(ATCommand.ANALOG_VOLTAGE_REFERENCE)
				&& !command.equals(ATCommand.DIO8_CONFIGURATION)
				&& !command.equals(ATCommand.DIO9_CONFIGURATION)
				&& !command.equals(ATCommand.ASSOC_LED_BLINK_TIME)
				&& !command.equals(ATCommand.PWM0_DUTY_CYCLE)
				&& !command.equals(ATCommand.PWM1_DUTY_CYCLE)
				&& !command.equals(ATCommand.PULL_DIRECTION)
				&& !command.equals(ATCommand.PULL_UP_RESISTOR)
				&& !command.equals(ATCommand.SUPPLY_VOLTAGE)
				&& !command.equals(ATCommand.ASSOCIATION_INDICATION)
				&& !command.equals(ATCommand.CONFIGURATION_CODE)
				&& !command.equals(ATCommand.RSSI)
				&& !command.equals(ATCommand.HARDWARE_VERSION)
				&& !command.equals(ATCommand.TEMPERATURE)
				&& !command.equals(ATCommand.FIRMWARE_VERSION)
				&& !command.equals(ATCommand.COMMAND_MODE_CHARACTER)
				&& !command.equals(ATCommand.COMMAND_MODE_TIMEOUT)
				&& !command.equals(ATCommand.GUARD_TIMES)
				&& !command.equals(ATCommand.SLEEP_MODE)
				&& !command.equals(ATCommand.SLEEP_OPTIONS)
				&& !command.equals(ATCommand.SLEEP_PERIOD)
				&& !command.equals(ATCommand.WAKE_TIME)
				&& !command.equals(ATCommand.WAKE_HOST)) {
			throw new IllegalArgumentException("error: " + command + "is not a supported command.");
		}
		// no problem found
		return true;
	}
}
