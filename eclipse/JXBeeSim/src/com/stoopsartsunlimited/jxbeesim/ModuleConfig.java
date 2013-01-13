package com.stoopsartsunlimited.jxbeesim;

import java.net.InetAddress;
import java.util.HashMap;

import com.stoopsartsunlimited.jxbeelib.ATCommand;

public class ModuleConfig {

	private InetAddress inetAddress = null;
	private boolean acceptAllSets = true;
	private int udpBufferSize = 2048;

	private HashMap<String, Object> startingState = new HashMap<String, Object>();
	
	/**
	 * @return the startingState
	 */
	public HashMap<String, Object> getStartingState() {
		return startingState;
	}

	public void setStartingState(String atCommand, Object value) {
		startingState.put(atCommand, value);
	}
	
	public Object getStartingState(String atCommand) {
		return startingState.get(atCommand);
	}
	
	
	/**
	 * @return the inetAddress
	 */
	public InetAddress getInetAddress() {
		return inetAddress;
	}
	/**
	 * @param inetAddress the inetAddress to set
	 */
	public void setInetAddress(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
		setStartingState(ATCommand.NETWORK_ADDRESS, inetAddress);
	}
	/**
	 * @return the acceptAllSets
	 */
	public boolean isAcceptAllSets() {
		return acceptAllSets;
	}
	/**
	 * @param acceptAllSets the acceptAllSets to set
	 */
	public void setAcceptAllSets(boolean acceptAllSets) {
		this.acceptAllSets = acceptAllSets;
	}
	/**
	 * @return the udpBufferSize
	 */
	public int getUdpBufferSize() {
		return udpBufferSize;
	}
	/**
	 * @param udpBufferSize the udpBufferSize to set
	 */
	public void setUdpBufferSize(int udpBufferSize) {
		this.udpBufferSize = udpBufferSize;
	}

}
