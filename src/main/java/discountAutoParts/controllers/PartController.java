package discountAutoParts.controllers;

import java.util.List;

import discountAutoParts.models.PartModel;
import discountAutoParts.services.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PartController {
	@Autowired private PartService partService;
	
	@PostMapping("AddPart")
	public String addPart(@RequestBody PartModel partModel) {
		return partService.addPart(partModel);
	}
		
	@GetMapping("ViewParts")
	public List<PartModel>  viewParts(){
		return partService.viewParts();
	}

}
