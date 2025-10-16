package discountAutoParts.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="orderLines")
public class OrderLineModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderLineId;
	private int quantity;
	private float price;
	private String status;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	@JoinColumn(name="productId")
	private ProductModel productModel;
	@Transient
	private long orderId;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	@JoinColumn(name="orderId")
	private OrderModel orderModel;

	
	
	
	
}
