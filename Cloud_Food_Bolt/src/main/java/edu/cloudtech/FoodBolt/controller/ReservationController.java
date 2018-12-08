package edu.cloudtech.FoodBolt.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.cloudtech.FoodBolt.dao.ServiceProvider;
import edu.cloudtech.FoodBolt.dao.TableReservation;
import edu.cloudtech.FoodBolt.service.ReservationService;
import edu.cloudtech.FoodBolt.service.ServiceProviderService;

@Controller
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	ServiceProviderService serviceproviderService;
	
//	@RequestMapping(value = "/reserveTable", method = RequestMethod.GET)
//	public List getAllCustomers() {
//		
//		System.out.println("In All Customer controller");
//		System.out.println("CUstomer Details" + customerDetailService.getAllCustomers());
//		return customerDetailService.getAllCustomers();
//		
//	}
	
	@RequestMapping(value = "/reserveTable", method = RequestMethod.GET)
	public String  reserveTable(@RequestParam(value="restID", required=false) int restID,Model model, HttpSession session , HttpServletRequest request) {
		
		if(!isLoggedIn(request.getSession()))
    	{
    		return "login";
    	}
    	else 
		System.out.println( "Reserve Table API"); 
		model.addAttribute("restaurantID", restID);
		
		session.setAttribute("restID", restID);
		
		return "reserveTable";
		
	
	}
	
	@RequestMapping(value = "/reserveTable", method = RequestMethod.POST)
	public String  reserveTable(@ModelAttribute(name="ReserveTable") TableReservation tblReserv, Model model,  HttpSession session,HttpServletRequest request) {
	
		if(!isLoggedIn(request.getSession()))
    	{
    		return "login";
    	}
    	else 
		{
    		System.out.println("Time Stamp " + tblReserv.getBookingTime());
    		Integer restaurantID= (Integer)session.getAttribute("restID");
		tblReserv.setRestaurantID(restaurantID);
		tblReserv.setStatus("RESERVED");
		System.out.println("Dynamic Restaurant ID" + tblReserv.getRestaurantID());
		
		
		 reservationService.reserveTable(tblReserv);
		
		 
		 
		List<ServiceProvider> restaurants = serviceproviderService.getAllServiceProviders();
		
		
		model.addAttribute("restaurants", restaurants);
		}
		
		
		 return "restaurant";
	
	}
	
	@RequestMapping(value = "/reserveTable/{bookingID}", method = RequestMethod.POST)
	public TableReservation  getReservation(@PathVariable int bookingID) {
		
		//Need to test this
		TableReservation reserv = new TableReservation();
		 return reservationService.getReservationByID(bookingID);
	
	}
	
	
	@RequestMapping(value = "/cancelReservation/{bookingID}", method = RequestMethod.GET)
	public String  cancelReservation(@PathVariable int bookingID, Model model, HttpSession session) {
		
		//Need to test this
		TableReservation reserv = new TableReservation();
		 reservationService.cancelReservation(bookingID);
		
		 
		 int rest_id = (Integer) session.getAttribute("restID");
		 
		 List<TableReservation> reservation = reservationService.getReservationByRestaurantID(rest_id);
			
			serviceproviderService.getServiceProvider(rest_id);
		model.addAttribute("reservation", reservation);
		 return "ServiceProvider";
	}
	
	
	
	@RequestMapping(value = "/findAvailability/{restID}", method = RequestMethod.POST)
	public TableReservation  findAvailability(@PathVariable int restID) {
		
		//Need to test this
		TableReservation reserv = new TableReservation();
		 return reservationService.getReservationByID(restID);
	
	}	
	
	  public static Boolean isLoggedIn(HttpSession session) {
	    	Boolean bRet = false;
	    	if(session != null) {
	    		bRet = (Boolean)session.getAttribute("isLoggedIn");
	    	}
	    	if(bRet == null)
	    		bRet = false;
	    	return bRet;
	    }
	
	
	

}
