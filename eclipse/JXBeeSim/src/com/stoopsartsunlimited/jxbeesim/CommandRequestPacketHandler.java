package com.stoopsartsunlimited.jxbeesim;

import java.util.List;

import com.stoopsartsunlimited.jxbeelib.ATCommandHelper;
import com.stoopsartsunlimited.jxbeelib.ip.CommandRequestPacket;
import com.stoopsartsunlimited.jxbeelib.ip.CommandResponsePacket;
import com.stoopsartsunlimited.jxbeelib.ip.CommandResponsePacket.Status;
import com.stoopsartsunlimited.jxbeelib.ip.Packet;
import com.stoopsartsunlimited.jxbeesim.statemodel.StateModel;

public class CommandRequestPacketHandler extends PacketHandler {

	public CommandRequestPacketHandler(ModuleSim sim, StateModel state,
			List<Packet> outboundPackets) {
		super(sim, state, outboundPackets);
	}

	public void update(Packet origPacket, Object... context) {
		if (!(origPacket instanceof CommandRequestPacket)) {
			return;
		}
		CommandRequestPacket packet = (CommandRequestPacket)origPacket; 
		
		CommandResponsePacket r = new CommandResponsePacket();
		r.setATCommand(packet.getATCommand());
		
		try {
			if (packet.getParameter().length == 0) {
				// get command
				// check whether the command can be read
				if (ATCommandHelper.canGet(packet.getATCommand())) {
					// can be read
					Object paramObject = state.getRegister(packet.getATCommand()).read();
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
						state.getRegister(packet.getATCommand()).write(paramObject);
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
		
		outboundPackets.add(r);
	}

}
