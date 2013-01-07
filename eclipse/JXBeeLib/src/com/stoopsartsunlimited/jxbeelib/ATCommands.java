package com.stoopsartsunlimited.jxbeelib;


/**
 * AT command codes available in XBee Wi-Fi devices.
 * @author Michael
 * 
 */
public final class ATCommands {
	// addressing
	//public static final String DESTINATION_ADDRESS_HIGH = "DH";
	public static final String DESTINATION_ADDRESS_LOW = "DL";
	public static final String NETWORK_ADDRESS = "MY"; // IP Address on IP devices, ZigBee 16-bit address on ZigBee devices
	//public static final String PARENT_NETWORK_ADDRESS = "MP";
	public static final String IP_ADDRESS_MASK = "MK";
	public static final String GATEWAY_IP_ADDRESS = "GW";
	//public static final String NUMBER_OF_CHILDREN_REMAINING = "NC";
	public static final String SERIAL_NUMBER_HIGH = "SH";
	public static final String SERIAL_NUMBER_LOW = "SL";
	public static final String NODE_IDENTIFIER = "NI";
	//public static final String SOURCE_ENDPOINT = "SE";
	public static final String DESTINATION_ENDPOINT = "DE";
	//public static final String CLUSTER_IDENTIFIER = "CI";
	public static final String SERIAL_COMM_SERVICE_PORT = "C0";
	public static final String DEVICE_TYPE_IDENTIFIER = "DD";
	public static final String MAX_RF_PAYLOAD_BYTES = "NP";
	
	// networking
	public static final String SSID = "ID";
	public static final String NETWORK_TYPE = "AH";
	public static final String IP_PROTOCOL = "IP";
	public static final String IP_ADDRESSING_MODE = "MA";
	public static final String TCP_TIMEOUT = "TM";
	//public static final String EXTENDED_PAN_ID = "ID";
	//public static final String OPERATING_EXTENDED_PAN_ID = "OP";
	//public static final String OPERATING_PAN_ID = "OI";
	//public static final String MAXIMUM_UNICAST_HOPS = "NH";
	//public static final String BROADCAST_HOPS = "BH";
	//public static final String FORCE_DISASSOCIATION = "DA";
	//public static final String NODE_DISCOVERY_TIMEOUT = "NT";
	//public static final String NETWORK_DISCOVERY_OPTIONS = "NO";
	//public static final String SCAN_CHANNELS = "SC";
	//public static final String SCAN_DURATION = "SD";
	//public static final String ZIGBEE_STACK_PROFILE = "ZS";
	//public static final String NODE_JOIN_TIME = "NJ";
	//public static final String CHANNEL_VERIFICATION = "JV";
	//public static final String NETWORK_WATCHDOG_TIMEOUT = "NW";
	//public static final String JOIN_NOTIFICATION = "JN";
	//public static final String AGGREGATE_ROUTING_NOTIFICATION = "AR";
	//public static final String DISABLE_JOINING = "DJ";
	//public static final String INITIAL_ID = "II";

	
	// security
	public static final String ENCRYPTION_ENABLE = "EE";
	//public static final String ENCRYPTION OPTIONS = "EO";
	public static final String WIFI_SECURITY_KEY = "PK";
	//public static final String NETWORK_ENCRYPTION_KEY = "NK";
	//public static final String LINK_KEY = "KY";
	
	// rf interfacing
	public static final String POWER_LEVEL = "PL";
	//public static final String POWER_MODE = "PM";
	public static final String CHANNEL = "CH";
	public static final String BIT_RATE_OF_IBSS_CREATOR = "BR";
	//public static final String PEAK_POWER = "PP";
	
	// serial interfacing
	public static final String API_ENABLE = "AP";
	//public static final String API_OPTIONS = "AO";
	public static final String INTERFACE_DATA_RATE = "BD";
	public static final String SERIAL_PARITY = "NB";
	public static final String STOP_BITS = "SB";
	public static final String PACKETIZATION_TIMEOUT = "RO";
	public static final String FLOW_CONTROL_THRESHOLD = "FT";
	public static final String DIO7_CONFIGURATION = "D7";
	public static final String DIO6_CONFIGURATION = "D6";
	
	// i/o settings
	public static final String FORCE_SAMPLE = "IS";
	public static final String IO_SAMPLE_RATE = "IR";
	public static final String IO_DIGITAL_CHANGE_DETECTION = "IC";
	public static final String SAMPLE_FROM_SLEEP_RATE = "IF";
	public static final String PWM0_CONFIGURATION = "P0";
	public static final String DIO10_CONFIGURATION = "P0";
	public static final String DIO11_CONFIGURATION = "P1";
	public static final String DIO12_CONFIGURATION = "P2";
	public static final String DIO13_CONFIGURATION = "P3";
	public static final String DOUT = "P3";
	public static final String DIN = "P4";
	public static final String AD0_DIO0_CONFIGURATION = "D0";
	public static final String AD1_DIO1_CONFIGURATION = "D1";
	public static final String AD2_DIO2_CONFIGURATION = "D2";
	public static final String AD3_DIO3_CONFIGURATION = "D3";
	public static final String DIO4_CONFIGURATION = "D4";
	public static final String DIO5_CONFIGURATION = "D5";
	public static final String DIO8_CONFIGURATION = "D8";
	public static final String DIO9_CONFIGURATION = "D9";
	public static final String ASSOC_LED_BLINK_TIME = "LT";
	public static final String PULL_UP_RESISTOR = "PR";
	public static final String PULL_DIRECTION = "PD";
	//public static final String RSSI_PWM_TIMER = "RP";
	public static final String ANALOG_VOLTAGE_REFERENCE = "AV";
	public static final String PWM0_DUTY_CYCLE = "M0";
	public static final String PWM1_DUTY_CYCLE = "M1";
	//public static final String IO_OUTPUT_ENABLE = "IU";
	//public static final String SAMPLES_BEFORE_TX = "IT";
	//public static final String DIGITAL_OUTPUT_LEVEL = "IO";
	//public static final String IO_INPUT_ADDRESS = "IA";
	//public static final String D0_OUTPUT_TIMEOUT = "T0";
	//public static final String D1_OUTPUT_TIMEOUT = "T1";
	//public static final String D2_OUTPUT_TIMEOUT = "T2";
	//public static final String D3_OUTPUT_TIMEOUT = "T3";
	//public static final String D4_OUTPUT_TIMEOUT = "T4";
	//public static final String D5_OUTPUT_TIMEOUT = "T5";
	//public static final String D6_OUTPUT_TIMEOUT = "T6";
	//public static final String D7_OUTPUT_TIMEOUT = "T7";
	//public static final String PWM_OUTPUT_TIMEOUT = "PT";
	
	// diagnostics interfacing
	public static final String FIRMWARE_VERSION = "VR";
	public static final String HARDWARE_VERSION = "HV";
	public static final String ASSOCIATION_INDICATION = "AI";
	public static final String ACTIVE_SCAN = "AS";
	public static final String TEMPERATURE = "TP";
	public static final String CONFIGURATION_CODE = "CK";
	public static final String SUPPLY_VOLTAGE = "%V";
	//public static final String VOLTAGE_SUPPLY_MONITORING = "V+";
	public static final String RSSI = "DB";
	//public static final String RF_ERRORS = "ER";
	//public static final String GOOD_PACKETS = "GD";
	//public static final String TRANSMISSION_ERRORS = "TR";
	//public static final String RF_BYTE_COUNT = "BC";


	// at command options
	public static final String COMMAND_MODE_TIMEOUT = "CT";
	public static final String EXIT_COMMAND_MODE = "CN";
	public static final String GUARD_TIMES = "GT";
	public static final String COMMAND_MODE_CHARACTER = "CC";
	
	// sleep commands
	public static final String SLEEP_MODE = "SM";
	//public static final String NUMBER_OF_SLEEP_PERIODS = "SN";
	public static final String SLEEP_PERIOD = "SP";
	public static final String SLEEP_OPTIONS = "SO";
	public static final String WAKE_HOST = "WH";
	public static final String WAKE_TIME = "ST"; // aka TIME_BEFORE_SLEEP
	//public static final String SLEEP_IMMEDIATELY = "SI";
	//public static final String POLLING_RATE = "PO";
	//public static final String MISSED_SYNCS = "MS";
	//public static final String MISSED_SYNC_COUNT = "SQ";
	//public static final String SLEEP_STATUS = "SS";
	//public static final String OPERATIONAL_SLEEP_PERIOD = "OS";
	//public static final String OPERATIONAL_WAKE_PERIOD = "OW";

	// execution
	public static final String APPLY_CHANGES = "AC";
	public static final String WRITE = "WR";
	public static final String RESTORE_DEFAULTS = "RE";
	public static final String SOFTWARE_RESET = "FR";
	public static final String NETWORK_RESET = "NR";
	//public static final String COMMISSIONING_PUSHBUTTON = "CB";
	//public static final String NODE_DISCOVER = "ND";
	//public static final String DESTINATION_NODE = "DN";
	//public static final String XBEE_SENSOR_SAMPLE = "1S"; // this looks sketchy but it's in the product manual
	//public static final String VERSION_LONG = "VL";

	// mac-level commands
	//public static final String BROADCAST_MULTI_TRANSMIT = "MT";
	//public static final String UNICAST_MAC_RETRIES = "RR";

	// digimesh
	//public static final String NETWORK_DELAY_SLOTS = "NN";
	//public static final String MESH_NETWORK_RETRIES = "MR";
	//public static final String NODE_TYPE = "CE";

	//public static final String XBEE_RETRIES = "RN";
	//public static final String MAC_MODE = "MM";

	// 802.15.4
	//public static final String END_DEVICE_ASSOCIATION = "A1";
	//public static final String COORDINATOR_ASSOCIATION = "A2";
	//public static final String FORCE_POLL = "FP";
	//public static final String ENERGY_SCAN = "ED";
	//public static final String CCA_THRESHOLD = "CA";
	//public static final String DISASSOCIATED_CYCLIC_SLEEP_PERIOD = "DP";
	//public static final String CCA_FAILURES = "EC";
	//public static final String ACK_FAILURES = "EA";





}
