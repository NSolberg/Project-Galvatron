package bteam.capstone.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import bteam.capstone.faction.AliensFaction;
import bteam.capstone.faction.MutantFaction;
import bteam.capstone.faction.StandardFaction;
import bteam.capstone.risk.Country;
import bteam.capstone.risk.Map;
import bteam.capstone.risk.RiskCard;
import bteam.capstone.risk.player;

public class RiskCore extends Thread {
	// Server Data
	private RiskServer theServer;
	// Player Data
	private int numPlayers;
	private int maxPlayers;
	public ArrayList<player> activePlayer = new ArrayList<player>();
	// Game Data
	Stack<RiskCard> cardDeck = new Stack<RiskCard>();
	private boolean islegacy;
	private boolean inGame;
	private boolean reserveSeat;
	private String password;
	private String dataBuffer;
	// World Data
	private int worldID;
	private String worldCreator;
	private String worldName;
	private String worldFile;
	// Map Data
	// private Map world;
	private ArrayList<Integer> resourceCards;
	private StandardFaction[] originalFac;
	private AliensFaction alienFac;
	private MutantFaction mutantFac;
	// place for mission cards
	// place for scar cards
	// place for event cars
	private int coinCards;
	// Attacking
	public ArrayList<player> playerStack = new ArrayList<player>();
	private int numOfDice;
	private boolean isAttacking;
	public ArrayList<Integer> missionAvail = new ArrayList<Integer>();
	private boolean newMissionCard = true;
	private int missionInt = -1;

	public RiskCore(RiskServer riskServer, String clientID, String gameFile,
			boolean legacy, boolean reserve, String pass) {
		// init start
		Random ran = new Random();
		theServer = riskServer;
		numPlayers = 0;
		maxPlayers = 6;
		if (legacy)
			maxPlayers--;
		islegacy = legacy;
		inGame = false;
		reserveSeat = reserve;
		password = pass;
		dataBuffer = "";
		worldFile = gameFile;
		if (true) {
			worldCreator = clientID;
			worldID = ran.nextInt(10000000);
			worldName = gameFile;
		} else {
			File file = new File(gameFile + "/index.txt");
			try {
				Scanner scan = new Scanner(file);
				worldCreator = scan.nextLine();
				worldID = scan.nextInt();
				scan.nextLine();
				worldName = scan.nextLine();
			} catch (FileNotFoundException e) {
			}
		}
		// init end

		// for lobby
		pName = new String[maxPlayers];
		pColor = new int[maxPlayers];
		pRdy = new boolean[maxPlayers];
		colors = new ArrayList<Integer>();
		for (int i = 0; i < maxPlayers; i++)
			colors.add(i);
	}

	boolean playing;

	public Map world;

	@Override
	public void run() {
		super.run();
		playing = true;
		createPlayers();
		initMap();
		intialTurnRisk(numPlayers);
		informAll(this.toString());
		while (true) {
			this.informAll("turn " + this.activePlayer.get(0));
			// cardPhase();
			recruitPhase();
			attackPhase();
			fortifyPhase();
			drawPhase();
			passTurn();
		}
		declareWinner();
	}

	private void recruitPhase() {
		// troops based on territories & countries owned
		int num = world.recruitTroops(activePlayer.get(0).getCountrys(),
				activePlayer.get(0).getClientID());
		// recruited troops added to player supply
		this.activePlayer.get(0).setTroops(
				num + this.activePlayer.get(0).getTroops());
		// current player is informed of the troops to place
		theServer.sendTo(activePlayer.get(0).getClientID(), "phase recruit "
				+ activePlayer.get(0).getTroops());
		// while the current player still has troops
		while (activePlayer.get(0).getTroops() > 0) {
			String data = this.getDataFromBuffer();
			Scanner scan = new Scanner(data);
			// get player id
			String cmd = scan.next();
			// verify player id
			if (cmd.equals(activePlayer.get(0).getClientID())) {
				cmd = scan.next();
				if (cmd.equals("sel")) {
					String ctry = "";
					if (scan.hasNext()) {
						ctry = scan.next();
						int local = world.getCountryByName(ctry);
						if (scan.hasNextInt()
								&& local > -1
								&& activePlayer.get(0).getCountrys()
										.contains(local)) {
							num = scan.nextInt();
							// increment country troops qty.
							world.getCountry(local).setTroopQuantity(
									world.getCountry(local).getTroopQuantity()
											+ num);
							// decrement current player troop pool
							activePlayer.get(0).setTroops(
									activePlayer.get(0).getTroops() - num);
							// inform players of changes
							this.informAll("set "
									+ activePlayer.get(0).getClientID()
									+ " "
									+ world.getCountry(local).getCountryName()
									+ " "
									+ world.getCountry(local)
											.getTroopQuantity());
							// inform current player of troop decrement
							theServer.sendTo(activePlayer.get(0).getClientID(),
									"phase recruit "
											+ activePlayer.get(0).getTroops());
						}
					}
				}
			}
		}
	}

	private void attackPhase() {
		// inform current player of phase
		theServer.sendTo(activePlayer.get(0).getClientID(), "phase attack");
		String cmd = "";
		do{
			String data = this.getDataFromBuffer();
			Scanner scan = new Scanner(data);
			//get player id
			cmd = scan.next();
			//verify player id
			if(cmd.equals(activePlayer.get(0).getClientID())){
				
			}
		}while(!cmd.equals("fin"));
	}

	private void createPlayers() {
		for (int i = 0; i < maxPlayers; i++) {
			if (pName[i] != null) {
				player p = new player();
				p.setClientID(pName[i]);
				this.activePlayer.add(p);
			}
		}
	}

	private void initMap() {
		try {
			Scanner scan = new Scanner(new File("Earth.txt"));
			String out = "";
			while (scan.hasNext())
				out += scan.nextLine() + "\n";
			world = new Map(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int getWorldID() {
		return worldID;
	}

	public String getWorldName() {
		return worldName;
	}

	public String getWorldCreator() {
		return worldCreator;
	}

	public boolean isLegacy() {
		return islegacy;
	}

	public boolean hasPassword() {
		if (password.length() > 0)
			return true;
		return false;
	}

	public void stopCore() {
		// TODO Auto-generated method stub

	}

	public String[] getClients() {
		String[] out = new String[numPlayers];
		int pos = 0;
		for (int i = 0; i < maxPlayers; i++) {
			if (pName[i] != null) {
				out[pos] = pName[i];
				pos++;
			}
		}
		return out;
	}

	/**
	 * @author Austin Langhorne
	 * 
	 * @param clientID
	 *            ID of the client that wishes to join
	 * @param pass
	 *            password input by the calling client for the game
	 * @return true if the client joins the game else false
	 */
	public boolean JoinGame(String clientID, String pass) {
		if (password.equals(pass)) {
			if (this.canJoin(clientID)) {
				this.addToLobby(clientID);
				theServer.sendTo(clientID, "yes");
				String out = null;
				for (int i = 0; i < maxPlayers; i++) {
					if (pName[i] != null && pName[i].equals(clientID)) {
						out = i + " add " + pName[i] + " " + pColor[i] + " "
								+ pRdy[i];
						break;
					}
				}
				this.informAll(out);
				return true;
			} else {
				theServer.sendTo(clientID, "no server full");
			}

		} else {
			theServer.sendTo(clientID, "Alert no wrong pass");
		}
		return false;
	}

	private boolean canJoin(String clientID) {
		if (numPlayers < maxPlayers && !inGame) {
			return true;
		} else {
			return false;
		}
	}

	private void addToLobby(String client) {
		for (int i = 0; i < maxPlayers; i++) {
			if (pName[i] == null) {
				pName[i] = client;
				pColor[i] = colors.remove(0);
				pRdy[i] = false;
				this.numPlayers++;
				break;
			}
		}
	}

	private void informAll(String data) {
		for (int j = 0; j < maxPlayers; j++) {
			theServer.sendTo(pName[j], data);
		}
	}

	/**
	 * @author Austin Langhorne
	 * 
	 *         to be used by server to redirect data to this core class. accepts
	 *         two commands: leave and start leave can be used in game or in the
	 *         lobby start can only be used from the lobby by the creator of
	 *         this instance and when there is at least 3 players
	 * @param data
	 *            to be sent from server to this core
	 */
	public synchronized void sendData(String data) {
		Scanner scan = new Scanner(data);
		String client = scan.next();
		String cmd = scan.nextLine();
		if (!this.inGame) {
			if (cmd.equals("help")) {

			} else if (cmd.equals("start")) {
				this.handleStart(client);
			} else if (cmd.equals("leave")) {
				this.handleLeave(client);
			} else if (cmd.equals("state")) {
				this.handleState(client);
			} else if (cmd.equals("col")) {
				this.handleColor(client);
			} else if (cmd.equals("rdy")) {
				this.handleReady(client);
			} else {
				theServer.sendTo(client, "Alert invalid command");
			}
		} else {
			this.dataBuffer += cmd;
		}
	}

	String[] pName;
	int[] pColor;
	boolean[] pRdy;
	ArrayList<Integer> colors;

	private void handleStart(String client) {
		if (worldCreator.equals(client) && numPlayers >= 3)
			if (numPlayers > 2) {
				boolean canStart = true;
				for (int i = 0; i < maxPlayers; i++) {
					if (pName[i] != null && !pRdy[i]) {
						canStart = false;
						break;
					}
				}
				if (canStart) {
					this.informAll("start");
					inGame = true;
					this.start();
				}
			} else
				theServer.sendTo(client, "Alert no not enough players");
		else
			theServer.sendTo(client, "Alert no not creator");
	}

	private void handleLeave(String client) {
		if (!inGame) {
			int pos = -1;
			for (int i = 0; i < maxPlayers; i++) {
				if (pName[i].equals(client)) {
					pos = i;
					break;
				}
			}
			if (pos != -1) {
				if (pName[pos] != null) {
					informAll(pos + " remove");
					theServer.sendTo(pName[pos], "leave");
					pName[pos] = null;
					colors.add(pColor[pos]);
				}
				if (pos == 0) {
					for (int i = 0; i < maxPlayers; i++) {
						if (pName[i] != null) {
							theServer.sendTo(pName[i], "leave");
							pName[i] = null;
							theServer.sendTo(pName[i], "Alert Host Left Game");
						}
					}
					int num = theServer.InstanceID.indexOf(this.worldID);
					theServer.Instances.remove(num);
					theServer.InstanceID.remove(num);
					theServer.sendList();
				}
				this.numPlayers--;
				int num = theServer.ClientID.indexOf(client);
				theServer.Clients.get(num).setGameID(-1);
			}
		}
	}

	private void handleState(String client) {
		if (!inGame) {
			String out = "map " + this.worldName;
			if (worldCreator.equals(client)) {
				out += "\nmaster";
			}
			for (int i = 0; i < maxPlayers; i++) {
				if (pName[i] != null) {
					out += "\n" + i + " add " + pName[i] + " " + pColor[i]
							+ " " + pRdy[i];
				} else {
					out += "\n" + i + " remove";
				}
			}
			theServer.sendTo(client, out);
		}
	}

	private void handleColor(String client) {
		int num = -1;
		for (int i = 0; i < maxPlayers; i++) {
			if (pName[i] != null && pName[i].equals(client)) {
				num = i;
				break;
			}
		}
		if (num > -1 && colors.size() > 0) {
			colors.add(pColor[num]);
			pColor[num] = colors.remove(0);
			informAll(num + " col " + pColor[num]);
		}
	}

	private void handleReady(String client) {
		int num = -1;
		for (int i = 0; i < maxPlayers; i++) {
			if (pName[i] != null && pName[i].equals(client)) {
				num = i;
				break;
			}
		}
		if (num > -1) {
			pRdy[num] = !pRdy[num];
			if (pRdy[num]) {
				informAll(num + " rdy");
			} else {
				informAll(num + " nrdy");
			}
		}
	}

	/**
	 * @author Austin Langhorne
	 * 
	 *         to be used to rout data from the core to a player
	 * @param client
	 *            clientID of the player to send data to
	 * @param data
	 *            to send to player
	 */
	public void sendDataToClient(String client, String data) {
		theServer.sendTo(client, data);
	}

	/**
	 * @author Austin Langhorne
	 * 
	 * @return a line of data in the dataBuffer
	 */
	public String getDataFromBuffer() {
		Scanner scan = new Scanner(dataBuffer);
		String out = scan.nextLine();
		dataBuffer = dataBuffer.substring(out.length());
		return out;
	}

	/**
	 * game loop: pass turn Start of Turn Join the War/Recruit Troops
	 * Expand&Attack Maneuver Troops End of Turn End of Game
	 */
	private boolean isDraft; // Weather or not draft cards are active
	private int numofPlayers;
	private player[] players;
	private ArrayList<Integer> whosTurn;

	/**
	 * @author Ian Paterson
	 * 
	 * @param legacy
	 *            : Determines weatehr or not it is standard or legacy
	 * @description The purpose of this method is to set up the first turn of
	 *              any Risk standard or Risk Legacy game. Its first function is
	 *              it takes in the number of players that will be participating
	 *              and add's them too two arrayList's that keep track of who's
	 *              turn it is and whosPlace which keeps track of whos "turn" it
	 *              is to place troops. If draft cards are active this method
	 *              becomes far more complicates as we have to go through the
	 *              various types of draft cards which determine placement
	 *              order, the amount of troops you can place, the turn order
	 *              for the game to be played, the number of "coins" you will
	 *              receive. Draft cards are selected by random assignment after
	 *              the arraylist of cards is shuffled and they are
	 *              "dealed out". After a card has been assigned it is removed
	 *              from the array list. IF draft cards are not active, then
	 *              these values are all assigned to their standard values as
	 *              prescribed by the game rules. If it is standard risk then
	 *              this is far simpler and most of the values are not even
	 *              needed, they are simply initialized to their standard
	 *              values.
	 * 
	 * @TODO The client/server communication needs to be implimented by austin.
	 * @TODO Implementations of faction assighnment
	 * @TODO Other faction ablilites need to be added
	 */
	public void firstTurnSetup(boolean legacy) {

		ArrayList<Integer> whosPlace = new ArrayList<Integer>();
		for (int i = 0; i < numofPlayers; i++) {
			whosTurn.add(i);
			whosPlace.add(i);
		}
		if (legacy) {
			if (isDraft) {
				ArrayList<Integer> placement = new ArrayList<Integer>();
				placement.add(1);
				placement.add(2);
				placement.add(3);
				if (numofPlayers > 3) {
					placement.add(4);
					if (numofPlayers > 4)
						placement.add(5);
				}

				ArrayList<Integer> turnOrder = new ArrayList<Integer>();
				turnOrder.add(1);
				turnOrder.add(2);
				turnOrder.add(3);
				if (numofPlayers > 3) {
					turnOrder.add(4);
					if (numofPlayers > 4)
						turnOrder.add(5);
				}
				ArrayList<Integer> troops = new ArrayList<Integer>();
				troops.add(6);
				troops.add(8);
				troops.add(10);
				if (numofPlayers > 3) {
					troops.add(8);
					if (numofPlayers > 4)
						troops.add(10);
				}
				ArrayList<Integer> coins = new ArrayList<Integer>();
				coins.add(0);
				coins.add(1);
				coins.add(2);
				if (numofPlayers > 3) {
					coins.add(0);
					if (numofPlayers > 4)
						coins.add(1);
				}
				// Temproray order of players for choosing draft cards
				ArrayList<Integer> tempOrder = new ArrayList<Integer>();
				for (int i = 0; i < numofPlayers; i++) {
					tempOrder.add(i);
				}
				Random ran = new Random();
				// Randomizes temporary order
				for (int i = 0; i < numofPlayers; i++) {
					int c1 = i;
					int c2 = ran.nextInt(numofPlayers);
					int temp = tempOrder.get(c1);
					tempOrder.set(c1, tempOrder.get(c2));
					tempOrder.set(c2, temp);
				}
				while (troops.size() != 0 || coins.size() != 0
						|| turnOrder.size() != 0 || placement.size() != 0) {
					int p = tempOrder.remove(0);
					// Send to player p: "Choose draft card"
					// Get choice from player
					String choice = "p 0";
					int val = Integer.parseInt(choice.charAt(2) + "");
					if (choice.charAt(0) == 'p') {
						val = placement.remove(val);
						whosPlace.set(val, p);
					} else if (choice.charAt(0) == 't') {
						players[p].setTroops(troops.remove(val));
					} else if (choice.charAt(0) == 'c') {
						players[p].setCoin(coins.remove(val));
					} else if (choice.charAt(0) == 'o') {
						val = turnOrder.remove(val);
						whosTurn.set(val, p);
					}
				}

			} else {
				// non draft
				Random ran = new Random();
				for (int i = 0; i < numofPlayers; i++) {
					int c1 = i;
					int c2 = ran.nextInt(numofPlayers);
					int temp = whosTurn.get(c1);
					whosTurn.set(c1, whosTurn.get(c2));
					whosTurn.set(c2, temp);
					players[i].setTroops(8);
					players[i].setCoin(0);
				}
				whosPlace = whosTurn;
			}
		}
	}

	/*
	 * Ignore, this will form the text based portion. ALMOST DONE!
	 */

	/**
	 * 
	 * TODO finish the code where commented and test
	 * 
	 * @param players
	 *            number of players
	 */
	public void playGame(int players) {
		Scanner scan = new Scanner(System.in);
		intialTurnRisk(players);
		boolean gameOver = false;
		boolean rein = false;
		int i = 0;
		while (gameOver != true) {
			for (i = 0; i < activePlayer.size(); i++) {
				drawMissionCard();
				System.out.println(activePlayer.get(i).getName()
						+ "... it is your turn.");
				writeOut();
				addTroops(activePlayer.get(i));
				System.out.println("");
				boolean attack = true;
				rein = false;
				writeOut();
				if (rein == false) {
					System.out.println("Would you like to reinforce now?");
					if (scan.next().equals("yes")) {
						System.out
								.println("enter country to move troops from followed by destination and then amount");
						int country1 = scan.nextInt();
						int country2 = scan.nextInt();
						int quant = scan.nextInt();
						world.moveToops(country1, country2, quant);
						writeOut();
					}
				}
				// Turn in cards? reinforce troops.
				while (attack) {
					System.out
							.println("Enter your country followed by the one you wish to attack");
					int country1 = scan.nextInt();
					int country2 = scan.nextInt();
					attack(world.getCountry(country1),
							world.getCountry(country2));
					System.out.println("attack another country?");
					if (scan.next().equals("no")) {
						attack = false;
					}
				}
				writeOut();
				for (int l = 0; l < activePlayer.size(); l++) {
					if (isEliminated(activePlayer.get(l))) {
						activePlayer.remove(l);
					}
				}
				if (activePlayer.size() < 2) {
					gameOver = true;
				}
				// Ask to move troops
				// End of turn next players
				// If a player is eliminated remove from the list.
				if (rein == false) {
					System.out.println("Would you like to reinforce now?");
					if (scan.next().equals("yes")) {
						int country1 = scan.nextInt();
						int country2 = scan.nextInt();
						int quant = scan.nextInt();
						world.moveToops(country1, country2, quant);
						rein = true;
					}
				}

				for (int k = 0; k < activePlayer.size(); k++) {
					if (activePlayer.get(k).isEliminated() == true) {
						activePlayer.remove(k);
					}
				}

				if (activePlayer.size() < 2) {
					gameOver = true;
				}
			}
			i = 0;
		}

	}

	private boolean isEliminated(player aPlayer) {
		if (aPlayer.getCountrys().size() < 1) {
			return true;
		} else {
			return false;
		}
	}

	public void intialRandomRiskCardDeck() {

		for (int i = 0; i < 42; i += 3) {
			cardDeck.add(new RiskCard(1, world.countrys.get(i)));
			cardDeck.add(new RiskCard(2, world.countrys.get(i + 1)));
			cardDeck.add(new RiskCard(3, world.countrys.get(i + 2)));
		}

		Collections.shuffle(cardDeck);

	}

	public void turnInCards(player aPlayer, int a, int b, int c) {
		boolean valid = false;
		if (aPlayer.getCards().get(a) != aPlayer.getCards().get(b)
				|| aPlayer.getCards().get(a) != aPlayer.getCards().get(c)
				|| aPlayer.getCards().get(b) != aPlayer.getCards().get(c)) {
			valid = true;
		}
		if (aPlayer.getCards().get(a) == aPlayer.getCards().get(b)
				|| aPlayer.getCards().get(a) == aPlayer.getCards().get(c)) {
			valid = true;
		}
		System.out.println("No valid card combinations");
		if (valid) {
			switch (aPlayer.getSets()) {
			case 1:
				aPlayer.setTroops(aPlayer.getTroops() + 4);
				// 4
				break;
			case 2:
				// 6+
				aPlayer.setTroops(aPlayer.getTroops() + 6);
				break;
			case 3:
				// 8
				aPlayer.setTroops(aPlayer.getTroops() + 8);
				break;
			case 4:
				// 10
				aPlayer.setTroops(aPlayer.getTroops() + 10);
				break;
			case 5:
				// 12
				aPlayer.setTroops(aPlayer.getTroops() + 12);
				break;
			case 6:
				// 15
				aPlayer.setTroops(aPlayer.getTroops() + 15);
				break;
			}

			if (aPlayer.getSets() > 6) {
				aPlayer.setTroops(aPlayer.getTroops() + 15 + 5
						* aPlayer.getSets() - 6);
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void intialTurnRisk(int players) {

		// Step 1: assign troops to players
		int numTroops = 35 - 5 * (players - 3);
		for (player p : this.activePlayer) {
			p.setTroops(numTroops);
		}
		// Step 2: distribute countries to players & add 1 troop from each
		// player to countries;
		ArrayList<Country> temp = (ArrayList<Country>) world.countrys.clone();
		Collections.shuffle(temp);
		int pos = 0;
		for (Country c : temp) {
			activePlayer.get(pos).addCountry(c.id());
			activePlayer.get(pos).setTroops(
					activePlayer.get(pos).getTroops() - 1);
			world.countrys.get(c.id()).setOwner(activePlayer.get(pos));
			world.countrys.get(c.id()).setTroopQuantity(1);
			// world.
			pos++;
			if (pos > this.activePlayer.size() - 1)
				pos = 0;
		}
		// Step 3: Randomly distribute remaining player troops among player
		// countries
		Random ran = new Random();
		for (player p : this.activePlayer) {
			int num = p.getCountrys().size();
			while (p.getTroops() > 0) {
				pos = ran.nextInt(num);
				p.setTroops(p.getTroops() - 1);
				Country c = world.countrys.get(p.getCountrys().get(pos));
				c.setTroopQuantity(c.getTroopQuantity() + 1);
			}
		}
		// Step 4: Randomize player turns
		Collections.shuffle(this.activePlayer);
	}

	public void addTroops(player player) {
		Scanner scan = new Scanner(System.in);
		int recruitable = world.recruitTroops(player.getCountrys(),
				player.getName());
		System.out.println("Available troops " + recruitable);
		System.out.println("Available countrys to reinforce: ");
		for (int i = 0; i < player.getCountrys().size(); i++) {
			System.out.print(world.countrys.get(player.getCountrys().get(i))
					.getCountryName() + " ");
		}
		System.out.println();
		while (recruitable > 0) {
			System.out.println("Available troops " + recruitable);
			System.out.println("Choose a country ");
			int country = scan.nextInt();
			System.out.println(world.countrys.get(country).getCountryName());
			System.out.println("How many troops will you reinforce with");
			int reinforce = scan.nextInt();
			world.countrys.get(country).setTroopQuantity(
					world.countrys.get(country).getTroopQuantity() + reinforce);
			System.out.println("Added " + reinforce + " to "
					+ world.countrys.get(country).getCountryName());
			recruitable = recruitable - reinforce;
		}

	}

	/**
	 * @author Nick Solberg
	 * @param atkCountry
	 *            represents the attackers country
	 * @param defCountry
	 *            represents the defenders country
	 * 
	 *            This method will resolve an attack phase between two players
	 *            Given the attacking country and defending country the method
	 *            will retrieve the number of troops each side has. The
	 *            attacking player will be allowed to choose how many dice will
	 *            be rolled in their phase, given the attacker appropriate has
	 *            available resources. The attacker is also given the option to
	 *            cancel the attack after any round. The loop will stop when the
	 *            attacking player has 1 troop or the denfeder has 0. Given a
	 *            player has missles. They are offered the chance to use one and
	 *            pick the dice they would like to apply it too. Each player
	 *            will be asked twice whether they would like to or not. The
	 *            second time being a double check.
	 * 
	 *            TODO Later modify this method to work with a gui and client
	 *            City and Fortifications.
	 */
	// playTestGui gui = new playTestGui();
	public void attack(Country atkCountry, Country defCountry) {
		//Scanner inScan = new Scanner(System.in);
		String missleTemp = "";
		int loopUntil = 0;
		int numAtkDice = 0;
		int numDefDice = 0;
		int misslesUsed = 0;
		boolean missleOnDefender = false;
		boolean isAttacking = true;
		if (atkCountry.getCountryBorders().contains(defCountry.id()) == false) {
			System.out.println("This will be an invalid attack");
			isAttacking = false;
		}
		// else if (isAttacking == true) {
		while (isAttacking == true && defCountry.getTroopQuantity() > 0
				&& atkCountry.getTroopQuantity() > 1) {
			System.out.println("Attack continues, valid arguments");
			int switchVal = 0;
			if (atkCountry.getTroopQuantity() >= 4) {

				switchVal += 10;
			} else if (atkCountry.getTroopQuantity() == 3) {

				switchVal += 5;
			} else if (atkCountry.getTroopQuantity() == 2) {

				switchVal += 2;
			}
			if (atkCountry.getTroopQuantity() == 1) {
				System.out
						.println("Not enough troops to attack, must be greater than one");
				break;
			}

			if (defCountry.getTroopQuantity() >= 2) {
				switchVal += 2;
			} else if (defCountry.hasHQ() == true) {
				switchVal += 2;
			}

			System.out.println(switchVal);
			System.out.println("Current attackers troops: "
					+ atkCountry.getTroopQuantity());
			System.out.println("Current defenders troops: "
					+ defCountry.getTroopQuantity());
			Scanner defInScanner = new Scanner(System.in);
			defInScanner.reset();
			String temp;
			String temp2;
			if (switchVal == 4) {
				// sendToClient(defCountry.getOwner(),
				// "Would you like to roll one or two dice");
				// temp = getClientResponse();
				System.out
						.println("Defender would you like to roll one or two dice?");
				temp = defInScanner.next();
				if (temp.equals("one")) {
					switchVal = 2;
					System.out.println("1v1");
				} else if (temp.equals("two")) {
					switchVal = 4;
					System.out.println("1v2");
				}
			}
			if (switchVal == 5) {
				// sendToClient(atkCountry.getOwner(),
				// "Would you like to roll one or two dice");
				// temp = getClientResponse();
				System.out
						.println("Attacker would you like to roll one or two dice?");
				temp = defInScanner.next();
				if (temp.equals("one")) {
					switchVal = 2;
					System.out.println("1v1");
				} else if (temp.equals("two")) {
					switchVal = 5;
					System.out.println("2v1");
				}
			}

			if (switchVal == 7) {
				// sendToClient(atkCountry.getOwner(),
				// "Would you like to roll one or two dice");
				// temp = getClientResponse();
				System.out
						.println("Attacker would you like to roll one or two dice?");
				temp = defInScanner.next();
				// sendToClient(defCountry.getOwner(),
				// "Would you like to roll one or two dice");
				// temp = getClientResponse();
				System.out
						.println("Defender would you like to roll one or two dice?");
				temp2 = defInScanner.next();
				if (temp.equals("one") && temp2.equals("two")) {
					switchVal = 4;
					System.out.println("1v2");
				} else if (temp.equals("two") && temp2.equals("two")) {
					switchVal = 7;
					System.out.println("2v2");
				} else if (temp.equals("one") && temp2.equals("one")) {
					switchVal = 2;
					System.out.println("1v1");
				} else if (temp.equals("two") && temp2.equals("one")) {
					switchVal = 5;
					System.out.println("2v1");
				}
			}

			if (switchVal == 10) {
				// sendToClient(atkCountry.getOwner(),
				// "Would you like to roll one, two, or three dice");
				// temp = getClientResponse();
				System.out
						.println("Attacker would you like to roll one, two, or three dice?");
				temp = defInScanner.next();
				if (temp.equals("one")) {
					switchVal = 2;
					System.out.println("1v1");
				} else if (temp.equals("two")) {
					switchVal = 5;
					System.out.println("2v1");
				} else if (temp.equals("three")) {
					switchVal = 10;
					System.out.println("3v1");
				}
			}

			if (switchVal == 12) {
				// sendToClient(atkCountry.getOwner(),
				// "Would you like to roll one or two dice");
				// temp = getClientResponse();
				System.out
						.println("Attacker would you like to roll one, two, or three dice?");
				temp = defInScanner.next();
				// sendToClient(defCountry.getOwner(),
				// "Would you like to roll one or two dice");
				// temp = getClientResponse();
				System.out
						.println("Defender would you like to roll one or two dice?");
				temp2 = defInScanner.next();
				if (temp.equals("one") && temp2.equals("two")) {
					switchVal = 4;
					System.out.println("1v2");
				} else if (temp.equals("two") && temp2.equals("two")) {
					switchVal = 7;
					System.out.println("2v2");
				} else if (temp.equals("three") && temp2.equals("two")) {
					switchVal = 12;
					System.out.println("3v2");
				} else if (temp.equals("one") && temp2.equals("one")) {
					switchVal = 2;
					System.out.println("1v1");
				} else if (temp.equals("two") && temp2.equals("one")) {
					switchVal = 5;
					System.out.println("2v1");
				} else if (temp.equals("three") && temp2.equals("one")) {
					switchVal = 10;
					System.out.println("3v1");
				}
			}

			switch (switchVal) {
			case 12:
				System.out.println("3v2");
				/*
				 * If fortified defender adds 1 to both of his dice. If the
				 * attacker attacks with 3 troops, the fortification gets worn
				 * down
				 */
				int[] attack6 = { randomDice(), randomDice(), randomDice() };
				Arrays.sort(attack6);
				int[] defense6 = { randomDice(), randomDice() };
				Arrays.sort(defense6);
				numOfDice = 4;
				System.out.println();
				System.out.print("attack die from lowest to highest ");
				for (int i = 0; i < 3; i++) {
					System.out.print(attack6[i] + " ");
				}
				System.out.println();
				System.out.print("defense die from lowest to highest ");
				for (int i = 0; i < 2; i++) {
					System.out.print(defense6[i] + " ");
				}
				// Missles
				inScan = new Scanner(System.in);
				missleTemp = "";
				loopUntil = 2;
				numAtkDice = 2;
				numDefDice = 2;
				misslesUsed = 0;
				missleOnDefender = false;
				while (loopUntil > 0) {
					for (int i = playerStack.size() - 1; i > -1; i--) {
						if (misslesUsed == numOfDice) {
							break;
						}
						if (playerStack.get(i).getMissles() > 0) {
							System.out.println();
							// sendToClient(playerStack.get(i),
							// "Would you like to use a missile?");
							// missletemp = getClientResponse();
							System.out
									.println(playerStack.get(i).getName()
											+ "would you like to use a missile? 'yes' or 'no'");
							missleTemp = inScan.next();
							if (missleTemp.equals("yes")) {
								// sendToClient(playerStack.get(i),
								// "Defender or Attackers Dice?");
								// missletemp = getClientResponse();
								System.out
										.println("Defender or Attackers Dice? 'd' or 'f'");
								missleTemp = inScan.next();
								if (missleTemp.equals("d") && numDefDice > 0) {
									// sendToClient(playerStack.get(i),
									// "Which dice toll would you like to apply the missile to?");
									// missletemp = getClientResponse();
									System.out
											.println("Which dice roll would you like to apply the missile to? "
													+ "first or second (first being lowest)");
									missleTemp = inScan.next();
									if (missleTemp.equals("first")) {
										System.out.println("Changed "
												+ defense6[0] + " to a 6");
										defense6[0] = 6;
										missleOnDefender = true;
										playerStack
												.get(i)
												.setMissles(
														playerStack.get(i)
																.getMissles() - 1);
										misslesUsed++;
										numDefDice--;
									} else if (missleTemp.equals("second")) {
										System.out.println("Changed "
												+ defense6[1] + " to a 6");
										defense6[1] = 6;
										playerStack
												.get(i)
												.setMissles(
														playerStack.get(i)
																.getMissles() - 1);
										misslesUsed++;
										numDefDice--;
									}

								} else if (missleTemp.equals("f")
										&& numAtkDice > 0) {
									// sendToClient(playerStack.get(i),
									// "Which dice roll would you like to apply the missile to?");
									// missleTemp = getClientResponse();
									System.out
											.println("Which dice roll would you like to apply the missile to? second or third(second being lowest)");
									missleTemp = inScan.next();
									if (missleTemp.equals("second")) {
										System.out.println("Changed "
												+ attack6[1] + " to a 6");
										attack6[1] = 6;
										playerStack
												.get(i)
												.setMissles(
														playerStack.get(i)
																.getMissles() - 1);
										misslesUsed++;
										numDefDice--;
									} else if (missleTemp.equals("third")) {
										System.out.println("Changed "
												+ attack6[2] + " to a 6");
										attack6[2] = 6;
										playerStack
												.get(i)
												.setMissles(
														playerStack.get(i)
																.getMissles() - 1);
										misslesUsed++;
										numDefDice--;
									}
								}
							}
						}
					}
					loopUntil--;

				}
				System.out.println();
				if (defCountry.getScarType() == 1 && missleOnDefender == false) {
					defense6[1] -= 1;
				}
				if (defCountry.getScarType() == 2 && missleOnDefender == false) {
					if (defense6[1] < 6) {
						defense6[1] += 1;
					}
				}

				if (defCountry.isCityFortified() == true) {
					if (defense6[1] < 6) {
						defense6[1] += 1;
					}
					if (defense6[0] < 6) {
						defense6[0] += 1;
					}
					defCountry
							.setFortifyEnergy(defCountry.getFortifyEnergy() - 1);
				}

				if (attack6[2] > defense6[1]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack6[2] + " is greater than "
							+ defense6[1]);
				}
				if (attack6[2] <= defense6[1]) {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack6[2] + " is less than or equal "
							+ defense6[1]);
				}
				if (attack6[1] > defense6[0]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack6[1] + " is greater than "
							+ defense6[0]);
				} else {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack6[1] + " is less than or equal "
							+ defense6[0]);
				}
				System.out.println("Attackers troops remaining: "
						+ atkCountry.getTroopQuantity());
				System.out.println("Defeners troops remaining: "
						+ defCountry.getTroopQuantity());
				switchVal = 0;
				break;
			case 10:
				System.out.println("3v1");
				int[] attack5 = { randomDice(), randomDice(), randomDice() };
				Arrays.sort(attack5);
				int[] defense5 = { randomDice() };
				Arrays.sort(defense5);
				numOfDice = 2;
				System.out.println();
				System.out.print("attack die from lowest to highest ");
				for (int i = 0; i < 3; i++) {
					System.out.print(attack5[i] + " ");
				}
				System.out.println();
				System.out.print("defense die from lowest to highest ");
				System.out.print(defense5[0] + " ");
				System.out.println();

				// Missles
				inScan = new Scanner(System.in);
				missleTemp = "";
				loopUntil = 2;
				numAtkDice = 1;
				numDefDice = 1;
				misslesUsed = 0;
				missleOnDefender = false;
				while (loopUntil > 0) {
					for (int i = playerStack.size() - 1; i > -1; i--) {
						if (misslesUsed == numOfDice) {
							break;
						}
						if (playerStack.get(i).getMissles() > 0) {
							System.out.println();
							// sendToClient(playerStack.get(i),
							// "Would you like to use a missle?");
							// missletemp = getClientResponse();
							System.out
									.println(playerStack.get(i).getName()
											+ "would you like to use a missle? 'yes' or 'no'");
							missleTemp = inScan.next();
							if (missleTemp.equals("yes")) {
								// sendToClient(playerStack.get(i),
								// "Defender or Attackers dice?");
								// missletemp = getClientResponse();
								System.out
										.println("Defender or Attackers Dice? 'd' or 'f'");
								missleTemp = inScan.next();
								if (missleTemp.equals("d") && numDefDice > 0) {
									System.out
											.println("Missle used on defenders dice");
									System.out.println("Changed " + defense5[0]
											+ " to a 6");
									defense5[0] = 6;
									missleOnDefender = true;
									playerStack.get(i)
											.setMissles(
													playerStack.get(i)
															.getMissles() - 1);
									misslesUsed++;
									numDefDice--;
								} else if (missleTemp.equals("f")
										&& numAtkDice > 0) {
									System.out
											.println("Missle used on attackers dice");
									System.out.println("Changed " + attack5[2]
											+ " to a 6");
									attack5[2] = 6;
									playerStack.get(i)
											.setMissles(
													playerStack.get(i)
															.getMissles() - 1);
									misslesUsed++;
									numAtkDice--;
								}
							}
						}
					}
					loopUntil--;

				}
				if (defCountry.getScarType() == 1 && missleOnDefender == false) {
					defense5[0] -= 1;
				}
				if (defCountry.getScarType() == 2 && missleOnDefender == false) {
					if (defense5[0] < 6) {
						defense5[0] += 1;
					}
				}

				if (defCountry.isCityFortified() == true) {
					if (defense5[0] < 6) {
						defense5[0] += 1;
					}
					defCountry
							.setFortifyEnergy(defCountry.getFortifyEnergy() - 1);
				}

				if (attack5[2] > defense5[0]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack5[2] + " is greater than "
							+ defense5[0]);
				}
				if (attack5[2] <= defense5[0]) {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack5[2] + " is less than or equal "
							+ defense5[0]);
				}
				System.out.println("Attackers troops remaining: "
						+ atkCountry.getTroopQuantity());
				System.out.println("Defeners troops remaining: "
						+ defCountry.getTroopQuantity());
				switchVal = 0;

				break;
			case 7:
				System.out.println("2v2");
				int[] attack4 = { randomDice(), randomDice() };
				Arrays.sort(attack4);
				int[] defense4 = { randomDice(), randomDice() };
				Arrays.sort(defense4);
				numOfDice = 4;
				System.out.println();
				System.out.print("attack die from lowest to highest ");
				for (int i = 0; i < 2; i++) {
					System.out.print(attack4[i] + " ");
				}
				System.out.println();
				System.out.print("defense die from lowest to highest ");
				for (int i = 0; i < 2; i++) {
					System.out.print(defense4[i] + " ");
				}
				// Missles
				inScan = new Scanner(System.in);
				missleTemp = "";
				loopUntil = 2;
				numAtkDice = 2;
				numDefDice = 2;
				misslesUsed = 0;
				missleOnDefender = false;
				while (loopUntil > 0) {
					for (int i = playerStack.size() - 1; i > -1; i--) {
						if (misslesUsed == numOfDice) {
							break;
						}
						if (playerStack.get(i).getMissles() > 0) {
							System.out.println();
							// sendToClient(playerStack.get(i),
							// "Would you like to use a missle?");
							// missletemp = getClientResponse();
							System.out
									.println(playerStack.get(i).getName()
											+ "would you like to use a missle? 'yes' or 'no'");
							missleTemp = inScan.next();
							if (missleTemp.equals("yes")) {
								// sendToClient(playerStack.get(i),
								// "Attacker or defenders dice?");
								// missletemp = getClientResponse();
								System.out
										.println("Defender or Attackers Dice? 'd' or 'f'");
								missleTemp = inScan.next();
								if (missleTemp.equals("d") && numDefDice > 0) {
									// sendToClient(playerStack.get(i),
									// "Which dice toll would you like to apply the missile to?");
									// missletemp = getClientResponse();
									System.out
											.println("Which dice roll would you like to apply the missle to? "
													+ "first or second (first being lowest)");
									missleTemp = inScan.next();
									if (missleTemp.equals("first")) {
										System.out.println("Changed "
												+ defense4[0] + " to a 6");
										defense4[0] = 6;
										missleOnDefender = true;
										playerStack
												.get(i)
												.setMissles(
														playerStack.get(i)
																.getMissles() - 1);
										misslesUsed++;
										numDefDice--;
									} else if (missleTemp.equals("second")) {
										System.out.println("Changed "
												+ defense4[1] + " to a 6");
										defense4[1] = 6;
										playerStack
												.get(i)
												.setMissles(
														playerStack.get(i)
																.getMissles() - 1);
										misslesUsed++;
										numDefDice--;
									}

								} else if (missleTemp.equals("f")
										&& numAtkDice > 0) {
									// sendToClient(playerStack.get(i),
									// "Which dice toll would you like to apply the missile to?");
									// missletemp = getClientResponse();
									System.out
											.println("Which dice roll would you like to apply the missle to? first or second(first being lowest)");
									missleTemp = inScan.next();
									if (missleTemp.equals("first")) {
										System.out.println("Changed "
												+ attack4[1] + " to a 6");
										attack4[0] = 6;
										playerStack
												.get(i)
												.setMissles(
														playerStack.get(i)
																.getMissles() - 1);
										misslesUsed++;
										numDefDice--;
									} else if (missleTemp.equals("second")) {
										System.out.println("Changed "
												+ attack4[2] + " to a 6");
										attack4[1] = 6;
										playerStack
												.get(i)
												.setMissles(
														playerStack.get(i)
																.getMissles() - 1);
										misslesUsed++;
										numDefDice--;
									}
								}
							}
						}
					}
					loopUntil--;

				}
				System.out.println();
				if (defCountry.getScarType() == 1 && missleOnDefender == false) {
					defense4[1] -= 1;
				}
				if (defCountry.getScarType() == 2 && missleOnDefender == false) {
					if (defense4[1] < 6) {
						defense4[1] += 1;
					}
				}

				if (defCountry.isCityFortified() == true) {
					if (defense4[1] < 6) {
						defense4[1] += 1;
					}
					if (defense4[0] < 6) {
						defense4[0] += 1;
					}
				}

				if (attack4[1] > defense4[1]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack4[1] + " is greater than "
							+ defense4[1]);
				}
				if (attack4[1] <= defense4[1]) {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack4[1] + " is less than or equal "
							+ defense4[1]);
				}
				if (attack4[0] > defense4[0]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack4[0] + " is greater than "
							+ defense4[0]);
				} else {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack4[0] + " is less than or equal "
							+ defense4[0]);
				}
				System.out.println("Attackers troops remaining: "
						+ atkCountry.getTroopQuantity());
				System.out.println("Defeners troops remaining: "
						+ defCountry.getTroopQuantity());
				switchVal = 0;

				break;
			case 5:
				System.out.println("2v1");
				int[] attack3 = { randomDice(), randomDice() };
				Arrays.sort(attack3);
				int[] defense3 = { randomDice() };
				Arrays.sort(defense3);
				numOfDice = 3;
				System.out.println();
				System.out.print("attack die from lowest to highest ");
				for (int i = 0; i < 2; i++) {
					System.out.print(attack3[i] + " ");
				}
				System.out.println();
				System.out.print("defense die from lowest to highest ");

				System.out.print(defense3[0] + " ");

				// Missles
				inScan = new Scanner(System.in);
				missleTemp = "";
				loopUntil = 2;
				numAtkDice = 2;
				numDefDice = 1;
				misslesUsed = 0;
				missleOnDefender = false;
				while (loopUntil > 0) {
					for (int i = playerStack.size() - 1; i > -1; i--) {
						if (misslesUsed == numOfDice) {
							break;
						}
						if (playerStack.get(i).getMissles() > 0) {
							// sendToClient(playerStack.get(i),
							// "Would you like to use a missle?");
							// missletemp = getClientResponse();
							System.out
									.println(playerStack.get(i).getName()
											+ "would you like to use a missle? 'yes' or 'no'");
							missleTemp = inScan.next();
							if (missleTemp.equals("yes")) {
								System.out
										.println("Defender or Attackers Dice? 'd' or 'f'");
								missleTemp = inScan.next();
								if (missleTemp.equals("d") && numDefDice > 0) {
									System.out.println("Changed " + defense3[0]
											+ " to a 6");
									defense3[0] = 6;
									missleOnDefender = true;
									playerStack.get(i)
											.setMissles(
													playerStack.get(i)
															.getMissles() - 1);
									misslesUsed++;
									numDefDice--;

								} else if (missleTemp.equals("f")
										&& numAtkDice > 0) {
									// sendToClient(playerStack.get(i),
									// "Which dice toll would you like to apply the missile to?");
									// missletemp = getClientResponse();
									System.out
											.println("Which dice roll would you like to apply the missle to? first or second(first being the lowest)");
									missleTemp = inScan.next();
									if (missleTemp.equals("first")) {
										System.out.println("Changed "
												+ defense3[0] + " to a 6");
										attack3[0] = 6;
										missleOnDefender = true;
										playerStack
												.get(i)
												.setMissles(
														playerStack.get(i)
																.getMissles() - 1);
										misslesUsed++;
										numDefDice--;
									} else if (missleTemp.equals("second")) {
										System.out.println("Changed "
												+ attack3[1] + " to a 6");
										attack3[1] = 6;
										playerStack
												.get(i)
												.setMissles(
														playerStack.get(i)
																.getMissles() - 1);
										misslesUsed++;
										numDefDice--;
									}
								}
							}
						}
					}
					loopUntil--;

				}
				System.out.println();
				if (defCountry.getScarType() == 1 && missleOnDefender == false) {
					defense3[0] -= 1;
				}
				if (defCountry.getScarType() == 2 && missleOnDefender == false) {
					if (defense3[0] < 6) {
						defense3[0] += 1;
					}
				}

				if (defCountry.isCityFortified() == true) {

					if (defense3[0] < 6) {
						defense3[0] += 1;
					}
				}
				if (attack3[1] > defense3[0]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack3[1] + " is greater than "
							+ defense3[0]);
				} else {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack3[1] + " is less than or equal "
							+ defense3[0]);
				}
				System.out.println("Attackers troops remaining: "
						+ atkCountry.getTroopQuantity());
				System.out.println("Defeners troops remaining: "
						+ defCountry.getTroopQuantity());
				switchVal = 0;

				break;
			case 4:
				// 1vs2
				System.out.println("1v2");
				int[] attack1 = { randomDice() };
				Arrays.sort(attack1);
				int[] defense1 = { randomDice(), randomDice() };
				Arrays.sort(defense1);
				numOfDice = 3;
				System.out.println();
				System.out.print("attack die from lowest to highest ");
				System.out.print(attack1[0] + " ");
				System.out.println();
				System.out.print("defense die from lowest to highest ");

				// Missles
				inScan = new Scanner(System.in);
				missleTemp = "";
				loopUntil = 2;
				numAtkDice = 1;
				numDefDice = 2;
				misslesUsed = 0;
				missleOnDefender = false;
				while (loopUntil > 0) {
					for (int i = playerStack.size() - 1; i > -1; i--) {
						if (misslesUsed == numOfDice) {
							break;
						}
						if (playerStack.get(i).getMissles() > 0) {
							// sendToClient(playerStack.get(i),
							// "Would you like to use a misslie?");
							// missletemp = getClientResponse();
							System.out
									.println(playerStack.get(i).getName()
											+ "would you like to use a missle? 'yes' or 'no'");
							missleTemp = inScan.next();
							if (missleTemp.equals("yes")) {
								// sendToClient(playerStack.get(i),
								// "Attacker or defenders missile?");
								// missletemp = getClientResponse();
								System.out
										.println("Defender or Attackers Dice? 'd' or 'f'");
								missleTemp = inScan.next();
								if (missleTemp.equals("d") && numDefDice > 0) {
									// sendToClient(playerStack.get(i),
									// "Which dice toll would you like to apply the missile to?");
									// missletemp = getClientResponse();
									System.out
											.println("Which dice roll would you like to apply the missle to? first or second(first being the lowest)");
									missleTemp = inScan.next();
									if (missleTemp.equals("first")) {
										System.out.println("Changed "
												+ defense1[0] + " to a 6");
										defense1[0] = 6;
										missleOnDefender = true;
										playerStack
												.get(i)
												.setMissles(
														playerStack.get(i)
																.getMissles() - 1);
										misslesUsed++;
										numDefDice--;
									} else if (missleTemp.equals("second")) {
										System.out.println("Changed "
												+ defense1[1] + " to a 6");
										defense1[1] = 6;
										playerStack
												.get(i)
												.setMissles(
														playerStack.get(i)
																.getMissles() - 1);
										misslesUsed++;
										numDefDice--;
									}

								} else if (missleTemp.equals("f")
										&& numAtkDice > 0) {
									System.out
											.println("Missle used on attackers dice");
									attack1[0] = 6;
									playerStack.get(i)
											.setMissles(
													playerStack.get(i)
															.getMissles() - 1);
									misslesUsed++;
									numAtkDice--;
								}
							}
						}
					}
					loopUntil--;

				}
				if (defCountry.getScarType() == 1 && missleOnDefender == false) {
					defense1[1] -= 1;
				}
				if (defCountry.getScarType() == 2 && missleOnDefender == false) {
					if (defense1[1] < 6) {
						defense1[1] += 1;
					}
				}

				if (defCountry.isCityFortified() == true) {
					if (defense1[1] < 6) {
						defense1[1] += 1;
					}
					if (defense1[0] < 6) {
						defense1[0] += 1;
					}
				}
				for (int i = 0; i < 2; i++) {
					System.out.print(defense1[i] + " ");
				}
				System.out.println();

				if (attack1[0] > defense1[1]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack1[0] + " is greater than "
							+ defense1[1]);
				} else {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack1[0] + " is less than or equal "
							+ defense1[1]);
				}
				System.out.println("Attackers troops remaining: "
						+ atkCountry.getTroopQuantity());
				System.out.println("Defeners troops remaining: "
						+ defCountry.getTroopQuantity());
				switchVal = 0;
				break;
			case 2:
				// 1vs1
				System.out.println("1v1");
				int[] attack2 = { randomDice() };
				Arrays.sort(attack2);
				int[] defense2 = { randomDice() };
				Arrays.sort(defense2);
				numOfDice = 2;
				System.out.println();
				System.out.print("attack die from lowest to highest ");
				System.out.print(attack2[0] + " ");
				System.out.println();
				System.out.print("defense die from lowest to highest ");
				System.out.print(defense2[0] + " ");
				System.out.println();

				// Missles
				inScan = new Scanner(System.in);
				missleTemp = "";
				loopUntil = 2;
				numAtkDice = 1;
				numDefDice = 1;
				misslesUsed = 0;
				missleOnDefender = false;
				while (loopUntil > 0) {
					for (int i = playerStack.size() - 1; i > -1; i--) {
						if (misslesUsed == numOfDice) {
							break;
						}
						if (playerStack.get(i).getMissles() > 0) {
							// sendToClient(playerStack.get(i),
							// "Would you like to use a missile?");
							// missletemp = getClientResponse();
							System.out
									.println(playerStack.get(i).getName()
											+ "would you like to use a missle? 'yes' or 'no'");
							missleTemp = inScan.next();
							if (missleTemp.equals("yes")) {
								// sendToClient(playerStack.get(i),
								// "Attacker or Defender?");
								// missletemp = getClientResponse();
								System.out
										.println("Defender or Attackers Dice? 'd' or 'f'");
								missleTemp = inScan.next();
								if (missleTemp.equals("d") && numDefDice > 0) {
									System.out
											.println("Missle used on defenders dice");
									System.out.println("Changed " + defense2[0]
											+ " to a 6");
									defense2[0] = 6;
									missleOnDefender = true;
									playerStack.get(i)
											.setMissles(
													playerStack.get(i)
															.getMissles() - 1);
									misslesUsed++;
									numDefDice--;
								} else if (missleTemp.equals("f")
										&& numAtkDice > 0) {
									System.out
											.println("Missle used on attackers dice");
									attack2[0] = 6;
									playerStack.get(i)
											.setMissles(
													playerStack.get(i)
															.getMissles() - 1);
									misslesUsed++;
									numAtkDice--;
								}
							}
						}
					}
					loopUntil--;

				}

				if (defCountry.getScarType() == 1 && missleOnDefender == false) {
					defense2[0] -= 1;
				}
				if (defCountry.getScarType() == 2 && missleOnDefender == false) {
					if (defense2[0] < 6) {
						defense2[0] += 1;
					}
				}

				if (defCountry.isCityFortified() == true) {

					if (defense2[0] < 6) {
						defense2[0] += 1;
					}
				}
				if (attack2[0] > defense2[0]) {
					defCountry
							.setTroopQuantity(defCountry.getTroopQuantity() - 1);
					System.out.println(attack2[0] + " is greater than "
							+ defense2[0]);
				} else {
					atkCountry
							.setTroopQuantity(atkCountry.getTroopQuantity() - 1);
					System.out.println(attack2[0] + " is less than or equal "
							+ defense2[0]);
				}
				System.out.println("Attackers troops remaining: "
						+ atkCountry.getTroopQuantity());
				System.out.println("Defeners troops remaining: "
						+ defCountry.getTroopQuantity());
				switchVal = 0;
				break;
			}

			// ignore for now
			Scanner in = new Scanner(System.in);
			// isAttacking = false;
			if (atkCountry.getTroopQuantity() > 1
					&& defCountry.getTroopQuantity() > 0) {
				// sendToClient(atkCountry.getOwner(),
				// "Do you wish to continue combat?");
				// missletemp = getClientResponse();
				System.out
						.println("Attacker! Do you wish to continue? type 'yes' to Continue");
				String temp3 = in.next();
				// in.close();
				if (temp3.equals("yes")) {
					isAttacking = true;
				} else {
					isAttacking = false;
					if (atkCountry.getTroopQuantity() == 1) {
						System.out.println("Not enough troops");
					} else {
						System.out.println("Combat stopped");

					}
					break;
				}
			}
			if (defCountry.getTroopQuantity() == 0) {
				System.out.println(defCountry.getOwner().getName()
						+ " you have been defeated, "
						+ defCountry.getCountryName() + " Now belongs to "
						+ atkCountry.getOwner().getName());

				atkCountry.getOwner().addCountry(defCountry.id());
				atkCountry.getOwner().addConquered(defCountry.id());

				// atkCountry.getOwner().addCard(cardDeck.pop());
				// if(defCountry.getOwner().getCountrys().size() < 1){
				// for(int i = 0; i < defCountry.getOwner().getCards().size();
				// i++){
				// atkCountry.getOwner().addCard(defCountry.getOwner().getCards().get(i));
				//
				// }
				// }
			}

		}

	}

	public void drawEventCard() {
		/*
		 * x3 Event CardWhomever has the largest population EITHER recieves 3
		 * troops that can be placed into one or more citty terrotories he
		 * controls OR he may choose a new mission from the mission deck to
		 * replace the current one
		 */
	}

	public void drawMissionCard() {

		if (newMissionCard == true) {
			Random generator = new Random();
			if (missionAvail.isEmpty() != true) {
				missionInt = generator.nextInt(missionAvail.size());
				missionInt = missionAvail.get(missionInt);
				newMissionCard = false;
			} else {
				missionInt = -1;
			}
		}
		/*
		 * SUPERIOR INFRASTRUCTURE: Control6+ Cities REIGN OF TERROR: Conquer 9+
		 * territories this turn URBAN ASSAULT: Conquer 4+ Cities this turn
		 * AMPHIBIOUS ONSLAUGHT: Conquer 4+ territories over sea lines this turn
		 * UNEXPECTED ATTACK: Conquer all the territories in one continent this
		 * turn IMPERIAL MIGHT: Have a current total continent bonus of 7+
		 */
		switch (missionInt) {
		case -1:
			break;

		case 0:
			System.out.println("Active mission Card 0");
			for (int i = 0; i < playerStack.size(); i++) {
				if (checkSuperior(playerStack.get(i)) == true) {
					missionAvail.remove(missionAvail.indexOf(0));
					newMissionCard = true;
					playerStack.get(i).setRedstar(
							playerStack.get(i).getRedstar() + 1);
					System.out
							.println("SUPERIOR INFRASTRUCTURE: Control6+ Cities");
					System.out.println(playerStack.get(i).getName()
							+ " was awarded a redstar");
					break;
				}
			}
			break;

		case 1:
			System.out.println("Active mission Card 1");
			for (int i = 0; i < playerStack.size(); i++) {
				if (checkReign(playerStack.get(i)) == true) {
					missionAvail.remove(missionAvail.indexOf(1));
					newMissionCard = true;
					playerStack.get(i).setRedstar(
							playerStack.get(i).getRedstar() + 1);
					System.out
							.println("REIGN OF TERROR: Conquer 9+ territories this turn");
					System.out.println(playerStack.get(i).getName()
							+ " was awarded a redstar");
					break;
				}
			}
			break;

		case 2:
			System.out.println("Active mission Card 2");
			for (int i = 0; i < playerStack.size(); i++) {
				if (checkUrban(playerStack.get(i)) == true) {
					missionAvail.remove(missionAvail.indexOf(2));
					newMissionCard = true;
					playerStack.get(i).setRedstar(
							playerStack.get(i).getRedstar() + 1);
					System.out
							.println("URBAN ASSAULT: Conquer 4+ Cities this turn");
					System.out.println(playerStack.get(i).getName()
							+ " was awarded a redstar");
					break;
				}
			}
			break;

		case 3:
			System.out.println("Active mission Card 3");
			for (int i = 0; i < playerStack.size(); i++) {
				if (checkAmphib(playerStack.get(i)) == true) {
					missionAvail.remove(missionAvail.indexOf(3));
					newMissionCard = true;
					playerStack.get(i).setRedstar(
							playerStack.get(i).getRedstar() + 1);
					System.out
							.println("AMPHIBIOUS ONSLAUGHT: Conquer 4+ territories over sea lines this turn");
					System.out.println(playerStack.get(i).getName()
							+ " was awarded a redstar");
					break;
				}
			}
			break;

		case 4:
			System.out.println("Active mission Card 4");
			for (int i = 0; i < playerStack.size(); i++) {
				if (checkUnexpectedA(playerStack.get(i)) == true) {
					missionAvail.remove(missionAvail.indexOf(4));
					newMissionCard = true;
					playerStack.get(i).setRedstar(
							playerStack.get(i).getRedstar() + 1);
					System.out
							.println("UNEXPECTED ATTACK: Conquer all the territories in one continent this turn");
					System.out.println(playerStack.get(i).getName()
							+ " was awarded a redstar");
					break;
				}
			}
			break;

		case 5:
			System.out.println("Active mission Card 5");
			for (int i = 0; i < playerStack.size(); i++) {
				if (checkImpMight(playerStack.get(i)) == true) {
					missionAvail.remove(missionAvail.indexOf(5));
					newMissionCard = true;
					playerStack.get(i).setRedstar(
							playerStack.get(i).getRedstar() + 1);
					System.out
							.println("IMPERIAL MIGHT: Have a current total continent bonus of 7+");
					System.out.println(playerStack.get(i).getName()
							+ " was awarded a redstar");
					break;
				}
			}
			break;

		}
	}

	/*
	 * Solberg Checks the conditions for various mission cards an whether they
	 * have been met or not
	 */
	public boolean checkSuperior(player aPlayer) {
		boolean val = false;
		int count = 0;
		ArrayList<Integer> countryList = aPlayer.getCountrys();
		for (int i = 0; i < countryList.size(); i++) {
			if (world.getCountry(countryList.get(i)).getCityType() != 0
					|| world.getCountry(countryList.get(i)).getCityType() != 4) {

				count++;
			}
		}
		if (count >= 6) {
			val = true;
		}
		return val;

	}

	public boolean checkReign(player aPlayer) {
		boolean val = false;
		ArrayList<Integer> countryList = aPlayer.getConquered();
		// reset list to empty at the end of a turn
		if (countryList.size() >= 9) {
			val = true;
		}

		return val;
	}

	public boolean checkUrban(player aPlayer) {
		boolean val = false;
		int count = 0;
		ArrayList<Integer> countryList = aPlayer.getConquered();
		for (int i = 0; i < countryList.size(); i++) {
			if (world.getCountry(countryList.get(i)).getCityType() != 0) {
				count++;
			}
		}
		if (count >= 4) {
			val = true;
		}
		return val;
	}

	public boolean checkAmphib(player aPlayer) {
		boolean val = false;
		// AMPHIBIOUS ONSLAUGHT: Conquer 4+ territories over sea lines this turn
		return val;
	}

	public boolean checkUnexpectedA(player aPlayer) {
		boolean val = false;
		ArrayList<Integer> countryList = new ArrayList<Integer>();
		for (int i = 0; i < aPlayer.getConquered().size(); i++) {
			countryList.add(aPlayer.getConquered().get(i));
		}

		if (countryList.containsAll(world.getContinent(0).getCountries())) {
			val = true;
		}
		if (countryList.containsAll(world.getContinent(1).getCountries())) {
			val = true;
		}
		if (countryList.containsAll(world.getContinent(2).getCountries())) {
			val = true;
		}
		if (countryList.containsAll(world.getContinent(3).getCountries())) {
			val = true;
		}
		if (countryList.containsAll(world.getContinent(4).getCountries())) {
			val = true;
		}
		if (countryList.containsAll(world.getContinent(5).getCountries())) {
			val = true;
		}
		// UNEXPECTED ATTACK: Conquer all the territories in one continent this
		// turn
		return val;
	}

	public boolean checkImpMight(player aPlayer) {

		boolean val = false;
		ArrayList<Integer> countryList = aPlayer.getConquered();
		int bonus = 0;
		if (countryList.containsAll(world.getContinent(0).getCountries()) == true) {
			bonus += world.getContinent(0).getBonus();
			System.out.println("0America");
		}
		if (countryList.containsAll(world.getContinent(1).getCountries()) == true) {
			bonus += world.getContinent(1).getBonus();
			System.out.println("1SouthAmerica");
		}
		if (countryList.containsAll(world.getContinent(2).getCountries()) == true) {
			bonus += world.getContinent(2).getBonus();
			System.out.println("2");
		}
		if (countryList.containsAll(world.getContinent(3).getCountries()) == true) {
			bonus += world.getContinent(3).getBonus();
			System.out.println("3");
		}
		if (countryList.containsAll(world.getContinent(4).getCountries()) == true) {
			bonus += world.getContinent(4).getBonus();
			System.out.println("4");
		}
		if (countryList.containsAll(world.getContinent(5).getCountries()) == true) {
			bonus += world.getContinent(5).getBonus();
			System.out.println("5");
		}

		if (bonus >= 7) {
			System.out.println(bonus);
			val = true;
		}
		// IMPERIAL MIGHT: Have a current total continent bonus of 7+
		return val;
	}

	public static int randomDice() {
		int dice;
		Random generator = new Random();
		dice = generator.nextInt(6) + 1;
		return dice;
	}

	public void writeOut() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(
					"everyturn.txt"));
			for (int i = 0; i < activePlayer.size(); i++) {
				// writes player name
				bw.write(activePlayer.get(i).getName() + ": ");
				bw.newLine();
				// Write out owned countrys followed by number of troops
				for (int j = 0; j < activePlayer.get(i).getCountrys().size(); j++) {
					Country temp = world.getCountry(activePlayer.get(i)
							.getCountrys().get(j));
					bw.write(temp.getCountryName() + ": "
							+ temp.getTroopQuantity() + " |  ");
				}
				bw.newLine();
				bw.write("Country:                 Borders:");
				bw.newLine();

				for (int k = 0; k < activePlayer.get(i).getCountrys().size(); k++) {
					Country temp = world.getCountry(activePlayer.get(i)
							.getCountrys().get(k));
					String country = temp.getCountryName();
					// int m = 25 - bords.length();
					for (int y = country.length(); y < 25; y++) {
						country = country.concat(" ");
					}
					// writes country
					bw.write(country);

					for (int l = 0; l < temp.getCountryBorders().size(); l++) {
						int borderTemp = temp.getCountryBorders().get(l);

						String bords = world.getCountry(borderTemp)
								.getCountryName();
						// int m = 25 - bords.length();
						for (int y = bords.length(); y < 25; y++) {
							bords = bords.concat(" ");
						}

						bw.write(bords);
					}
					bw.newLine();
					bw.write("                         ");
					for (int h = 0; h < temp.getCountryBorders().size(); h++) {
						int borderTemp = temp.getCountryBorders().get(h);
						String name = world.getCountry(borderTemp).getOwner()
								.getName();

						// int m = 25 - bords.length();
						for (int y = name.length(); y < 25; y++) {
							name = name.concat(" ");

						}
						bw.write(name);
					}
					bw.newLine();
					bw.newLine();
				}
				bw.newLine();
			}

			bw.close();

		} catch (Exception e) {
		}
	}

	String data = "6"
			+ "\n"
			+ "NorthAmerica 	title NONE 0 8	 0 1 2 3 4 5 6 7 8"
			+ "\n"
			+ "SouthAmerica 	title NONE 0 4	 9 10 11 12"
			+ "\n"
			+ "Africa 		title NONE 0 3	 13 14 15 16 17 18 19"
			+ "\n"
			+ "Europe 	 	title NONE 0 5	 20 21 22 23 24 25"
			+ "\n"
			+ "Asia 			title NONE 0 9	 26 27 28 29 30 31 32 33 34 35 36 37"
			+ "\n"
			+ "Australia 		title NONE 0 2	 38 39 40 41"
			+ "\n"
			+

			"0Alaska 				\\NONE 0 false 0 faction 0 \\NONE 0 false  34 1 5 "
			+ "\n"
			+ "1NorthwestTerritory 	\\NONE 0 false 0 faction 0 \\NONE 0 false  0 2 4 5"
			+ "\n"
			+ "2GreenLand 			\\NONE 0 false 0 faction 0 \\NONE 0 false  1 4 3 22"
			+ "\n"
			+ "3EasternCanada 		\\NONE 0 false 0 faction 0 \\NONE 0 false  2 4 7"
			+ "\n"
			+ "4Ontario 				\\NONE 0 false 0 faction 0 \\NONE 0 false  1 5 6 7 3 2"
			+ "\n"
			+ "5Alberta 				\\NONE 0 false 0 faction 0 \\NONE 0 false  1 0 6 3"
			+ "\n"
			+ "6WesternUS 			\\NONE 0 false 0 faction 0 \\NONE 0 false  5 4 8 7"
			+ "\n"
			+ "7EasternUS 			\\NONE 0 false 0 faction 0 \\NONE 0 false  3 4 6 8"
			+ "\n"
			+ "8CentralAmerica 		\\NONE 0 false 0 faction 0 \\NONE 0 false  7 6 9"
			+ "\n"
			+ "9Venezuala				\\NONE 0 false 0 faction 0 \\NONE 0 false  8 12 10"
			+ "\n"
			+ "10Peru 				\\NONE 0 false 0 faction 0 \\NONE 0 false  9 11 12"
			+ "\n"
			+ "11Argentina 			\\NONE 0 false 0 faction 0 \\NONE 0 false  10 12"
			+ "\n"
			+ "12Brazil 				\\NONE 0 true 0 faction 50 \\NONE 50 false  9 10 11 13"
			+ "\n"
			+ "13NorthAfrica 			\\NONE 0 true 0 faction 50 \\NONE 50 false  12 14 17 18 19 20"
			+ "\n"
			+ "14CentralAfrica 		\\NONE 0 true 0 faction 50 \\NONE 50 false  13 15 17"
			+ "\n"
			+ "15SouthAfrica 			\\NONE 0 true 0 faction 50 \\NONE 50 false  16 14 17"
			+ "\n"
			+ "16Madagascar 			\\NONE 0 true 0 faction 50 \\NONE 50 false  15 17"
			+ "\n"
			+ "17EastAfrica 			\\NONE 0 true 0 faction 50 \\NONE 50 false  16 15 14 13 18 28"
			+ "\n"
			+ "18Egypt 				\\NONE 0 true 0 faction 50 \\NONE 50 false  19 13 17 28"
			+ "\n"
			+ "19SouthernEurope 		\\NONE 0 true 0 faction 50 \\NONE 50 false  13 18 28 25 24 20"
			+ "\n"
			+ "20WesternEurope 		\\NONE 0 true 0 faction 50 \\NONE 50 false  21 24 19 13"
			+ "\n"
			+ "21GreatBritain 		\\NONE 0 true 0 faction 50 \\NONE 50 false 22 23 24 20"
			+ "\n"
			+ "22IceLand 				\\NONE 0 true 0 faction 50 \\NONE 50 false  2 21 23"
			+ "\n"
			+ "23Scandinavia 			\\NONE 0 true 0 faction 50 \\NONE 50 false  22 21 24 25"
			+ "\n"
			+ "24NorthernEurope 		\\NONE 0 true 0 faction 50 \\NONE 50 false  21 23 20 19 25"
			+ "\n"
			+ "25Russia 				\\NONE 0 true 0 faction 50 \\NONE 50 false  23 24 19 28 27 26"
			+ "\n"
			+ "26Ural 				\\NONE 0 true 0 faction 50 \\NONE 50 false  25 32 31 27"
			+ "\n"
			+ "27Afghanistan 			\\NONE 0 true 0 faction 50 \\NONE 50 false  25 26 31 29 28"
			+ "\n"
			+ "28MiddleEast 			\\NONE 0 true 0 faction 50 \\NONE 50 false  25 19 18 17 29 27"
			+ "\n"
			+ "29India 				\\NONE 0 true 0 faction 50 \\NONE 50 false  28 27 31 30"
			+ "\n"
			+ "30SoutheastAsia		\\NONE 0 true 0 faction 50 \\NONE 50 false  39 31 29"
			+ "\n"
			+ "31China 				\\NONE 0 true 0 faction 50 \\NONE 50 false  30 29 27 26 32 36"
			+ "\n"
			+ "32Siberia 				\\NONE 0 true 0 faction 50 \\NONE 50 false  26 31 36 37 33"
			+ "\n"
			+ "33Yakutsk 				\\NONE 0 true 0 faction 50 \\NONE 50 false  34 37 32"
			+ "\n"
			+ "34Kamchatka 			\\NONE 0 true 0 faction 50 \\NONE 50 false  0 35 33 37 36"
			+ "\n"
			+ "35Japan 				\\NONE 0 true 0 faction 50 \\NONE 50 false  36 34"
			+ "\n"
			+ "36Mongolia 			\\NONE 0 true 0 faction 50 \\NONE 50 false  35 34 37 32 31"
			+ "\n"
			+ "37Irkutsk 				\\NONE 0 true 0 faction 50 \\NONE 50 false 33 32 36 34"
			+ "\n"
			+ "38NewGuinea 			\\NONE 0 true 0 faction 50 \\NONE 50 false  41 40 39"
			+ "\n"
			+ "39Indonesia 			\\NONE 0 true 0 faction 50 \\NONE 50 false  30 38 40"
			+ "\n"
			+ "40WesternAustralia 	\\NONE 0 true 0 faction 50 \\NONE 50 false  41 38 39"
			+ "\n"
			+ "41EasternAustralia 	\\NONE 0 true 0 faction 50 \\NONE 50 false 38 40";

	// public Map world = new Map(data);

	@Override
	public String toString() {
		String out = "";
		// Step 1: player turns
		out = "order";
		for (player p : this.activePlayer) {
			out += "/" + p.getClientID();
		}

		for (player p : this.activePlayer) {
			for (Integer c : p.getCountrys()) {
				out += "\nset/" + p.getClientID() + "/"
						+ world.getCountry(c).getCountryName() + "/"
						+ world.getCountry(c).getTroopQuantity();
			}
		}

		return out;
	}
}
