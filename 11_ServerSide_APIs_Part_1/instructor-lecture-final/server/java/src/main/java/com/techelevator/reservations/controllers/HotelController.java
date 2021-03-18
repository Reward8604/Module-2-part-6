package com.techelevator.reservations.controllers;

import com.techelevator.reservations.dao.HotelDAO;
import com.techelevator.reservations.dao.MemoryHotelDAO;
import com.techelevator.reservations.dao.MemoryReservationDAO;
import com.techelevator.reservations.dao.ReservationDAO;
import com.techelevator.reservations.models.Hotel;
import com.techelevator.reservations.models.HotelFilters;
import com.techelevator.reservations.models.Reservation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HotelController
{

	private HotelDAO hotelDAO;
	private ReservationDAO reservationDAO;

	public HotelController()
	{
		this.hotelDAO = new MemoryHotelDAO();
		this.reservationDAO = new MemoryReservationDAO(hotelDAO);
	}
	
	// Hotels
	
	@RequestMapping(path = "/hotels", method = RequestMethod.GET)
	public List<Hotel> list(
			@RequestParam(value="name_like", defaultValue = "") String name
			, @RequestParam(value="cost_gte", defaultValue = "0") double costPerNight
			)
	{
		List<Hotel> hotels =  hotelDAO.list();
		hotels = HotelFilters.filterByName(hotels, name);
		hotels = HotelFilters.filterByCost(hotels, costPerNight);
		
		return hotels;
	}

	@RequestMapping(path = "/hotels/{id}", method = RequestMethod.GET)
	public Hotel get(@PathVariable int id)
	{
		return hotelDAO.get(id);
	}
	
	// @RequestParam looks in the queryString
	// For a hotelId it is best practice to use a path variable
	// query string is better suited for additional flexible parameters
	@RequestMapping(path = "/hotels/getbyid", method = RequestMethod.GET)
	public Hotel getById(@RequestParam( value="hotelId",defaultValue = "1") int id)
	{
		return hotelDAO.get(id);
	}
	
	@RequestMapping(path="/hotels", method = RequestMethod.POST)
	public void insert(@RequestBody Hotel hotel)
	{
		hotelDAO.create(hotel);
	}
	
	// Reservations
	

    /**
     * Returns all reservations in the system
     *
     * @return all reservations
     */
    @RequestMapping( path = "/reservations", method = RequestMethod.GET)
    public List<Reservation> listReservations() {
        return reservationDAO.findAll();
    }

    /**
     * Get a reservation by its id
     *
     * @param id
     * @return a single reservation
     */
    @RequestMapping( path = "reservations/{id}", method = RequestMethod.GET)
    public Reservation getReservation(@PathVariable int id) {
        return reservationDAO.get(id);
    }

    /**
     * List of reservations by hotel
     *
     * @param hotelID
     * @return all reservations for a given hotel
     */
    @RequestMapping( path = "/hotels/{id}/reservations", method = RequestMethod.GET)
    public List<Reservation> listReservationsByHotel(@PathVariable("id") int hotelID) {
        return reservationDAO.findByHotel(hotelID);
    }

    /**
     * Create a new reservation for a given hotel
     *
     * @param reservation
     * @param hotelID
     */
    @RequestMapping( path = "/hotels/{id}/reservations", method = RequestMethod.POST)
    public Reservation addReservation(@RequestBody Reservation reservation, @PathVariable("id") int hotelID) {
        return reservationDAO.create(reservation, hotelID);
    }

    /**
     * /hotels/filter?state=oh&city=cleveland
     *
     * @param state the state to filter by
     * @param city  the city to filter by
     * @return a list of hotels that match the city & state
     */
    @RequestMapping(path = "/hotels/filter", method = RequestMethod.GET)
    public List<Hotel> filterByStateAndCity(@RequestParam String state, @RequestParam(required = false) String city) {

        List<Hotel> filteredHotels = new ArrayList<>();
        List<Hotel> hotels = list("", 0); // add default values

        // return hotels that match state
        for (Hotel hotel : hotels) {

            // if city was passed we don't care about the state filter
            if (city != null) {
                if (hotel.getAddress().getCity().toLowerCase().equals(city.toLowerCase())) {
                    filteredHotels.add(hotel);
                }
            } else {
                if (hotel.getAddress().getState().toLowerCase().equals(state.toLowerCase())) {
                    filteredHotels.add(hotel);
                }

            }
        }

        return filteredHotels;
    }

}
