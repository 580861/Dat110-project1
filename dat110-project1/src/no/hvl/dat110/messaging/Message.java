package no.hvl.dat110.messaging;

import java.util.Arrays;

import no.hvl.dat110.TODO;

public class Message {

	private byte[] payload;

	public Message(byte[] payload) {
		
		this.payload = payload;
		if (payload.length > MessageConfig.SEGMENTSIZE) {
			throw new ArrayIndexOutOfBoundsException("Message size cannot exceed 128 byte");
		}
	}

	public Message() {
		super();
	}

	public byte[] getData() {
		return this.payload;
	}

	public byte[] encapsulate() {

		byte[] encoded = new byte[MessageConfig.SEGMENTSIZE];
		encoded[0] = (byte) payload.length;
		for (int i = 0; i < payload.length; i++) {
			encoded[i + 1] = payload[i];

		}

		return encoded;

	}

	public void decapsulate(byte[] received) {

		
		int size = received[0];
		byte[] decapsulate = new byte[size];
		for (int i = 0; i < size; i++) {
			decapsulate[i] = received[i + 1];
		}
		payload = decapsulate;

	}
}
