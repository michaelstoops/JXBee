package com.stoopsartsunlimited.jxbeesim;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import com.stoopsartsunlimited.jxbeelib.ATCommand;
import com.stoopsartsunlimited.jxbeelib.ATCommandHelper;
import com.stoopsartsunlimited.jxbeelib.ip.CommandRequestPacket;
import com.stoopsartsunlimited.jxbeelib.ip.CommandResponsePacket;
import com.stoopsartsunlimited.jxbeelib.ip.CommandResponsePacket.Status;
import com.stoopsartsunlimited.jxbeelib.ip.IOSamplePacket;
import com.stoopsartsunlimited.jxbeelib.ip.Packet;
import com.stoopsartsunlimited.jxbeelib.ip.PacketFactory;
import com.stoopsartsunlimited.jxbeelib.ip.SamplePackets;
import com.stoopsartsunlimited.jxbeelib.ip.SerialDataAckPacket;
import com.stoopsartsunlimited.jxbeelib.ip.SerialDataPacket;

public class ModuleSim extends Thread {

	private static final String threadNamePrefix = "JXBeeSim"; 
	
	private ModuleConfig config = null;
	
	public ModuleSim(ModuleConfig config) {
		super(String.format("%s-%s", threadNamePrefix, config.getInetAddress()));
		this.config = config;
		
		initState();
		
		LinkedList<String> keys = new LinkedList<String>(config.getStartingState().keySet());
		Collections.sort(keys);
		for (String atCommand : keys) {
			state.put(atCommand, config.getStartingState().get(atCommand));
			logStatus(String.format("Set %s to %s", atCommand, ATCommandHelper.toString(atCommand, state.get(atCommand))));
		}
	}
		
	private boolean keepGoing = true;

	DatagramSocket controlSocket = null;
	DatagramSocket serialSocket = null;
	
	public void run() {
		
		// open socket
		try {
			controlSocket = new DatagramSocket(0xBEE, (InetAddress)state.get(ATCommand.NETWORK_ADDRESS));
			controlSocket.setSoTimeout(1);
			logStatus(String.format("Listening for control messages on port %s:%d/UDP", controlSocket.getLocalAddress(), controlSocket.getLocalPort()));
			serialSocket = new DatagramSocket((Integer)state.get(ATCommand.SERIAL_COMM_SERVICE_PORT), (InetAddress)state.get(ATCommand.NETWORK_ADDRESS));
			serialSocket.setSoTimeout(1);
			logStatus(String.format("Listening for serial data on port %s:%d/UDP", serialSocket.getLocalAddress(), serialSocket.getLocalPort()));
		} catch (SocketException e) {
			// that's not good.
			logStatus(String.format("Error while setting up listeners. There may be another program occupying port %s:%d/UDP or %s:%d/UDP", (InetAddress)state.get(ATCommand.NETWORK_ADDRESS), 0xBEE, (InetAddress)state.get(ATCommand.NETWORK_ADDRESS), state.get(ATCommand.SERIAL_COMM_SERVICE_PORT)));
			keepGoing = false;
			e.printStackTrace();
		}

		while (keepGoing) {
			Packet incomingPacket = null;
			DatagramPacket datagramPacket = new DatagramPacket(new byte[config.getUdpBufferSize()], config.getUdpBufferSize());
			// wait for incoming packet
			// loop, check for interrupt
			while (keepGoing
					&& incomingPacket == null) {
				
				// poll the control socket first
				try {
					controlSocket.receive(datagramPacket);
					incomingPacket = PacketFactory.getPacketFromNetworkBytes(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
					logInboundPacket(incomingPacket, controlSocket.getLocalAddress(), controlSocket.getLocalPort(), datagramPacket.getAddress(), datagramPacket.getPort());
					// only allow control packets
					if (incomingPacket instanceof CommandRequestPacket
							|| incomingPacket instanceof CommandResponsePacket
							|| incomingPacket instanceof IOSamplePacket) {
						// yay, valid packet
						break;
					} else {
						// this packet isn't allowed
						logStatus(String.format("Dropping unrecognized packet that came in on the control port."));
						incomingPacket = null;
					}
				} catch (SocketTimeoutException e) {
					// not a problem, ignore
				} catch (Exception e) {
					// this one is a problem
					logStatus(String.format("Error while checking for control messages."));
					e.printStackTrace();
					return;
				}
				// try the serial socket
				try {
					serialSocket.receive(datagramPacket);
					incomingPacket = PacketFactory.getPacketFromNetworkBytes(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
					logInboundPacket(incomingPacket, serialSocket.getLocalAddress(), serialSocket.getLocalPort(), datagramPacket.getAddress(), datagramPacket.getPort());
					// only allow control packets
					if (incomingPacket instanceof SerialDataPacket
							|| incomingPacket instanceof SerialDataPacket) {
						// yay, valid packet
						break;
					} else {
						// this packet isn't allowed
						logStatus(String.format("Dropping unrecognized packet that came in on the serial data port."));
						incomingPacket = null;
					}
				} catch (SocketTimeoutException e) {
					// not a problem, ignore
				} catch (Exception e) {
					// this one is a problem
					logStatus(String.format("Error while checking for serial data."));
					e.printStackTrace();
					return;
				}
			}
			
			// handle packet
			Packet response = handlePacket(incomingPacket);
			
			// send response
			if (response != null) {
				DatagramPacket responseDatagram = new DatagramPacket(response.getBytes(), response.getBytes().length, datagramPacket.getAddress(), datagramPacket.getPort());
				logOutboundPacket(response, controlSocket.getLocalAddress(), controlSocket.getLocalPort(), responseDatagram.getAddress(), responseDatagram.getPort());
				try {
					controlSocket.send(responseDatagram);
				} catch (IOException e) {
					// not good
					keepGoing = false;
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void interrupt() {
		super.interrupt();
		keepGoing = false;
	}
	
	
	
	
	
	
	// module state implementation

	private HashMap<String, Object> state = new HashMap<String, Object>();

	/**
	 * Initializes state for this module to the published defaults. Uses the most meaningful type for the parameter. 
	 */
	private void initState() {
		// Information taken from Digi XBee docs for firmware 102x, DCN: 90002124_F

		// AUTO-GENERATED CODE. DON'T EDIT.
		
		state.put(ATCommand.DEVICE_TYPE_IDENTIFIER, 0x50000);
		state.put(ATCommand.SERIAL_NUMBER_HIGH, (int)0xFFFFFFFF);
		state.put(ATCommand.SERIAL_NUMBER_LOW, (int)0xFFFFFFFF);
		state.put(ATCommand.SERIAL_COMM_SERVICE_PORT, 0x2616);
		state.put(ATCommand.DESTINATION_ENDPOINT, 0x2616);
		state.put(ATCommand.MAX_RF_PAYLOAD_BYTES, 0xFFFF);
		try {
			state.put(ATCommand.DESTINATION_ADDRESS_LOW,
					InetAddress.getByName("255.255.255.255"));
			state.put(ATCommand.GATEWAY_IP_ADDRESS,
					InetAddress.getByName("0.0.0.0"));
			state.put(ATCommand.IP_ADDRESS_MASK,
					InetAddress.getByName("0.0.0.0"));
			InetAddress my = InetAddress.getByName("0.0.0.0");
			state.put(ATCommand.NETWORK_ADDRESS,
					my);
		} catch (UnknownHostException e) {
			throw new IllegalArgumentException();
		}
		state.put(ATCommand.NODE_IDENTIFIER, " ");
		state.put(ATCommand.CHANNEL, 1);
		state.put(ATCommand.TCP_TIMEOUT, 0x0A);
		state.put(ATCommand.NETWORK_TYPE, 2);
		state.put(ATCommand.BIT_RATE_OF_IBSS_CREATOR, 0);
		state.put(ATCommand.ENCRYPTION_ENABLE, 0);
		state.put(ATCommand.IP_PROTOCOL, 0);
		state.put(ATCommand.IP_ADDRESSING_MODE, 0);
		state.put(ATCommand.POWER_LEVEL, 4);
		state.put(ATCommand.SSID, "");
		state.put(ATCommand.WIFI_SECURITY_KEY, "");
		state.put(ATCommand.PACKETIZATION_TIMEOUT, 3);
		state.put(ATCommand.API_ENABLE, 1);
		state.put(ATCommand.INTERFACE_DATA_RATE, 3);
		state.put(ATCommand.DIO6_CONFIGURATION, 0);
		state.put(ATCommand.DIO7_CONFIGURATION, 1);
		state.put(ATCommand.SERIAL_PARITY, 0);
		state.put(ATCommand.STOP_BITS, 0);
		state.put(ATCommand.FLOW_CONTROL_THRESHOLD, 0x7F3);
		state.put(ATCommand.IO_DIGITAL_CHANGE_DETECTION, 0);
		state.put(ATCommand.SAMPLE_FROM_SLEEP_RATE, 1);
		// no value for FORCE_SAMPLE
		state.put(ATCommand.DIO10_CONFIGURATION, 0);
		state.put(ATCommand.DIO11_CONFIGURATION, 0);
		state.put(ATCommand.DIO12_CONFIGURATION, 0);
		state.put(ATCommand.DOUT, 1);
		state.put(ATCommand.DIN, 1);
		state.put(ATCommand.IO_SAMPLE_RATE, 0);
		state.put(ATCommand.AD0_DIO0_CONFIGURATION, 0);
		state.put(ATCommand.AD1_DIO1_CONFIGURATION, 0);
		state.put(ATCommand.AD2_DIO2_CONFIGURATION, 0);
		state.put(ATCommand.AD3_DIO3_CONFIGURATION, 0);
		state.put(ATCommand.DIO4_CONFIGURATION, 0);
		state.put(ATCommand.DIO5_CONFIGURATION, 1);
		state.put(ATCommand.PULL_DIRECTION, 0x7FF);
		state.put(ATCommand.PULL_UP_RESISTOR, 0x7FF);
		state.put(ATCommand.ASSOC_LED_BLINK_TIME, 0);
		state.put(ATCommand.ANALOG_VOLTAGE_REFERENCE, 1);
		state.put(ATCommand.DIO8_CONFIGURATION, 1);
		state.put(ATCommand.DIO9_CONFIGURATION, 1);
		state.put(ATCommand.PWM0_DUTY_CYCLE, 0);
		state.put(ATCommand.PWM1_DUTY_CYCLE, 0);
		state.put(ATCommand.RSSI, 0xFF);
		state.put(ATCommand.ASSOCIATION_INDICATION, 0x23);
		// no value for ACTIVE_SCAN
		state.put(ATCommand.SUPPLY_VOLTAGE, 3100);
		state.put(ATCommand.CONFIGURATION_CODE, 0x98D8);
		state.put(ATCommand.HARDWARE_VERSION, 0x1F47);
		state.put(ATCommand.TEMPERATURE, 37);
		state.put(ATCommand.FIRMWARE_VERSION, 0x102D);
		state.put(ATCommand.SLEEP_OPTIONS, 0x100);
		// no value for EXIT_COMMAND_MODE
		state.put(ATCommand.SLEEP_MODE, 0);
		state.put(ATCommand.SLEEP_PERIOD, 0xC8);
		state.put(ATCommand.WAKE_TIME, 0x7D0);
		state.put(ATCommand.COMMAND_MODE_TIMEOUT, 100);
		state.put(ATCommand.GUARD_TIMES, 1000);
		state.put(ATCommand.WAKE_HOST, 0);
		state.put(ATCommand.COMMAND_MODE_CHARACTER, "+");
		// no value for APPLY_CHANGES
		// no value for SOFTWARE_RESET
		// no value for NETWORK_RESET
		// no value for RESTORE_DEFAULTS
		// no value for WRITE
		
	}
	
	
	
	
	
	
	
	
	
	// packet handlers
	
	private Packet handlePacket(Packet incomingPacket) {
		Packet response = null; 
		if (incomingPacket instanceof CommandRequestPacket) {
			response = handlePacket((CommandRequestPacket)incomingPacket);
		} else if (incomingPacket instanceof CommandResponsePacket) {
			response = handlePacket((CommandResponsePacket)incomingPacket);
		} else if (incomingPacket instanceof IOSamplePacket) {
			response = handlePacket((IOSamplePacket)incomingPacket);
		} else if (incomingPacket instanceof SerialDataPacket) {
			response = handlePacket((SerialDataPacket)incomingPacket);
		} else if (incomingPacket instanceof SerialDataAckPacket) {
			response = handlePacket((SerialDataAckPacket)incomingPacket);
		} else {
			response = new CommandResponsePacket(SamplePackets.commandResponse_get_ID_failed, 0, SamplePackets.commandResponse_get_ID_failed.length);
		}
		return response;
	}

	private Packet handlePacket(CommandRequestPacket packet) {
		CommandResponsePacket r = new CommandResponsePacket();
		r.setATCommand(packet.getATCommand());
		
		try {
			if (packet.getParameter().length == 0) {
				// get command
				// check whether the command can be read
				if (ATCommandHelper.canGet(packet.getATCommand())) {
					// can be read
					Object paramObject = state.get(packet.getATCommand());
					byte[] paramBytes;
					try {
						paramBytes = ATCommandHelper.encodeAT(packet.getATCommand(), paramObject);
						r.setParameter(paramBytes);
						r.setStatus(Status.OK);
					} catch (Exception e) {
						// couldn't understand the parameter
						r.setParameter(new byte[0]);
						r.setStatus(Status.INVALID_PARAMETER);
					}
				} else {
					// can't be read
					r.setStatus(Status.INVALID_COMMAND);
				}
				
			} else {
				// set/execute command
				// check whether the command is set/execute-able
				if (ATCommandHelper.canSet(packet.getATCommand())) {
					// can be set
					byte[] paramBytes = packet.getParameter();
					Object paramObject = null;
					try {
						paramObject = ATCommandHelper.decodeAT(packet.getATCommand(), paramBytes);
						state.put(packet.getATCommand(), paramObject);
						r.setParameter(new byte[0]);
						r.setStatus(Status.OK);
					} catch (Exception e) {
						// couldn't understand the parameter
						r.setParameter(new byte[0]);
						r.setStatus(Status.INVALID_PARAMETER);
					}
				} else {
					// can't be set
					r.setStatus(Status.INVALID_COMMAND);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.setStatus(Status.ERROR);
		}

		// suppress response?
		if (packet.getResponseSuppressed()) {
			r = null;
		} else {
			r.setFrameID(packet.getFrameID());
		}
		
		return r;
	}

	private Packet handlePacket(CommandResponsePacket packet) {
		// always send back a "failed" message
		return new CommandResponsePacket(SamplePackets.commandResponse_get_ID_failed, 0, SamplePackets.commandResponse_get_ID_failed.length);
	}

	private Packet handlePacket(IOSamplePacket packet) {
		// always send back a "failed" message
		return new CommandResponsePacket(SamplePackets.commandResponse_get_ID_failed, 0, SamplePackets.commandResponse_get_ID_failed.length);
	}

	private Packet handlePacket(SerialDataPacket packet) {
		// always send back a "failed" message
		return new CommandResponsePacket(SamplePackets.commandResponse_get_ID_failed, 0, SamplePackets.commandResponse_get_ID_failed.length);
	}

	private Packet handlePacket(SerialDataAckPacket packet) {
		// always send back a "failed" message
		return new CommandResponsePacket(SamplePackets.commandResponse_get_ID_failed, 0, SamplePackets.commandResponse_get_ID_failed.length);
	}

	
	
	
	
	
	
	
	
	
	
	// logging functions
	private void logStatus(String s) {
		System.out.println(s);
	}
	private void logInboundPacket(Packet packet, InetAddress localHost, int localPort, InetAddress remoteHost, int remotePort) {
		String r = String.format("[%s:0x%X => %s:0x%X] %s", remoteHost.getHostAddress(), remotePort, localHost.getHostAddress(), localPort, packet.getClass().getSimpleName());
		
		if (packet instanceof CommandRequestPacket) {
			CommandRequestPacket packet2 = (CommandRequestPacket)packet;
			String more = String.format(": %s %s%s",
					packet2.getParameter().length == 0 ? "get" : "set",
					packet2.getATCommand(),
					packet2.getParameter().length == 0 ? "" : "=" + ATCommandHelper.decodeToString(packet2.getATCommand(), packet2.getParameter()));
			r += more;
		} else if (packet instanceof CommandResponsePacket) {
			CommandResponsePacket packet2 = (CommandResponsePacket)packet;
			String more = String.format(": %s, %s%s",
					packet2.getStatus(),
					packet2.getATCommand(),
					packet2.getParameter().length == 0 ? "" : "=" + ATCommandHelper.decodeToString(packet2.getATCommand(), packet2.getParameter()));
			r += more;
		}
		
		System.out.println(r);
	}
	private void logOutboundPacket(Packet packet, InetAddress localHost, int localPort, InetAddress remoteHost, int remotePort) {
		String r = String.format("[%s:0x%X <= %s:0x%X] %s", remoteHost.getHostAddress(), remotePort, localHost.getHostAddress(), localPort, packet.getClass().getSimpleName());
		
		if (packet instanceof CommandRequestPacket) {
			CommandRequestPacket packet2 = (CommandRequestPacket)packet;
			String more = String.format(": %s %s%s",
					packet2.getParameter().length == 0 ? "get" : "set",
					packet2.getATCommand(),
					packet2.getParameter().length == 0 ? "" : "=" + ATCommandHelper.decodeToString(packet2.getATCommand(), packet2.getParameter()));
			r += more;
		} else if (packet instanceof CommandResponsePacket) {
			CommandResponsePacket packet2 = (CommandResponsePacket)packet;
			String more = String.format(": %s %s%s",
					packet2.getStatus(),
					packet2.getATCommand(),
					packet2.getParameter().length == 0 ? "" : "=" + ATCommandHelper.decodeToString(packet2.getATCommand(), packet2.getParameter()));
			r += more;
		}
		
		System.out.println(r);
	}

}
