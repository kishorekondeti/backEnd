package discountAutoParts.services;

import discountAutoParts.models.*;
import discountAutoParts.repositories.*;
import jakarta.transaction.Transactional;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class ProductService {
	@Autowired private ProductRepository productRepository;
	@Autowired private MakeRepository makeRepository;
	@Autowired private SubPartRepository subPartRepository;
	@Autowired private VendorReposirtory vendorReposirtory;
	@Autowired private PartRepository categoryRepository;
	@Autowired private LoginRepository loginRepository;
	@Autowired private CustomerRepository customerRepository;
	@Autowired private RatingRepository ratingRepository;
	@Value("${productPicture}")
	String productPicture;

	public String addProduct(ProductModel productModel, long makeId, long subPartId,String email) {
		VendorModel vendorModel = vendorReposirtory.findByEmail(email);
		MakeModel makeModel = makeRepository.findById(makeId).get();
		SubPartModel subPartModel = subPartRepository.findById(subPartId).get();
		productModel.setSubPartModel(subPartModel);
		productModel.setMakeModel(makeModel);
		productModel.setVendorModel(vendorModel);
		productRepository.save(productModel);
		return "Product Added Successfully";
	}

	public List<ProductModel> viewProducts(String name,String makeId,String searchKeyword,String partId,String subPartId) {
		UsersModel usersModel = loginRepository.findByEmail(name);
		List<ProductModel> productModelsList = new ArrayList<ProductModel>();
		if(usersModel.getRole().equalsIgnoreCase("ROLE_VENDOR")) {
			VendorModel vendorModel = vendorReposirtory.findByEmail(name);
			if(makeId.equalsIgnoreCase("") && partId.equalsIgnoreCase("")&& subPartId.equalsIgnoreCase("")) {
				productModelsList = productRepository.findByVendorModelAndProductNameLike(vendorModel,"%"+searchKeyword+"%");
			}else if(makeId.equalsIgnoreCase("") && partId.equalsIgnoreCase("")&& !subPartId.equalsIgnoreCase("")) {
				SubPartModel subPartModel = subPartRepository.findById(Long.parseLong(subPartId)).get();
				productModelsList = productRepository.findByVendorModelAndProductNameLikeAndSubPartModel(vendorModel,"%"+searchKeyword+"%", subPartModel);
			}else if(makeId.equalsIgnoreCase("") && !partId.equalsIgnoreCase("")&& subPartId.equalsIgnoreCase("")) {
				PartModel partModel  = categoryRepository.findById(Long.parseLong(partId)).get();
				List<SubPartModel> subPartModelList = subPartRepository.findByPartModel(partModel);
				productModelsList = productRepository.findByVendorModelAndProductNameLikeAndSubPartModelIn(vendorModel,"%"+searchKeyword+"%", subPartModelList);
			}else if(makeId.equalsIgnoreCase("") && !partId.equalsIgnoreCase("")&& !subPartId.equalsIgnoreCase("")) {
				SubPartModel subPartModel = subPartRepository.findById(Long.parseLong(subPartId)).get();
				productModelsList = productRepository.findByVendorModelAndProductNameLikeAndSubPartModel(vendorModel,"%"+searchKeyword+"%", subPartModel);
			}else if(!makeId.equalsIgnoreCase("") && partId.equalsIgnoreCase("")&& subPartId.equalsIgnoreCase("")) {
				MakeModel makeModel = makeRepository.findById(Long.parseLong(makeId)).get();
				productModelsList = productRepository.findByVendorModelAndProductNameLikeAndMakeModel(vendorModel,"%"+searchKeyword+"%",makeModel);
			}else if(!makeId.equalsIgnoreCase("") && partId.equalsIgnoreCase("")&& !subPartId.equalsIgnoreCase("")) {
				MakeModel makeModel = makeRepository.findById(Long.parseLong(makeId)).get();
				SubPartModel subPartModel = subPartRepository.findById(Long.parseLong(subPartId)).get();
				productModelsList = productRepository.findByVendorModelAndProductNameLikeAndMakeModelAndSubPartModel(vendorModel,"%"+searchKeyword+"%",makeModel, subPartModel);
			}else if(!makeId.equalsIgnoreCase("") && !partId.equalsIgnoreCase("")&& subPartId.equalsIgnoreCase("")) {
				MakeModel makeModel = makeRepository.findById(Long.parseLong(makeId)).get();
				PartModel categoryModel  = categoryRepository.findById(Long.parseLong(partId)).get();
				List<SubPartModel> subPartModelList = subPartRepository.findByPartModel(categoryModel);
				productModelsList = productRepository.findByVendorModelAndProductNameLikeAndMakeModelAndSubPartModelIn(vendorModel,"%"+searchKeyword+"%",makeModel, subPartModelList);
			}else if(!makeId.equalsIgnoreCase("") && !partId.equalsIgnoreCase("")&& !subPartId.equalsIgnoreCase("")) {
				MakeModel makeModel = makeRepository.findById(Long.parseLong(makeId)).get();
				SubPartModel subPartModel = subPartRepository.findById(Long.parseLong(subPartId)).get();
				productModelsList = productRepository.findByVendorModelAndProductNameLikeAndMakeModelAndSubPartModel(vendorModel,"%"+searchKeyword+"%",makeModel, subPartModel);
			}
		}else if(usersModel.getRole().equalsIgnoreCase("ROLE_CUSTOMER")) {
			if(makeId.equalsIgnoreCase("") && partId.equalsIgnoreCase("")&& subPartId.equalsIgnoreCase("")) {
				productModelsList = productRepository.findByProductNameLike("%"+searchKeyword+"%");
			}else if(makeId.equalsIgnoreCase("") && partId.equalsIgnoreCase("")&& !subPartId.equalsIgnoreCase("")) {
				SubPartModel subPartModel = subPartRepository.findById(Long.parseLong(subPartId)).get();
				productModelsList = productRepository.findByProductNameLikeAndSubPartModel("%"+searchKeyword+"%", subPartModel);
			}else if(makeId.equalsIgnoreCase("") && !partId.equalsIgnoreCase("")&& subPartId.equalsIgnoreCase("")) {
				PartModel categoryModel  = categoryRepository.findById(Long.parseLong(partId)).get();
				List<SubPartModel> subPartModelList = subPartRepository.findByPartModel(categoryModel);
				productModelsList = productRepository.findByProductNameLikeAndSubPartModelIn("%"+searchKeyword+"%", subPartModelList);
			}else if(makeId.equalsIgnoreCase("") && !partId.equalsIgnoreCase("")&& !subPartId.equalsIgnoreCase("")) {
				SubPartModel subPartModel = subPartRepository.findById(Long.parseLong(subPartId)).get();
				productModelsList = productRepository.findByProductNameLikeAndSubPartModel("%"+searchKeyword+"%", subPartModel);
			}else if(!makeId.equalsIgnoreCase("") && partId.equalsIgnoreCase("")&& subPartId.equalsIgnoreCase("")) {
				MakeModel makeModel = makeRepository.findById(Long.parseLong(makeId)).get();
				productModelsList = productRepository.findByProductNameLikeAndMakeModel("%"+searchKeyword+"%",makeModel);
			}else if(!makeId.equalsIgnoreCase("") && partId.equalsIgnoreCase("")&& !subPartId.equalsIgnoreCase("")) {
				MakeModel makeModel = makeRepository.findById(Long.parseLong(makeId)).get();
				SubPartModel subPartModel = subPartRepository.findById(Long.parseLong(subPartId)).get();
				productModelsList = productRepository.findByProductNameLikeAndMakeModelAndSubPartModel("%"+searchKeyword+"%",makeModel, subPartModel);
			}else if(!makeId.equalsIgnoreCase("") && !partId.equalsIgnoreCase("")&& subPartId.equalsIgnoreCase("")) {
				MakeModel makeModel = makeRepository.findById(Long.parseLong(makeId)).get();
				PartModel categoryModel  = categoryRepository.findById(Long.parseLong(partId)).get();
				List<SubPartModel> subPartModelList = subPartRepository.findByPartModel(categoryModel);
				productModelsList = productRepository.findByProductNameLikeAndMakeModelAndSubPartModelIn("%"+searchKeyword+"%",makeModel, subPartModelList);
			}else if(!makeId.equalsIgnoreCase("") && !partId.equalsIgnoreCase("")&& !subPartId.equalsIgnoreCase("")) {
				MakeModel makeModel = makeRepository.findById(Long.parseLong(makeId)).get();
				SubPartModel subPartModel = subPartRepository.findById(Long.parseLong(subPartId)).get();
				productModelsList = productRepository.findByProductNameLikeAndMakeModelAndSubPartModel("%"+searchKeyword+"%",makeModel, subPartModel);
			}
		}
		
		List<ProductModel> productModels2 = new ArrayList<ProductModel>();
		Iterator<ProductModel> iterator = productModelsList.iterator();
		while(iterator.hasNext()) {
			ProductModel productModel = (ProductModel) iterator.next();
			String rating  = ratingRepository.getRating(productModel.getProductId());
			if(rating==null) {
				productModel.setRating(0);
			}else {
				productModel.setRating(Double.parseDouble(rating));
			}
			System.out.println(rating);
			try {
				 File file=new File(productPicture+"/"+productModel.getPicture());
				 InputStream in = new FileInputStream(file);
				 productModel.setPicture2(Base64.getEncoder().encodeToString(IOUtils.toByteArray(in)));
				 } catch (Exception e) {
				 System.out.println(e);
				 }
			productModels2.add(productModel);
		}
		
		return productModelsList;
	}

	public ProductModel getProduct(long productId) {
		ProductModel productModel = productRepository.findById(productId).get();
		return productModel;
	}

	public String updateProduct(int available, float price, long productId) {
		ProductModel productModel = productRepository.findById(productId).get();
		productModel.setAvailable(available);
		productModel.setPrice(price);
		productRepository.saveAndFlush(productModel);
		return "Product Updated";
	}

}
