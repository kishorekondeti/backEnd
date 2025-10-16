package discountAutoParts.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTO {
	private float firstOrderDiscount;
	private float bulkOrderDiscount;
}
