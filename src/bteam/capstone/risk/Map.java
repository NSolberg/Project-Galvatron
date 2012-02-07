/**
 * @author Ian Paterson
 * @author Austin Langhorne
 * @author Nick Solberg
 */
package bteam.capstone.risk;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author pcse_user
 * 
 */
public class Map {
	// Change this later
	public ArrayList<Country> countrys = new ArrayList<Country>();
	public ArrayList<Continent> continent = new ArrayList<Continent>();

	public Map(String data) {
		Scanner scan = new Scanner(data);
		int numCont = scan.nextInt();
		scan.nextLine();
		for (int i = 0; i < numCont; i++) {
			String dataLine = scan.nextLine();
			Continent temp = new Continent(dataLine);
			continent.add(temp);
		}
		while (scan.hasNextLine()) {
			String dataLine = scan.nextLine();
			countrys.add(new Country(dataLine));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String data = "";
		data += continent.size() + "\n";
		for (Continent co : continent) {
			data += co.toString() + "\n";
		}
		for (Country c : countrys) {
			data += c.toString() + "\n";
		}
		return data;
	}

	/*
	 * To be used when expanding/fortifying/post-attack to an adjacent country.
	 * warning uses both placeTroops and removeTroops method see them for
	 * further information on the use of this method.
	 * 
	 * note: for post-attack call the remove method on the remaining defeated
	 * defending troops before use of moveTroops into that country.
	 */
	public boolean moveToops(int countryOne, int countryTwo, int troopQuantity) {
		boolean out = false;
		if (countryOne != countryTwo) {
			Country temp = countrys.get(countryTwo);
			if (temp.getControllingFaction().equals("\\NONE")) {
				placeTroops(countryTwo, troopQuantity,
						temp.getControllingFaction());
				removeTroops(countryOne, troopQuantity);
				out = true;
			}
		}
		return out;
	}

	/**
	 * Tells user if specified country has a controlling faction.
	 * 
	 * @param country
	 *            country to be checked
	 * @return true if unoccupied else false
	 */
	public boolean isCountryControlled(int country) {
		Country temp = countrys.get(country);
		if (temp.getControllingFaction().equals("\\NONE"))
			return true;
		return false;
	}

	/**
	 * To be used in placing initial troops on map, and placing reinforcements.
	 * 
	 * Given an integer representing a country, number of troops and a String
	 * for a faction name, add troops to the specified country and change its
	 * controlling faction to the given faction
	 * 
	 * @param country
	 *            country to place troops in
	 * @param troopQuantity
	 *            number of troops to place
	 * @param Faction
	 *            faction placing troops
	 */
	public void placeTroops(int country, int troopQuantity, String Faction) {
		Country temp = countrys.get(country);
		temp.setControllingFaction(Faction);
		temp.setTroopQuantity(temp.getTroopQuantity() + troopQuantity);
	}

	/**
	 * Given a country and a number of troops remove those troops from that
	 * country.
	 * 
	 * @param country
	 *            integer of the country for troops to be removed from
	 * @param troopQuantity
	 *            number of troops to remove from specified country
	 */
	public void removeTroops(int country, int troopQuantity) {
		Country temp = countrys.get(country);
		temp.setTroopQuantity(temp.getTroopQuantity() - troopQuantity);
	}

	/*
	 * TODO placeHQ removeHQ placeCity placeScar
	 */

	/**
	 * To be used for getting the number of troops to be recrutied
	 * 
	 * Given a list of integers representing countries and the name of the
	 * player recruiting troops. The number of troops that can be recruited is
	 * the number of controlled countries plus the population in the countries
	 * (1 for Minor city, 2 for Major, 5 for capitols and HQs) divided by 3 plus
	 * any continent bonuses from owning a continent and modifiers on those
	 * continents.
	 * 
	 * @param controlledCountries
	 *            list of integers representing countries controlled by the
	 *            player calling the method
	 * @param playerName
	 *            String containing the name of the player
	 * @return integer representing the number of troops to be recruited.
	 */
	public int recruitTroops(ArrayList<Integer> controlledCountries,
			String playerName) {
		int out = 0;
		Country temp;
		for (int i : controlledCountries) {
			// +1 for country
			out++;
			temp = countrys.get(i);
			// +1 for minor city +2 for major and +3 for capitol
			if (temp.getCityType() == 1) {
				out++;
			} else if (temp.getCityType() == 2) {
				out += 2;
			} else if (temp.getCityType() == 3) {
				out += 5;
			}
			// + five for HQs
			if (!temp.getFactionHQ().equals("\\NONE")) {
				out += 5;
			}
		}
		if (out > 0)
			out = (int) Math.ceil(out / 3);
		for (Continent c : continent) {
			if (c.allContries(controlledCountries)) {
				out += c.getValue() + c.getBonus();
				if (!c.getTitle().equals("\\NONE")
						&& c.getNamer().equals(playerName)) {
					// +1 for naming the continent and owning it
					out++;
				}
			}
		}
		if (out == 0)
			return 3;
		return out;
	}

	/**
	 * To be used to move troops from one country to another.
	 * 
	 * Given two integers representing countries and an integer representing
	 * troop quantity move those troops from the first country to the second
	 * one.
	 * 
	 * @param countryFrom
	 *            country troops are being removed from
	 * @param countryTo
	 *            country troops are being moved to
	 * @param troopQuantity
	 *            number of troops to move
	 */
	public void fortifyCountry(int countryFrom, int countryTo, int troopQuantity) {
		Country one = countrys.get(countryFrom);
		Country two = countrys.get(countryTo);
		one.setTroopQuantity(one.getTroopQuantity() - troopQuantity);
		two.setTroopQuantity(two.getTroopQuantity() + troopQuantity);
	}

	/**
	 * Determains if a country can be fortied to given two integers representing
	 * the countries If the two countries are connected in that they share a
	 * border or can be connected to each other through other countries they
	 * share boarders with and those countries and so on as long as the two
	 * specified countries and the connection between them share the same
	 * controlling faction.
	 * 
	 * @param countryOne
	 *            first country
	 * @param countryTwo
	 *            second country
	 * @return true if countries are directly connected or can be transversed
	 *         from one to the other through bordering countries and they and
	 *         those countries have the same controlling factions.
	 */
	public boolean isFortifyable(int countryOne, int countryTwo) {
		boolean out = false;
		// If its not the same nation
		if (countryOne != countryTwo) {
			// adding current nation to the stack so it can be checked
			ArrayList<Integer> stack = new ArrayList<Integer>();
			stack.add(countryOne);
			Country temp;
			// as long as the stack is not empty, contiune the loop
			while (!stack.isEmpty()) {
				temp = countrys.get(stack.remove(0));
				if (!temp.getControllingFaction().equals("NONE")) {
					if (temp.getControllingFaction().equals(
							(countrys).get(countryTwo).getControllingFaction())) {
						// if above is true contry is fortifiable
						out = true;
						break;
					} else {
						// or its not. Bitch.
						// adds the contries borders to the stack
						for (int i : temp.getCountryBorders()) {
							stack.add(i);
						}
					}
				}
			}
		}
		return out;
	}

}
