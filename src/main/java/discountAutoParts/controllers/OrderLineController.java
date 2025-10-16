package discountAutoParts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import discountAutoParts.services.OrderLineService;

@RestController
public class OrderLineController {
	@Autowired private OrderLineService orderLineService;
	
	@GetMapping("RemoveProduct")
	public String removeProduct(@RequestParam("orderId") long orderId,@RequestParam("orderLineId") long orderLineId) {
		return orderLineService.removeProduct(orderLineId,orderId);
	}

}
