/**
 * @author Ian Paterson
 * 
 */
/**
 * @todo 
 * REMAKE THESE AS SEPERATE CLASSES
 */
package bteam.capstone.risk;

import java.util.Scanner;

public class AliensFaction extends Faction {

	private int[] PowerList;
	private int count;
/**
 * @author Ian Paterson
 * @param PowerList: Is the array that holds the special "powers
 * for this type of faction
 * @param Count counts up the amount of powers the are allowed
 * @Description The alien faction works very similarly too the other factions 
 * but 3 of its powers are predetermined and not assigned by the player. It also
 * contains a fromString and tooString to allow for saveing and loading
 * Alien faction comes into the game later in the game and changes the board in 
 * diffrent ways from other faction in that it has the ablility to "destroy" various 
 * city's and to remove 
 * missles
 */
	public AliensFaction(String data) {
		count = 3;
		PowerList = new int[4];
		/*
		 * HEY DOUCHBAGS! CHANGE THESE LATER TO THE ACTUAL RULES
		 */
		PowerList[0] = 1;
		PowerList[1] = 2;
		PowerList[2] = 3;
		Scanner scan = new Scanner(data);
		this.PlayerName = scan.next();
		this.startingTerritory = scan.next();
		this.isWinner = scan.nextBoolean();
		this.wasEliminated = scan.nextBoolean();
		PowerList[3] = scan.nextInt();
		PowerList[4] = scan.nextInt();
		PowerList[5] = scan.nextInt();
	}

	@Override
	public String toString() {
		String out = "";
		out += this.PlayerName + " ";
		out += this.startingTerritory + " ";
		out += this.isWinner + " ";
		out += this.wasEliminated + " ";
		out += this.count + " ";
		out += this.PowerList[0] + " ";
		out += this.PowerList[1] + " ";
		out += this.PowerList[2] + " ";
		out += this.PowerList[3] + " ";
		out += this.PowerList[4] + " ";
		out += this.PowerList[5] + " ";
		return out;
	}

	/**
	 * @return the powerList
	 */
	public int[] getPowerList() {
		return PowerList;
	}

	/**
	 * @param powerList
	 *            the powerList to set
	 */
	public void setPowerList(int[] powerList) {
		PowerList = powerList;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	public void addPower(int power) {
		PowerList[count] = power;
		count++;
	}

}
