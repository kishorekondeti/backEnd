package discountAutoParts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import discountAutoParts.models.OrderLineModel;
import discountAutoParts.models.OrderModel;
import discountAutoParts.models.ProductModel;
import discountAutoParts.repositories.OrderLineRepository;
import discountAutoParts.repositories.OrderRepository;
import discountAutoParts.repositories.ProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderLineService {
	@Autowired private OrderLineRepository orderLineRepository;
	@Autowired private OrderRepository orderRepository;
	@Autowired private ProductRepository productRepository;

	public String removeProduct(long orderLineId, long orderId) {
		OrderLineModel orderLineModel = orderLineRepository.findById(orderLineId).get();
		ProductModel productModel = orderLineModel.getProductModel();
		OrderModel orderModel = orderLineModel.getOrderModel();
		orderLineRepository.delete(orderLineModel);
		List<OrderLineModel> orderLineModelsList = orderLineRepository.findByOrderModel(orderModel);
		if(orderLineModelsList.size() == 0) {
			orderRepository.delete(orderModel);
		}
		return productModel.getProductName()+"Product Removed";
	}

}
