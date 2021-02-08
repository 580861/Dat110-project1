package no.hvl.dat110.rpc;

import java.io.IOException;
import java.util.HashMap;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.Connection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

	private MessagingServer msgserver;
	private Connection connection;

	

	private final HashMap<Integer, RPCImpl> services;

	public RPCServer(int port) {

		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Integer, RPCImpl>();

		services.put((int) RPCCommon.RPIDSTOP, new RPCServerStopImpl());
	}

	public void run()  {

		System.out.println("RPC SERVER RUN - Services: " + services.size());

		try {
			connection = msgserver.accept();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	System.out.println("RPC SERVER ACCEPTED");

	boolean stop = false;

	while (!stop) {

		int rpcid;
		byte[] data;
		data = connection.receive().getData();

		rpcid = data[0];
		
		RPCImpl implement = services.get(rpcid);

		if (implement != null) {
			Message message = new Message(implement.invoke(data));
			connection.send(message);
		}


		if (rpcid == RPCCommon.RPIDSTOP) {
			stop = true;
		}
	}

}

	public void register(int rpcid, RPCImpl impl) {
		services.put(rpcid, impl);
	}

	public void stop() {
		connection.close();
		msgserver.stop();

	}
}
