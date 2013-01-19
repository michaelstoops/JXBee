package com.stoopsartsunlimited.jxbeetool;

import java.math.BigInteger;

import com.stoopsartsunlimited.jxbeelib.ATCommand;
import com.stoopsartsunlimited.jxbeelib.ATCommandHelper;
import com.stoopsartsunlimited.jxbeelib.XBeeClient;

/**
 * Provides functionality to send an AT command to an XBee module.
 * 
 * @author Michael
 *
 */
public class SendTool extends Tool {

	private String COMMAND = "c";
	private String HEX = "x";
	private String DATA = "d";

	public SendTool(String[] args) {
		super(args);
		setToolName("send");
		setDescription("sends a command to an XBee device");
		options.addOption(COMMAND, "command", true, "the AT command to be executed");
		options.addOption(HEX, "hex", false, "interpret the data option as a hex string. Useful for sending unusual data");
		options.addOption(DATA, "data", true, "delay (in ms) between samples. *sample rate not guaranteed.");
	}

	public void main(String[] args) throws Throwable {
		super.main(args);
		
		// get command
		String command = null;
		if (cmd.hasOption(COMMAND)) {
			command = cmd.getOptionValue(COMMAND);
		} else {
			System.err.println("an AT command is required.");
			printHelp();
			throw new InterruptedException();
		}
		
		// check command
		if (!isCommandOK(command)) {
			System.err.println("the AT command \"" + command + "\" is not allowable. See your device's documentation for available commands.");
			printHelp();
			throw new InterruptedException();
		}

		
		// get data
		byte[] data = null;
		if (cmd.hasOption(DATA)) {
			if (cmd.hasOption(HEX)) {
				data = new BigInteger(cmd.getOptionValue(DATA), 16).toString(16).getBytes();
			} else {
				data = ATCommandHelper.encodeAT(command, ATCommandHelper.parse(command, cmd.getOptionValue(DATA)));
			}
		}

		
		// open network connection
		XBeeClient client = new XBeeClient(host);
		printlnIfVerbose("remote host resolved: " + host);
		client.setReadTimeout(50);

		// send command
		client.sendCommandNoResponse(command, true, data);
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
