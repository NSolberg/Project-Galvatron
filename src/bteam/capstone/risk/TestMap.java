package bteam.capstone.risk;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;

import org.junit.Test;



public class TestMap {
	//File file = new File("theWorld.txt");
	//String data = file.toString();
	String data = "6" + "\n" + 
	"NorthAmerica 	title NONE 5 0	 0 1 2 3 4 5 6 7 8" + "\n" +
	"SouthAmerica 	title NONE 5 0	 9 10 11 12" + "\n" +
	"Africa 		title NONE 5 0	 13 14 15 16 17 18 19" + "\n" +
	"Europe 	 	title NONE 5 0	 20 21 22 23 24 25" + "\n" +
	"Asia 			title NONE 5 0	 26 27 28 29 30 31 32 33 34 35 36 37" + "\n" +
	"Australia 		title NONE 5 0	 38 39 40 41" + "\n" +
	"0Alaska 				cityName 0 false 0 faction 0 \\NONE 0 false  34 1 5 " + "\n" +
	"1NorthwestTerritory 	cityName 0 false 0 faction 0 \\NONE 0 false  0 2 4 5" + "\n" +
	"2GreenLand 			cityName 0 false 0 faction 0 \\NONE 0 false  1 4 3 22" + "\n" +
	"3EasternCanada 		cityName 0 false 0 faction 0 \\NONE 0 false  2 4 7" + "\n" +
	"4Ontario 				cityName 0 false 0 faction 0 \\NONE 0 false  1 5 6 7 3 2" + "\n" +
	"5Alberta 				cityName 0 false 0 faction 0 \\NONE 0 false  1 0 6 3" + "\n" +
	"6WesternUS 			cityName 0 false 0 faction 0 \\NONE 0 false  5 4 8 7" + "\n" +
	"7EasternUS 			cityName 0 false 0 faction 0 \\NONE 0 false  3 4 6 8" + "\n" +
	"8CentralAmerica 		cityName 0 false 0 faction 0 \\NONE 0 false  7 6 9" + "\n" +
	"9Venezuala				cityName 0 false 0 faction 0 factionHQ 0 false  8 12 10" + "\n" +
	"10Peru 				cityName 0 false 0 faction 0 factionHQ 0 false  9 11 12" + "\n" +
	"11Argentina 			cityName 0 false 0 faction 0 factionHQ 0 false  10 12" + "\n" +
	"12Brazil 				cityName 1 true 1 faction 50 factionHQ 50 false  9 10 11 13" + "\n" +
	"13NorthAfrica 			cityName 1 true 1 faction 50 factionHQ 50 false  12 14 17 18 19 20" + "\n" +
	"14CentralAfrica 		cityName 1 true 1 faction 50 factionHQ 50 false  13 15 17" + "\n" +
	"15SouthAfrica 			cityName 1 true 1 faction 50 factionHQ 50 false  16 14 17" + "\n" +
	"16Madagascar 			cityName 1 true 1 faction 50 factionHQ 50 false  15 17" + "\n" +
	"17EastAfrica 			cityName 1 true 1 faction 50 factionHQ 50 false  16 15 14 13 18 28" + "\n" +
	"18Egypt 				cityName 1 true 1 faction 50 factionHQ 50 false  19 13 17 28" + "\n" +
	"19SouthernEurope 		cityName 1 true 1 faction 50 factionHQ 50 false  13 18 28 25 24 20" + "\n" +
	"20WesternEurope 		cityName 1 true 1 faction 50 factionHQ 50 false  21 24 19 13" + "\n" +
	"21GreatBritain 		cityName 1 true 1 faction 50 factionHQ 50 false 22 23 24 20" + "\n" +
	"22IceLand 				cityName 1 true 1 faction 50 factionHQ 50 false  2 21 23" + "\n" +
	"23Scandinavia 			cityName 1 true 1 faction 50 factionHQ 50 false  22 21 24 25" + "\n" +
	"24NorthernEurope 		cityName 1 true 1 faction 50 factionHQ 50 false  21 23 20 19 25" + "\n" +
	"25Russia 				cityName 1 true 1 faction 50 factionHQ 50 false  23 24 19 28 27 26" + "\n" +
	"26Ural 				cityName 1 true 1 faction 50 factionHQ 50 false  25 32 31 27" + "\n" +
	"27Afghanistan 			cityName 1 true 1 faction 50 factionHQ 50 false  25 26 31 29 28" + "\n" +
	"28MiddleEast 			cityName 1 true 1 faction 50 factionHQ 50 false  25 19 18 17 29 27" + "\n" +
	"29India 				cityName 1 true 1 faction 50 factionHQ 50 false  28 27 31 30" + "\n" +
	"30SoutheastAsia		cityName 1 true 1 faction 50 factionHQ 50 false  39 31 29" + "\n" +
	"31China 				cityName 1 true 1 faction 50 factionHQ 50 false  30 29 27 26 32 36" + "\n" +
	"32Siberia 				cityName 1 true 1 faction 50 factionHQ 50 false  26 31 36 37 33" + "\n" +
	"33Yakutsk 				cityName 1 true 1 faction 50 factionHQ 50 false  34 37 32" + "\n" +
	"34Kamchatka 			cityName 1 true 1 faction 50 factionHQ 50 false  0 35 33 37 36" + "\n" +
	"35Japan 				cityName 1 true 1 faction 50 factionHQ 50 false  36 34" + "\n" +
	"36Mongolia 			cityName 1 true 1 faction 50 factionHQ 50 false  35 34 37 32 31" + "\n" +
	"37Irkutsk 				cityName 1 true 1 faction 50 factionHQ 50 false 33 32 36 34" + "\n" +
	"38NewGuinea 			cityName 1 true 1 faction 50 factionHQ 50 false  41 40 39" + "\n" +
	"39Indonesia 			cityName 1 true 1 faction 50 factionHQ 50 false  30 38 40" + "\n" +
	"40WesternAustralia 	cityName 1 true 1 faction 50 factionHQ 50 false  41 38 39" + "\n" +
	"41EasternAustralia 	cityName 1 true 1 faction 50 factionHQ 50 false 38 40";

	@Test
	public void testMap(){
		Map map = new Map(data);
		
	}
	player PlayerOne = new player("One  0 0 0 0");
	Map testMap = new Map(data);
	/* tests that a player that controls all of northamerica gets
	 * the correct bonus
	 */
	@Test
	public void testPlayerBonusCont(){
		PlayerOne.addCountry(0);
		PlayerOne.addCountry(1);
		PlayerOne.addCountry(2);
		PlayerOne.addCountry(3);
		PlayerOne.addCountry(4);
		PlayerOne.addCountry(5);
		PlayerOne.addCountry(6);
		PlayerOne.addCountry(7);
		PlayerOne.addCountry(8);
		
		assertEquals(8, testMap.recruitTroops(PlayerOne.getCountrys(), PlayerOne.getName()));
		
	}
	
	@Test
	public void testPlayerBonus(){
		PlayerOne.addCountry(0);
		PlayerOne.addCountry(1);
		PlayerOne.addCountry(2);
		PlayerOne.addCountry(3);
		PlayerOne.addCountry(4);
		PlayerOne.addCountry(5);
		PlayerOne.addCountry(6);
		PlayerOne.addCountry(7);
		PlayerOne.addCountry(9);
		
		assertEquals(3, testMap.recruitTroops(PlayerOne.getCountrys(), PlayerOne.getName()));
		
	}
	
	@Test
	public void  testPlayerBonusCityType1(){
		PlayerOne.addCountry(0);
		testMap.getCountry(0).setCityType(1);
		assertEquals(1, testMap.recruitTroops(PlayerOne.getCountrys(), PlayerOne.getName()));
		
	}
	
	@Test
	public void  testPlayerBonusCityType2(){
		PlayerOne.addCountry(0);
		testMap.getCountry(0).setCityType(2);
		assertEquals(2, testMap.recruitTroops(PlayerOne.getCountrys(), PlayerOne.getName()));
		
	}
	
	@Test
	public void  testPlayerBonusCityType3(){
		PlayerOne.addCountry(0);
		testMap.getCountry(0).setCityType(3);
		assertEquals(5, testMap.recruitTroops(PlayerOne.getCountrys(), PlayerOne.getName()));
		
	}
	
	@Test
	public void  testPlayerBonusHQNamed(){
		PlayerOne.addCountry(0);
		testMap.getCountry(0).setFactionHQ("namedHQ");
		assertEquals(5, testMap.recruitTroops(PlayerOne.getCountrys(), PlayerOne.getName()));
		
	}
	@Test
	public void  testPlayerBonusNamingCont(){
		PlayerOne.addCountry(0);
		PlayerOne.addCountry(1);
		PlayerOne.addCountry(2);
		PlayerOne.addCountry(3);
		PlayerOne.addCountry(4);
		PlayerOne.addCountry(5);
		PlayerOne.addCountry(6);
		PlayerOne.addCountry(7);
		PlayerOne.addCountry(8);
		testMap.getCountry(0).setCityType(0);
		testMap.getCountry(0).setFactionHQ("//NONE");
		testMap.getContinent(0).setNamer("One");
		assertEquals(9, testMap.recruitTroops(PlayerOne.getCountrys(), PlayerOne.getName()));
		
	}
	
}
