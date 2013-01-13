package com.stoopsartsunlimited.jxbeelib.ip;

public class SamplePackets {

	// packet images as seen in the XBee docs.
	
	public static byte[] commandRequest_get_ID = new byte[]{ 0x02, 0x00, 0x01, 0x02, 0x49, 0x44 };
	public static byte[] commandResponse_get_ID = new byte[]{ (byte)0x82, 0x00, 0x01, 0x49, 0x44, 0x00, 'm', 'y', 's', 's', 'i', 'd' };
	public static byte[] commandResponse_get_ID_failed = new byte[]{ (byte)0x82, 0x00, 0x01, 0x49, 0x44, 0x01 };
	
	public static byte[] commandRequest_set_ID = new byte[]{ 0x02, 0x00, 0x01, 0x02, 0x49, 0x44, 'm', 'y', 's', 's', 'i', 'd' };
	public static byte[] commandResponse_set_ID = new byte[]{ (byte)0x82, 0x00, 0x01, 0x49, 0x44, 0x00 };
	
	
	public static byte[] commandRequest_default = new byte[]{ 0x02, 0x00, 0x00, 0x00, 0x00, 0x00 };

	//public static byte[] commandResponse_default = new byte[]{ 0x02, 0x00, 0x00, 0x00, 0x00, 0x00 };
	
	public static byte[] commandRequest_set_NI = new byte[]{ 0x02, 0x00, 0x01, 0x02, 'N', 'I', 'T', 'e', 's', 'T', 'i', 'n', 'G', '!' };
	
}
