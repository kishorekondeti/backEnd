package discountAutoParts.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import discountAutoParts.models.CustomerModel;
import discountAutoParts.models.OrderLineModel;
import discountAutoParts.models.ProductModel;
import discountAutoParts.models.RatingModel;
import discountAutoParts.repositories.CustomerRepository;
import discountAutoParts.repositories.OrderLineRepository;
import discountAutoParts.repositories.ProductRepository;
import discountAutoParts.repositories.RatingRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RatingService {
	@Autowired private RatingRepository ratingRepository;
	@Autowired private ProductRepository productRepository;
	@Autowired private CustomerRepository customerRepository;
	@Autowired private OrderLineRepository orderLineRepository;

	public String ratingForProduct(String rating, long productId, String review,String name,long orderLineId) {
		ProductModel productModel = productRepository.findById(productId).get();
		CustomerModel customerModel = customerRepository.findByEmail(name);
		OrderLineModel orderLineModel = orderLineRepository.findById(orderLineId).get();
		orderLineModel.setStatus("Rating Given");
		RatingModel ratingModel = new RatingModel();
		ratingModel.setDate(new Date());
		ratingModel.setRating(rating);
		ratingModel.setReview(review);
		ratingModel.setCustomerModel(customerModel);
		ratingModel.setOrderLineModel(orderLineModel);
		ratingRepository.save(ratingModel);
		return "Your Response Submitted";
	}

	public List<RatingModel> getRatings(long productId) {
		List<RatingModel> ratingModelsList = ratingRepository.getRatings(productId);
		return ratingModelsList;
	}

}
