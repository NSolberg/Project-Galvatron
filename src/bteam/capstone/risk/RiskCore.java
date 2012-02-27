package bteam.capstone.risk;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RiskCore extends Thread {
	// Server Data
	private RiskServer theServer;
	// Player Data
	private ArrayList<String> theClients;
	private ArrayList<Boolean> clientActive;
	private int numPlayers;
	private int maxPlayers;
	// Game Data
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

	public RiskCore(RiskServer riskServer, String clientID, String gameFile,
			boolean legacy, boolean reserve, String pass) {
		// init start
		Random ran = new Random();
		theServer = riskServer;
		theClients = new ArrayList<String>();
		clientActive = new ArrayList<Boolean>();
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
		if (gameFile.equals("Default")) {
			worldCreator = clientID;
			worldID = ran.nextInt(10000000);
			worldName = "\\NONE";
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

	}

	@Override
	public void run() {
		while (!inGame) {

		}
		setUpWorld();
		firstTurnSetup(islegacy);
		playGame();
	}

	/**
	 * game loop: pass turn Start of Turn Join the War/Recruit Troops
	 * Expand&Attack Maneuver Troops End of Turn End of Game
	 */
	private void playGame() {
		// TODO Auto-generated method stub

	}

	/**
	 * pass turn to the next player. Inform players whoms turn it is.
	 */
	private void passTurn() {

	}

	/**
	 * If game is not legacy: method does nothing If game is legacy: if player
	 * has no resourcards skip to scar: player turns in resource cards for a red
	 * Star, players can buy as many reds stars as they want, red stars are
	 * worth 4 resources. If player purchases 4th red star they win the game
	 * immediately. Territory cards turned in are discarded. Coin cards turned
	 * in are returned to coin pile.
	 * 
	 * Steps needed ask player if they want to turn in cards if yes expect
	 * integers seperated by spaces representing cards being turned in. inform
	 * all users of changes. if no method ends Scars if player has scar card
	 * they can play ask if they want to play expect scar number and target from
	 * player. inform players of changes.
	 * 
	 * @param legacy
	 */
	private void startOfTurn(boolean legacy) {

	}

	/**
	 * if not legacy recruit troops if legacy if player not on board join the
	 * war else recruit troops
	 * 
	 * players not on board are eligible to rejoin with half starting troops and
	 * no HQ as long as there exist a valid starting position. Inform user of
	 * possible starting locations. expect integer representing starting
	 * country. inform all of changes
	 * 
	 * inform player of available troops to place loop and ask player where to
	 * place troops until all are place. expect integer representing valid
	 * country and integer representing number of troops to place.
	 * 
	 * @param legacy
	 */
	private void joinOrRecruit(boolean legacy) {

	}

	private void expandAndAttack(boolean legacy) {

	}

	/**
	 * 
	 * @param legacy
	 */
	private void firstTurnSetup(boolean legacy) {
		// TODO Auto-generated method stub

	}

	/**
	 * Create Factions, Create resource deck, load scar cards, load mission
	 * cards, load event cards, load world assign red stars or missiles, give
	 * all players game state players choose from available factions give
	 * players scar cards
	 */
	private void setUpWorld() {
		// TODO Auto-generated method stub

	}

	private String getGameState() {
		// TODO Auto-generated method stub
		return null;
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

	public ArrayList<String> getClients() {
		return theClients;
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
			if (reserveSeat && theClients.contains(clientID)) {
				int num = theClients.indexOf(clientID);
				clientActive.set(num, true);
				theServer.sendTo(clientID, "yes " + this.getGameState());
				return true;
			} else if (numPlayers < maxPlayers && !inGame) {
				theClients.add(clientID);
				clientActive.add(true);
				theServer.sendTo(clientID, "yes " + this.getGameState());
				return true;
			} else if (clientActive.contains(false) && !reserveSeat) {
				int num = clientActive.indexOf(false);
				theClients.set(num, clientID);
				clientActive.set(num, true);
				theServer.sendTo(clientID, "yes " + this.getGameState());
				return true;
			} else {
				theServer.sendTo(clientID, "no server full");
			}
		} else {
			theServer.sendTo(clientID, "no wrong pass");
		}
		return false;
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
		String cmd = scan.next();
		if (cmd.equals("help")) {
			cmd = "leave\nstart";
			theServer.sendTo(client, cmd);
		} else if (!inGame) {
			if (cmd.equals("leave")) {
				int num = theClients.indexOf(client);
				theClients.remove(num);
				clientActive.remove(num);
				num = theServer.ClientID.indexOf(client);
				theServer.Clients.get(num).setGameID(-1);
				theServer.sendTo(client, "leftgame");
			} else if (cmd.equals("start")) {
				if (worldCreator.equals(client) && numPlayers >= 3)
					if (numPlayers > 2)
						inGame = true;
					else
						theServer.sendTo(client, "no not enough players");
				else
					theServer.sendTo(client, "no not creator");
			} else {
				theServer.sendTo(client, "invalid command");
			}
		} else {
			if (cmd.equals("leave")) {
				int num = theClients.indexOf(client);
				clientActive.set(num, false);
				num = theServer.ClientID.indexOf(client);
				theServer.Clients.get(num).setGameID(-1);
			} else {
				dataBuffer += data + "\n";
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
		String out = scan.next();
		dataBuffer = dataBuffer.substring(out.length());
		return out;
	}

}
