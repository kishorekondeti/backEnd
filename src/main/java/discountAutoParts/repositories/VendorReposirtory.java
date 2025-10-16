package discountAutoParts.repositories;

import java.util.List;

import discountAutoParts.models.VendorModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorReposirtory extends JpaRepository<VendorModel, Long> {

	List<VendorModel> findByEmailOrPhone(String email, String phone);

	VendorModel findByEmail(String email);


}
