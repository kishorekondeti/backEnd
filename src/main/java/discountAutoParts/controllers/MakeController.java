package discountAutoParts.controllers;

import discountAutoParts.models.MakeModel;
import discountAutoParts.services.MakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MakeController {
  @Autowired private MakeService makeService;
  
  @PostMapping("AddMake")
   public String addMake(@RequestBody MakeModel makeModel) {
	  return makeService.addMake(makeModel);
  }
  
  @GetMapping("ViewMakes")
  public List<MakeModel> viewMake(){
	  return makeService.viewMakes();
  }
  
  
}
