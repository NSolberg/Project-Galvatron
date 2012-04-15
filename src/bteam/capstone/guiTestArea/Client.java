package bteam.capstone.guiTestArea;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * 
 * @author Austin Langhorne
 * 
 */
public class Client extends Thread {
	private Socket client;
	private PrintWriter out;
	private Scanner in;
	private boolean linked;
	private ClientUser control;
	private Application app;

	public Client(ClientUser controller,Application app) {
		linked = false;
		control = controller;
		this.app = app;
	}

	public void switchController(ClientUser controller) {
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
			//control.displayData("Link to server established");
		} catch (Exception e) {
			control.displayData("Server not avaliable");
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
				app.switchView(0);
			}
			if (data.length() > 0)
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
