package discountAutoParts.repositories;

import discountAutoParts.models.CustomerModel;
import discountAutoParts.models.OrderModel;
import discountAutoParts.models.VendorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {

	List<OrderModel> findByVendorModelAndCustomerModelAndStatus(VendorModel vendorModel,
																  CustomerModel customerModel, String string);

	List<OrderModel> findByCustomerModelAndStatus(CustomerModel customerModel, String string);

	List<OrderModel> findByVendorModelAndStatus(VendorModel vendorModel, String string);

	List<OrderModel> findByCustomerModelAndStatusIn(CustomerModel customerModel, List<String> statusList);

    Long countByCustomerModelEmailAndStatus(String email, String status);
}
