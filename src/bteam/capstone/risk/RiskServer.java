package bteam.capstone.risk;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class RiskServer {
	private final static int MaxClients = 25;
	private final static int MaxInstances = 8;
	private final static int Port = 1337;
	private static int CurrentClients = 0;
	private static int CurrentInstances = 0;
	private ServerSocket server;
	private static tempCore[] Instances;
	private static clientHandler[] Clients;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new RiskServer();
	}

	public RiskServer() {
		Instances = new tempCore[MaxInstances];
		Clients = new clientHandler[MaxClients];
		try {
			server = new ServerSocket(Port);
			System.out.println("Server Started");
			while (true) {
				Socket soc = server.accept();
				clientHandler client = new clientHandler(soc);
				client.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int reserveSeat(clientHandler c) {
		int out = -1;
		for (int i = 0; i < MaxClients; i++) {
			if (Clients[i] == null || !Clients[i].isConnected()) {
				out = i;
				break;
			}
		}
		Clients[out] = c;
		return out;
	}

	private class clientHandler extends Thread {
		private Socket soc;
		private PrintWriter out;
		private Scanner in, parse;
		private int seat;
		private String id;
		private boolean connected;
		private int game = -1;

		public clientHandler(Socket socket) throws IOException {
			soc = socket;
			out = new PrintWriter(soc.getOutputStream());
			in = new Scanner(soc.getInputStream());
			connected = false;
			greet();
		}

		public boolean isConnected() {

			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void run() {
			super.run();
			while (connected && !soc.isClosed()) {
				String input = in.nextLine();
				parseInput(input);
			}
		}

		private synchronized void greet() {
			if (CurrentClients < MaxClients) {
				seat = reserveSeat(this);
				connected = true;
				out.println("CONNECTED");
			} else {
				out.println("SERVER FULL");
				out.close();
				in.close();
				try {
					soc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		private void parseInput(String input) {
			parse = new Scanner(input);
			String cmd = parse.next();
			if (cmd.equals("help")) {
				cmd = "<games>  prints a list of all currently running games in the following format:\n";
				cmd += "		i n/5. where i is the identify of the game and n is the number of player \n";
				cmd += "		currently in the game up to five.\n\n";
				cmd += "<join n> add the user to the specified game where n is the identifier of the game.\n\n";
				cmd += "<exit> disconnects the client from the server.\n\n";
				cmd += "<newgame n [savename]> creates an instance of a new risk game and adds the client\n";
				cmd += "		to the game if an instance can be created. The n flag can be either 0 for\n";
				cmd += "		a new game with default settings or 1 and the name of a saved game file to\n";
				cmd += "		load\n\n";
				cmd += "all other commands will be ignored or routed to a game instance if the client is\n";
				cmd += "part of an instance.\n";
			} else if (cmd.equals("games")) {
				cmd = "ID 		Name		Creator 		Players\n";
				for (int i = 0; i < MaxInstances; i++) {
					if (Instances[i] != null && Instances[i].isActive()) {
						cmd += i + " " + " " + Instances[i].getWorldName()
								+ " " + Instances[i].creator() + " "
								+ Instances[i].getNumberPlayers() + "/5\n";
					}
				}
				out.println(cmd);
			} else if (cmd.equals("join")) {
				if (parse.hasNextInt()) {
					int i = parse.nextInt();
					if (Instances[i] != null && Instances[i].isActive()
							&& Instances[i].canJoin()) {
						Instances[i].join(id);
					}
				}
			} else if (cmd.equals("exit")) {
				connected = false;
				out.println("goodbye");
				out.close();
				in.close();
				try {
					soc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (cmd.equals("newgame")) {
				if (CurrentInstances < MaxInstances) {
					if (parse.hasNextInt()) {
						int f = parse.nextInt();
						if (f == 0) {
							// TODO create a new game instance
						} else if (f == 1 && parse.hasNext()) {
							String name = parse.next();
							// TODO load game data into new game instance
						} else {
							out.println("INVALID COMMAND");
						}
					}
				} else {
					out.println("SERVER GAME TABLES FULL");
				}

			} else if (cmd.equals("listsaved")) {
				if (parse.hasNext()) {
					String name = parse.next();
					// TODO list all saved game files by user
				}
				// TODO list all saved game files
			} else if (game > -1 && Instances[game] != null
					& Instances[game].isActive()) {
				Instances[game].send(input);
			}
		}
	}
}
