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


The source consists of a single router called the dispatcher. 
The dispatcher generates packets with a random size between the ***Minimum Packet Size*** and the ***Maximum Packet Size***. Packet generation is dependent on ***Arrival Probability***: If the probability is 1.0, a packet will be generated at every time cycle.
It sends the packets to a collection of routers called the Intermediate Routers. The number of these routers depends on ***Number of Intermediate Routers***. These routers can process one Packet at a time in an internal queue called the buffer. The length of the queue depends on ***Maximum Buffer Size***.
The time it takes for each Packet to be processed is as follows: ***TimeToDestination = PacketSize/Bandwidth***.

The Packets are sent to a final router called destination. 

### Understanding Output

At each time interval, the Packets present in each Intermediate Router is displayed as well as information about which Packets have left the dispatcher and which packets have arrived at the destination (The time that the Packet remained in the router network is displayed with a + upon arrival).



