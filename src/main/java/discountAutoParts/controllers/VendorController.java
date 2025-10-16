package discountAutoParts.controllers;

import discountAutoParts.models.VendorModel;
import discountAutoParts.services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VendorController {
	@Autowired private VendorService vendorService;
	
	@PostMapping("AddVendor")
	public String  addVendor(@RequestBody VendorModel vendorModel) {
		return vendorService.addVendor(vendorModel);
	}
	@GetMapping("ViewVendors")
	public List<VendorModel> viewVendors(){
		return vendorService.viewVendors();
	}
			

}
