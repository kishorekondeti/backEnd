package discountAutoParts.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import discountAutoParts.models.OrderLineModel;
import discountAutoParts.models.OrderModel;
import discountAutoParts.models.ProductModel;

public interface OrderLineRepository extends JpaRepository<OrderLineModel, Long> {

	OrderLineModel findByOrderModelAndProductModel(OrderModel orderModel, ProductModel productModel);

	List<OrderLineModel> findByOrderModel(OrderModel orderModel);


}
