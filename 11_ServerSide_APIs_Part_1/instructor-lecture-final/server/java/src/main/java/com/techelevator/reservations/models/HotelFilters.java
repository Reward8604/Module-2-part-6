package com.techelevator.reservations.models;

import java.util.ArrayList;
import java.util.List;

public class HotelFilters
{
	
	public static List<Hotel> filterByName(List<Hotel> hotels, String name)
	{
		// new list
		List<Hotel> filteredList = new ArrayList<Hotel>();
		
		for (Hotel hotel : hotels)
		{
			if(hotel.getName().toLowerCase().contains(name.toLowerCase()))
			{
				filteredList.add(hotel);
			}
		}
		
		return filteredList;
		
	}
	
	public static List<Hotel> filterByCost(List<Hotel> hotels, double costPerNight)
	{
		// new list
		List<Hotel> filteredList = new ArrayList<Hotel>();
		
		for (Hotel hotel : hotels)
		{
			if(hotel.getCostPerNight() >= costPerNight)
			{
				filteredList.add(hotel);
			}
		}
		
		return filteredList;		
	}
	

}
