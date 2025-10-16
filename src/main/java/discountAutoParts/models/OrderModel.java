package discountAutoParts.models;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;

@Entity
@Transactional
@Data
@Table(name="orderDetails")
public class OrderModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private String status;
    private LocalDateTime orderedDate;
    private float totalPrice;
    private String tax;
    private String payable;
    private String loyalityAmount;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	@JoinColumn(name="customerId")
	private CustomerModel customerModel;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	@JoinColumn(name="vendorId")
	private VendorModel vendorModel;
	@Transient
	private List<OrderLineModel> orderLineModels;

}
