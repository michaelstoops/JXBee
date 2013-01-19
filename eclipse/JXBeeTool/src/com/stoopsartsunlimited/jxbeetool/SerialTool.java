package com.stoopsartsunlimited.jxbeetool;

import java.io.FileInputStream;
import java.io.InputStream;

import com.stoopsartsunlimited.jxbeelib.XBeeClient;


/**
 * Provides functionality to send serial data to a an XBee module.
 * 
 * @author Michael
 *
 */
public class SerialTool extends Tool {

	private String INPUT_FILE = "if";
	
	public SerialTool(String[] args) {
		super(args);
		setToolName("serial");
		setDescription("asks an XBee device to send data out its serial port");
		options.addOption(INPUT_FILE, "input-file", true, "send data from the given file. Use - for stdin");
	}

	@Override
	public void main(String[] args) throws Throwable {
		super.main(args);
	
		// get input data
		InputStream inStream;
		if (cmd.hasOption(INPUT_FILE)) {
			if (cmd.getOptionValue(INPUT_FILE) == "-") {
				inStream = System.in;
			} else {
				inStream = new FileInputStream(cmd.getOptionValue(INPUT_FILE));
			}
		} else {
			System.err.println("Error: input stream not specified.");
			printHelp();
			throw new InterruptedException();
		}
		
		
		// open network connection
		XBeeClient client = new XBeeClient(host);
		printlnIfVerbose("remote host resolved: " + host);
		client.setReadTimeout(50);
		
		// read and send data
		printlnIfVerbose("sending data as a stream...");
		byte[] outboundData = new byte[1398]; // documentation says that max serial packet payload is 1398
		int bytesRead = 0;
		while (bytesRead != -1) {
			bytesRead = inStream.read(outboundData);
			if (bytesRead > 0) {
				client.sendSerialData(outboundData, 0, bytesRead);
				printlnIfVerbose(String.format("sent %d bytes", bytesRead));
			}
		}
	}
	
}
