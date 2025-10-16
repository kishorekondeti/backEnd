package discountAutoParts.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import discountAutoParts.models.CustomerModel;

public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

	List<CustomerModel> findByEmailOrPhone(String email, String phone);

	CustomerModel findByEmail(String name);

}
