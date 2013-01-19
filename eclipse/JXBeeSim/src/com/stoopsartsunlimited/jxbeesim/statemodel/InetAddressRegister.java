package com.stoopsartsunlimited.jxbeesim.statemodel;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressRegister extends Register {

	public InetAddressRegister() {
		super();
	}
	
	public InetAddressRegister(InetAddress value) {
		this();
		write(value);
	}
	
	public InetAddressRegister(String hostname) throws UnknownHostException {
		this();
		write(hostname);
	}
	
	@Override
	public Object read() {
		return readInetAddress();
	}
	

	public InetAddress readInetAddress() {
		try {
			return InetAddress.getByAddress(value);
		} catch (UnknownHostException e) {
			return null;
		}
	}

	@Override
	public void write(Object value) {
		this.write((InetAddress)value);
	}

	public void write(InetAddress value) {
		super.value = value.getAddress();
	}

	public void write(String hostname) throws UnknownHostException {
		super.value = InetAddress.getByName(hostname).getAddress();
	}

}
