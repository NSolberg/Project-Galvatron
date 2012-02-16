package bteam.capstone.risk;

import java.io.File;
import java.io.FileNotFoundException;
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
	private static String helpFile;
	private static boolean alive;
	private Socket soc;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new RiskServer();
	}

	// Complete
	public RiskServer() {
		alive = true;
		Instances = new tempCore[MaxInstances];
		Clients = new clientHandler[MaxClients];
		try {
			server = new ServerSocket(Port);
			while (alive) {
				soc = server.accept();
				clientHandler client = new clientHandler(soc);
				client.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server Stoped");
	}

	public void sendToClient(String clientID, String Data) {
		for (int i = 0; i < MaxClients; i++) {
			if (Clients[i] != null && Clients[i].getID().equals(clientID)) {
				Clients[i].send(Data);
				break;
			}
		}
	}

	// Complete
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

	private synchronized void closeServer() {
		if (alive) {
			alive = false;
			for (int i = 0; i < MaxClients; i++) {
				if (Clients[i] != null && Clients[i].isConnected()) {
					Clients[i].goodBye();
					Clients[i] = null;
				}
			}
			for (int i = 0; i < MaxInstances; i++) {
				if (Instances[i] != null) {
					Instances[i].closeGame();
				}
			}
			try {
				soc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private synchronized int createGame(int f, String id, String name) {
		if (f == 0) {
			for (int i = 0; i < CurrentInstances; i++) {
				tempCore temp = new tempCore(this, id);
				Instances[i] = temp;
				return i;
			}
		} else {
			for (int i = 0; i < CurrentInstances; i++) {
				tempCore temp = new tempCore(this, id, name);
				Instances[i] = temp;
				return i;
			}
		}
		return -1;
	}

	// Complete maybe
	private static String listGames(boolean saved, String user) {
		String out = "";
		if (saved) {
			out = "World Name 		Creator\n";
			File dir = new File("Saved Games");
			String games[] = dir.list();
			Scanner temp;
			if (games != null) {
				for (int i = 0; i < games.length; i++) {
					dir = new File("Saved Games/" + games[i] + "/index.txt");
					try {
						temp = new Scanner(dir);
						String name = temp.next();
						String creator = temp.next();
						if (user != "Default" && creator.equals(user)) {
							out += name + " 	" + creator + "\n";
						} else {
							out += name + " 	" + creator + "\n";
						}
						temp.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}

		} else {
			out = "Game ID 		World Name 		Creator\n";
			for (int i = 0; i < MaxInstances; i++) {
				if (Instances[i] != null) {
					if (user != "Default"
							&& Instances[i].creator().equals(user)) {
						out += i + " 		" + Instances[i].getName() + " 		"
								+ Instances[i].creator() + "\n";
					} else {
						out += i + " 		" + Instances[i].getName() + " 		"
								+ Instances[i].creator() + "\n";
					}
				}
			}
		}
		return out;
	}

	// Complete
	private class clientHandler extends Thread {
		private Socket soc;
		private PrintWriter out;
		private Scanner in;
		private String id;
		private boolean connected;
		private int game = -1;

		// Complete
		public clientHandler(Socket socket) throws IOException {
			soc = socket;
			out = new PrintWriter(soc.getOutputStream(),true);
			in = new Scanner(soc.getInputStream());
			connected = false;
			greet();
		}

		// Complete
		public void goodBye() {
			out.println("goodbye");
			out.close();
			in.close();
			try {
				soc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Complete
		public boolean isConnected() {
			return !soc.isClosed();
		}

		// Complete
		public void send(String data) {
			out.println(data);
		}

		// Complete
		public String getID() {
			return id;
		}

		// Complete
		public void setGame(int num) {
			game = num;
		}

		// Complete
		@Override
		public void run() {
			super.run();
			while (connected && !soc.isClosed()) {
				String input = in.nextLine();
				parseInput(input);
			}
			System.out.println("Client Stoped");
		}

		// Complete
		private synchronized void greet() {
			out.println("Whats your ID");
			id = in.nextLine();
			if (CurrentClients < MaxClients) {
				reserveSeat(this);
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

		// Complete
		private void parseInput(String input) {
			if (game == -1) {
				Scanner scan = new Scanner(input);
				String cmd = scan.next();
				if (cmd.equals("help")) {
					cmd = "HELP 					list all the avaliable commands\n";
					cmd += "LIST [w,g] <user>		with the additional specification of w prints out to the screen\n";
					cmd += "						all the saved worlds, g prints out all the avaliable games. With\n";
					cmd += "						and addition to u after w or g list only those created by the\n";
					cmd += "						the specified user are printed out.\n";
					cmd += "JOIN [id]				Attempts to allow the player to join the game specefied by [id]\n";
					cmd += "						where it represents the id of the game to be joined\n";
					cmd += "CREATE [n/l] <name>		Attempts to create a game instance, where a n specifies a new game\n";
					cmd += "						and l and <name> where name is the name of a saved world loads\n";
					cmd += "						the the world.\n";
					cmd += "EXIT					Disconnects the client from the server.\n";
					out.println(cmd);
				} else if (cmd.equals("list")) {
					if (scan.hasNext()) {
						cmd = scan.next();
						if (cmd.equals("w")) {
							if (scan.hasNext()) {
								cmd = scan.next();
								if (cmd.equals("Default")) {
									out.println("invalid command");
								} else {
									out.println(listGames(true, cmd));
								}
							} else {
								out.println(listGames(true, "Default"));
							}
						} else if (cmd.equals("g")) {
							if (scan.hasNext()) {
								cmd = scan.next();
								if (cmd.equals("Default")) {
									out.println("invalid command");
								} else {
									out.println(listGames(false, cmd));
								}
							} else {
								out.println(listGames(false, "Default"));
							}
						} else {
							out.println("invalid command");
						}
					} else {
						out.println("invalid command");
					}

				} else if (cmd.equals("join") && scan.hasNextInt()) {
					int num = scan.nextInt();
					if (num < MaxInstances && Instances[num] != null) {
						if (Instances[num].canJoin()) {
							Instances[num].join(id);
						} else {
							out.println("cannot join game");
						}
					} else {
						out.println("game does not exist");
					}
				} else if (cmd.equals("create") && scan.hasNext()) {
					cmd = scan.next();
					if (cmd.equals("n")) {
						int num = createGame(0, id, "Default");
						Instances[num].join(id);
					} else if (cmd.equals("l") && scan.hasNext()) {
						cmd = scan.next();
						int num = createGame(1, id, cmd);
						Instances[num].join(id);
					} else {
						out.println("invalid command");
					}
				} else if (cmd.equals("exit")) {
					out.println("goodbye");
					out.close();
					in.close();
					try {
						soc.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (cmd.equals("stop")) {
					closeServer();
				} else {
					out.println("invalid command");
				}
			} else {
				Instances[game].sendToGame(input);
			}
		}
	}
}
