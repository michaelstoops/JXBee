package com.stoopsartsunlimited.jxbeelib.ip;

import java.net.InetAddress;
import java.net.SocketException;

import com.stoopsartsunlimited.jxbeelib.XBeeException;

public class XBeeIPConnection {
	protected InetAddress remoteAddress;
	protected int remoteControlPort = 0xBEE;
	// TODO: hardcoded port below
	protected int remoteDataPort = 0x2616;
	/**
	 * This is a saved reference to the concentrator. It's static, so we should always be able to get the same
	 * instance. This reference is mostly to make sure it doesn't get accidentally garbage-collected and lose its state.
	 */
	protected XBeeIPConcentrator concentrator;

	/**
	 * @param remoteAddress
	 * @throws XBeeException if the connection object can't be created. See XBeeIPConcentrator for other options.
	 */
	public XBeeIPConnection(InetAddress remoteAddress) throws XBeeException {
		this.remoteAddress = remoteAddress;
		// register with the concentrator
		concentrator = XBeeIPConcentrator.getInstance();
		concentrator.registerConnection(this);
	}
	
	/**
	 * Checks whether this connection is still open. Because the underlying protocol is connection-less,
	 * this open/closed state has less to do with the state of the remote XBee device and more to do with
	 * managing the concentrator's resources.
	 * 
	 * @return
	 */
	public boolean isOpen() {
		XBeeIPConnection connection = concentrator.getExstingConnection(remoteAddress);
		return connection != null;
	}

	/**
	 * Closes this connection.
	 */
	public void close() {
		concentrator.closeConnection(this);
	}
	
	/**
	 * Send a packet on this connection. Calls the concentrator to actually send the packet.
	 * @param packet
	 * @throws XBeeException 
	 */
	public void sendPacket(Packet packet) throws XBeeException {
		concentrator.sendPacket(packet, this);
	}

	/**
	 * Receive the next packet.
	 * @return the packet if one is found. If there isn't one available, returns null. 
	 */
	public Packet receivePacket() {
		return concentrator.getPacket(this, timeout);
	}

	/**
	 * @return the remoteControlPort
	 */
	public int getRemoteControlPort() {
		return remoteControlPort;
	}

	/**
	 * @return the getRemoteDataPort
	 */
	public int getRemoteDataPort() {
		return remoteDataPort;
	}

	/**
	 * @return the remoteAddress
	 */
	public InetAddress getRemoteAddress() {
		return remoteAddress;
	}

	protected int timeout;
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	protected boolean serialReadChannelOpen = false;

	/**
	 * @return the serialReadChannelOpen
	 */
	public boolean isSerialReadChannelOpen() {
		return serialReadChannelOpen;
	}
	public void openSerialReadChannel() throws SocketException {
		concentrator.openSerialReadChannel(this);
		serialReadChannelOpen = true;
	}
	public void closeSerialReadChannel() {
		serialReadChannelOpen = false;
		concentrator.closeSerialReadChannel(this);
	}

}
