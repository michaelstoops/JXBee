package com.stoopsartsunlimited.jxbeesim.statemodel;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic state-model of a CPU or other register machine.
 * 
 * @author Michael
 *
 */
public class StateModel {

	protected Map<String, Register> registers = new HashMap<String, Register>();
	
	public StateModel() {
	}
	
	public void addRegister(String key, Register register) {
		registers.put(key, register);
	}
	
	public Register getRegister(String key) {
		Register r = registers.get(key);
		if (r == null) {
			throw new IllegalArgumentException("Key not found: " + key);
		}
		return r;
	}
}
