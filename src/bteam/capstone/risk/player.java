package bteam.capstone.risk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 * @author Nick The player class will have a constructor that will create a
 *         player given a name and faction. The class will hold data that
 *         includes missles, redstars, coins, and resources.
 * 
 */
public class player {

	// private variables storing data class wide
	private int missles, redstar, coin, resource;
	private String name;
	private Faction faction;
	private ArrayList<Integer> countrys = new ArrayList<Integer>();

	// private Faction faction;

	/**
	 * Constructor for creating a new player given a name and a faction.
	 * 
	 * @Param Data is string formatted to hold all the necessary data
	 */

	public player(String data) {
		Scanner scan = new Scanner(data);
		this.name = scan.next();
		this.missles = scan.nextInt();
		this.redstar = scan.nextInt();
		this.coin = scan.nextInt();
		this.resource = scan.nextInt();
		scan.close();
	}

	public ArrayList<Integer> getCountrys() {
		return countrys;
	}

	public int getCountryAt(int index) {
		return getCountrys().get(index);
	}

	public void removeCountrys(int i) {
		countrys.remove(i);
	}

	public void removeAllCountrys() {
		countrys.clear();
	}

	@Override
	public String toString() {
		String out = "";

		out += this.name + " ";
		out += this.missles + " ";
		out += this.redstar + " ";
		out += this.coin + " ";
		out += this.resource;
		return out;
	}

	public void addCountry(Integer country) {
		countrys.add(country);
	}

	public String getName() {
		return name;
	}

	public void setMissles(int missles) {
		this.missles = missles;
	}

	public int getMissles() {
		return missles;
	}

	public void setRedstar(int redstar) {
		this.redstar = redstar;
	}

	public int getRedstar() {
		return redstar;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getCoin() {
		return coin;
	}

	public void setResource(int resource) {
		this.resource = resource;
	}

	public int getResource() {
		return resource;
	}

	public void setFaction(StandardFaction faction) {
		this.faction = faction;
	}

	public Faction getFaction() {
		return faction;
	}

	private ArrayList<Integer> NA = new ArrayList<Integer>(Arrays.asList(0, 1,
			2, 3, 4, 5, 6, 7, 8));
	private ArrayList<Integer> SA = new ArrayList<Integer>(Arrays.asList(9, 10,
			11, 12));
	private ArrayList<Integer> AF = new ArrayList<Integer>(Arrays.asList(13,
			14, 15, 16, 17, 18, 19));
	private ArrayList<Integer> EU = new ArrayList<Integer>(Arrays.asList(20,
			21, 22, 23, 24, 25));
	private ArrayList<Integer> AS = new ArrayList<Integer>(Arrays.asList(26,
			27, 28, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37));
	private ArrayList<Integer> AU = new ArrayList<Integer>(Arrays.asList(38,
			39, 40, 41));

}
