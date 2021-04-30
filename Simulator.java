import java.util.ArrayList;
import java.util.Scanner;
public class Simulator {
	public static final int MAX_PACKETS = 3;
	Router dispatcher = new Router();
	Router destination = new Router();
	ArrayList<Router> routers = new ArrayList<Router>();
	ArrayList<Integer> passThrough = new ArrayList<Integer>();
	private int totalServiceTime = 0;
	private int totalPacketsArrived = 0;
	private int packetsDropped = 0;
	double arrivalProb;
	int numIntRouters;
	int maxBufferSize;
	int minPacketSize;
	int maxPacketSize;
	int bandwidth;
	int duration;
	public Simulator(){}
	
	
	/** 
	 * @param numIntRouters
	 * @param arrivalProb
	 * @param maxBufferSize
	 * @param minPacketSize
	 * @param maxPacketSize
	 * @param bandwidth
	 * @param duration
	 * @return double 
	 * This is the simulate function: It keeps track of the current time, and updates routers on each value of time (1, 2, 3, etc.)
	 * 
	 * 
	 */
	public double simulate(int numIntRouters, double arrivalProb, int maxBufferSize, int minPacketSize, int maxPacketSize, int bandwidth, int duration){
		int time = 1;
		int packetcount = 1;
		dispatcher.setBuffer(MAX_PACKETS);
		destination.setBuffer(bandwidth);
		for(int i = 0; i < numIntRouters; i++){
			Router a = new Router();
			a.setBuffer(maxBufferSize);
			routers.add(a);
		}
		while(time <= duration){
			for(int i = 0; i < MAX_PACKETS; i++){
				if(Math.random() < arrivalProb){
					int n = randInt(minPacketSize, maxPacketSize);
					Packet pa = new Packet(packetcount, n, time, n/100);
					dispatcher.enqueue(pa);
					System.out.println("Packet " + pa.getId() + " arrives at dispatcher with size " + pa.getPacketSize() + ".");
					packetcount = packetcount + 1;
				}
			}
			if(dispatcher.isEmpty()){
				System.out.println("No Packets Arrived");
			}
			for(int i = 0; i < MAX_PACKETS; i++){
				if(!dispatcher.isEmpty()){
					try{
						int n = Router.sendPacketTo(routers);
						routers.get(n).enqueue(dispatcher.dequeue());
						System.out.println("Packet " + routers.get(n).peek().getId() + " sent to Router " + (n+1) + ".");
					}
					catch(Exception a){
						System.out.println("Network is congested. Packet " + dispatcher.packetList.get(0).getId() + " is dropped");
						dispatcher.dequeue();
						packetsDropped++;
					}
				}
			}
			if(!passThrough.isEmpty()){			
				while(!destination.isFull() && !passThrough.isEmpty()){
					destination.enqueue(routers.get(passThrough.remove(0)).dequeue());
					totalPacketsArrived++;
					System.out.println("Packet " + destination.packetList.get(0).getId() + " has successfully reached its destination: +" + (time - destination.packetList.get(0).getTimeArrive()));
					destination.packetList.remove(0);
				}			
			}
			else if(passThrough.isEmpty()){
			for(int i = 0; i < numIntRouters; i++){
				if(!routers.get(i).isEmpty()){
				if((routers.get(i).peek().getTimeToDest() == 0) && !destination.isFull()){
					Packet a = new Packet();
					a = routers.get(i).dequeue();
					destination.enqueue(a);
					totalPacketsArrived++;
					System.out.println("Packet " + destination.packetList.get(0).getId() + " has successfully reached its destination: +" + (time - destination.packetList.get(0).getTimeArrive()));
					totalServiceTime += (time - destination.packetList.get(0).getTimeArrive());
					destination.packetList.remove(0);
				}
				else if((routers.get(i).peek().getTimeToDest() == 0 && destination.isFull())){
					passThrough.add(i);
				}
				}
			}
			}
			for(int i = 0; i < numIntRouters; i++){
				if(!routers.get(i).isEmpty()){					
						if(routers.get(i).packetList.get(0).getTimeToDest() != 0){
							routers.get(i).packetList.get(0).setTimeToDest(routers.get(i).packetList.get(0).getTimeToDest() - 1);
					}
				}
			}
			for(int i = 0; i < numIntRouters; i++){
				if(!routers.get(i).isEmpty()){
				System.out.println("R" + (i+1) + ": " + routers.get(i).toString());
				}
				else{
					System.out.println("R" + (i+1) + ": {}");
				}
			}		
			System.out.println("Time " + time);
			time++;		
			destination.packetList.clear();
		}
		return ((double)totalServiceTime/(double)totalPacketsArrived);
	}
	
	/** 
	 * @param minVal
	 * @param maxVal
	 * @return int 
	 * returns random integer betweeen minPacketSize and maxPacketSize
	 */
	private int randInt(int minVal, int maxVal){
		return (((int)((Math.random() * (maxVal-minVal)) + 1) + minVal)); 
	}
	
	/** 
	 * @param args
	 * main function 
	 * Calls simulate and asks user for instuctions/inputs
	 */
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		Simulator sim = new Simulator();
		while(true){
			System.out.println("Enter the number of Intermediate routers: ");
			sim.numIntRouters = scan.nextInt();
			System.out.println("Enter the arrival probability of a packet: ");
			sim.arrivalProb = scan.nextDouble();
			System.out.println("Enter the maximum buffer size of a router: ");
			sim.maxBufferSize = scan.nextInt();
			System.out.println("Enter the minimum size of a packet: ");
			sim.minPacketSize = scan.nextInt();
			System.out.println("Enter the maximum size of a packet: ");
			sim.maxPacketSize = scan.nextInt();
			System.out.println("Enter the bandwidth size: ");
			sim.bandwidth = scan.nextInt();
			System.out.println("Enter the simulation duration: ");
			sim.duration = scan.nextInt();
			System.out.println("Average service time per packet: " + sim.simulate(sim.numIntRouters, sim.arrivalProb, sim.maxBufferSize, sim.minPacketSize, sim.maxPacketSize, sim.bandwidth, sim.duration));
			System.out.println("Total service time: " + sim.totalServiceTime);
			System.out.println("Total packets served: " + sim.totalPacketsArrived);
			System.out.println("Total packets dropped: " + sim.packetsDropped);
			System.out.println("Simulation ending...");
			System.out.println("Do you want to try another simulation? (y/n): ");
			scan.nextLine();
			String qwe = scan.nextLine();
			if(!qwe.equalsIgnoreCase("y")){
				break;
			}
			sim.dispatcher.packetList.clear();
			sim.destination.packetList.clear();
			sim.routers.clear();
			sim.passThrough.clear();
		}
		scan.close();
	}
}
