# Network-Simulator

This program is a network simulator. It can simulate different configurations of networks with various traffic conditions fully controlled by the user.

## How To Use Network Simulator
To run the program, open a Java IDE such as eclipse (https://www.eclipse.org/downloads/packages/release/kepler/sr1/eclipse-ide-java-developers) or the IDE of your choice.

Compile and run all three files (Packet.java, Router.java, Simulator.java) together. This will prompt the program to request user input. You will input the following information:
```
Number of Intermediate Routers
Arrival Probability of a Packet (between 0 and 1.0)
Maximum Buffer Size
Minimum Packet Size
Maximum Packet Size
Bandwidth
Simulation Duration
```
The simulation will commence for the given duration and end, yielding the latency (Average time per packet), the total service time, the total packets served, and the total packets dropped.
The following sections will describe the functionality of the program. 

## Functionality
### Objects
There are two objects that are vital to understand when using the Network Simulator:
#### Packet

The Packet object holds data unique to each packet. It holds the following attributes:
```
id
packetSize
timeArrive
timeToDest
```
It also maintains the total number of packets generated.

#### Router

The Router object holds two attributes: 
```
packetList
maxBufferSize
```

### Simulation
To understand how the network is simulated, you must first understand the principles of Queues such as First-In-First-Out. The network consists of two queues. The first queue consists of a datapath. 
Packets move through a series of routers to simulate data being delivered from a source to a destination. This chart shows the flow of packets from source to destination through three parallel routers.
```
         -------> Router 1 ------->   
Dispatch -------> Router 2 -------> Destination                                     
         -------> Router 3 ------->
```

The source consists of a single router called the dispatcher. 
The dispatcher generates packets with a random size between the ***Minimum Packet Size*** and the ***Maximum Packet Size***. Packet generation is dependent on ***Arrival Probability***: If the probability is 1.0, a packet will be generated at every time cycle for each space in the Dispatcher's Buffer.
It sends the packets to a collection of routers called the Intermediate Routers. The number of these routers depends on ***Number of Intermediate Routers***. These routers can process one Packet at a time in an internal queue called the buffer. The length of the queue depends on ***Maximum Buffer Size***.
The time it takes for each Packet to be processed is as follows: ***TimeToDestination = PacketSize/Bandwidth***.

The Packets are sent to a final router called destination. 

### Understanding Output

At each time interval, the Packets present in each Intermediate Router is displayed as well as information about which Packets have left the dispatcher and which packets have arrived at the destination (The time that the Packet remained in the router network is displayed with a + upon arrival).
```
Packet 36 arrives at dispatcher with size 328.
Packet 37 arrives at dispatcher with size 462.
Network is congested. Packet 36 is dropped
Network is congested. Packet 37 is dropped
R1: {[28, 12, 1], [33, 14, 4]}
R2: {[29, 12, 1], [34, 14, 2]}
R3: {[23, 10, 0], [30, 13, 2]}
Time 15
```
The above output is a standard output shown at each time in the simulation. In this case, the buffers of all the intermediate routers R1-R3 are saturated with packets, so packets are lost at this time stamp. A packet is displayed here with the attributes (id, arrival time, time to destination). 

```
Average service time per packet: 0.31645569620253167
Total service time: 50
Total packets served: 158
Total packets dropped: 15
Simulation ending...
```
These statistics are shown at the end of the simulation. ***Average service time per packet = Total Service Time / Total Packets Served*** and is equal to the average latency throughout the simulation. 

These statistics can help you determine what values to use as inputs to the program to improve latency/packetloss. For example, having more intermediate routers will reduce the probability of saturating the network and causing packet loss. It would be theoretically possible to improve the router's processing by scheduling certain packets(Packets with lower PacketSize) to complete first. This can be useful when certain input packet sizes are expected. 



