package discountAutoParts.controllers;

import java.util.List;

import discountAutoParts.models.SubPartDTO;
import discountAutoParts.models.SubPartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import discountAutoParts.services.SubPartService;

@RestController
public class SubPartController {
	@Autowired private SubPartService subPartService;
	
	@PostMapping("AddSubPart")
	public String addSubPart(@RequestBody SubPartModel subPartModel, @RequestParam("partId") long partId) {
		System.out.println(subPartModel);
		System.out.println(partId);
		return subPartService.addSubPart(partId, subPartModel);
	}

	@GetMapping("ViewSubParts")
	public List<SubPartModel> viewSubParts(@RequestParam("partId") String partId){
		return subPartService.viewSubParts(partId);
	}
	
	@GetMapping("ViewSubPartsDTO")
	public List<SubPartDTO> viewSubPartsDTO(@RequestParam("partId") String partId){
		return subPartService.viewSubPartsDTO(partId);
	}
	@GetMapping("GetSubParts")
	public List<SubPartModel> getSubParts(){
		return subPartService.getSubParts();
	}

}
