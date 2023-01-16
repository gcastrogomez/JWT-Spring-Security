package com.gcastro.services;

import java.nio.file.FileAlreadyExistsException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gcastro.entities.User;
import com.gcastro.entities.UserDTO;
import com.gcastro.repositories.UserRepository;

@Service
public class JwtUserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encryptedPassword;

	@PostConstruct
	private void initAdmin() {
		if (userRepository.findByUsername("admin") == null) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword("$2a$12$uoNco/LfYVp9SNIXjCCdu.qihyX.grIXq50c23la3UIaTkuQh/RIS");
			admin.setRole("ADMIN");
			userRepository.save(admin);
		}
	}
	
	public void verifyPassword(String password, String passwordEncoder) {
		if (!encryptedPassword.matches(password, passwordEncoder)) {
			throw new UsernameNotFoundException("Incorrect password. ");
		}
	}
	
	public User loadUserByUsername(String username) {
		User userLoad = userRepository.findByUsername(username);
		if (userLoad == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return userLoad;
	}

	public User save(UserDTO user) throws FileAlreadyExistsException {

		User newUser = new User();
		if (userRepository.findByUsername(user.getUsername()) == null) {
			newUser.setUsername(user.getUsername());
			newUser.setPassword(encryptedPassword.encode(user.getPassword()));
			newUser.setRole("USER");
		} else {
			throw new FileAlreadyExistsException(user.getUsername()+" already exists.");
		}
		return userRepository.save(newUser);
	}
	
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User save(User user) {
		user.setPassword(encryptedPassword.encode(user.getPassword()));
		return userRepository.save(user);
		
	}

}
