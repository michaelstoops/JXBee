package com.stoopsartsunlimited.jxbeelib.ip;

/**
 * Defines an interface that wants to be notified of packets that are available from a packet publisher.
 * @author Michael
 */
public interface PacketSubscriber {
	/**
	 * Called by the packet publisher to notify this object of a packet.
	 * 
	 * This method will be called by a thread that has other work to do, so don't block for long.
	 * 
	 * @param packet
	 */
	public void update(Packet packet, Object... context);
}
