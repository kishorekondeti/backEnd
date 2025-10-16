package discountAutoParts.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="subParts")
public class SubPartModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long subPartId;
	private String subPartName;
//	@Transient
//	private long categoryId;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	@JoinColumn(name="partId")
	private  PartModel partModel;
	

	

}
