package com.stoopsartsunlimited.jxbeelib.ip;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.stoopsartsunlimited.jxbeelib.XBeeException;

/**
 * Provides a connection to an XBee device over IP. At this point, the wifi modules are the only ones that use this.
 * 
 * The XBee communication scheme for IP requires that all communication to or from an XBee module use UDP port 0xBEE.
 * This causes a problem when you're trying to connection with several modules, because all of the modules will send
 * response packets to the same local UDP port on this host. However, the sockets layer only allows one socket to
 * listen at that local port, creating a conflict between connections.
 * 
 * So in an attempt to support communication with several remote XBee devices at the same time, sharing of the local
 * UDP port is done through this concentrator class. The relationship between connections and the concentrator is of
 * the "manager" pattern, and the concentrator is a singleton. The concentrator tracks the connections, and routes
 * any received packets to the correct connection. This resolves the conflict of sharing the one local UDP port, at
 * least for all XBee connections that share this singleton.
 * 
 * As a consequence of this coordination, event-driven multiplexing isn't available. This concentrator has no thread
 * of its own, so code that uses this concentrator must poll it for packets. A "push" scheme could be implemented by
 * instantiating a thread that runs only this concentrator, then adding callbacks. In practice, it would be more
 * complicated but no more effective. Polling is fine.
 * 
 * Also, the polling the concentrator may have side effects for other connections. Having no thread of its own, the
 * concentrator must take the opportunity to handle packets for other connections too. 
 * 
 * As with any network communication and especially UDP, no delivery is guaranteed, nor is the other host's reaction
 * to your request. Check your own preconditions.
 * 
 * A token effort has been made to provide thread-safety, but it isn't guaranteed. And really, there's never a
 * guarantee that nobody else is messing with your XBee devices.
 *  
 * 
 * Features to add:
 * packet queuing and connection multiplexing
 * 
 * @author Michael
 *
 */
public final class XBeeIPConcentrator {
	private static XBeeIPConcentrator instance = null;
	
	private XBeeIPConcentrator() {
	}
	
	public static synchronized XBeeIPConcentrator getInstance() {
		if (instance == null) {
			instance = new XBeeIPConcentrator();
		}
		return instance;
	}
	
	private Map<InetAddress, XBeeIPConnection> connections = new HashMap<InetAddress, XBeeIPConnection>();
	//private LinkedList<Packet> packetQueue = new LinkedList<Packet>();
	private DatagramSocket controlSocket = null;
	
	private DatagramSocket getControlSocket() throws SocketException {
		if (controlSocket == null
			|| controlSocket.isClosed()) {
			controlSocket = new DatagramSocket(0xBEE);
			// don't wait around
			controlSocket.setSoTimeout(1);
		}
		return controlSocket;
	}
	
	/**
	 * Registers a connection object with the concentrator.
	 * @param remoteAddress
	 * @return
	 * @throws XBeeException if there's a conflicting connection.
	 */
	public void registerConnection(XBeeIPConnection connection) throws XBeeException {
		// make sure that there is a socket open now, so that we won't get surprised later if it isn't available.
		try {
			getControlSocket();
		} catch (SocketException e) {
			throw new XBeeException("Unable to create socket at the local UDP port 0xBEE. Likely there is already a socket listening on that port.", e);
		}
		
		// don't create a second connection for one XBee device.
		if (connections.containsKey(connection.getRemoteAddress())) {
			throw new XBeeException("There is already a connection for that IP address. Use XBeeIPConcentrator.getExstingConnection() to get another reference to that same connection.");
		}

		// register the connection
		connections.put(connection.getRemoteAddress(), connection);
	}
	
	/**
	 * Gets an existing connection object. Doesn't create any new connection, and if it doesn't exist already, returns null.
	 * @param remoteAddress
	 * @return
	 */
	public XBeeIPConnection getExstingConnection(Inet4Address remoteAddress) {
		XBeeIPConnection r = connections.get(remoteAddress);
		return r;
	}
	
	/**
	 * Forcibly closes a connection. If it's already closed, leaves it closed without error.
	 * 
	 * @param connection
	 */
	public void closeConnection(XBeeIPConnection connection) {
		connections.remove(connection.getRemoteAddress());
		if (connections.size() == 0) {
			// release the socket
			controlSocket.close();
			controlSocket = null;
		}
	}

	// TODO: filter packets by connection
	public Packet getPacket(XBeeIPConnection xBeeIPConnection, int timeout) {
		int oldTimeout = 1; // default to 1 ms
		try {
			DatagramPacket datagramPacket = new DatagramPacket(new byte[2048], 2048);
			oldTimeout = controlSocket.getSoTimeout();
			controlSocket.setSoTimeout(timeout);
			controlSocket.receive(datagramPacket);
			Packet xbeePacket = PacketFactory.getPacketFromNetworkBytes(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
			return xbeePacket;
		} catch (Exception e) {
			// if there's an exception, just drop the packet and return null
			return null;
		} finally {
			try {
				controlSocket.setSoTimeout(oldTimeout);
			} catch (Exception e) {
				// ignore
			}
		}
	}
	
	/**
	 * Gets all queued packets for the supplied connection. First received is first in the array.  
	 * @param connection
	 * @return
	 */
	public Packet[] getPackets(XBeeIPConnection connection) {
		// bring in any new packets
		//processIncomingPackets();
		
		LinkedList<Packet> incomingPackets = new LinkedList<Packet>();
		// read all packet from the control socket
		try {
			while (true) {
				DatagramPacket datagramPacket = new DatagramPacket(new byte[2048], 2048);
				controlSocket.receive(datagramPacket);
				Packet xbeePacket = PacketFactory.getPacketFromNetworkBytes(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
				incomingPackets.add(xbeePacket);
			}
		} catch (Exception e) {
			// stop receiving packets when there is a timeout exception
		}
		// read all packet from the serial socket
		try {
			while (true) {
				DatagramPacket datagramPacket = new DatagramPacket(new byte[2048], 2048);
				serialSocket.receive(datagramPacket);
				Packet xbeePacket = PacketFactory.getPacketFromNetworkBytes(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
				incomingPackets.add(xbeePacket);
			}
		} catch (Exception e) {
			// stop receiving packets when there is a timeout exception
		}
		
		// take the chance to clean out abandoned packets;
		//cleanCaches();

		// return all the packets
		// TODO: filter the packets by connection
		return (Packet[])incomingPackets.toArray();
	}
	
	/**
	 * The maximum number of packets to cache. Set to a liberally large number, assuming resources are bountiful.
	 */
	//private int packetQueueLimit = 16384;
	/**
	 * Removes expired items from the caches/queues.
	 */
	/*
	private void cleanCaches() {
		
	}
	*/
	
	/**
	 * Checks for incoming packets and processes them into the queue.
	 */
	/*
	private synchronized void processIncomingPackets() {
		try {
			while (true) {
				DatagramPacket datagramPacket = new DatagramPacket(new byte[2048], 2048);
				socket.receive(datagramPacket);
				// TODO: Create an XBee packet from this datagram packet
				Packet xbeePacket = ;
				
				packetQueue.addFirst(xbeePacket);
			}
		} catch (IOException e) {
			// do nothing, just stop processing
		}
	}
	*/
	
	/**
	 * 
	 * @param packet
	 * @param connection
	 * @throws XBeeException
	 * @throws SocketException
	 */
	public synchronized void sendPacket(Packet packet, XBeeIPConnection connection) throws XBeeException {
		if (!connection.isOpen()) {
			throw new XBeeException("Can't send packet because the connection is closed.");
		}
		byte[] packetBytes = packet.getBytes();
		
		int remotePort = 0;
		if (packet instanceof SerialDataPacket
			|| packet instanceof SerialDataAckPacket) {
			// is a data packet, goes to the data port 
			remotePort = connection.getRemoteDataPort();
		} else {
			// is a control packet, goes to the control port
			remotePort = connection.getRemoteControlPort();
		}
		
		DatagramPacket p = new DatagramPacket(packetBytes, packetBytes.length, connection.remoteAddress, remotePort);
		try {
			getControlSocket().send(p);
		} catch (IOException e) {
			throw new XBeeException(String.format("Error while sending packet %s to %s:%d", packet.toString(), connection.remoteAddress.toString(), remotePort), e);
		}
	}

	
	// serial IO
	private DatagramSocket serialSocket = null;
	
	private DatagramSocket getSerialSocket() throws SocketException {
		if (serialSocket == null
			|| serialSocket.isClosed()) {
			serialSocket = new DatagramSocket(0x2616);
			// don't wait around
			serialSocket.setSoTimeout(1);
		}
		return serialSocket;
	}
	
	
	public void openSerialReadChannel(XBeeIPConnection xBeeIPConnection) throws SocketException {
		// open the serial socket
		getSerialSocket();
	}

	public void closeSerialReadChannel(XBeeIPConnection xBeeIPConnection) {
		// closes the serial socket only if there are no readers
		for (XBeeIPConnection connection : connections.values()) {
			if (connection.isSerialReadChannelOpen()) {
				// somebody is using the socket, don't actually close it
				return;
			}
		}
		// nobody is using the socket, close it.
		serialSocket.close();
		
	}
}
