package discountAutoParts.services;

import discountAutoParts.models.PartModel;
import discountAutoParts.repositories.PartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PartService {
	@Autowired PartRepository partRepository;

	public String addPart(PartModel partModel) {
		List<PartModel> partModelsList = partRepository.findByPartName(partModel.getPartName());
		if(partModelsList.size()>0) {
			return "Part Exists";
		}else {
			partRepository.save(partModel);
			return "Part Added";
		}

	}

	public List<PartModel> viewParts() {
		List<PartModel> partModelsList = partRepository.findAll();
		return partModelsList;
	}

}
