package discountAutoParts.controllers;

import java.security.Principal;

import discountAutoParts.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import discountAutoParts.models.CustomerModel;
import discountAutoParts.services.CustomerService;

@RestController
public class CustomerController {
	@Autowired private CustomerService customerService;
	@Autowired private OrderRepository orderRepository;

	@PostMapping("CustomerRegistration")
	public String  customerRegistration(@RequestBody CustomerModel customerModel,Principal principal) {
		return customerService.CustomerRegistration(customerModel,principal);
	}

	@GetMapping("customerProfile")
	public CustomerModel customerProfile(Principal principal) {
		return customerService.customerProfile(principal.getName());
	}

//	@GetMapping("getCustomerOrderCount")
//	public Long countByCustomerModelEmailAndStatus(@RequestParam("email") String email, @RequestParam("status") String status){
//		return orderRepository.countByCustomerModelEmailAndStatus(email,status);
//	}

	@GetMapping("getCustomerOrderCount")
	public Long countByCustomerModelEmailAndStatus(Principal principal, @RequestParam("status") String status){
		return orderRepository.countByCustomerModelEmailAndStatus(principal.getName(),status);
	}



}
