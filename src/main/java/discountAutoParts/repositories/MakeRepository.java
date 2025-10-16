package discountAutoParts.repositories;

import java.util.List;

import discountAutoParts.models.MakeModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MakeRepository extends JpaRepository<MakeModel, Long> {

	List<MakeModel> findByMakeName(String makeName);


}
