package com.stoopsartsunlimited.jxbeesim;

import java.util.List;

import com.stoopsartsunlimited.jxbeelib.ip.Packet;
import com.stoopsartsunlimited.jxbeelib.ip.SerialDataAckPacket;
import com.stoopsartsunlimited.jxbeelib.ip.SerialDataPacket;
import com.stoopsartsunlimited.jxbeesim.statemodel.StateModel;

public class SerialDataPacketHandler extends PacketHandler {

	public SerialDataPacketHandler(ModuleSim sim, StateModel state,
			List<Packet> outboundPackets) {
		super(sim, state, outboundPackets);
	}

	public void update(Packet origPacket, Object... context) {
		if (!(origPacket instanceof SerialDataPacket)) {
			return;
		}
		SerialDataPacket packet = (SerialDataPacket)origPacket; 
		
		sim.addIncomingSerialData(packet.getSerialData());

		if ((packet.getPacketCommandOptions() & (byte)0x02) == 0x02) {
			outboundPackets.add(new SerialDataAckPacket());
		}
	}
}
