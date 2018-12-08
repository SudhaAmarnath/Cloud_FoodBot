package edu.cloudtech.FoodBolt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import edu.cloudtech.FoodBolt.dao.CustomerDetails;
import edu.cloudtech.FoodBolt.dao.ServiceProvider;
import edu.cloudtech.FoodBolt.dao.TableReservation;
import edu.cloudtech.FoodBolt.service.ReservationService;
import edu.cloudtech.FoodBolt.service.ServiceProvideRowMapper;
import edu.cloudtech.FoodBolt.service.ServiceProviderService;
import edu.cloudtech.FoodBolt.service.UserRowMapper;


@Controller
public class LoginController {
	
	@Autowired 
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ServiceProviderService serviceproviderService;
	
	@Autowired
	ReservationService reservationService;
	
	@GetMapping("/login")
	public String getLogin() {
	
		System.out.println("In Login Controller");
		return "login";
	}
	
	
	@PostMapping("/login")
	public String login(@ModelAttribute(name="customerDetails") CustomerDetails customerDetails, Model model,  HttpSession session) {
		
		String Username = customerDetails.getEmail();
		String Password = customerDetails.getPassword();
		String DB_Username = "";
		String DB_Password = "";
		String First_Name ="";
		String Middle_Name ="";
		String Last_Name ="";
		String default_no_of_Guests="";
		String CuisineType="";
		String SELECT_SQL = "SELECT * FROM CUST_DETAILS where Email = ?";
		
		session.setAttribute("isLoggedIn", true);
		session.setAttribute("email", Username);

		CustomerDetails user = null;
		try {
			 user = jdbcTemplate.queryForObject(SELECT_SQL, new UserRowMapper(), customerDetails.getEmail());
		} catch(DataAccessException dae) {
			dae.printStackTrace();
		}
		
		if(user == null) {
			model.addAttribute("error", true);
			model.addAttribute("errorMessage", "UserNotAvailable");
			return "login";
		}
		DB_Username =user.getEmail();
		DB_Password =user.getPassword();
		First_Name=user.getFirst_name();
		Last_Name=user.getLast_name();
		CuisineType=user.getPref_cuisin_typ();

		default_no_of_Guests=user.getDefault_guests();
		
		session.setAttribute("firstName", First_Name);
		session.setAttribute("middleName", Middle_Name);
		session.setAttribute("lastName", Last_Name);
		session.setAttribute("cuisineType", CuisineType);
		session.setAttribute("default_no_of_guests", default_no_of_Guests);
	 
			
		if(DB_Username.isEmpty() || DB_Username == null) {
			model.addAttribute("error", true);
			model.addAttribute("errorMessage", "UserNotAvailable");
			return "login";
		}
		else if(DB_Username.equals(Username) && DB_Password.equals(Password)) {
			
			List<ServiceProvider> restaurants = serviceproviderService.getAllServiceProviders();
			model.addAttribute("restaurants", restaurants);
			 return "restaurant";
		}
		else {
			model.addAttribute("error", true);
			model.addAttribute("errorMessage", "InvalidCredential");
			return "login";
		}
	
			
	}
	
	@GetMapping("/loginservice")
	public String getServiceLogin() {
	
		System.out.println("In Login Controller");
		return "servicelogin";
	}
	
	@PostMapping("/loginservice")
	public String loginService(@ModelAttribute(name="ServiceProvider") ServiceProvider serviceProvider, Model model,  HttpSession session) {
		
		String Username = serviceProvider.getEmail();
		String Password = serviceProvider.getPassword();
		String DB_Username = "";
		String DB_Password = "";
		int DB_RestaurantID =0;
		String Middle_Name ="";
		String Last_Name ="";
		String default_no_of_Guests="";
		String CuisineType="";
		String SELECT_SQL = "SELECT * FROM SERVICE_PROVIDER where Email = ?";
		
		session.setAttribute("isLoggedIn", true);
		session.setAttribute("email", Username);

		ServiceProvider service = null;
		try {
			service = jdbcTemplate.queryForObject(SELECT_SQL, new ServiceProvideRowMapper(), serviceProvider.getEmail());
		} catch(DataAccessException dae) {
			dae.printStackTrace();
		}
		
		if(service == null) {
			model.addAttribute("error", true);
			model.addAttribute("errorMessage", "UserNotAvailable");
			return "ServiceProvider";
		}
		DB_Username =service.getEmail();
		DB_Password =service.getPassword();
		DB_RestaurantID= service.getRestaurant_id();
		
		System.out.println("DB_RestaurantID --- " + DB_RestaurantID);
		/*First_Name=user.getFirst_name();
		Last_Name=user.getLast_name();
		CuisineType=user.getPref_cuisin_typ();

		default_no_of_Guests=user.getDefault_guests()*/;
		
		session.setAttribute("restID", DB_RestaurantID);
		/*session.setAttribute("middleName", Middle_Name);
		session.setAttribute("lastName", Last_Name);
		session.setAttribute("cuisineType", CuisineType);
		session.setAttribute("default_no_of_guests", default_no_of_Guests);
	 */
			
		if(DB_Username.isEmpty() || DB_Username == null) {
			model.addAttribute("error", true);
			model.addAttribute("errorMessage", "UserNotAvailable");
			return "servicelogin";
		}
		else if(DB_Username.equals(Username) && DB_Password.equals(Password)) {
			
			
			
			
			List<TableReservation> reservation = reservationService.getReservationByRestaurantID(DB_RestaurantID);
					
				serviceproviderService.getServiceProvider(DB_RestaurantID);
			model.addAttribute("reservation", reservation);
			 return "ServiceProvider";
		}
		else {
			model.addAttribute("error", true);
			model.addAttribute("errorMessage", "InvalidCredential");
			return "login";
		}
	
			
	}

	
	@GetMapping("/logout")
	public String signout(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("isLoggedIn", false);
		request.getSession().setAttribute("firstName", "");
		request.getSession().setAttribute("middleName", "");
		request.getSession().setAttribute("lastName", "");
		request.getSession().setAttribute("cuisineType", "");
		request.getSession().setAttribute("default_no_of_guests", "");
	 
		
		return "login";
	}

	
	

}
