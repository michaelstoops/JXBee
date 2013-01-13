package com.stoopsartsunlimited.jxbeelib.ip;

/**
 * Defines an interface for a publisher of packets to any subscribers.
 * @author Michael
 */
public interface PacketPublisher {
	public void attach(PacketSubscriber subscriber);
	public void detach(PacketSubscriber subscriber);
}
