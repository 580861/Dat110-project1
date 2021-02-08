package no.hvl.dat110.system.sensor;

import no.hvl.dat110.rpc.RPCServer;
import no.hvl.dat110.system.controller.Common;
import no.hvl.dat110.system.display.DisplayImpl;

public class SensorDevice {

	public static void main(String[] args) {
		
		System.out.println("Sensor server starting ...");
		
		DisplayImpl display = new DisplayImpl();
		
		RPCServer server = new RPCServer(Common.SENSORPORT);
		
	    server.register(1,display);
		
		server.run();
		
		server.stop();
		
		System.out.println("Sensor server stopping ...");
		
	}
}
