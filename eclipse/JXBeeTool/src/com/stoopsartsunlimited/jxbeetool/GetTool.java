package com.stoopsartsunlimited.jxbeetool;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;

import com.stoopsartsunlimited.jxbeelib.ATCommand;
import com.stoopsartsunlimited.jxbeelib.ATCommandHelper;
import com.stoopsartsunlimited.jxbeelib.XBeeClient;

/**
 * Provides functionality to retrieve register data from an XBee module.
 *  
 * @author Michael
 *
 */
public class GetTool extends Tool {

	private String ALL = "C0,DD,DE,DL,GW,MK,MY,NI,NP,SH,SL,AH,BR,CH,EE,ID,IP,MA,PK,PL,TM,AP,BD,D6,D7,FT,NB,RO,SB,IC,IF,IR,P0,P0,P1,P2,P3,P3,P4,D0,D1,D2,D3,D4,D5,AV,D8,D9,LT,M0,M1,PD,PR,%V,AI,CK,DB,HV,TP,VR,CC,CT,GT,SM,SO,SP,ST,WH";
	private String NO_HEADER = "nh";
	private String NO_TIMESTAMP = "nt";
	private String HEX = "x";
	private String SEPARATOR = "s";
	private String NUMBER = "n";
	private String DELAY = "d";
	private String COMMANDS = "c";

	public GetTool(String[] args) {
		super(args);
		setToolName("get");
		setDescription("asks an XBee device for information");
		options.addOption(NO_HEADER, "no-header", false, "don't print a header line");
		options.addOption(NO_TIMESTAMP, "no-timestamp", false, "don't print a timestamp on each line");
		options.addOption(HEX, "hex", false, "output raw bytes in hex instead of friendly, formatted data");
		options.addOption(SEPARATOR, "separator", true, "specify the separator to use between columns in the output");
		options.addOption(NUMBER, "number-of-samples", true, "number of samples to take");
		options.addOption(DELAY, "delay", true, "delay (in ms) between samples. *sample rate not guaranteed.");
		options.addOption(COMMANDS, "commands", true, "comma-separated list of the AT commands to be polled. Special value \"ALL\" is equivalent to all documented AT commands.");
	}
	
	public void main(String[] args) throws Throwable {
		super.main(args);
		
		// get commands
		String[] commands = null;
		if (cmd.hasOption(COMMANDS)) {
			if (cmd.getOptionValue(COMMANDS).equals("ALL")) {
				commands = ALL.split(",");
			} else {
				commands = cmd.getOptionValue(COMMANDS).split(",");
			}
			for (String command : commands) {
				if (!isCommandOK(command)) {
					System.err.println("the AT command \"" + command + "\" is not allowable. See your device's documentation for available commands.");
					printHelp();
					throw new InterruptedException();
				}
			}
		} else {
			System.err.println("an AT command is required.");
			printHelp();
			throw new InterruptedException();
		}

		
		// get output separator
		String separator = ",";
		if (cmd.hasOption(SEPARATOR)) {
			separator = cmd.getOptionValue(SEPARATOR);
		}
					
		// get number of samples
		int numSamples = 1;
		if (cmd.hasOption(NUMBER)) {
			numSamples = Integer.parseInt(cmd.getOptionValue(NUMBER));
		}
		
		// get delay
		int delay = 1000;
		if (cmd.hasOption(DELAY)) {
			delay = Integer.parseInt(cmd.getOptionValue(DELAY));
		}
		
		
		// output header
		if (!cmd.hasOption(NO_HEADER)) {
			if (!cmd.hasOption(NO_TIMESTAMP)) {
				System.out.print("TIMESTAMP" + separator);
			}
			System.out.println(StringUtils.join(commands, separator));
		}
		
		
		// open network connection
		XBeeClient client = new XBeeClient(host);
		printlnIfVerbose("remote host resolved: " + host);
		client.setReadTimeout(50);


		// sample and output
		for (int i = 0; i < numSamples; i++) {
			LinkedList<String> values = new LinkedList<String>();

			// sample
			if (!cmd.hasOption(NO_TIMESTAMP)) {
				if (cmd.hasOption(HEX)) {
					values.add(String.format("%x", new Date().getTime()));
				} else {
					values.add(new Date().toString() + separator);
				}
			}
			for (String command : commands) {
				client.sendCommandAndWait(command, true, null);
				byte[] state = client.getState(command);
				if (cmd.hasOption(HEX)) {
					values.add(new BigInteger(1, state).toString(16));
				} else {
					values.add(ATCommandHelper.decodeToString(command, state));
				}
			}
			
			// output
			System.out.println(StringUtils.join(values, separator));
			
			// delay
			Thread.sleep(delay);

		}
		
	}
	
	private boolean isCommandOK(String command) {
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
