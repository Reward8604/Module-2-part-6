package com.techelevator.auctions.controller;

import com.techelevator.auctions.DAO.AuctionDAO;
import com.techelevator.auctions.DAO.MemoryAuctionDAO;
import com.techelevator.auctions.model.Auction;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auctions")
public class AuctionController 
{

    private AuctionDAO dao;

    public AuctionController() 
    {
        this.dao = new MemoryAuctionDAO();
    }
    
    @RequestMapping( method = RequestMethod.GET)
    public List<Auction> list(@RequestParam( value = "title_like", defaultValue = "")String title
    		, @RequestParam( value = "currentBid_lte" , defaultValue = "0") double currentBid)
    {
    	if(title.length() > 0 && currentBid > 0)
    	{
    		return	dao.searchByTitleAndPrice(title, currentBid);
    	}
    	else if (title.length() > 0)
    	{
    		return dao.searchByTitle(title);
    	}
    	else if (currentBid > 0)
    	{
    		return dao.searchByPrice(currentBid);
    	}
    	else 
    	{
    	    return dao.list();
    	}
    }
    
    @RequestMapping( path = "/{id}", method = RequestMethod.GET)
    public Auction get(@PathVariable int id)
    {
    	return dao.get(id);
    }
    
    @RequestMapping( method = RequestMethod.POST)
    public Auction create(@RequestBody Auction auction)
    {
    	return dao.create(auction);
    }

   
}
