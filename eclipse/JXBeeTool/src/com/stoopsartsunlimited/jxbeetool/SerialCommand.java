package com.stoopsartsunlimited.jxbeetool;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import com.stoopsartsunlimited.jxbeelib.XBeeClient;
import com.stoopsartsunlimited.jxbeelib.XBeeException;

public class SerialCommand {

	static final String toolName = "serial";
	
	static String host;
	static XBeeClient client;

	static boolean useVerbose = true;

	// send data as a block or streaming?
	static boolean sendAsBlock = true;
	// block-style output methods
	static boolean useInputFile = false;
	static String inputFilePath = null;
	// stream-style output methods
	static boolean useStdin = false;
	
	static final String USAGE = "Usage:\n" +
			"  " + JXBeeTool.programName + " " + toolName + " [OPTIONS] HOSTNAME\n" +
			"OPTIONS:\n" +
			"  -h        view this help message\n" +
			"  -ifPATH   send the file at PATH\n" +
			"  -ic       send data from the console stdin (CTRL+D to stop, flushes at the crlf. Sends the crlf.)\n" +
			"HOSTNAME:\n" +
			"  The dotted IPV4 address or DNS name of the remote device you want to poll.";	
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
						} else if (arg.startsWith("-if")) {
							useInputFile = true;
							sendAsBlock = true;
							inputFilePath = arg.substring(3);
							if (inputFilePath.length() == 0) {
								System.err.println("The option for \"input file\" was given, but the PATH is empty.");
								System.err.println(USAGE);
								return;
							}
						} else if (arg.equals("-ic")) {
							useStdin = true;
							sendAsBlock = false;
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
			} catch (Exception e) {
				System.err.println("error while processing arguments");
				System.err.println(USAGE);
				return;
			}
			
			client = new XBeeClient((Inet4Address)Inet4Address.getByName(host));
			printlnIfVerbose("remote host resolved: " + (Inet4Address)Inet4Address.getByName(host));
			client.setReadTimeout(50);
			
			if (sendAsBlock) {
				// use block io
				byte[] outboundData;
				if (useInputFile) {
					printlnIfVerbose("reading input data...");
					outboundData = readAllBytes(inputFilePath);
				} else {
					printlnIfVerbose("no input file specified. Sending ASCII string \"test\" instead");
					outboundData = new byte[]{ 't','e','s','t' };
				}
				
				printlnIfVerbose("sending data as a block...");
				client.sendSerialData(outboundData);
				printlnIfVerbose(String.format("sent %d bytes", outboundData.length));
				printlnIfVerbose("delivery confirmation is not currently supported");
			} else {
				// use stream io
				printlnIfVerbose("sending data as a stream...");
				BufferedInputStream inputStream = null;
				if (useStdin) {
					inputStream = new BufferedInputStream(System.in);
				} else {
					System.err.println("can't figure out what stream to use" + host);
					return;
				}
				try {
					byte[] outboundData = new byte[1398]; // documentation says that max serial packet payload is 1398
					while (true) {
						int bytesRead = inputStream.read(outboundData);
						if (bytesRead < 0) {
							// eof
							throw new IOException();
						} else if (bytesRead == 0) {
							// nothing to send
						} else {
							// bytesRead > 0
							client.sendSerialData(outboundData, 0, bytesRead);
							printlnIfVerbose(String.format("sent %d bytes", bytesRead));
						}
					}
				} catch (Exception e) {
					// just stop reading and finish
				}
			}
			
		} catch (UnknownHostException e) {
			System.err.println("unknown host: " + host);
			return;
		} catch (XBeeException e) {
			System.err.println(e);
			return;
		} catch (IOException e) {
			System.err.println(e);
			return;
		}
	}
	
	private static void printlnIfVerbose(String string) {
		if (useVerbose) {
			System.out.println(string);
		}
	}
	
	private static byte[] readAllBytes(String path) throws IOException{
		File file = new File(path);
		byte[] result = new byte[(int)file.length()];
		InputStream input = null;
		try {
			int totalBytesRead = 0;
			input = new BufferedInputStream(new FileInputStream(file));
			while(totalBytesRead < result.length){
				int bytesRemaining = result.length - totalBytesRead;
				int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
				if (bytesRead > 0){
					totalBytesRead = totalBytesRead + bytesRead;
				}
			}
		}
		finally {
			try {
				input.close();
			} catch (Exception e) {
				// ignore
			}
		}
		return result;
	}
}
