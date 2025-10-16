package discountAutoParts.services;

import discountAutoParts.models.CustomerModel;
import discountAutoParts.models.DiscountDTO;
import discountAutoParts.models.OrderModel;
import discountAutoParts.repositories.CustomerRepository;
import discountAutoParts.repositories.OrderRepository;
import discountAutoParts.repositories.VendorReposirtory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class DiscountService {
	@Autowired private VendorReposirtory vendorReposirtory;
	@Autowired private OrderRepository orderRepository;
	@Autowired private CustomerRepository customerRepository;






	public DiscountDTO orderDiscountValidation(long orderId) {
		String customerStatus = "delivered";
		OrderModel orderModel = orderRepository.findById(orderId).get();
		CustomerModel customerModel = orderModel.getCustomerModel();

		String email  = customerModel.getEmail();
		DiscountDTO discountDTO = new DiscountDTO();
		float total_amount = orderModel.getTotalPrice();

		if(orderRepository.countByCustomerModelEmailAndStatus(email,customerStatus) ==0){
			//Set First Order Discount as 5%
			discountDTO.setFirstOrderDiscount((float) (total_amount*0.05));

			if(total_amount>1000){
				//Set Bulk Order Discount as 10%
				discountDTO.setBulkOrderDiscount((float)(total_amount*0.10));
			}

		} else{

			if(total_amount>1000){
				//Set Bulk Order Discount as 10%
				discountDTO.setBulkOrderDiscount((float)(total_amount*0.10));
			}
		}

		float payable = total_amount - discountDTO.getFirstOrderDiscount()-discountDTO.getBulkOrderDiscount();
		System.out.println(payable +":payable amount");
		int tax_percentage= 0;
		if(customerModel.getCategory().equalsIgnoreCase("Student")) {
			tax_percentage = 8;
		}else if(customerModel.getCategory().equalsIgnoreCase("Veteran")) {
			tax_percentage = 0;
		}else if(customerModel.getCategory().equalsIgnoreCase("Senior Citizen")) {
			tax_percentage = 5;
		}else if(customerModel.getCategory().equalsIgnoreCase("Normal")) {
			tax_percentage = 10;
		}
		float tax = payable * tax_percentage/100;
		payable = payable + tax;
		System.out.println(payable);
		System.out.println(customerModel.getLoyalityPoints());
		// LoayalityAmount = noOfLoyalityPoints * 0.10
		double loyality_amount = Float.parseFloat(customerModel.getLoyalityPoints()) * 0.10;
		String formattedLoyaltyAmount = String.format("%.2f", loyality_amount);

		System.out.println(loyality_amount+"Loyality Amount Generated");
		if(payable>=loyality_amount) {
			payable = payable - (float)loyality_amount;
			customerRepository.saveAndFlush(customerModel);
		}else {
			payable = 0;
			//Each Dollar is 10 loyality points
			int remaining_points = (int)((loyality_amount - payable)* 10);
			customerModel.setLoyalityPoints(""+remaining_points);
			customerRepository.saveAndFlush(customerModel);
		}
		orderModel.setTax(""+tax);
		orderModel.setLoyalityAmount(""+formattedLoyaltyAmount);
		orderModel.setPayable(""+payable);

		orderRepository.saveAndFlush(orderModel);

		return discountDTO;
	}

}
