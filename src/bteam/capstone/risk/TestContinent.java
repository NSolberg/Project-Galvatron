package bteam.capstone.risk;

import static org.junit.Assert.*;

import org.junit.Test;


public class TestContinent {
	String cont1 = "NorthAmerica title namer 50 502 0 1 2 3 4 5 6 7 8 ";
	String cont2 = "SouthAmerica title namer 50 502 9 10 11 12 ";
	
	@Test
	public void testContMakeSome(){
		Continent one = new Continent(cont1);
		Continent two = new Continent(cont2);
		
		assertEquals(cont1, one.toString());
		assertEquals(cont2, two.toString());
	}
}
