package discountAutoParts.services;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import discountAutoParts.models.CustomerModel;
import discountAutoParts.models.UsersModel;
import discountAutoParts.repositories.CustomerRepository;
import discountAutoParts.repositories.LoginRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {
	@Autowired private CustomerRepository customerRepository;
	@Autowired private LoginRepository loginRepository;
	
	public String CustomerRegistration(CustomerModel customerModel, Principal principal) {
		List<CustomerModel> customerModelsList = customerRepository.findByEmailOrPhone(customerModel.getEmail(),customerModel.getPhone());
		if(customerModelsList.size() >0) {
			return "Duplicate Customer Details";
		}

		UsersModel credentialsModel = new UsersModel();
		credentialsModel.setEmail(customerModel.getEmail());
		credentialsModel.setPassword(new BCryptPasswordEncoder().encode(customerModel.getPassword()));
		credentialsModel.setRole("ROLE_CUSTOMER");
		loginRepository.save(credentialsModel);
		UsersModel credentialsModel2 = loginRepository.findByEmail(customerModel.getEmail());
	    customerModel.setLoginCredentialsModel(credentialsModel2);
	    float loyalityPoints = 0;
	    customerModel.setLoyalityPoints(""+loyalityPoints);
		customerRepository.save(customerModel);
		return "Customer Registered Successfully";
	}

	public CustomerModel customerProfile(String name) {
		CustomerModel customerModel = customerRepository.findByEmail(name);
		return customerModel;
	}
	

}
