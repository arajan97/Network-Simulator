
public class Packet {
	static int packetCount;
	private int id;
	private int packetSize;
	private int timeArrive;
	private int timeToDest;
	
	Packet(){}
	Packet(int id1, int packetSize1, int timeArrive1, int timeToDest1){
		id = id1;
		packetSize = packetSize1;
		timeArrive = timeArrive1;
		timeToDest = timeToDest1;
	}

	
	/** 
	 * @return int packetCount
	 */
	public static int getPacketCount() {
		return packetCount;
	}

	
	/** 
	 * 
	 * @param packetCount
	 * sets packetCount to param
	 */
	public static void setPacketCount(int packetCount) {
		Packet.packetCount = packetCount;
	}

	
	/** 
	 * @return int id
	 */
	public int getId() {
		return id;
	}

	
	/** 
	 * @param id
	 * sets id to param
	 */
	public void setId(int id) {
		this.id = id;
	}

	
	/** 
	 * @return int packetSize
	 */
	public int getPacketSize() {
		return packetSize;
	}

	
	/** 
	 * @param packetSize
	 * sets packetSize to param
	 */
	public void setPacketSize(int packetSize) {
		this.packetSize = packetSize;
	}

	
	/** 
	 * @return int time of arrival
	 */
	public int getTimeArrive() {
		return timeArrive;
	}
	
	/** 
	 * @param timeArrive
	 * sets time of arrival
	 */
	public void setTimeArrive(int timeArrive) {
		this.timeArrive = timeArrive;
	}

	
	/** 
	 * @return int time to destination
	 */
	public int getTimeToDest() {
		return timeToDest;
	}

	
	/** 
	 * @param timeToDest
	 * sets time to destination to param
	 */
	public void setTimeToDest(int timeToDest) {
		this.timeToDest = timeToDest;
	}
	
	/** 
	 * @return String string representation of packet
	 * in order: [id, time of arrival, time to destination]
	 */
	public String toString(){
		String result = "[" + String.valueOf(this.getId()) + ", " + String.valueOf(this.getTimeArrive()) + ", " + String.valueOf(this.getTimeToDest()) + "]";
		return result;
	}
}
