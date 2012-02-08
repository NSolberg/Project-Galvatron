package bteam.capstone.risk;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;

import org.junit.Test;



public class TestMap {
	//File file = new File("theWorld.txt");
	//String data = file.toString();
	String data2 = "6";
	String data = "6" + "\n" + 
	"NorthAmerica title namer 5 0 0 1 2 3 4 5 6 7 8" + "\n" +
	"SouthAmerica title namer 2 0 9 10 11 12" + "\n" +
	"Africa title namer 3 0 13 14 15 16 17 18 19" + "\n" +
	"Europe title namer 5 0 20 21 22 23 24 25" + "\n" +
	"Asia title namer 7 0 26 27 28 29 30 31 32 33 34 35 36 37" + "\n" +
	"Australia title namer 2 0 38 39 40 41" + "\n" +
	"0Alaska cityName 1 true 1 NONE 0 factionHQ 0 false 34 1 5 " + "\n" +
	"1NorthwestTerritory cityName 1 true 1 NONE 0 factionHQ 0 false 0 2 4 5" + "\n" +
	"2GreenLand cityName 1 true 1 NONE 0 factionHQ 0 false  1 4 3 22" + "\n" +
	"3EasternCanada cityName 1 true 1 NONE 0 factionHQ 0 false  2 4 7" + "\n" +
	"4Ontario cityName 1 true 1 NONE 0 factionHQ 0 false  1 5 6 7 3 2" + "\n" +
	"5Alberta cityName 1 true 1 NONE 0 factionHQ 0 false  1 0 6 3" + "\n" +
	"6WesternUS cityName 1 true 1 NONE 0 factionHQ 0 false  5 4 8 7" + "\n" +
	"7EasternUS cityName 1 true 1 NONE 0 factionHQ 0 false  3 4 6 8" + "\n" +
	"8CentralAmerica cityName 1 true 1 faction 50 factionHQ 50 false  7 6 9" + "\n" +
	"9Venezuala cityName 1 true 1 faction 50 factionHQ 50 false  8 12 10" + "\n" +
	"10Peru cityName 1 true 1 faction 50 factionHQ 50 false  9 11 12" + "\n" +
	"11Argentina cityName 1 true 1 faction 50 factionHQ 50 false  10 12" + "\n" +
	"12Brazil cityName 1 true 1 faction 50 factionHQ 50 false  9 10 11 13" + "\n" +
	"13NorthAfrica cityName 1 true 1 faction 50 factionHQ 50 false  12 14 17 18 19 20" + "\n" +
	"14CentralAfrica cityName 1 true 1 faction 50 factionHQ 50 false  13 15 17" + "\n" +
	"15SouthAfrica cityName 1 true 1 faction 50 factionHQ 50 false  16 14 17" + "\n" +
	"16Madagascar cityName 1 true 1 faction 50 factionHQ 50 false  15 17" + "\n" +
	"17EastAfrica cityName 1 true 1 faction 50 factionHQ 50 false  16 15 14 13 18 28" + "\n" +
	"18Egypt cityName 1 true 1 faction 50 factionHQ 50 false  19 13 17 28" + "\n" +
	"19SouthernEurope cityName 1 true 1 faction 50 factionHQ 50 false  13 18 28 25 24 20" + "\n" +
	"20WesternEurope cityName 1 true 1 faction 50 factionHQ 50 false  21 24 19 13" + "\n" +
	"21GreatBritain cityName 1 true 1 faction 50 factionHQ 50 false 22 23 24 20" + "\n" +
	"22IceLand cityName 1 true 1 faction 50 factionHQ 50 false  2 21 23" + "\n" +
	"23Scandinavia cityName 1 true 1 faction 50 factionHQ 50 false  22 21 24 25" + "\n" +
	"24NorthernEurope cityName 1 true 1 faction 50 factionHQ 50 false  21 23 20 19 25" + "\n" +
	"25Russia cityName 1 true 1 faction 50 factionHQ 50 false  23 24 19 28 27 26" + "\n" +
	"26Ural cityName 1 true 1 faction 50 factionHQ 50 false  25 32 31 27" + "\n" +
	"27Afghanistan cityName 1 true 1 faction 50 factionHQ 50 false  25 26 31 29 28" + "\n" +
	"28MiddleEast cityName 1 true 1 faction 50 factionHQ 50 false  25 19 18 17 29 27" + "\n" +
	"29India cityName 1 true 1 faction 50 factionHQ 50 false  28 27 31 30" + "\n" +
	"30SoutheastAsia	cityName 1 true 1 faction 50 factionHQ 50 false  39 31 29" + "\n" +
	"31China cityName 1 true 1 faction 50 factionHQ 50 false  30 29 27 26 32 36" + "\n" +
	"32Siberia cityName 1 true 1 faction 50 factionHQ 50 false  26 31 36 37 33" + "\n" +
	"33Yakutsk cityName 1 true 1 faction 50 factionHQ 50 false  34 37 32" + "\n" +
	"34Kamchatka cityName 1 true 1 faction 50 factionHQ 50 false  0 35 33 37 36" + "\n" +
	"35Japan cityName 1 true 1 faction 50 factionHQ 50 false  36 34" + "\n" +
	"36Mongolia cityName 1 true 1 faction 50 factionHQ 50 false  35 34 37 32 31" + "\n" +
	"37Irkutsk cityName 1 true 1 faction 50 factionHQ 50 false 33 32 36 34" + "\n" +
	"38NewGuinea cityName 1 true 1 faction 50 factionHQ 50 false  41 40 39" + "\n" +
	"39Indonesia cityName 1 true 1 faction 50 factionHQ 50 false  30 38 40" + "\n" +
	"40WesternAustralia cityName 1 true 1 faction 50 factionHQ 50 false  41 38 39" + "\n" +
	"41EasternAustralia cityName 1 true 1 faction 50 factionHQ 50 false 38 40";
	Map testMap = new Map(data);
	player playerA = new player("PlayerA 1 2 12 5");
	
	
	@Test
	public void testGenerateANewMap(){
		Map map = new Map(data);
		
	}
	
	@Test
	public void testPlayerGetsJustContBonus(){
		playerA.addCountry(0);
		playerA.addCountry(1); 
		playerA.addCountry(2); 
		playerA.addCountry(3); 
		playerA.addCountry(4); 
		playerA.addCountry(5); 
		playerA.addCountry(6); 
		playerA.addCountry(7); 
		playerA.addCountry(8); 
		//player has no bonus's except a continent bonus;
		assertEquals(8, testMap.recruitTroops(playerA.getCountrys(), "PlayerA"));
		
	}
	
	@Test
	public void testPlayerGetsContBonusAndCity(){
		playerA.removeAllCountrys();
		testMap.countrys.get(0).setCityType(3);
		playerA.addCountry(0);
		playerA.addCountry(1); 
		playerA.addCountry(2); 
		playerA.addCountry(3); 
		playerA.addCountry(4); 
		playerA.addCountry(5); 
		playerA.addCountry(6); 
		playerA.addCountry(7); 
		playerA.addCountry(8); 
		
		assertEquals(14, testMap.recruitTroops(playerA.getCountrys(), "PlayerA"));
		
	}
	
	@Test
	public void testPlayerGetsNumbersBonus(){
		
	}
	
	@Test
	public void testPlayerGetsMultContHQBonus(){
		
	}
}
