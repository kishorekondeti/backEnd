package discountAutoParts.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="vendors")
public class VendorModel {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long vendorId;
	private String name;
	private String email;
	private String phone;
	@Transient
	private String password;
	private String address;
	
	@Transient
	private long credentialId;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	@JoinColumn(name="credentialId")
	private UsersModel usersModel;

	public UsersModel getLoginCredentialsModel() {
		return usersModel;
	}
	public void setLoginCredentialsModel(UsersModel usersModel) {
		this.usersModel = usersModel;
	}


}
