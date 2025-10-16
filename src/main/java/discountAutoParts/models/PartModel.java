package discountAutoParts.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="parts")
public class PartModel {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id 
	private long partId;
	private String partName;

}
