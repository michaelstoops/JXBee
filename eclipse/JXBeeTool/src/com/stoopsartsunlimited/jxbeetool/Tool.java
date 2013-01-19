package com.stoopsartsunlimited.jxbeetool;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;

public abstract class Tool {
	
	protected static final String HELP = "h";
	protected static final String REMOTE = "r";
	protected static final String VERBOSE = "v";

	private String toolName = null;
	private String description = null;
	protected Options options = new Options();
	protected CommandLine cmd = null;
	protected InetAddress host = null;


	public Tool(String[] args) {
		this(args, null);
	}
	public Tool(String[] args, String[] waiveOptions) {
		if (waiveOptions != null
				&& !ArrayUtils.contains(waiveOptions, HELP)) {
			options.addOption(HELP, "help", false, "view usage information");
		}
		if (waiveOptions != null
				&& !ArrayUtils.contains(waiveOptions, REMOTE)) {
			options.addOption(REMOTE, "remote-host", true, "the remote XBee device's hostname or IP address");
		}		
		if (waiveOptions != null
				&& !ArrayUtils.contains(waiveOptions, VERBOSE)) {
			options.addOption(VERBOSE, "verbose", false, "verbose output");
		}
	}
	
	public void main(String[] args) throws Throwable {
		try {
 			parse(args);
	
			// check for help query
			if (cmd.hasOption(HELP)) {
				printHelp();
				throw new InterruptedException();
			}
			
			// get target host
			if (options.hasOption(REMOTE)) {
				if (cmd.hasOption(REMOTE)) {
					host = InetAddress.getByName(cmd.getOptionValue(REMOTE));
				} else {
					System.err.println("error: remote XBee device not specified.");
					printHelp();
					throw new InterruptedException();
				}
			}
			
		} catch (UnknownHostException e) {
			System.err.println("unknown host: " + e.getMessage());
			throw new InterruptedException();
		}
	}
	
	
	
	
	
	
	
	
	
	
	// inheritable helper methods
	
	protected void parse(String args[]) {
		try {
			cmd = new GnuParser().parse(options, args);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	protected void printlnIfVerbose(String string) {
		if (cmd.hasOption(VERBOSE)) {
			System.out.println(string);
		}
	}
	
	protected void printHelp() {
		new HelpFormatter().printHelp(JXBeeTool.PROGRAM_NAME + " " + getToolName(), options);
	}

	
	
	
	
	
	
	
	
	
	// accessors
	
	public String getToolName() {
		return toolName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

}
