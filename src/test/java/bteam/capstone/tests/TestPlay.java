package bteam.capstone.tests;


import static org.junit.Assert.*;

import org.junit.Test;

import bteam.capstone.guiTestArea.GUILogOnPanel;
import bteam.capstone.risk.Map;
import bteam.capstone.risk.player;
import bteam.capstone.server.RiskCore;

//public class TestPlay {

//	Play play = new Play();
//	@Test
//	public void testAttackOneInvalidAttack() {
//		System.out.println();
//		System.out.println("--------------------------------------------------");
//		System.out.println("||||                 Test 1                   ||||");
//		System.out.println("--------------------------------------------------");
//		System.out.println("Invalid attack, not enough troops to battle");
//		map.getCountry(0).setOwner(aPlayer);
//		map.getCountry(1).setOwner(bPlayer);
//		aPlayer.addCountry(0);
//		bPlayer.addCountry(1);
//		map.getCountry(0).setTroopQuantity(1);
//		map.getCountry(1).setTroopQuantity(1);
//		play.attack(map.getCountry(0), map.getCountry(1), map);
//		System.out.println("--------------------------------------------------");
//		System.out.println();
//		aPlayer.removeAllCountrys();
//		bPlayer.removeAllCountrys();
//		
//	}
//	
//	@Test
//	public void testAttackOneInvalidAttackNotConnected() {
//		System.out.println();
//		System.out.println("--------------------------------------------------");
//		System.out.println("||||                 Test 2                   ||||");
//		System.out.println("--------------------------------------------------");
//		System.out.println("Invalid attack country not connected");
//		map.getCountry(16).setOwner(aPlayer);
//		map.getCountry(0).setOwner(bPlayer);
//		aPlayer.addCountry(16);
//		bPlayer.addCountry(0);
//		map.getCountry(16).setTroopQuantity(4);
//		map.getCountry(0).setTroopQuantity(3);
//		play.attack(map.getCountry(16), map.getCountry(0), map);
//		System.out.println("--------------------------------------------------");
//		aPlayer.removeAllCountrys();
//		bPlayer.removeAllCountrys();
//	}
//	
//	@Test
//	public void testAttackSwitchThreeVsTwo() {
//		System.out.println();
//		System.out.println("--------------------------------------------------");
//		System.out.println("||||                 Test 3                   ||||");
//		System.out.println("--------------------------------------------------");
//		System.out.println("testAttack switch case one");
//		map.getCountry(0).setOwner(aPlayer);
//		map.getCountry(1).setOwner(bPlayer);
//		aPlayer.addCountry(0);
//		bPlayer.addCountry(1);
//		map.getCountry(0).setTroopQuantity(4);
//		map.getCountry(1).setTroopQuantity(3);
//		
//		play.attack(map.getCountry(0), map.getCountry(1), map);
//		System.out.println("Should Hit the first switch case: 3 atc dice 2 def");
//		System.out.println("--------------------------------------------------");
//		System.out.println();
//		aPlayer.removeAllCountrys();
//		bPlayer.removeAllCountrys();
//	}
//	
//	@Test
//	public void testAttackSwitchThreeVsOne() {
//		System.out.println();
//		System.out.println("--------------------------------------------------");
//		System.out.println("||||                 Test 4                   ||||");
//		System.out.println("--------------------------------------------------");
//		System.out.println("testAttack switch case two");
//		map.getCountry(0).setOwner(aPlayer);
//		map.getCountry(1).setOwner(bPlayer);
//		aPlayer.addCountry(0);
//		bPlayer.addCountry(1);
//		map.getCountry(0).setTroopQuantity(4);
//		map.getCountry(1).setTroopQuantity(1);
//		
//		play.attack(map.getCountry(0), map.getCountry(1), map);
//		System.out.println("Should Hit the second switch case: 3 atc dice 1 def");
//		System.out.println("--------------------------------------------------");
//		System.out.println();
//		aPlayer.removeAllCountrys();
//		bPlayer.removeAllCountrys();
//	}
//	
//	@Test
//	public void testAttackSwitchTwoVsTwo() {
//		System.out.println();
//		System.out.println("--------------------------------------------------");
//		System.out.println("||||                 Test 5                   ||||");
//		System.out.println("--------------------------------------------------");
//		System.out.println("testAttack switch case three");
//		map.getCountry(0).setOwner(aPlayer);
//		map.getCountry(1).setOwner(bPlayer);
//		aPlayer.addCountry(0);
//		bPlayer.addCountry(1);
//		map.getCountry(0).setTroopQuantity(3);
//		map.getCountry(1).setTroopQuantity(2);
//		
//		play.attack(map.getCountry(0), map.getCountry(1), map);
//		System.out.println("Should Hit the third switch case: 2 atc dice 2 def");
//		System.out.println("--------------------------------------------------");
//		System.out.println();
//		aPlayer.removeAllCountrys();
//		bPlayer.removeAllCountrys();
//	}
//	
//	@Test
//	public void testAttackSwitchTwoVsOne() {
//		System.out.println();
//		System.out.println("--------------------------------------------------");
//		System.out.println("||||                 Test 6                   ||||");
//		System.out.println("--------------------------------------------------");
//		System.out.println("testAttack switch case four");
//		map.getCountry(0).setOwner(aPlayer);
//		map.getCountry(1).setOwner(bPlayer);
//		aPlayer.addCountry(0);
//		bPlayer.addCountry(1);
//		map.getCountry(0).setTroopQuantity(3);
//		map.getCountry(1).setTroopQuantity(1);
//		
//		play.attack(map.getCountry(0), map.getCountry(1), map);
//		System.out.println("Should Hit the fourth switch case: 2 atc dice 1 def");
//		System.out.println("--------------------------------------------------");
//		System.out.println();
//		aPlayer.removeAllCountrys();
//		bPlayer.removeAllCountrys();
//	}
//	
//	@Test
//	public void testAttackSwitchOneVsOne() {
//		System.out.println();
//		System.out.println("--------------------------------------------------");
//		System.out.println("||||                 Test 7                   ||||");
//		System.out.println("--------------------------------------------------");
//		System.out.println("testAttack switch case five");
//		map.getCountry(0).setOwner(aPlayer);
//		map.getCountry(1).setOwner(bPlayer);
//		aPlayer.addCountry(0);
//		bPlayer.addCountry(1);
//		map.getCountry(0).setTroopQuantity(2);
//		map.getCountry(1).setTroopQuantity(1);
//		
//		play.attack(map.getCountry(0), map.getCountry(1), map);
//		System.out.println("Should Hit the fifth switch case: 2 atc dice 1 def");
//		System.out.println("--------------------------------------------------");
//		System.out.println();
//		aPlayer.removeAllCountrys();
//		bPlayer.removeAllCountrys();
//	}
//	
//	@Test
//	public void testAttackSwitchOneVsTwo() {
//		System.out.println();
//		System.out.println("--------------------------------------------------");
//		System.out.println("||||                 Test 8                   ||||");
//		System.out.println("--------------------------------------------------");
//		System.out.println("testAttack switch case six");
//		map.getCountry(0).setOwner(aPlayer);
//		map.getCountry(1).setOwner(bPlayer);
//		aPlayer.addCountry(0);
//		bPlayer.addCountry(1);
//		map.getCountry(0).setTroopQuantity(2);
//		map.getCountry(1).setTroopQuantity(2);
//		
//		play.attack(map.getCountry(0), map.getCountry(1), map);
//		System.out.println("Should Hit the sixth switch case: 2 atc dice 1 def");
//		System.out.println("--------------------------------------------------");
//		System.out.println();
//		aPlayer.removeAllCountrys();
//		bPlayer.removeAllCountrys();
//	}
//	
//	@Test
//	public void testAttackTestScarsAmmoShortage() {
//		System.out.println();
//		System.out.println("--------------------------------------------------");
//		System.out.println("||||                 Test 9                   ||||");
//		System.out.println("--------------------------------------------------");
//		System.out.println("testAttack AMMOSHORTAGE");
//		map.getCountry(0).setOwner(aPlayer);
//		map.getCountry(1).setOwner(bPlayer);
//		aPlayer.addCountry(0);
//		bPlayer.addCountry(1);
//		//add Ammo Shortage
//		map.getCountry(1).setScarType(1);
//		map.getCountry(0).setTroopQuantity(5);
//		map.getCountry(1).setTroopQuantity(6);
//		
//		play.attack(map.getCountry(0), map.getCountry(1), map);
//		System.out.println("Should Hit the first switch case: 3 atc dice 2 def");
//		System.out.println("--------------------------------------------------");
//		System.out.println();
//		aPlayer.removeAllCountrys();
//		bPlayer.removeAllCountrys();
//	}
//	
//	@Test
//	public void testAttackTestScarsBunker() {
//		System.out.println();
//		System.out.println("--------------------------------------------------");
//		System.out.println("||||                 Test 10                  ||||");
//		System.out.println("--------------------------------------------------");
//		System.out.println("testAttack BIOHAZARD!");
//		map.getCountry(0).setOwner(aPlayer);
//		map.getCountry(1).setOwner(bPlayer);
//		aPlayer.addCountry(0);
//		bPlayer.addCountry(1);
//		//Add Biohazard
//		map.getCountry(1).setScarType(2);
//		map.getCountry(0).setTroopQuantity(4);
//		map.getCountry(1).setTroopQuantity(3);
//		
//		play.attack(map.getCountry(0), map.getCountry(1), map);
//		System.out.println("Should Hit the first switch case: 3 atc dice 2 def");
//		System.out.println("--------------------------------------------------");
//		System.out.println();
//		aPlayer.removeAllCountrys();
//		bPlayer.removeAllCountrys();
//	}
//	
//	@Test
//	public void testAttackSwitch20vs20() {
//		System.out.println();
//		System.out.println("--------------------------------------------------");
//		System.out.println("||||                 Test 12                  ||||");
//		System.out.println("--------------------------------------------------");
//		System.out.println("testAttack with 20 vs 20");
//		map.getCountry(0).setOwner(aPlayer);
//		map.getCountry(1).setOwner(bPlayer);
//		aPlayer.addCountry(0);
//		bPlayer.addCountry(1);
//		map.getCountry(0).setTroopQuantity(20);
//		map.getCountry(1).setTroopQuantity(20);
//		
//		play.attack(map.getCountry(0), map.getCountry(1), map);
//		System.out.println("results will be random and hit different phases. ");
//		System.out.println("--------------------------------------------------");
//		System.out.println();
//		aPlayer.removeAllCountrys();
//		bPlayer.removeAllCountrys();
//	}
//	
//	@Test
//	public void testAttackerWinsCountryOwnerShip() {
//		System.out.println();
//		System.out.println("--------------------------------------------------");
//		System.out.println("||||                 Test 13                  ||||");
//		System.out.println("--------------------------------------------------");
//		System.out.println("testAttack with 20 vs 3");
//		map.getCountry(0).setOwner(aPlayer);
//		map.getCountry(1).setOwner(bPlayer);
//		aPlayer.addCountry(0);
//		bPlayer.addCountry(1);
//		map.getCountry(0).setTroopQuantity(20);
//		map.getCountry(1).setTroopQuantity(3);
//		
//		play.attack(map.getCountry(0), map.getCountry(1), map);
//		System.out.println("results will be random and hit different phases. ");
//		System.out.println(aPlayer.getCountrys().toString());
//		System.out.println("--------------------------------------------------");
//		System.out.println();
//		aPlayer.removeAllCountrys();
//		bPlayer.removeAllCountrys();
//	}
//	
//	@Test
//	public void testAttackStackAndMissles() {
//		System.out.println();
//		System.out.println("--------------------------------------------------");
//		System.out.println("||||                 Test 14                  ||||");
//		System.out.println("--------------------------------------------------");
//		System.out.println("testAttack with the missles and a stack");
//		map.getCountry(0).setOwner(aPlayer);
//		map.getCountry(1).setOwner(bPlayer);
//		aPlayer.addCountry(0);
//		bPlayer.addCountry(1);
//		player cPlayer = new player("PlayerChuck  0 0 0 0");
//		player dPlayer = new player("PlayerDave 0 0 0 0");
//		player ePlayer = new player("PlayerEllen  0 0 0 0");
//		aPlayer.setMissles(1);
//		bPlayer.setMissles(1);
//		cPlayer.setMissles(1);
//		dPlayer.setMissles(1);
//		ePlayer.setMissles(1);
//		map.getCountry(0).setTroopQuantity(4);
//		map.getCountry(1).setTroopQuantity(1);
//		
//		play.playerStack.add(ePlayer);
//		play.playerStack.add(dPlayer);
//		play.playerStack.add(cPlayer);
//		play.playerStack.add(bPlayer);
//		play.playerStack.add(aPlayer);
//		play.attack(map.getCountry(0), map.getCountry(1), map);
//		System.out.println("results will be random and hit different phases. ");
//		System.out.println(aPlayer.getCountrys().toString());
//		System.out.println("--------------------------------------------------");
//		System.out.println();
//		aPlayer.removeAllCountrys();
//		bPlayer.removeAllCountrys();
//	}
	
//	@Test
//	public void testNicksBasicRisk(){
//		RiskCore core = new RiskCore(null, data, data, false, false, data);
//		player cPlayer = new player("PlayerChuck  0 0 0 0");
//		player dPlayer = new player("PlayerDave 0 0 0 0");
//		player ePlayer = new player("PlayerEllen  0 0 0 0");
//		player fPlayer = new player("PlayerFred  0 0 0 0");
//		player gPlayer = new player("PlayerGeorge 0 0 0 0");
//		core.activePlayer.add(cPlayer);
//		core.activePlayer.add(dPlayer);
//		core.activePlayer.add(ePlayer);
//		core.activePlayer.add(fPlayer);
//		core.activePlayer.add(gPlayer);
//	

		//core.intialTurnRisk(5);
		//GUILogOnPanel.testInformation();
		//core.playGame(5);

	//}
//	String data = "6" + "\n" + 
//	"NorthAmerica 	title NONE 5 0	 0 1 2 3 4 5 6 7 8" + "\n" +
//	"SouthAmerica 	title NONE 5 0	 9 10 11 12" + "\n" +
//	"Africa 		title NONE 5 0	 13 14 15 16 17 18 19" + "\n" +
//	"Europe 	 	title NONE 5 0	 20 21 22 23 24 25" + "\n" +
//	"Asia 			title NONE 5 0	 26 27 28 29 30 31 32 33 34 35 36 37" + "\n" +
//	"Australia 		title NONE 5 0	 38 39 40 41" + "\n" +
//	"0Alaska 				\\NONE 0 false 0 faction 0 \\NONE 0 false  34 1 5 " + "\n" +
//	"1NorthwestTerritory 	\\NONE 0 false 0 faction 0 \\NONE 0 false  0 2 4 5" + "\n" +
//	"2GreenLand 			\\NONE 0 false 0 faction 0 \\NONE 0 false  1 4 3 22" + "\n" +
//	"3EasternCanada 		\\NONE 0 false 0 faction 0 \\NONE 0 false  2 4 7" + "\n" +
//	"4Ontario 				\\NONE 0 false 0 faction 0 \\NONE 0 false  1 5 6 7 3 2" + "\n" +
//	"5Alberta 				\\NONE 0 false 0 faction 0 \\NONE 0 false  1 0 6 3" + "\n" +
//	"6WesternUS 			\\NONE 0 false 0 faction 0 \\NONE 0 false  5 4 8 7" + "\n" +
//	"7EasternUS 			\\NONE 0 false 0 faction 0 \\NONE 0 false  3 4 6 8" + "\n" +
//	"8CentralAmerica 		\\NONE 0 false 0 faction 0 \\NONE 0 false  7 6 9" + "\n" +
//	"9Venezuala				\\NONE 0 false 0 faction 0 \\NONE 0 false  8 12 10" + "\n" +
//	"10Peru 				\\NONE 0 false 0 faction 0 \\NONE 0 false  9 11 12" + "\n" +
//	"11Argentina 			\\NONE 0 false 0 faction 0 \\NONE 0 false  10 12" + "\n" +
//	"12Brazil 				\\NONE 1 true 1 faction 50 \\NONE 50 false  9 10 11 13" + "\n" +
//	"13NorthAfrica 			\\NONE 1 true 1 faction 50 \\NONE 50 false  12 14 17 18 19 20" + "\n" +
//	"14CentralAfrica 		\\NONE 1 true 1 faction 50 \\NONE 50 false  13 15 17" + "\n" +
//	"15SouthAfrica 			\\NONE 1 true 1 faction 50 \\NONE 50 false  16 14 17" + "\n" +
//	"16Madagascar 			\\NONE 1 true 1 faction 50 \\NONE 50 false  15 17" + "\n" +
//	"17EastAfrica 			\\NONE 1 true 1 faction 50 \\NONE 50 false  16 15 14 13 18 28" + "\n" +
//	"18Egypt 				\\NONE 1 true 1 faction 50 \\NONE 50 false  19 13 17 28" + "\n" +
//	"19SouthernEurope 		\\NONE 1 true 1 faction 50 \\NONE 50 false  13 18 28 25 24 20" + "\n" +
//	"20WesternEurope 		\\NONE 1 true 1 faction 50 \\NONE 50 false  21 24 19 13" + "\n" +
//	"21GreatBritain 		\\NONE 1 true 1 faction 50 \\NONE 50 false 22 23 24 20" + "\n" +
//	"22IceLand 				\\NONE 1 true 1 faction 50 \\NONE 50 false  2 21 23" + "\n" +
//	"23Scandinavia 			\\NONE 1 true 1 faction 50 \\NONE 50 false  22 21 24 25" + "\n" +
//	"24NorthernEurope 		\\NONE 1 true 1 faction 50 \\NONE 50 false  21 23 20 19 25" + "\n" +
//	"25Russia 				\\NONE 1 true 1 faction 50 \\NONE 50 false  23 24 19 28 27 26" + "\n" +
//	"26Ural 				\\NONE 1 true 1 faction 50 \\NONE 50 false  25 32 31 27" + "\n" +
//	"27Afghanistan 			\\NONE 1 true 1 faction 50 \\NONE 50 false  25 26 31 29 28" + "\n" +
//	"28MiddleEast 			\\NONE 1 true 1 faction 50 \\NONE 50 false  25 19 18 17 29 27" + "\n" +
//	"29India 				\\NONE 1 true 1 faction 50 \\NONE 50 false  28 27 31 30" + "\n" +
//	"30SoutheastAsia		\\NONE 1 true 1 faction 50 \\NONE 50 false  39 31 29" + "\n" +
//	"31China 				\\NONE 1 true 1 faction 50 \\NONE 50 false  30 29 27 26 32 36" + "\n" +
//	"32Siberia 				\\NONE 1 true 1 faction 50 \\NONE 50 false  26 31 36 37 33" + "\n" +
//	"33Yakutsk 				\\NONE 1 true 1 faction 50 \\NONE 50 false  34 37 32" + "\n" +
//	"34Kamchatka 			\\NONE 1 true 1 faction 50 \\NONE 50 false  0 35 33 37 36" + "\n" +
//	"35Japan 				\\NONE 1 true 1 faction 50 \\NONE 50 false  36 34" + "\n" +
//	"36Mongolia 			\\NONE 1 true 1 faction 50 \\NONE 50 false  35 34 37 32 31" + "\n" +
//	"37Irkutsk 				\\NONE 1 true 1 faction 50 \\NONE 50 false 33 32 36 34" + "\n" +
//	"38NewGuinea 			\\NONE 1 true 1 faction 50 \\NONE 50 false  41 40 39" + "\n" +
//	"39Indonesia 			\\NONE 1 true 1 faction 50 \\NONE 50 false  30 38 40" + "\n" +
//	"40WesternAustralia 	\\NONE 1 true 1 faction 50 \\NONE 50 false  41 38 39" + "\n" +
//	"41EasternAustralia 	\\NONE 1 true 1 faction 50 \\NONE 50 false 38 40";
//
//	//Map map = new Map(data);
//	
//	player aPlayer = new player("AttackerBob  0 0 0 0");
//	player bPlayer = new player("DefenderSue 0 0 0 0");
//	
//
//}
