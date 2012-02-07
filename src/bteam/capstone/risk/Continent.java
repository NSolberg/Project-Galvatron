package bteam.capstone.risk;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class representing a single continent containing the following information:
 * The name of the continent, the title of the continent if given one if not to
 * be represented by the String \NONE, the name of the player that gave the
 * continent its title, the value or worth of the continent and a bonus which
 * can be either: +1 or -1
 * 
 * @author Austin Langhorne
 * 
 */
public class Continent {
	private String name;
	private String title;
	private String namer;
	private int value;
	private int bonus;
	private ArrayList<Integer> countries = new ArrayList<Integer>();

	/**
	 * Constructor for the class accepting a String to be parsed.
	 * 
	 * @param data
	 *            String to be parsed that must follow the following format
	 *            (where underscores represent spaces, all one line, and x
	 *            represents an arbitrary number of integers separated by a
	 *            space): name_title_namer_value_bonus_x
	 */
	public Continent(String data) {
		Scanner scan = new Scanner(data);
		name = scan.next();
		title = scan.next();
		namer = scan.next();
		value = scan.nextInt();
		bonus = scan.nextInt();
		while (scan.hasNextInt()) {

			Integer temp = scan.nextInt();
			countries.add(temp);

		}
	}

	/**
	 * Creates String representing the class in the following following format
	 * (where underscores represent spaces, all one line, and x represents an
	 * arbitrary number of integers separated by a space):
	 * name_title_namer_value_bonus_x
	 */
	@Override
	public String toString() {
		String data = "";
		data += name + " ";
		data += title + " ";
		data += namer + " ";
		data += value + " ";
		data += bonus + " ";
		for (int i : countries) {
			data += i + " ";
		}
		return data;
	}

	/**
	 * Method accepting a list of integers representing countries, and if the
	 * array contains all the counties in this continent it returns true.
	 * 
	 * @param inCountries
	 *            list of integers representing countries to be tested to see if
	 *            it contains all the countries in the class
	 * @return true if inCountries contain all the countries in this continent
	 *         else false
	 */
	public boolean allContries(ArrayList<Integer> inCountries) {
		if (countries.size() < inCountries.size()) {
			for (int c : countries) {
				if (!inCountries.contains(c)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * @return the namer
	 */
	public String getNamer() {
		return namer;
	}

	/**
	 * @param namer
	 *            the namer to set
	 */
	public void setNamer(String namer) {
		this.namer = namer;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the bonus
	 */
	public int getBonus() {
		return bonus;
	}

	/**
	 * @param bonus
	 *            the bonus to set
	 */
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	/**
	 * @return the countries
	 */
	public ArrayList<Integer> getCountries() {
		return countries;
	}

	/**
	 * @param countries
	 *            the countries to set
	 */
	public void setCountries(ArrayList<Integer> countries) {
		this.countries = countries;
	}

}
