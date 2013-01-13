package com.stoopsartsunlimited.jxbeetool;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;

import com.stoopsartsunlimited.jxbeelib.ATCommandHelper;
import com.stoopsartsunlimited.jxbeelib.ATCommand;
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
					r[i] = ATCommandHelper.decodeToString(command, state);
				} else {
					r[i] = new BigInteger(1, state).toString(16);
				}
			}
		}
		return r;
	}
}
