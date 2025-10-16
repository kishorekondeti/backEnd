package discountAutoParts.repositories;

import java.util.List;

import discountAutoParts.models.PartModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PartRepository extends JpaRepository<PartModel, Long> {

	List<PartModel> findByPartName(String partName);

}
