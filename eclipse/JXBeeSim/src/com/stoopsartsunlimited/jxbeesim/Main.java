package com.stoopsartsunlimited.jxbeesim;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import com.stoopsartsunlimited.jxbeelib.ATCommand;

public class Main {

	static final ModuleSim[] moduleSims = null;
	/**
	 * @param args
	 * @throws ParseException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws ParseException, UnknownHostException {
		
		/*
		 * Simulates a Digi XBee Wi-Fi module.
		 * 
		 * Create any number of simulators
		 * 
		 * listen on specific address
		 * listen on all addresses
		 * 
		 */
		Options options = new Options();
		options.addOption("l", true, "listen at the given address");

		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);
		
		// allocate simulators
		ModuleConfig config = new ModuleConfig();
		config.setInetAddress(InetAddress.getByName(cmd.hasOption("l") ? cmd.getOptionValue("l") : "0.0.0.0"));
		config.setStartingState(ATCommand.SERIAL_NUMBER_HIGH, (int)(Math.random() * Integer.MAX_VALUE));
		config.setStartingState(ATCommand.SERIAL_NUMBER_LOW, (int)(Math.random() * Integer.MAX_VALUE));
		ModuleSim sim = new ModuleSim(config);

		sim.run();
		
	}

}
