package com.stoopsartsunlimited.jxbeesim;

import java.util.List;

import com.stoopsartsunlimited.jxbeelib.ip.Packet;
import com.stoopsartsunlimited.jxbeelib.ip.PacketSubscriber;
import com.stoopsartsunlimited.jxbeesim.statemodel.StateModel;

public abstract class PacketHandler implements PacketSubscriber {
	protected ModuleSim sim;
	protected StateModel state;
	protected List<Packet> outboundPackets;
	
	public PacketHandler(ModuleSim sim, StateModel state, List<Packet> outboundPackets) {
		super();
		this.sim = sim; 
		this.state = state;
		this.outboundPackets = outboundPackets;
	}
	
}
