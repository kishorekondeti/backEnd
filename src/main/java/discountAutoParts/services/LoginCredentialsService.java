package discountAutoParts.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import discountAutoParts.models.UsersModel;
import discountAutoParts.repositories.LoginRepository;
import discountAutoParts.security.JwtTokenUtil;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class LoginCredentialsService implements UserDetailsService{
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UsersModel usersModel  = loginRepository.findByEmail(email);
		if(usersModel == null) {
			throw new UsernameNotFoundException("Invalid Email Address") ;
		}
		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usersModel.getRole());
		grantedAuthorities.add(authority);
		return new User(usersModel.getEmail(),usersModel.getPassword(),grantedAuthorities);
	}

	
	
	
	}
	
	
	


