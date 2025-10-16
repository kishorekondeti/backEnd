package discountAutoParts.models;

import lombok.Data;

import java.util.List;


@Data
public class ViewOrdersModel {
	private OrderModel orderModel;
	private List<OrderLineModel> orderLineModels;

}
