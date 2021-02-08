package no.hvl.dat110.messaging;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import no.hvl.dat110.TODO;

public class MessagingServer {

	private ServerSocket welcomeSocket;

	public MessagingServer(int port) {

		try {

			this.welcomeSocket = new ServerSocket(port);

		} catch (IOException e) {

			System.out.println("Messaging server: " + e.getMessage());
			e.printStackTrace();
		}
	}

	
	public Connection accept() throws IOException {

		Connection connection = null;
		Socket messaging = welcomeSocket.accept();
		connection = new Connection(messaging);

		return connection;

	}

	public void stop() {

		if (welcomeSocket != null) {

			try {
				welcomeSocket.close();
			} catch (IOException e) {

				System.out.println("Messaging server: " + e.getMessage());
				e.printStackTrace();

			}
		}
	}

}
