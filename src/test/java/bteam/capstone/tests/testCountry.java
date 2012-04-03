//package bteam.capstone.tests;
//
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//
//import org.junit.Test;
//
//import bteam.capstone.risk.Country;
//
//public class testCountry {
//	String countryA = "0Alaska cityName 1 true 1 faction 50 factionHQ 50 false 34 1 5 ";
//	String countryB = "1NorthwestTerritory cityName 1 true 1 faction 50 factionHQ 50 false 0 2 4 5 ";
//	String countryC = "2GreenLand cityName 1 true 1 faction 50 factionHQ 50 false 1 4 3 22 ";
//	String countryD = "3EasternCanada cityName 1 true 1 faction 50 factionHQ 50 false 2 4 7 ";
//	String countryE = "4Ontario cityName 1 true 1 faction 50 factionHQ 50 false 1 5 6 7 3 2 ";
//	String countryF = "5Alberta cityName 1 true 1 faction 50 factionHQ 50 false 1 0 6 3 ";
//	String countryG = "6WesternUS cityName 1 true 1 faction 50 factionHQ 50 false 5 4 8 7 ";
//	String countryH = "7EasternUS cityName 1 true 1 faction 50 factionHQ 50 false 3 4 6 8 ";
//	Country one = new Country(countryA);
//	Country two = new Country(countryB);
//	Country three = new Country(countryC);
//	Country four = new Country(countryD);
//	Country five = new Country(countryE);
//	Country six = new Country(countryF);
//	Country seven = new Country(countryG);
//	Country eight = new Country(countryH);
//	
//	@Test
//	public void testCountrySaveLoad() {
//
//		assertEquals(countryA, one.toString());
//		assertEquals(countryB, two.toString());
//		assertEquals(countryC, three.toString());
//		assertEquals(countryD, four.toString());
//		assertEquals(countryE, five.toString());
//		assertEquals(countryF, six.toString());
//		assertEquals(countryG, seven.toString());
//		assertEquals(countryH, eight.toString());
//
//	}
//
//	@Test
//	public void testGetCountryName() {
//		assertEquals("0Alaska", one.getCountryName());
//		assertEquals("1NorthwestTerritory", two.getCountryName());
//		assertEquals("2GreenLand", three.getCountryName());
//		assertEquals("3EasternCanada", four.getCountryName());
//		assertEquals("4Ontario", five.getCountryName());
//		assertEquals("5Alberta", six.getCountryName());
//		assertEquals("6WesternUS", seven.getCountryName());
//		assertEquals("7EasternUS", eight.getCountryName());
//	}
//
//	@Test
//	public void testSetCountryName() {
//		one.setCityName("NewAlaska");
//		two.setCityName("NewNorthwest");
//
//		assertEquals("NewAlaska", one.getCityName());
//		assertEquals("NewNorthwest", two.getCityName());
//	}
//
//	@Test
//	public void testAddCountryBorders() {
//		ArrayList<Integer> temp = new ArrayList<Integer>();
//		temp.add(1);
//		temp.add(34);
//		temp.add(5);
//		temp.add(14);
//		one.addCountryBorders(14);
//		one.getCountryBorders();
//		assertEquals(true, temp.containsAll(one.getCountryBorders()));
//	}
//
//	@Test
//	public void testIsCityFortified() {
//		one.setCityFortified(true);
//		assertEquals(true, one.isCityFortified());
//	}
//
//}
