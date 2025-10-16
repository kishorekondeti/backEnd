package discountAutoParts.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import discountAutoParts.models.RatingModel;
import discountAutoParts.services.RatingService;

@RestController
public class RatingController {
	@Autowired private RatingService ratingService;
	
	@GetMapping("RatingForProduct")
	public String ratingForProduct(@RequestParam("productId") long productId,@RequestParam("review") String review,@RequestParam("rating") String rating,Principal principal,@RequestParam("orderLineId") long orderLineId) {
		return ratingService.ratingForProduct(rating,productId,review,principal.getName(),orderLineId);
	}
	@GetMapping("GetRatings")
	public List<RatingModel> getRatings(@RequestParam("productId") long productId){
		return ratingService.getRatings(productId);
	}

}
