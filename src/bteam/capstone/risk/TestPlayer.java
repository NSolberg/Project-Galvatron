package bteam.capstone.risk;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPlayer {
	String data1 = "PlayerA 1 2 12 5";
	String data2 = "PlayerB 2 2 1 2";
	String data3 = "PlayerC 0 2 2 1";
	String data4 = "PlayerD 1 2 7 3";
	String data5 = "PlayerE 0 3 5 4";
	
	player playerA = new player(data1);
	player playerB = new player(data2);
	player playerC = new player(data3);
	player playerD = new player(data4);
	player playerE = new player(data5);
	
	@Test
	public void testPlayer() {	
		assertEquals(playerA.toString(), data1);
		assertEquals(playerB.toString(), data2);;
		assertEquals(playerC.toString(), data3);;
		assertEquals(playerD.toString(), data4);;
		assertEquals(playerE.toString(), data5);;
	}

	@Test
	public void testGetName() {
		assertEquals("PlayerA", playerA.getName()); 
		assertEquals("PlayerB", playerB.getName());
		assertEquals("PlayerC", playerC.getName());
		assertEquals("PlayerD", playerD.getName());
		assertEquals("PlayerE", playerE.getName());
	}

	public void testPlayerCountrys() {
	
	}
	
	@Test
	public void testSetMissles() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMissles() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetRedstar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRedstar() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCoin() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCoin() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetResource() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetResource() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFaction() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFaction() {
		fail("Not yet implemented");
	}

}
