package com.stoopsartsunlimited.jxbeetool;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;

import com.stoopsartsunlimited.jxbeelib.ATCommandHelper;
import com.stoopsartsunlimited.jxbeelib.ATCommands;
import com.stoopsartsunlimited.jxbeelib.XBeeClient;
import com.stoopsartsunlimited.jxbeelib.XBeeException;

public class GetCommand {

	static final String toolName = "get";
	
	static String host;
	static XBeeClient client;

	static boolean useHeader = true;
	static boolean useDate = true;
	static boolean useFormattedStrings = true;
	static String separator = ",";
	static int numSamples = 1;
	static int delay = 1000;

	static String[] atCommands;
	static final String atALL = "C0,DD,DE,DL,GW,MK,MY,NI,NP,SH,SL,AH,BR,CH,EE,ID,IP,MA,PK,PL,TM,AP,BD,D6,D7,FT,NB,RO,SB,IC,IF,IR,P0,P0,P1,P2,P3,P3,P4,D0,D1,D2,D3,D4,D5,AV,D8,D9,LT,M0,M1,PD,PR,%V,AI,CK,DB,HV,TP,VR,CC,CT,GT,SM,SO,SP,ST,WH";

	static final String USAGE = "Usage:\n" +
			"  " + JXBeeTool.programName + " " + toolName + " [OPTIONS] HOSTNAME ATCOMMANDS\n" +
			"OPTIONS:\n" +
			"  -h        view this help message\n" +
			"  -oh       suppress output header in first line\n" +
			"  -ot       suppress timestamp on each sample\n" +
			"  -or       output raw bytes as hex string instead of friendly, formatted data\n" +
			"  -osSEP    sets the output field separator\n" +
			"  -nNUMBER  number of samples to take\n" +
			"  -dDELAY   delay (in ms) between samples. Sample rate not guaranteed.\n" +
			"HOSTNAME:\n" +
			"  The dotted IPV4 address or DNS name of the remote device you want to poll.\n" +
			"ATCOMMANDS\n" +
			"  A comma-separated list of the two-character AT commands to be polled. ex: NI,TP,%V\n" +
			"  The special value ALL is equivalent to all of the documented AT commands.";

	
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
						} else if (arg.equals("-oh")) {
							useHeader = false;
						} else if (arg.equals("-ot")) {
							useDate = false;
						} else if (arg.equals("-or")) {
							useFormattedStrings = false;
						} else if (arg.startsWith("-os")) {
							separator = arg.substring(3);
						} else if (arg.startsWith("-n")) {
							numSamples = Integer.parseInt(arg.substring(2));
						} else if (arg.startsWith("-d")) {
							delay = Integer.parseInt(arg.substring(2));
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
					if (args[i].equals("ALL")) {
						args[i] = atALL;
					}
					atCommands = args[i++].split(",");
					for (String command : atCommands) {
						if (!isCommandOK(command)) {
							System.err.println("the AT command \"" + command + "\" is not allowable. See your device's documentation for available commands.");
							System.err.println(USAGE);
							return;
						}
					}
				} catch (IllegalArgumentException e) {
					System.err.println(e.getMessage());
					return;
				} catch (Exception e) {
					System.err.println("error while looking for the ATCOMMANDS argument: " + e);
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
			if (useHeader) {
				if (useDate) {
					System.out.print("DATE" + separator);
				}
				System.out.println(join(atCommands, separator));
			}
			
			for (i = 0; i < numSamples; i++) {
				String[] atValues = pollDeviceState(atCommands, useFormattedStrings);
				if (useDate) {
					if (useFormattedStrings) {
						System.out.print(new Date().toString() + separator);
					} else {
						System.out.print(String.format("%d%s", new Date().getTime(), separator));
					}
				}
				System.out.println(join(atValues, separator));
				Thread.sleep(delay);
			}
			
		} catch (UnknownHostException e) {
			System.err.println("unknown host: " + host);
			return;
		} catch (XBeeException e) {
			System.err.println(e);
			return;
		} catch (InterruptedException e) {
			// just stop
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

	private static String join(String[] array, String separator) {
		StringBuilder sb = new StringBuilder();
		for (String item : array) {
			sb.append(item);
			sb.append(separator);
		}
		
		// pop off the trailing separator
		sb.delete(sb.length() - separator.length(), sb.length());
		
		return sb.toString();
	}
	
	protected static String[] pollDeviceState(String[] atCommands, boolean useFormattedStrings) throws XBeeException {
		String[] r = new String[atCommands.length];
		for (int i = 0; i < atCommands.length; i++) {
			String command = atCommands[i];
			client.sendCommandAndWait(command, true, null);
			if (client.containsState(atCommands[i])) {
				byte[] state = client.getState(command);
				if (useFormattedStrings) {
					r[i] = ATCommandHelper.getParameterString(command, state);
				} else {
					r[i] = new BigInteger(1, state).toString(16);
				}
			}
		}
		return r;
	}
}
