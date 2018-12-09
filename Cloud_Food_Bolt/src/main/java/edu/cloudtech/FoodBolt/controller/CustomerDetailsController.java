package edu.cloudtech.FoodBolt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.cloudtech.FoodBolt.dao.CustomerDetails;
import edu.cloudtech.FoodBolt.dao.ServiceProvider;
import edu.cloudtech.FoodBolt.service.CustomerDetailsService;
import edu.cloudtech.FoodBolt.service.ServiceProviderService;


@Controller
/*@RequestMapping("/users")*/
public class CustomerDetailsController {

	
	@Autowired
	CustomerDetailsService customerDetailService;
	
	@Autowired
	ServiceProviderService serviceproviderService;
	
	@RequestMapping(value = "/customerDetails", method = RequestMethod.GET)
	public List getAllCustomers() {
		
		System.out.println("In All Customer controller");
		System.out.println("CUstomer Details" + customerDetailService.getAllCustomers());
		return customerDetailService.getAllCustomers();
		
	}
	
//	@GetMapping("/userprofile")
//	public String userprofile() {
//	System.out.println("Inside Get method ****");
////	Console.log("Inside get method");
//		return "userprofile_template.html";
//	}
	
	
	@RequestMapping(value = "/userprofile", method = RequestMethod.GET)
	public String getCustome(Model model, HttpServletRequest request) {
		
		int cust_id = (int)request.getSession().getAttribute("cust_ID");
		CustomerDetails cust = customerDetailService.getCustomerDetails(cust_id);
		
		System.out.println("In Customer controller with Cust ID " + cust_id);
		System.out.println("CUST ID FROM DB " + cust.getCust_id());
		model.addAttribute("userDetails", cust);
		
		return "userprofile";
		
	}
	
	@RequestMapping(value = "/userprofile", method = RequestMethod.POST)
	public String updateCustome(@ModelAttribute(name="CustomerDetails") CustomerDetails user ,Model model, HttpServletRequest request) {
		
		int cust_id = (int)request.getSession().getAttribute("cust_ID");
//		CustomerDetails cust = customerDetailService.getCustomerDetails(cust_id);
//		
		user.setCust_id(cust_id);
		System.out.println("User ID "  + user);
		customerDetailService.updateUser(user);
		
		System.out.println("In Customer controller with Cust ID " + user);
		System.out.println("CUST ID FROM DB " + user.getCust_id());
		model.addAttribute("userDetails", user);
		
		return "userprofile";
		
	}
	
	
//	@RequestMapping(value = "/signup", method = RequestMethod.GET)
//	public String addCustomer(@ModelAttribute(name="CustomerDetails") CustomerDetails user, Model model,  HttpSession session) {
//		
//
//		customerDetailService.addCustomerDetails(user);
//		
//		return "signup";
//		
//	}
	
	@GetMapping("/Signup")
	public String signup() {
	System.out.println("Inside Get method ****");
//	Console.log("Inside get method");
		return "signup";
	}
	
	@RequestMapping(value = "/Signup", method = RequestMethod.POST)
	public String addCustomer(@ModelAttribute(name="CustomerDetails") CustomerDetails user, Model model,  HttpSession session) {
		
		System.out.println("In SIgnUp Controller" + user.getEmail());
		System.out.println("Default Guest" +  user.getDefault_guests());
		customerDetailService.addCustomerDetails(user);
		
		List<ServiceProvider> restaurants = serviceproviderService.getAllServiceProviders();
		model.addAttribute("restaurants", restaurants);
		return "restaurant";
	
	}
}
