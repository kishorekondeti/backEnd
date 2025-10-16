package discountAutoParts.controllers;
import discountAutoParts.models.DiscountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import discountAutoParts.services.DiscountService;

@RestController
public class DiscountController {
	@Autowired private DiscountService discountService;

	@GetMapping("orderDiscountValidation")
	public DiscountDTO orderDiscountValidation(@RequestParam("orderId") long orderId) {
		System.out.println(orderId);
		return discountService.orderDiscountValidation(orderId);
	}
	

}
