package discountAutoParts.repositories;

import discountAutoParts.models.MakeModel;
import discountAutoParts.models.ProductModel;
import discountAutoParts.models.SubPartModel;
import discountAutoParts.models.VendorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

	List<ProductModel> findByVendorModel(VendorModel vendorModel);

	List<ProductModel> findByVendorModelAndProductNameLike(VendorModel vendorModel, String string);

	List<ProductModel> findByVendorModelAndMakeModel(VendorModel vendorModel, MakeModel makeModel);

	List<ProductModel> findByVendorModelAndProductNameLikeAndSubPartModel(VendorModel vendorModel,
			String string, SubPartModel subPartModel);

	List<ProductModel> findByVendorModelAndProductNameLikeAndSubPartModelIn(VendorModel vendorModel,
			String string, List<SubPartModel> subPartModelList);

	List<ProductModel> findByVendorModelAndProductNameLikeAndMakeModel(VendorModel vendorModel, String string,
			MakeModel makeModel);

	List<ProductModel> findByVendorModelAndProductNameLikeAndMakeModelAndSubPartModel(
			VendorModel vendorModel, String string, MakeModel makeModel, SubPartModel subPartModel);

	List<ProductModel> findByVendorModelAndProductNameLikeAndMakeModelAndSubPartModelIn(
			VendorModel vendorModel, String string, MakeModel makeModel,
			List<SubPartModel> subPartModelList);

	List<ProductModel> findByProductNameLikeAndMakeModelAndSubPartModel(String string, MakeModel makeModel,
			SubPartModel subPartModel);

	List<ProductModel> findByProductNameLikeAndMakeModelAndSubPartModelIn(String string, MakeModel makeModel,
			List<SubPartModel> subPartModelList);


	List<ProductModel> findByProductNameLikeAndSubPartModel(String string, SubPartModel subPartModel);

	List<ProductModel> findByProductNameLikeAndMakeModel(String string, MakeModel makeModel);

	List<ProductModel> findByProductNameLikeAndSubPartModelIn(String string,
			List<SubPartModel> subPartModelList);

	List<ProductModel> findByProductNameLike(String string);

	



}
