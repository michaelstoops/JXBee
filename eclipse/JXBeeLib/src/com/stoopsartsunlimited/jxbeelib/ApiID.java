package com.stoopsartsunlimited.jxbeelib;

/**
 * Uniquely identifies a device capability set. Devices with the same API ID should be able to execute the exact same commands.
 * Derived from device's model, firmware, and hardware versions.
 * @author Michael
 *
 */
public final class ApiID {
	/**
	 * XBee model codes. Names were taken from the cover page of the manuals. Numbers were assigned arbitrarily.
	 * @author Michael
	 *
	 */
	public class Models {
		// WiFi family
		// "XBee Wi-Fi" line
		public static final int WIFI = 		0x00010101;
		
		// ZigBee/802.15.4 family
		// "XBee ZB" line
		public static final int XBEE2 = 	0x00020201;
		public static final int XBEEPRO2 = 	0x00020202;
		public static final int PRO_S2B = 	0x00020B02;
		// "XBee ZB SMT" line
		public static final int XBEE_S2C = 	0x00020C01;
		public static final int PRO_S2C = 	0x00020C02;
		// "XBee 802.15.4" and "XBee DigiMesh 2.4" lines
		public static final int XB24 = 		0x00020401;
		public static final int XBP24 = 	0x00020401;
		
		// Proprietary family
		// "XBee-PRO 900HP" and "XBee-PRO XSC" line
		public static final int XBEE_PRO_S3 = 0x00030101;
		public static final int XBEE_PRO_S3B = 0x00030B01;
		// "XBee 868LP for Europe" and "XBee 865LP for India" lines
		public static final int XBEE_S8 = 	0x00080101;
		// "XBee-PRO 868" line. May be the same as the other 86x lines
		public static final int XBP08 = 	0x00080102;
		// "XBee-PRO 900" and "XBee-PRO DigiMesh 900" lines, both obsolete
		public static final int XBP09 = 	0x00090102;
	}
	
	protected int model = 0;
	protected int firmware = 0;
	protected int hardware = 0;
	
	public ApiID() {
	}

	public ApiID(int model, int firmware, int hardware) {
		this.model = model;
		this.firmware = firmware;
		this.hardware = hardware;
	}


	/**
	 * @return the model
	 */
	public int getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(int model) {
		this.model = model;
	}
	/**
	 * @return the firmware
	 */
	public int getFirmware() {
		return firmware;
	}
	/**
	 * @param firmware the firmware to set
	 */
	public void setFirmware(int firmware) {
		this.firmware = firmware;
	}
	/**
	 * @return the hardware
	 */
	public int getHardware() {
		return hardware;
	}
	/**
	 * @param hardware the hardware to set
	 */
	public void setHardware(int hardware) {
		this.hardware = hardware;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new ApiID(model, firmware, hardware);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ApiID)) {
			return false;
		}
		ApiID o = (ApiID)obj;
		return model == o.model && firmware == o.firmware && hardware == o.hardware;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%08X.%08X.%08X", model, firmware, hardware);
	}
	
}
