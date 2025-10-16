package discountAutoParts.services;

import discountAutoParts.models.UsersModel;
import discountAutoParts.models.VendorModel;
import discountAutoParts.repositories.LoginRepository;
import discountAutoParts.repositories.VendorReposirtory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class VendorService {
	@Autowired private VendorReposirtory vendorReposirtory;
	@Autowired private LoginRepository loginRepository;

	public String addVendor(VendorModel vendorModel) {
		List<VendorModel> vendorModelsList = vendorReposirtory.findByEmailOrPhone(vendorModel.getEmail(),vendorModel.getPhone());
		if(vendorModelsList.size()>0) {
			return "Duplicate Merchant Details";
		}
		UsersModel credentialsModel = new UsersModel();
		credentialsModel.setEmail(vendorModel.getEmail());
		credentialsModel.setPassword(new BCryptPasswordEncoder().encode(vendorModel.getPassword()));
		credentialsModel.setRole("ROLE_VENDOR");
		loginRepository.save(credentialsModel);
		UsersModel credentialsModel2 = loginRepository.findByEmail(vendorModel.getEmail());
		vendorModel.setLoginCredentialsModel(credentialsModel2);
		vendorReposirtory.save(vendorModel);
		return "Vendor Added Successfully";
	}

	public List<VendorModel> viewVendors() {
		List<VendorModel> vendorModelsList = vendorReposirtory.findAll();
		return vendorModelsList;
	}

}
