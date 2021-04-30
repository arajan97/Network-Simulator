import java.util.ArrayList;
@SuppressWarnings("serial")
public class Router extends ArrayList<Packet>{
	ArrayList<Packet> packetList = new ArrayList<Packet>();
	private int maxBufferSize;
	public Router(){}
	public Router(ArrayList<Packet> packetList1, int maxBufferSize1){
		packetList = packetList1;
		maxBufferSize = maxBufferSize1;
	}
	
	/** 
	 * @param maxBufferSize1
	 * sets buffer size of all routers
	 */
	public void setBuffer(int maxBufferSize1){
		maxBufferSize = maxBufferSize1;
	}
	
	/** 
	 * @param packet p
	 * enqueue packet p to router object ()
	 */
	public void enqueue(Packet p){
		packetList.add(p);
	}
	
	/** 
	 * @return Packet
	 */
	public Packet dequeue(){
		return packetList.remove(0);
	}
	
	/** 
	 * @return Packet
	 */
	public Packet peek(){
		return packetList.get(0);
	}
	
	/** 
	 * @return int
	 */
	public int size(){
		return packetList.size();
	}
	
	/** 
	 * @return boolean
	 */
	public boolean isEmpty(){
		return packetList.isEmpty();
	}
	
	/** 
	 * @return boolean
	 */
	public boolean isFull(){
		if(packetList.size() == maxBufferSize){
			return true;
		}
		return false;
	}
	
	/** 
	 * @return String
	 */
	public String toString(){
		String result = "{" + packetList.get(0).toString();
		for(int i = 1; i < packetList.size(); i++){
			result += ", " + packetList.get(i).toString();  
		}
		result += "}";
		return result;
	}
	
	/** 
	 * @param routers
	 * @return int
	 * @throws Exception
	 */
	public static int sendPacketTo(ArrayList<Router> routers) throws Exception{
		int indexOfAcceptableRouter = -1;
		int temp = routers.get(0).packetList.size();
		for(int i = 1; i < routers.size(); i++){
			if(routers.get(i).packetList.size() < temp){
				indexOfAcceptableRouter = i;
				return indexOfAcceptableRouter;
			}
		}
		if(!routers.get(0).isFull()){
			indexOfAcceptableRouter = 0;
			return indexOfAcceptableRouter;
		}
		if(indexOfAcceptableRouter == -1){
			throw new Exception();
		}
		return indexOfAcceptableRouter;
	}
}
