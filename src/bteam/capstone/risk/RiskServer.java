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
			System.out.println("Server Started");
			while (alive) {
				soc = server.accept();
				clientHandler client = new clientHandler(soc);
				client.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
				tempCore temp = new tempCore(this,id);
				Instances[i] = temp;
				return i;
			}
		}else{
			for (int i = 0; i < CurrentInstances; i++) {
				tempCore temp = new tempCore(this,id,name);
				Instances[i] = temp;
				return i;
			}
		}
		return -1;
	}

	private static void listGames(boolean byUser, String user) {
		// TODO finish me
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
			out = new PrintWriter(soc.getOutputStream());
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
		}

		// Complete
		private synchronized void greet() {
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
					cmd += "						the specified user are printed out.";
					cmd += "JOIN [id]				Attempts to allow the player to join the game specefied by [id]\n";
					cmd += "						where it represents the id of the game to be joined";
					cmd += "CREATE [n/l] <name>		Attempts to create a game instance, where a n specifies a new game\n";
					cmd += "						and l and <name> where name is the name of a saved world loads\n";
					cmd += "						the the world.";
					cmd += "EXIT					Disconnects the client from the server.";
				} else if (cmd.equals("list")) {
					if (scan.hasNext()) {
						cmd = scan.next();
						if (cmd.equals("w")) {
							if (scan.hasNext()) {
								cmd = scan.next();
								if (cmd.equals("Default")) {
									out.println("invalid command");
								} else {
									listGames(true, cmd);
								}
							} else {
								listGames(true, "Default");
							}
						} else if (cmd.equals("g")) {
							if (scan.hasNext()) {
								cmd = scan.next();
								if (cmd.equals("Default")) {
									out.println("invalid command");
								} else {
									listGames(false, cmd);
								}
							} else {
								listGames(false, "Default");
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
						int num = createGame(1, id,cmd);
						Instances[num].join(id);
					} else {
						out.println("invalid command");
					}
				} else if (cmd.equals("exit")) {
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
