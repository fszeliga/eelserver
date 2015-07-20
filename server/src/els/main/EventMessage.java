package els.main;


public class EventMessage {

	private byte[] data = null;
	private byte[] flags = null;
	private byte[] eventId = null;
	private byte rssi = (byte)0xFF;
	private byte msgType = (byte)0x45;

	public void setEventId(byte[] eventId) {
		this.eventId = eventId;
		System.out.println("############evmsg#################" + eventId[0] + " : "+eventId[1]);
	}	
	
	public void setEventId(int b) {
		this.eventId = Utils.intToByteArray(b);
	}

	public byte[] getEventId() {
		return eventId;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	public void setData(int b) {
		this.data = Utils.intToByteArray(b);
	}

	public byte[] getData() {
		return data;
	}

	public byte getMessageType() {
		return msgType;
	}

	public byte[] getMessageAsByteArray() {
		byte[] msg = new byte[8];

		msg[0] = getMessageType();
		msg[1] = getEventId()[0];
		msg[2] = getEventId()[1];
		msg[3] = getData()[0];
		msg[4] = getData()[1];
		msg[5] = getFlags()[0];
		msg[6] = getFlags()[1];
		msg[7] = getRSSI();

		return msg;
	}

	private byte getRSSI() {
		return rssi;
	}

	public byte[] getFlags() {
		if(flags == null)
			return new byte[] {0x00,0x00};
		return flags;
	}

	public void setFlags(byte[] flags) {
		this.flags = flags;

	}

	public void setRSSI(byte b) {
		this.rssi = b;
	}
		
	public boolean requestEventUpdate(){
		//System.out.println("Checking Request update flag");
		//System.out.println(flags[0]);
		return (flags[1] & (byte)0x01) == (byte)0x01;
	}
	
	public boolean resetEventUpdateList(){
		return (flags[1] & (byte)0x02) == (byte)0x02;
	}
	
	public boolean isRequestMessage(){
		return (flags[1] & (byte)0x04) == (byte)0x04;
	}
	
	public boolean dataNA(){
		return (flags[1] & (byte)0x08) == (byte)0x08;
	}
	
	@Override
	public String toString(){
		String msg = "-------------------------Event Message-------------------------\n";
		msg += "Message Type: " +Utils.byteToHexString(getMessageType()) + "\n";
		msg += "EventID: " + Utils.byteArrayToUnsignedInt(getEventId()) + "\n";
		msg += "Data: " + Utils.byteArrayToSignedInt(getData()) + " Byte rep: " + Utils.byteArrayToHexString(getData()) + "\n";
		msg += "Flags: " + Utils.byteArrayToBinaryString(getFlags());
		
		return msg;
	}

	public boolean sendToAll() {
		return (msgType & (byte)0x45) == (byte)0x45;
	}

	public void setMessageType(byte msgType) {
		this.msgType  = msgType;
	}

}
