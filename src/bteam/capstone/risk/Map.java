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
	//                        Change this later
	public ArrayList<Country> cunttrees = new ArrayList<Country>();
	public ArrayList<Continent> continent = new ArrayList<Continent>();

	public Map(String data){
		Scanner scan = new Scanner(data);
		int numCont = scan.nextInt();
		scan.nextLine();
		for(int i=0;i<numCont;i++){
			String dataLine = scan.nextLine();
			continent.add(new Continent(dataLine));
		}
		while(scan.hasNextLine()){
			String dataLine = scan.nextLine();
			cunttrees.add(new Country(dataLine));
		}
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String data = "";
		data += continent.size() +"\n";
		for(Continent co: continent){
			data += co.toString() +"\n";
		}
		for(Country c:cunttrees){
			data += c.toString() + "\n";
		}
		return data;
	}
	
	/*
	 * To be used when expanding/fortifying to an adjacent country.
	 * 
	 */
	public boolean moveToops(int countryOne, int countryTwo, int troopQuantity){
		boolean out = false;
		if(countryOne != countryTwo){
			Country temp = cunttrees.get(countryTwo);
			if(temp.getControllingFaction().equals("\\NONE")){
				temp.setControllingFaction(cunttrees.get(countryOne).getControllingFaction());
				placeTroops(countryTwo, troopQuantity);
				removeTroops(countryOne, troopQuantity);
			}
		}
		return out;
	}

	/*
	 * Given an array list of integers representing controlled countries
	 * by a player returns an integer representing the number of troops
	 * that player gets for reinforcements.
	 * 
	 * revision:added continent bonus for owning all the countries in that
	 * continent and if the owning player also named the country they get
	 * a bonus one troop; 
	 * 
	 */
	public int recruitTroops(ArrayList<Integer> controlledCountries, String playerName){
		int out = 0;
		Country temp;
		for(int i: controlledCountries){
			out++;
			temp = cunttrees.get(i);
			if(temp.getCityType() == 1){
				out++;
			}else if(temp.getCityType() ==2){
				out +=2;
			}else if(temp.getCityType()==3){
				out +=5;
			}
		}
		if(controlledCountries.size() >0){
			out /=controlledCountries.size();
		}
		for(Continent c: continent){
			if(c.allContries(controlledCountries)){
				out += c.getValue()+c.getBonus();
				if(!c.getTitle().equals("\\NONE") && c.getNamer().equals(playerName)){
					out ++;
				}
			}
		}
		return  out;
	}
	/*
	 * moves troops from one country to another. Subtracts from one and adds
	 * to the other. Note that user must make sure player can only choose at
	 * max n-1 troops from the first country where n is the total troops in 
	 * this country. 
	 */
	public void fortifyCountry(int countryFrom, int countryTo, int troopQuantity) {
		Country one = cunttrees.get(countryFrom);
		Country two = cunttrees.get(countryTo);
		one.setTroopQuantity(one.getTroopQuantity() - troopQuantity);
		two.setTroopQuantity(two.getTroopQuantity() + troopQuantity);
	}

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
				temp = cunttrees.get(stack.remove(0));
				if (!temp.getControllingFaction().equals("NONE")) {
					if (temp.getControllingFaction()
							.equals((cunttrees).get(countryTwo)
									.getControllingFaction())) {
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
