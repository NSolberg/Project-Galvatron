package bteam.capstone.risk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.Timer;

public class RiskServer {
	// Static
	private final int maxClients = 25;
	private final int maxInstances = 8;
	// Client
	public ArrayList<String> ClientID;
	public ArrayList<ClientHandler> Clients;
	// Instances
	private ArrayList<Integer> InstanceID;
	private ArrayList<RiskCore> Instances;
	// Server
	private ServerSocket server;
	private boolean serverClosing;

	public static void main(String[] args) {
		new RiskServer();
	}

	public RiskServer() {
		init();
		try {
			serverClosing = false;
			server = new ServerSocket(1337);
			Socket socket;
			do {
				socket = server.accept();
				ClientHandler client = new ClientHandler(socket);
				client.start();
			} while (socket != null);
		} catch (Exception e) {
		}
	}

	private void init() {
		ClientID = new ArrayList<String>();
		Clients = new ArrayList<ClientHandler>();
		InstanceID = new ArrayList<Integer>();
		Instances = new ArrayList<RiskCore>();
		serverClosing = false;
	}

	private synchronized void stopServer() {
		if (!serverClosing) {
			serverClosing = true;
			final Timer t = new Timer(1000, new ActionListener() {
				int tick = 0;

				@Override
				public void actionPerformed(ActionEvent arg0) {
					for (ClientHandler c : Clients) {
						if (c != null)
							c.sendData("Alert Server shut down in "
									+ (30 - tick) + " seconds");
					}
					tick++;
					if (tick > 30) {
						try {
							for (ClientHandler c : Clients) {
								if (c != null)
									c.goodBye();
							}
							for (RiskCore c : Instances) {
								c.stopCore();
							}
							server.close();
						} catch (IOException e) {
						}
					}
				}

			});
			t.start();
		}
	}

	private String list(String Options, String ClientID) {
		boolean saved = false;
		boolean byUser = false;
		String user = "";
		Scanner scan = new Scanner(Options);
		while (scan.hasNext()) {
			String temp = scan.next();
			if (temp.equals("s")) {
				saved = true;
			} else if (temp.equals("u") && scan.hasNext()) {
				byUser = true;
				user = scan.next();
			}
		}
		String out = "";
		if (!saved) {
			out = "GameID     WorldName     Creator     Legacy     Password";
			for (RiskCore core : Instances) {
				if (!byUser || byUser && core.getWorldCreator().equals(user)) {
					out += core.getWorldID() + " " + core.getWorldName() + " "
							+ core.getWorldCreator() + " " + core.isLegacy()
							+ " " + core.hasPassword() + "\n";
				}
			}
		} else {
			File dir = new File(ClientID);
			if (!dir.exists()) {
				dir.mkdir();
				dir = new File(ClientID + "/games.txt");
				try {
					dir.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				dir = new File(ClientID + "/games.txt");
			}
			out = "WorldName     Creator     Legacy     Password";
			try {
				scan = new Scanner(dir);
				while (scan.hasNext()) {
					String temp = scan.nextLine();
					out += temp;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return out;
	}

	private synchronized boolean Reserve(ClientHandler Client) {
		if (ClientID.size() < maxClients
				&& !ClientID.contains(Client.getClientID())) {
			String id = Client.getClientID();
			ClientID.add(id);
			Clients.add(Client);
			return true;
		}
		return false;
	}

	private synchronized void RemoveClient(String client) {
		int num = ClientID.indexOf(client);
		ClientID.remove(num);
		Clients.remove(num);
	}

	private int JoinGame(String ClientID, int GameID, String Pass) {
		if (InstanceID.contains(GameID)) {
			int num = InstanceID.indexOf(GameID);
			if (Instances.get(num).JoinGame(ClientID, Pass))
				return Instances.get(num).getWorldID();
		} else {
			this.sendTo(ClientID, "game does not exist");
		}
		return -1;
	}

	private int CreateGame(String ClientID, String Options) {
		boolean reserve = false;
		boolean legacy = false;
		String gameFile = "Default";
		String pass = "";
		Scanner scan = new Scanner(Options);
		while (scan.hasNext()) {
			String temp = scan.next();
			if (temp.equals("l")) {
				gameFile = scan.next();
			} else if (temp.equals("r")) {
				reserve = true;
			} else if (temp.equals("p")) {
				pass = scan.next();
			} else if (temp.equals("a")) {
				legacy = true;
			}
		}
		if (InstanceID.size() < maxInstances) {
			File file = new File(gameFile);
			if (file.exists()) {
				RiskCore core = new RiskCore(this, ClientID, gameFile, legacy,
						reserve, pass);
				int num = core.getWorldID();
				InstanceID.add(num);
				Instances.add(core);
				return JoinGame(ClientID, num, pass);
			} else {
				sendTo(ClientID, "no file does not exist");
			}
		} else {
			sendTo(ClientID, "no tables full");
		}
		return -1;
	}

	public void sendTo(String clientID, String data) {
		if (ClientID.contains(clientID)) {
			int num = ClientID.indexOf(clientID);
			Clients.get(num).sendData(data);
		}
	}

	class ClientHandler extends Thread {
		private Socket Client;
		private PrintWriter out;
		private Scanner in;
		private String ClientID;
		private int GameID;
		private boolean cont;

		public ClientHandler(Socket socket) {
			Client = socket;
			try {
				out = new PrintWriter(Client.getOutputStream(), true);
				in = new Scanner(Client.getInputStream());
			} catch (IOException e) {
			}
			GameID = -1;
		}

		public String getClientID() {
			return ClientID;
		}

		public void setGameID(int i) {
			GameID = i;
		}

		public void goodBye() {
			out.println("goodbye");
			out.close();
			in.close();
			try {
				Client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			cont = false;
		}

		public void sendData(String data) {
			out.println(data);
		}

		private boolean Greet() {
			ClientID = in.nextLine();
			return Reserve(this);
		}

		private void Comunicate() {
			String input = in.nextLine();
			Scanner scan = new Scanner(input);
			String cmd = scan.next();
			if (GameID == -1) {
				if (cmd.equals("help")) {

				} else if (cmd.equals("create")) {
					cmd = scan.nextLine();
					GameID = CreateGame(ClientID, cmd);
				} else if (cmd.equals("join")) {
					int worldID = scan.nextInt();
					cmd = "";
					if (scan.hasNext())
						cmd = scan.next();
					GameID = JoinGame(ClientID, worldID, cmd);
				} else if (cmd.equals("list")) {
					if (scan.hasNext())
						cmd = scan.nextLine();
					list(cmd, ClientID);
				} else if (cmd.equals("exit")) {
					this.goodBye();
				} else if (cmd.equals("stop")) {
					stopServer();
				} else {
					out.println("invalid command");
				}
			} else {
				int num = InstanceID.get(GameID);
				if (cmd.equals("message")) {
					if (scan.hasNext()) {
						String msg = scan.nextLine();
						for (String client : Instances.get(num).getClients()) {
							sendTo(client, msg);
						}
					}
				} else {
					Instances.get(num).sendData(ClientID + " " + input);
				}
			}
		}

		@Override
		public void run() {
			super.run();
			cont = Greet();
			if (!cont)
				out.println("Server Full");
			else
				out.println("Connected");
			while (cont) {
				try {
					Comunicate();
				} catch (NoSuchElementException e) {
					cont = false;
					RemoveClient(ClientID);
					if (GameID != -1) {
						int num = InstanceID.indexOf(GameID);
						Instances.get(num).sendData(ClientID + " disconect");
					}
				}
			}
		}
	}
}
