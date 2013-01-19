package com.stoopsartsunlimited.jxbeesim;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.stoopsartsunlimited.jxbeelib.ATCommand;
import com.stoopsartsunlimited.jxbeelib.ATCommandHelper;
import com.stoopsartsunlimited.jxbeelib.ip.CommandRequestPacket;
import com.stoopsartsunlimited.jxbeelib.ip.CommandResponsePacket;
import com.stoopsartsunlimited.jxbeelib.ip.IOSamplePacket;
import com.stoopsartsunlimited.jxbeelib.ip.Packet;
import com.stoopsartsunlimited.jxbeelib.ip.PacketFactory;
import com.stoopsartsunlimited.jxbeelib.ip.PacketPublisher;
import com.stoopsartsunlimited.jxbeelib.ip.PacketSubscriber;
import com.stoopsartsunlimited.jxbeelib.ip.SerialDataPacket;
import com.stoopsartsunlimited.jxbeesim.statemodel.InetAddressRegister;
import com.stoopsartsunlimited.jxbeesim.statemodel.IntegerRegister;
import com.stoopsartsunlimited.jxbeesim.statemodel.NonValueRegister;
import com.stoopsartsunlimited.jxbeesim.statemodel.StateModel;
import com.stoopsartsunlimited.jxbeesim.statemodel.StringRegister;

public class ModuleSim extends Thread implements PacketPublisher {

	private StateModel state = new StateModel();

	private static final String threadNamePrefix = "JXBeeSim"; 
	
	private ModuleConfig config = null;
	
	public ModuleSim(ModuleConfig config) {
		super(String.format("%s-%s", threadNamePrefix, config.getInetAddress()));
		this.config = config;

		initState();
		
		LinkedList<String> keys = new LinkedList<String>(config.getStartingState().keySet());
		Collections.sort(keys);
		for (String atCommand : keys) {
			state.getRegister(atCommand).write(config.getStartingState().get(atCommand));
			logStatus(String.format("Set %s to %s", atCommand, ATCommandHelper.toString(atCommand, state.getRegister(atCommand).read())));
		}
		
		
		// attach packet handlers
		attach(new CommandRequestPacketHandler(this, state, outgoingPackets));
		attach(new SerialDataPacketHandler(this, state, outgoingPackets));
	}
	
	protected DatagramSocket controlSocket = null;
	protected DatagramSocket serialSocket = null;
	
	public void run() {
		
		try {
			// open socket
			openSocket();
	
			boolean keepGoing = true;
			while (keepGoing) {
				Packet incomingPacket = null;
				DatagramPacket datagramPacket = new DatagramPacket(new byte[config.getUdpBufferSize()], config.getUdpBufferSize());
				// wait for incoming packet
				// loop, check for interrupt
				while (keepGoing
						&& incomingPacket == null) {
					// poll the control socket first
					try {
						incomingPacket = listenForPacket(controlSocket, datagramPacket);
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
					} catch (SocketTimeoutException e) {}
					// try the serial socket
					try {
						incomingPacket = listenForPacket(serialSocket, datagramPacket);
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
					} catch (SocketTimeoutException e) {}
				}
				
				// handle packet
				publishPacket(incomingPacket);
				
				// send response
				for (Packet outboundPacket : outgoingPackets) {
					DatagramPacket responseDatagram = new DatagramPacket(outboundPacket.getBytes(), outboundPacket.getBytes().length, datagramPacket.getAddress(), datagramPacket.getPort());
					logOutboundPacket(outboundPacket, controlSocket.getLocalAddress(), controlSocket.getLocalPort(), responseDatagram.getAddress(), responseDatagram.getPort());
					controlSocket.send(responseDatagram);
				}
				outgoingPackets.clear();
			}
		} catch (IOException e) {
			logStatus("Network error");
		}
		
	}
	
	
	protected List<Packet> outgoingPackets = new LinkedList<Packet>();

	protected Packet listenForPacket(DatagramSocket socket, DatagramPacket datagramPacket) throws IOException {
		socket.receive(datagramPacket);
		Packet incomingPacket = PacketFactory.getPacketFromNetworkBytes(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
		logInboundPacket(incomingPacket, socket.getLocalAddress(), socket.getLocalPort(), datagramPacket.getAddress(), datagramPacket.getPort());
		return incomingPacket;
	}
	
	protected void openSocket() throws SocketException {
		try {
			controlSocket = new DatagramSocket(0xBEE, (InetAddress)state.getRegister(ATCommand.NETWORK_ADDRESS).read());
			controlSocket.setSoTimeout(1);
			logStatus(String.format("Listening for control messages on port %s:%d/UDP", controlSocket.getLocalAddress(), controlSocket.getLocalPort()));
			serialSocket = new DatagramSocket((Integer)state.getRegister(ATCommand.SERIAL_COMM_SERVICE_PORT).read(), (InetAddress)state.getRegister(ATCommand.NETWORK_ADDRESS).read());
			serialSocket.setSoTimeout(1);
			logStatus(String.format("Listening for serial data on port %s:%d/UDP", serialSocket.getLocalAddress(), serialSocket.getLocalPort()));
		} catch (SocketException e) {
			// that's not good.
			logStatus(String.format("Error while setting up listeners. There may be another program occupying port %s:%d/UDP or %s:%d/UDP", (InetAddress)state.getRegister(ATCommand.NETWORK_ADDRESS).read(), 0xBEE, (InetAddress)state.getRegister(ATCommand.NETWORK_ADDRESS).read(), state.getRegister(ATCommand.SERIAL_COMM_SERVICE_PORT).read()));
			throw e;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// logging functions
	private void logStatus(String s) {
		System.out.println(s);
	}
	private void logInboundPacket(Packet packet, InetAddress localHost, int localPort, InetAddress remoteHost, int remotePort) {
		String r = String.format("[%s:0x%X => %s:0x%X] %s", remoteHost.getHostAddress(), remotePort, localHost.getHostAddress(), localPort, packet);
		System.out.println(r);
	}
	private void logOutboundPacket(Packet packet, InetAddress localHost, int localPort, InetAddress remoteHost, int remotePort) {
		String r = String.format("[%s:0x%X <= %s:0x%X] %s", remoteHost.getHostAddress(), remotePort, localHost.getHostAddress(), localPort, packet);
		System.out.println(r);
	}

	
	
	
	
	
	
	
	
	// PacketPublisher interface implementation
	
	protected void publishPacket(Packet packet) {
		for (PacketSubscriber subscriber : subscribers) {
			subscriber.update(PacketFactory.getCopyOf(packet));
		}
	}
	
	protected List<PacketSubscriber> subscribers = new LinkedList<PacketSubscriber>();
	
	public void attach(PacketSubscriber subscriber) {
		subscribers.add(subscriber);
	}

	public void detach(PacketSubscriber subscriber) {
		subscribers.remove(subscriber);
	}
	
	
	
	
	
	
	
	
	
	protected void initState() {
		try {
			state.addRegister(ATCommand.DESTINATION_ADDRESS_LOW, new InetAddressRegister("255.255.255.255"));
			state.addRegister(ATCommand.GATEWAY_IP_ADDRESS, new InetAddressRegister("0.0.0.0"));
			state.addRegister(ATCommand.IP_ADDRESS_MASK, new InetAddressRegister("0.0.0.0"));
			state.addRegister(ATCommand.NETWORK_ADDRESS, new InetAddressRegister("0.0.0.0"));
			state.addRegister(ATCommand.DEVICE_TYPE_IDENTIFIER, new IntegerRegister(0x50000));
			state.addRegister(ATCommand.SERIAL_NUMBER_HIGH, new IntegerRegister((int)0xFFFFFFFF));
			state.addRegister(ATCommand.SERIAL_NUMBER_LOW, new IntegerRegister((int)0xFFFFFFFF));
			state.addRegister(ATCommand.SERIAL_COMM_SERVICE_PORT, new IntegerRegister(0x2616));
			state.addRegister(ATCommand.DESTINATION_ENDPOINT, new IntegerRegister(0x2616));
			state.addRegister(ATCommand.MAX_RF_PAYLOAD_BYTES, new IntegerRegister(0xFFFF));
			state.addRegister(ATCommand.NODE_IDENTIFIER, new StringRegister(" "));
			state.addRegister(ATCommand.CHANNEL, new IntegerRegister(1));
			state.addRegister(ATCommand.TCP_TIMEOUT, new IntegerRegister(0x0A));
			state.addRegister(ATCommand.NETWORK_TYPE, new IntegerRegister(2));
			state.addRegister(ATCommand.BIT_RATE_OF_IBSS_CREATOR, new IntegerRegister(0));
			state.addRegister(ATCommand.ENCRYPTION_ENABLE, new IntegerRegister(0));
			state.addRegister(ATCommand.IP_PROTOCOL, new IntegerRegister(0));
			state.addRegister(ATCommand.IP_ADDRESSING_MODE, new IntegerRegister(0));
			state.addRegister(ATCommand.POWER_LEVEL, new IntegerRegister(4));
			state.addRegister(ATCommand.SSID, new StringRegister(""));
			state.addRegister(ATCommand.WIFI_SECURITY_KEY, new StringRegister(""));
			state.addRegister(ATCommand.PACKETIZATION_TIMEOUT, new IntegerRegister(3));
			state.addRegister(ATCommand.API_ENABLE, new IntegerRegister(1));
			state.addRegister(ATCommand.INTERFACE_DATA_RATE, new IntegerRegister(3));
			state.addRegister(ATCommand.DIO6_CONFIGURATION, new IntegerRegister(0));
			state.addRegister(ATCommand.DIO7_CONFIGURATION, new IntegerRegister(1));
			state.addRegister(ATCommand.SERIAL_PARITY, new IntegerRegister(0));
			state.addRegister(ATCommand.STOP_BITS, new IntegerRegister(0));
			state.addRegister(ATCommand.FLOW_CONTROL_THRESHOLD, new IntegerRegister(0x7F3));
			state.addRegister(ATCommand.IO_DIGITAL_CHANGE_DETECTION, new IntegerRegister(0));
			state.addRegister(ATCommand.SAMPLE_FROM_SLEEP_RATE, new IntegerRegister(1));
			state.addRegister(ATCommand.DIO10_CONFIGURATION, new IntegerRegister(0));
			state.addRegister(ATCommand.DIO11_CONFIGURATION, new IntegerRegister(0));
			state.addRegister(ATCommand.DIO12_CONFIGURATION, new IntegerRegister(0));
			state.addRegister(ATCommand.DOUT, new IntegerRegister(1));
			state.addRegister(ATCommand.DIN, new IntegerRegister(1));
			state.addRegister(ATCommand.IO_SAMPLE_RATE, new IntegerRegister(0));
			state.addRegister(ATCommand.FORCE_SAMPLE, new NonValueRegister());
			state.addRegister(ATCommand.AD0_DIO0_CONFIGURATION, new IntegerRegister(0));
			state.addRegister(ATCommand.AD1_DIO1_CONFIGURATION, new IntegerRegister(0));
			state.addRegister(ATCommand.AD2_DIO2_CONFIGURATION, new IntegerRegister(0));
			state.addRegister(ATCommand.AD3_DIO3_CONFIGURATION, new IntegerRegister(0));
			state.addRegister(ATCommand.DIO4_CONFIGURATION, new IntegerRegister(0));
			state.addRegister(ATCommand.DIO5_CONFIGURATION, new IntegerRegister(1));
			state.addRegister(ATCommand.PULL_DIRECTION, new IntegerRegister(0x7FF));
			state.addRegister(ATCommand.PULL_UP_RESISTOR, new IntegerRegister(0x7FF));
			state.addRegister(ATCommand.ASSOC_LED_BLINK_TIME, new IntegerRegister(0));
			state.addRegister(ATCommand.ANALOG_VOLTAGE_REFERENCE, new IntegerRegister(1));
			state.addRegister(ATCommand.DIO8_CONFIGURATION, new IntegerRegister(1));
			state.addRegister(ATCommand.DIO9_CONFIGURATION, new IntegerRegister(1));
			state.addRegister(ATCommand.PWM0_DUTY_CYCLE, new IntegerRegister(0));
			state.addRegister(ATCommand.PWM1_DUTY_CYCLE, new IntegerRegister(0));
			state.addRegister(ATCommand.RSSI, new IntegerRegister(0xFF));
			state.addRegister(ATCommand.ASSOCIATION_INDICATION, new IntegerRegister(0x23));
			state.addRegister(ATCommand.SUPPLY_VOLTAGE, new IntegerRegister(3100));
			state.addRegister(ATCommand.CONFIGURATION_CODE, new IntegerRegister(0x98D8));
			state.addRegister(ATCommand.HARDWARE_VERSION, new IntegerRegister(0x1F47));
			state.addRegister(ATCommand.TEMPERATURE, new IntegerRegister(37));
			state.addRegister(ATCommand.FIRMWARE_VERSION, new IntegerRegister(0x102D));
			state.addRegister(ATCommand.ACTIVE_SCAN, new NonValueRegister());
			state.addRegister(ATCommand.SLEEP_OPTIONS, new IntegerRegister(0x100));
			state.addRegister(ATCommand.SLEEP_MODE, new IntegerRegister(0));
			state.addRegister(ATCommand.SLEEP_PERIOD, new IntegerRegister(0xC8));
			state.addRegister(ATCommand.WAKE_TIME, new IntegerRegister(0x7D0));
			state.addRegister(ATCommand.COMMAND_MODE_TIMEOUT, new IntegerRegister(100));
			state.addRegister(ATCommand.GUARD_TIMES, new IntegerRegister(1000));
			state.addRegister(ATCommand.WAKE_HOST, new IntegerRegister(0));
			state.addRegister(ATCommand.COMMAND_MODE_CHARACTER, new StringRegister("+"));
			state.addRegister(ATCommand.EXIT_COMMAND_MODE, new NonValueRegister());
			state.addRegister(ATCommand.APPLY_CHANGES, new NonValueRegister());
			state.addRegister(ATCommand.SOFTWARE_RESET, new NonValueRegister());
			state.addRegister(ATCommand.NETWORK_RESET, new NonValueRegister());
			state.addRegister(ATCommand.RESTORE_DEFAULTS, new NonValueRegister());
			state.addRegister(ATCommand.WRITE, new NonValueRegister());
		} catch (UnknownHostException e) {
		}
	}


	
	
	
	
	
	public void addIncomingSerialData(byte[] data) {
		System.out.println(Charset.forName("US-ASCII").decode(ByteBuffer.wrap(data)));
	}
	
	public void addOutgoingSerialData(byte[] data) {
		// not doing this now.
		//System.out.println(Charset.forName("US-ASCII").decode(ByteBuffer.wrap(data)));
	}

	
	
}

