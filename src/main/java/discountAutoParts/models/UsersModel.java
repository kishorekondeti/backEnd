package discountAutoParts.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="users")
public class UsersModel implements Serializable{
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long credentialId;
	private String email;
	private String password;
	private String role;

	

}
