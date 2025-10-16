package discountAutoParts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import discountAutoParts.models.UsersModel;
import discountAutoParts.repositories.LoginRepository;

@Component
public class AdminUserInitializer implements CommandLineRunner{
	
	
	private final LoginRepository loginRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	@Value("${admin.email}")
	private String adminEmail;
	
	@Value("${admin.password}")
	private String adminpassword;
	
	@Value("${admin.role}")
	private String adminRole;
	
	
	public AdminUserInitializer(LoginRepository loginRepository, PasswordEncoder passwordEncoder) {
		super(); 
		this.loginRepository = loginRepository;
	    this.passwordEncoder = passwordEncoder; 
	  }
	 

	@Override
	public void run(String... args) throws Exception {
		
		if(loginRepository.count() ==0) {
			UsersModel adminModel = new UsersModel();
			adminModel.setEmail(adminEmail);
			adminModel.setPassword(passwordEncoder.encode(adminpassword));
			adminModel.setRole(adminRole);
			loginRepository.save(adminModel);
			
		}
		
		
	}
	

}
