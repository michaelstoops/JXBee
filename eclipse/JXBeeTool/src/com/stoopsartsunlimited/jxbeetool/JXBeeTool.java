package com.stoopsartsunlimited.jxbeetool;

import java.util.Arrays;


public class JXBeeTool {
	
	static String programName = "jxbeetool";

	static String USAGE = "Usage:\n" +
			"  " + JXBeeTool.programName + " COMMAND PARAMS\n" +
			"COMMAND:\n" +
			"  " + GetCommand.toolName + "       asks an XBee device for information\n" +
			"  " + SendCommand.toolName + "       sends a command to an XBee device\n" +
			"  " + SerialCommand.toolName + "       asks an XBee device to send data out its serial port\n" +
			"PARAMS:\n" +
			"  varies by the COMMAND used. Call a command with PARAMS=\"-h\" for help on that command.";

	public static void main(String[] args) {
		
		try {
			// find the requested tool and execute it
			String arg = args[0];
			String[] toolArgs = Arrays.copyOfRange(args, 1, args.length);
			if (arg.equals("-h")) {
				System.out.println(USAGE);
				return;
			} else if (arg.equals(GetCommand.toolName)) {
				GetCommand.main(toolArgs);
				return;
			} else if (arg.equals(SendCommand.toolName)) {
				SendCommand.main(toolArgs);
				return;
			} else if (arg.equals(SerialCommand.toolName)) {
				SerialCommand.main(toolArgs);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
