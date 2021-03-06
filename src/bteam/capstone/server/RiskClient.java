package bteam.capstone.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 
 * @author Austin Langhorne
 *
 */
public class RiskClient extends Thread {
	private Socket client;
	private PrintWriter out;
	private Scanner in;
	private boolean linked;
	private ClientUser control;

	public RiskClient(ClientUser controller) {
		linked = false;
		control = controller;
	}

	public void sendData(String data) {
		out.println(data);
	}

	public void connect(String ip, int port) {
		try {
			client = new Socket(ip, port);
			out = new PrintWriter(client.getOutputStream(), true);
			in = new Scanner(client.getInputStream());
			linked = true;
			control.displayData("Link to server established");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return linked;
	}

	@Override
	public void run() {
		super.run();
		while (linked) {
			String data = in.nextLine();
			if (data.equals("goodbye")) {
				linked = false;
			}
			control.displayData(data);
		}
		control.displayData("Client disconnected from server");
		in.close();
		out.close();
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
