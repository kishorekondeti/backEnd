package discountAutoParts.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import discountAutoParts.models.RatingModel;

public interface RatingRepository extends JpaRepository<RatingModel, Long> {
	@Query(value = "SELECT avg(rating) FROM reviews WHERE order_line_id in (select order_line_id from order_lines where product_id=?)", nativeQuery = true)
	String getRating(long productId);
	
	@Query(value = "SELECT * FROM reviews WHERE order_line_id in (select order_line_id from order_lines where product_id=?)", nativeQuery = true)
	List<RatingModel> getRatings(long productId);

}
