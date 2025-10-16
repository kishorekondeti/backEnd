package discountAutoParts.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import discountAutoParts.models.ProductModel;
import discountAutoParts.services.ProductService;

@RestController
public class ProductController {
	@Autowired private ProductService productService;
	@Value("${productPicture}")
	String productPicture;
	@RequestMapping(value = "AddProduct", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String addProduct (
			@RequestParam(name="picture") MultipartFile multipartFile,
			@RequestParam String productName,
			@RequestParam float price,
			@RequestParam String about,
			@RequestParam long subPartId,
			@RequestParam long makeId,
			@RequestParam int available,
			Principal principal
			)
	{
		try {
			File uploadedFile = new File(productPicture, multipartFile.getOriginalFilename());
			uploadedFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(uploadedFile);
			fos.write(multipartFile.getBytes());
			fos.close();
			
			ProductModel productModel = new ProductModel();
			productModel.setAbout(about);
			productModel.setPicture(multipartFile.getOriginalFilename());
			productModel.setPrice(price);
			productModel.setProductName(productName);
			productModel.setAvailable(available);
			return productService.addProduct(productModel,makeId,subPartId,principal.getName());
		} catch (Exception e) {
			System.out.println(e);
			return "Fail to upload";
		}
	}
	
	@GetMapping("ViewProducts")
	public List<ProductModel> viewProducts(Principal principal,@RequestParam("searchKeyword") String searchKeyword,@RequestParam("partId") String partId,@RequestParam("subPartId") String subPartId,@RequestParam("makeId") String makeId){
		return productService.viewProducts(principal.getName(),makeId,searchKeyword,partId,subPartId);
	}
	
	@GetMapping("getProduct")
	public ProductModel getProduct(@RequestParam("productId") long productId) {
		return productService.getProduct(productId);
	}
	
	@GetMapping("updateProduct")
	public String updateProduct(@RequestParam("productId") long productId,@RequestParam("available") int available,@RequestParam("price") float price) {
		return productService.updateProduct(available,price,productId);
	}

}
