package discountAutoParts.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="make")
public class MakeModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long makeId;
	private String makeName;
}
