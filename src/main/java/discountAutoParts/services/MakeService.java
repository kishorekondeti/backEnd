package discountAutoParts.services;

import java.util.List;

import discountAutoParts.models.MakeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import discountAutoParts.repositories.MakeRepository;

@Service
public class MakeService {
	@Autowired private MakeRepository makeRepository;

	public String addMake(MakeModel makeModel) {
		List<MakeModel> makeModelList = makeRepository.findByMakeName(makeModel.getMakeName());
		if(makeModelList.size()>0) {
			return makeModel.getMakeName()+ " Exists";
		}else {
			makeRepository.save(makeModel);
			return "Added Successfully";
		}
		
	}

	public List<MakeModel> viewMakes() {
		List<MakeModel> makeModelsList = makeRepository.findAll();
		return makeModelsList;
	}

}
