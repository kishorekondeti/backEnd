package discountAutoParts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import discountAutoParts.models.UsersModel;
import discountAutoParts.security.JwtTokenUtil;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class UsersService {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private LoginCredentialsService loginCredentialsService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	public ResponseEntity<String> loginAction(UsersModel usersModel) {
		System.out.println(usersModel.getEmail());
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usersModel.getEmail(), usersModel.getPassword()));
			
		}catch (DisabledException e) {
			return ResponseEntity.ok("Email Disabled");
		}
		catch (BadCredentialsException e) {
            return ResponseEntity.ok("Invalid Login Details");
        } catch (Exception e) {
            return ResponseEntity.ok("Invalid Login Details");
        }
		final UserDetails userDetails = loginCredentialsService.loadUserByUsername(usersModel.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(token);
	}
	
}
