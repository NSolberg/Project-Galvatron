package bteam.capstone.risk;

import java.util.ArrayList;
import java.util.Scanner;

public class Continent {
	private String name;
	private String title;
	private String namer;

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

	private int value;
	private int bonus;
	private ArrayList<Integer> countries;

	public Continent(String data) {
		Scanner scan = new Scanner(data);
		name = scan.next();
		title = scan.next();
		namer = scan.next();
		value = scan.nextInt();
		bonus = scan.nextInt();
		while (scan.hasNextInt()) {
			int temp = scan.nextInt();
			countries.add(temp);
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

	/*
	 * given a list of integers representing countries see if it contains all
	 * the countries that make up the continent and if it does return true;
	 */
	public boolean allContries(ArrayList<Integer> inCountries) {
		boolean out = false;
		int num = 0;
		for (int i : inCountries) {
			if (countries.contains(i)) {
				num++;
			}
		}
		if (num == countries.size()) {
			out = true;
		}
		return out;
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
