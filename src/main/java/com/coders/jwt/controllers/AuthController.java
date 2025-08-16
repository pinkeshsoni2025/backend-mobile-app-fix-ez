package com.coders.jwt.controllers;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coders.jwt.exception.ResourceNotFoundException;
import com.coders.jwt.exception.TokenRefreshException;
import com.coders.jwt.models.ERole;
import com.coders.jwt.models.RefreshToken;
import com.coders.jwt.models.Role;
import com.coders.jwt.models.User;
import com.coders.jwt.payload.request.LoginRequest;
import com.coders.jwt.payload.request.SignupRequest;
import com.coders.jwt.payload.request.TokenRefreshRequest;
import com.coders.jwt.payload.response.JwtResponse;
import com.coders.jwt.payload.response.MessageResponse;
import com.coders.jwt.payload.response.TokenRefreshResponse;
import com.coders.jwt.repository.RoleRepository;
import com.coders.jwt.repository.UserRepository;
import com.coders.jwt.security.jwt.JwtUtils;
import com.coders.jwt.security.services.RefreshTokenService;
import com.coders.jwt.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	// private final JwtService jwtService;

	@Autowired
	RefreshTokenService refreshTokenService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws AccessDeniedException {
		
		

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		if (userDetails.getIsActive() != 1) {
			 throw new AccessDeniedException("Access Denied: You do not have permission to sign in."); 
		}

		String jwt = jwtUtils.generateJwtToken(userDetails);

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		String roleStr =  roles.stream().collect(Collectors.joining(", "));
		
		

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

		return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
				userDetails.getUsername(), userDetails.getEmail(), roleStr, userDetails.getLocation(),
				userDetails.getName(), userDetails.getIsActive()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws AccessDeniedException {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			//return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
			
			throw new AccessDeniedException("Error: Username is already taken!");
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			//return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
			throw new AccessDeniedException("Error: Email is already in use!");
			
		}

		signUpRequest.setIsActive(1);
		signUpRequest.setLocation("MDS");

		// Create new user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getIsActive(),
				signUpRequest.getLocation(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getCreatedAt(),
				signUpRequest.getCreatedBy(), signUpRequest.getUpdatedAt(), signUpRequest.getUpdatedBy(),
				signUpRequest.getEmail());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				case "worker":
					Role workerRole = roleRepository.findByName(ERole.ROLE_WORKER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(workerRole);
					break;
				case "analyse":
					Role analyseRole = roleRepository.findByName(ERole.ROLE_ANALYZE)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(analyseRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		
		
		
		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		

	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser).map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getUsername());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		SecurityContextHolder.clearContext();
	return ResponseEntity.ok(new MessageResponse("Log out successful!"));

	}

	@PutMapping("edit/{username}")
	public ResponseEntity<User> updateUser(@PathVariable String username, @Valid @RequestBody User userDetail)
			throws ResourceNotFoundException {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() ->  new RuntimeException("username not found on :: " + username));

		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

		user.setEmail(userDetail.getEmail());
		user.setName(userDetail.getName());
		//user.setIsActive(userDetail.getIsActive());
		user.setUpdatedAt(now);
		user.setUpdatedBy(username);

		final User user1 = userRepository.save(user);
		return ResponseEntity.ok(user1);
	}
	
	@PutMapping("changepassword/{email}")
	public ResponseEntity<User> changePasswordUser(@PathVariable String email, @Valid @RequestBody User userDetail)
			throws ResourceNotFoundException {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() ->  new RuntimeException("email not found on :: " + email));

		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

		//user.setEmail(userDetail.getEmail());
		//user.setName(userDetail.getName());
		//user.setIsActive(userDetail.getIsActive());
		user.setUpdatedAt(now);
		
		user.setPassword(encoder.encode(userDetail.getPassword()));
	    //user.setPassword(userDetail.getPassword());
		

		final User user1 = userRepository.save(user);
		
		
		return ResponseEntity.ok(user1);
	}

	@DeleteMapping("delete/{username}")
	public Object deleteUser(@PathVariable(value = "username") String username)

			throws ResourceNotFoundException {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() ->  new RuntimeException("username not found on :: " + username));
		
		if (user.getIsActive() != 1) {
			return  new RuntimeException("username not exist :: " + username);
		}

		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

		user.setIsActive(0);
		user.setUpdatedAt(now);
		user.setUpdatedBy(username);
		userRepository.save(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;

	}

}
