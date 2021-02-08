package no.hvl.dat110.messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import no.hvl.dat110.TODO;

public class Connection {
	private DataOutputStream outStream; 
	private DataInputStream inStream; 
	private Socket socket; 

	public Connection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream(socket.getInputStream());

		} catch (IOException e) {

			System.out.println("Connection: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void send(Message message) {

		byte[] encap = message.encapsulate();
		try {
			outStream.write(encap, 0, MessageConfig.SEGMENTSIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Message receive() {

		Message message = new Message();
		byte[] recvbuf = new byte[MessageConfig.SEGMENTSIZE];

		

		try {
			inStream.read(recvbuf, 0, MessageConfig.SEGMENTSIZE);
		} catch (IOException e) {
			e.printStackTrace();
			
		}

		message.decapsulate(recvbuf);

		return message;

	}

	
	public void close() {

		try {

			outStream.close();
			inStream.close();

			socket.close();
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}