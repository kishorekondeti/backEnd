package discountAutoParts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import discountAutoParts.models.PartModel;
import discountAutoParts.models.SubPartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import discountAutoParts.models.SubPartModel;
import discountAutoParts.repositories.PartRepository;
import discountAutoParts.repositories.SubPartRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SubPartService {
	@Autowired private SubPartRepository subPartRepository;
	@Autowired private PartRepository partRepository;

	public String addSubPart(long partId, SubPartModel subPartModel) {
		List<SubPartModel> subPartModelsList = subPartRepository.findBySubPartName(subPartModel.getSubPartName());
		if(subPartModelsList.size()>0) {
			return subPartModel.getSubPartName()+ " Exists";
		}else {
			PartModel partModel = partRepository.findById(partId).get();
			subPartModel.setPartModel(partModel);
			subPartRepository.save(subPartModel);
			return "Sub Part Added";
		}
		
	}

	public List<SubPartModel> viewSubParts(String partId) {
		List<SubPartModel> subPartModelsList = new ArrayList<SubPartModel>();
		if(partId.equalsIgnoreCase("")) {
			subPartModelsList = subPartRepository.findAll();
		}else {
			PartModel partModel = partRepository.findById(Long.parseLong(partId)).get();
			subPartModelsList = subPartRepository.findByPartModel(partModel);
		}
		return subPartModelsList;
	}

	public List<SubPartDTO> viewSubPartsDTO(String partId) {
		List<SubPartModel> subPartModelsList = new ArrayList<SubPartModel>();
		//List<SubPartDTO> subPartDTOList = new ArrayList<>();
		List<SubPartDTO> subPartDTOList = null;
		if (partId.equalsIgnoreCase("")) {
			subPartModelsList = subPartRepository.findAll();
		} else {
			PartModel partModel = partRepository.findById(Long.parseLong(partId)).get();
			subPartModelsList = subPartRepository.findByPartModel(partModel);

			if (subPartModelsList.size() > 0) {
//				for(SubPartModel subCategory : subPartModelsList) {
//					SubPartDTO subCategoryDTO = new SubPartDTO();
//					subCategoryDTO.setSubCategoryId(subCategory.getSubCategoryId());
//					subCategoryDTO.setSubCategoryName(subCategory.getSubCategoryName());
//
//					subPartDTOList.add(subCategoryDTO);
//
//				}

				subPartDTOList = subPartModelsList.stream()
						.map(model -> {
							SubPartDTO dto = new SubPartDTO();
							dto.setSubPartId(model.getSubPartId());
							dto.setSubPartName(model.getSubPartName());
							return dto;
						})
						.collect(Collectors.toList());
			}
		}
		return subPartDTOList;
	}




	public List<SubPartModel> getSubParts() {
		List<SubPartModel> subPartModelsList = subPartRepository.findAll();
		return subPartModelsList;
	}

	

}
