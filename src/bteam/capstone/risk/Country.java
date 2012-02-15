package bteam.capstone.risk;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class represents a single country with the following information. The name of
 * the country, a list of country that this country shares boarders
 * with(represented by integers), Is the country surrounded by water(island).
 * Type of city in the country that can be either NONE, MINOR, MAJOR, CAPITOL,
 * Destroyed(represented by integers), the name of the city if one exists, if
 * the city is fortified and how much energy the fortification has. Who if
 * anyone is in control of the country. How many troops are in the country, and
 * if there is an HQ(head quarters) in the country and they type of scar on the
 * country represented by an integer.
 * 
 * @author Austin Langhorne *
 */
public class Country {

	// country variables
	private String countryName;
	private ArrayList<Integer> countryBorders;
	private boolean island;
	private int id = 0;
	private boolean hasHQ = false;
	
	// city variables
	private String cityName;
	// 0 = NONE, 1 = MINOR, 2 = MAJOR, 3 = CAPITOL, 4 = Destroyed
	private int cityType;
	private boolean cityFortified;
	private int fortifyEnergy;
	
	// faction variables
	private String controllingFaction;
	private int troopQuantity;
	private String factionHQ;

	// scars variable
	private int scarType;

	/**
	 * Default constructor for the Country class accepting a String to be parsed
	 * to setup the class
	 * 
	 * @param data
	 *            String of data to be parsed and used for the construction of
	 *            the class. The format of the String is as follows (underscores
	 *            represent spaces, all one line statement, x represent an
	 *            arbitrary number of boarders represented as integers separated
	 *            by spaces):
	 *            countryName_cityName_cityType_cityFortified_fortifyEnergy_
	 *            controllingFaction_troopQuantity_factionHQ_scarType_island_x
	 */
	public Country(String data) {
		Scanner scan = new Scanner(data);
		this.countryName = scan.next();
		this.cityName = scan.next();
		this.cityType = scan.nextInt();
		this.cityFortified = scan.nextBoolean();
		this.fortifyEnergy = scan.nextInt();
		this.controllingFaction = scan.next();
		this.troopQuantity = scan.nextInt();
		this.factionHQ = scan.next();
		this.scarType = scan.nextInt();
		this.countryBorders = new ArrayList<Integer>();
		this.island = scan.nextBoolean();
		while (scan.hasNext()) {
			int temp = scan.nextInt();
			this.countryBorders.add(temp);
		}
	}

	/**
	 * Method to represent all of the data in the class in the following
	 * format:(underscores represent spaces, all one line statement, x represent
	 * an arbitrary number of boarders represented as integers separated by
	 * spaces): countryName_cityName_cityType_cityFortified_fortifyEnergy_
	 * controllingFaction_troopQuantity_factionHQ_scarType_island_x
	 */
	@Override
	public String toString() {
		String out = "";
		out += this.countryName + " ";
		out += this.cityName + " ";
		out += this.cityType + " ";
		out += this.cityFortified + " ";
		out += this.fortifyEnergy + " ";
		out += this.controllingFaction + " ";
		out += this.troopQuantity + " ";
		out += this.factionHQ + " ";
		out += this.scarType + " ";
		out += this.island + " ";
		for (Integer b : this.countryBorders) {
			out += b + " ";
		}
		return out;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public ArrayList<Integer> getCountryBorders() {
		return countryBorders;
	}

	public void addCountryBorders(int countryBorder) {
		this.countryBorders.add(countryBorder);
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getCityType() {
		return cityType;
	}

	public void setCityType(int cityType) {
		this.cityType = cityType;
	}

	public boolean isCityFortified() {
		return cityFortified;
	}

	public void setCityFortified(boolean cityFortified) {
		this.cityFortified = cityFortified;
	}

	public int getFortifyEnergy() {
		return fortifyEnergy;
	}

	public void setFortifyEnergy(int fortifyEnergy) {
		this.fortifyEnergy = fortifyEnergy;
	}

	public String getControllingFaction() {
		return controllingFaction;
	}

	public void setControllingFaction(String controllingFaction) {
		this.controllingFaction = controllingFaction;
	}

	public int getTroopQuantity() {
		return troopQuantity;
	}

	public void setTroopQuantity(int troopQuantity) {
		this.troopQuantity = troopQuantity;
	}

	public String getFactionHQ() {
		return factionHQ;
	}

	public void setFactionHQ(String factionHQ) {
		this.factionHQ = factionHQ;
		hasHQ = true;
	}

	public boolean hasHQ(){
		return hasHQ;
	}
	public int getScarType() {
		return scarType;
	}

	public void setScarType(int scarType) {
		this.scarType = scarType;
	}
	
	public void setId(int ident){
		id = ident;
	}
	public int id() {
		// TODO Auto-generated method stub
		
		return id;
	}

}