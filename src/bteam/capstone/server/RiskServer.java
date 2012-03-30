package bteam.capstone.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.Timer;

/**
 * 
 * @author Austin Langhorne
 * 
 */
public class RiskServer {
	// Static
	private final int maxClients = 25;
	private final int maxInstances = 8;
	// Client
	public ArrayList<String> ClientID;
	public ArrayList<ClientHandler> Clients;
	// Instances
	public ArrayList<Integer> InstanceID;
	public ArrayList<RiskCore> Instances;
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
			// "GameID     WorldName     Creator     Legacy     Password"
			out = "<list>";
			for (RiskCore core : Instances) {
				if (!byUser || byUser && core.getWorldCreator().equals(user)) {
					out += "\n" + core.getWorldID() + " " + core.getWorldName()
							+ " " + core.getWorldCreator() + " "
							+ core.isLegacy() + " " + core.hasPassword();
				}
			}
			out += "\n</list>";
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
					out += "\n" + temp;
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
			File file = new File("Maps/" + gameFile);
			if (file.exists() || gameFile.equals("Default")) {
				RiskCore core = new RiskCore(this, ClientID, gameFile, legacy,
						reserve, pass);
				core.start();
				int num = core.getWorldID();
				InstanceID.add(num);
				Instances.add(core);
				sendList();
				return JoinGame(ClientID, num, pass);
			} else {
				sendTo(ClientID, "no file does not exist");
			}
		} else {
			sendTo(ClientID, "no tables full");
		}
		return -1;
	}

	public void sendList() {
		String out = "";
		out = "<list>";
		for (RiskCore c : Instances) {
			out += "\n" + c.getWorldID() + " " + c.getWorldName() + " "
					+ c.getWorldCreator() + " " + c.isLegacy() + " "
					+ c.hasPassword();
		}
		out += "\n</list>";
		for (ClientHandler c : Clients) {
			if (c.getGameID() == -1)
				c.sendData(out);
		}
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

		public int getGameID() {
			return GameID;
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
					cmd = "create     creates an instance of a game. Additional options seperated by space:\n";
					cmd += "           l r p a, where l is for createing a game instance from an existing game file\n";
					cmd += "           a file name must also be specified after the l. r reserves seats in the game\n";
					cmd += "           a specifies a new legacy version of risk, p is for requireing a password for\n";
					cmd += "           entry into the game also followed by a string for the password\n\n";
					cmd += "list       list the avaliable games on the server. the addition of u followed by a name\n";
					cmd += "           list only games created by that user that is running, s followed by a name\n";
					cmd += "           list all avaliable games saved by the calling user(if any exist)\n\n";
					cmd += "join       followed by an integer representing a currently running game joins the client to\n";
					cmd += "           the game and the addition of a string after the integer specifies the password to\n";
					cmd += "           be used to join the game if it has one\n\n";
					cmd += "exit       disconnects the user from the server\n\n";
					cmd += "stop       warning after 30 seconds all users will be forcefully dissconnected and games shut\n";
					cmd += "           down and then the server will close";
					out.println(cmd);
				} else if (cmd.equals("create")) {
					if (scan.hasNext())
						cmd = scan.nextLine();
					else
						cmd = "";
					GameID = CreateGame(ClientID, cmd);
				} else if (cmd.equals("join") && scan.hasNext()) {
					int worldID = scan.nextInt();
					cmd = "";
					if (scan.hasNext())
						cmd = scan.next();
					GameID = JoinGame(ClientID, worldID, cmd);
				} else if (cmd.equals("list")) {
					if (scan.hasNext())
						cmd = scan.nextLine();
					else
						cmd = "";
					out.println(list(cmd, ClientID));
				} else if (cmd.equals("exit")) {
					this.goodBye();
				} else if (cmd.equals("stop")) {
					stopServer();
				} else {
					out.println("invalid command");
				}
			} else {
				System.out.println(InstanceID.size());
				int num = InstanceID.indexOf(GameID);
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
