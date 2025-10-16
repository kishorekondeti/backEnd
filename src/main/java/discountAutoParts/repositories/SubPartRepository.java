package discountAutoParts.repositories;

import java.util.List;

import discountAutoParts.models.PartModel;
import discountAutoParts.models.SubPartModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubPartRepository extends JpaRepository<SubPartModel, Long> {

	List<SubPartModel> findBySubPartName(String subPartName);

	List<SubPartModel> findByPartModel(PartModel partModel);

}
