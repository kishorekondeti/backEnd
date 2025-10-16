package discountAutoParts.services;

import discountAutoParts.exception.OutOfStockException;
import discountAutoParts.models.*;
import discountAutoParts.repositories.*;
import jakarta.transaction.Transactional;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class OrderService {
	@Autowired private OrderRepository orderRepository;
	@Autowired private ProductRepository productRepository;
	@Autowired private CustomerRepository customerRepository;
	@Autowired private OrderLineRepository orderLineRepository;
	@Autowired private LoginRepository loginRepository;
	@Autowired private VendorReposirtory vendorReposirtory;

	@Value("${productPicture}")
	String productPicture;

	public String addToCart(String name, String quantity, long productId) {
		ProductModel productModel = productRepository.findById(productId).get();
		VendorModel vendorModel = productModel.getVendorModel();
        CustomerModel customerModel = customerRepository.findByEmail(name);
        List<OrderModel> orderModelsList = orderRepository.findByVendorModelAndCustomerModelAndStatus(vendorModel,customerModel,"cart");
		OrderModel orderModel = new OrderModel();
        if(orderModelsList.size()==0) {
			orderModel.setStatus("cart");
			orderModel.setOrderedDate(LocalDateTime.now());
			System.out.println("--------ORDERED DATE-------"+LocalDateTime.now());
			orderModel.setCustomerModel(customerModel);
			orderModel.setVendorModel(vendorModel);
			orderRepository.save(orderModel);
		}else {
			orderModel = orderModelsList.get(0);
		}
        OrderLineModel orderLineModel = orderLineRepository.findByOrderModelAndProductModel(orderModel,productModel);
        if(orderLineModel ==null) {
			orderLineModel = new OrderLineModel();
			orderLineModel.setPrice(productModel.getPrice());
			orderLineModel.setOrderModel(orderModel);
			orderLineModel.setProductModel(productModel);
			orderLineModel.setQuantity(Integer.parseInt(quantity));
			orderLineRepository.save(orderLineModel);
			return "Added To Cart";
        }else {
			orderLineModel.setQuantity(orderLineModel.getQuantity()+Integer.parseInt(quantity));
			orderLineRepository.saveAndFlush(orderLineModel);
			productRepository.saveAndFlush(productModel);
			return "Cart Updated";
        }

	}

	public List<ViewOrdersModel> viewOrders(String name, String type) {
		UsersModel usersModel = loginRepository.findByEmail(name);

		List<ViewOrdersModel> viewOrdersModelsList = new ArrayList<ViewOrdersModel>();
		List<OrderModel> orderModelsList = new ArrayList<OrderModel>();
		if(usersModel.getRole().equalsIgnoreCase("ROLE_CUSTOMER")) {
			CustomerModel customerModel = customerRepository.findByEmail(name);
			if(type.equalsIgnoreCase("cart")) {
				orderModelsList = orderRepository.findByCustomerModelAndStatus(customerModel,"cart");
			}else if(type.equalsIgnoreCase("ordered")) {
				List<String> statusList = new ArrayList<>();
				statusList.add("ordered");
				statusList.add("dispatched");
				orderModelsList = orderRepository.findByCustomerModelAndStatusIn(customerModel, statusList);
			}else if(type.equalsIgnoreCase("history")) {
				orderModelsList = orderRepository.findByCustomerModelAndStatus(customerModel,"delivered");
			}
		}else if(usersModel.getRole().equalsIgnoreCase("ROLE_VENDOR")) {
			VendorModel merchantModel = vendorReposirtory.findByEmail(name);
			if(type.equalsIgnoreCase("ordered")) {
				orderModelsList = orderRepository.findByVendorModelAndStatus(merchantModel,"ordered");
			}else if(type.equalsIgnoreCase("dispatched")) {
				orderModelsList = orderRepository.findByVendorModelAndStatus(merchantModel, "dispatched");
			}else if(type.equalsIgnoreCase("history")) {
				orderModelsList = orderRepository.findByVendorModelAndStatus(merchantModel, "delivered");
			}
		}
		Iterator<OrderModel> orderModelsListIterator = orderModelsList.iterator();
		while(orderModelsListIterator.hasNext()) {
			OrderModel orderModel = orderModelsListIterator.next();
			List<OrderLineModel> orderLineModelsList = orderLineRepository.findByOrderModel(orderModel);
			ViewOrdersModel viewOrdersModel = new ViewOrdersModel();
			viewOrdersModel.setOrderModel(orderModel);
			List<OrderLineModel> orderLineModelsList2 = new ArrayList<OrderLineModel>();
			Iterator<OrderLineModel> orderItemModelsListIterator = orderLineModelsList.iterator();
			float totalPrice = 0;
			while(orderItemModelsListIterator.hasNext()) {
				OrderLineModel orderLineModel = orderItemModelsListIterator.next();
				CustomerModel customerModel = orderModel.getCustomerModel();
				ProductModel productModel = orderLineModel.getProductModel();
				try {
					File file=new File(productPicture+"/"+productModel.getPicture());
					InputStream in = new FileInputStream(file);
					productModel.setPicture2(Base64.getEncoder().encodeToString(IOUtils.toByteArray(in)));
					float price = productModel.getPrice();
					int quantity = orderLineModel.getQuantity();
					totalPrice = totalPrice+quantity * price;

					orderModel.setTotalPrice(totalPrice);

					orderLineModel.setProductModel(productModel);
					orderLineModelsList2.add(orderLineModel);
				}catch (Exception e) {
					System.out.println(e);
				}
			}
			viewOrdersModel.setOrderLineModels(orderLineModelsList2);
			viewOrdersModelsList.add(viewOrdersModel);
		}

		return viewOrdersModelsList;
	}


	public String orderNow(long orderId,float totalPrice,String payable,float loyalityAmount) {
		OrderModel orderModel = orderRepository.findById(orderId).get();
		System.out.println("Printing all the input of OrderNow:"+"orderId-"+orderId+"totalPrice-"+totalPrice+"payable-"+payable+loyalityAmount);
		List<OrderLineModel> orderLineModelsList = orderLineRepository.findByOrderModel(orderModel);
		Iterator<OrderLineModel> iterator = orderLineModelsList.iterator();
		int count = 0;
		while(iterator.hasNext()) {
			OrderLineModel orderLineModel = iterator.next();
			ProductModel productModel1 = orderLineModel.getProductModel();
			System.out.println(productModel1.getAvailable());
			System.out.println(orderLineModel.getQuantity());
			if(productModel1.getAvailable() >= orderLineModel.getQuantity()) {
				System.out.println("Available");
				orderLineModel.setStatus("Available");
				orderLineRepository.saveAndFlush(orderLineModel);
			}else {
				System.out.println("Not Available");
				orderLineModel.setStatus("Not Available");
				count++;
				orderLineRepository.saveAndFlush(orderLineModel);
				ErrorResponse errorResponse = new ErrorResponse("FAILED","OUT_OF_STOCK","Product "+productModel1.getProductName() + " is Out of Stock. Requested quantity: "+orderLineModel.getQuantity()+" is not available");
				throw new OutOfStockException(errorResponse);
			}

		}
		if(count > 0) {
			orderModel.getCustomerModel().setLoyalityPoints(String.valueOf(loyalityAmount/0.10));
			return "Order Not Placed";
		}
		Iterator<OrderLineModel> iterator1 = orderLineModelsList.iterator();
		while(iterator1.hasNext()) {
			OrderLineModel orderLineModel = iterator1.next();
			orderLineModel.setStatus("Ordered");
			orderLineRepository.saveAndFlush(orderLineModel);
			orderLineModel.getQuantity();
			ProductModel productModel = orderLineModel.getProductModel();
			int available = productModel.getAvailable() - orderLineModel.getQuantity();
			productModel.setAvailable(available);
			productRepository.saveAndFlush(productModel);
		}
		orderModel.setStatus("ordered");
		orderModel.setOrderedDate(LocalDateTime.now());
		orderModel.setTotalPrice(totalPrice);
		orderModel.setPayable(payable);
		orderModel.setLoyalityAmount(String.valueOf(loyalityAmount));
		orderModel.getCustomerModel().setLoyalityPoints(""+0);
		System.out.println(orderModel.getPayable());
		orderRepository.saveAndFlush(orderModel);
		return "Order Placed";
	}

	public String dispatchOrder(long orderId) {
		OrderModel orderModel = orderRepository.findById(orderId).get();
		orderModel.setStatus("dispatched");
		orderModel.setOrderedDate(LocalDateTime.now());
		orderRepository.saveAndFlush(orderModel);
		return "Order Dispatched";
	}

	public String delivered(long orderId) {
		OrderModel orderModel = orderRepository.findById(orderId).get();
		orderModel.setStatus("delivered");
		orderModel.setOrderedDate(LocalDateTime.now());
		CustomerModel customerModel = orderModel.getCustomerModel();
		try {
			float loyaltyPoints = Float.parseFloat(customerModel.getLoyalityPoints());
			float payable = Float.parseFloat(orderModel.getPayable());
			//loyaltyPoints = (float) (payable*0.1);
			//Loayalitypoints: $100 = 10 points
			int newPoints = (int) (loyaltyPoints + payable*0.1);
			customerModel.setLoyalityPoints(String.valueOf(newPoints));
		} catch (NumberFormatException e) {
			// Handle the exception, log it, or perform appropriate error handling
			e.printStackTrace(); // Example: Log the exception
		}
		customerRepository.saveAndFlush(customerModel);
		orderRepository.saveAndFlush(orderModel);
		return "Order Received";
	}


	public OrderModel viewAmount(long orderId) {
		OrderModel orderModel = orderRepository.findById(orderId).get();
		return orderModel;
	}


}
