package com.qalaa.user.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.qalaa.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.qalaa.comman_message.EmailTemplate;
import com.qalaa.exception.CustomException;
import com.qalaa.exception.ResourceNotFoundException;
import com.qalaa.jwt.service.JwtService;
import com.qalaa.user.mapper.UserMapper;
import com.qalaa.user.model.User;
import com.qalaa.user.reposotory.UserRepository;
import com.qalaa.user.wrapper.UserWrapper;

import jakarta.mail.internet.MimeMessage;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private JwtService jwtService;


	@Override
	public User registerUser(UserWrapper userWrapper) {
		Optional<User> existUser = userRepository.findByEmail(userWrapper.getEmail());
		if (existUser.isPresent() && existUser.get().isVerified()) {
			throw new CustomException("User already exist.");
		}
		if (existUser.isPresent() && !existUser.get().isVerified()) {
			existUser.get().setPassword(passwordEncoder.encode(userWrapper.getPassword()));
			existUser.get().setName(userWrapper.getName());
			existUser.get().setMobileNumber(userWrapper.getMobileNumber());
			existUser.get().setRole(RoleEnum.USER);
			constructEmail(existUser.get(), "generalOtp");
			return existUser.get();
		}

		if(userRepository.existsByMobileNumber(userWrapper.getMobileNumber())){
			throw new CustomException("User Mobile number already exist.");
		}
		User user = UserMapper.mapToUserWrapper(userWrapper);
		user.setPassword(passwordEncoder.encode(userWrapper.getPassword()));
		user.setRole(RoleEnum.USER);
		user.setVerified(false);
		constructEmail(user, "generalOtp");
	    return user;
	}

	@Override
	public UserWrapper getUserWrapper(Long userId) {
		Optional<User> existUser = userRepository.findById(userId);
		if (existUser.isEmpty()) {
			throw new CustomException("User not found.");
		}

		return UserMapper.mapToUserWrapper(existUser.get(),null);
	}

	@Override
	public User getUser(Long userId) {
		Optional<User> existUser = userRepository.findById(userId);
		if (existUser.isEmpty()) {
			throw new CustomException("User not found.");
		}

		return existUser.get();
	}

	@Override
	public UserWrapper verifyOtp(String email, String otp) {
		Optional<User> existUser = userRepository.findByEmail(email);
		if (!existUser.isPresent()) {
			throw new ResourceNotFoundException("User not found.");
		}
		if (existUser.get().isVerified()) {
			throw new CustomException("User already verified.");
		}
		if (existUser.get().getOtp().equals(otp)) {
			existUser.get().setVerified(true);
			existUser.get().setOtp(null);
			userRepository.save(existUser.get());
			return UserMapper.mapToUserWrapper(existUser.get(),null);
		}
		throw new CustomException("Invalid otp.");
	}

	@Override
	public UserWrapper login(String email, String password) {
		Optional<User> existUser = userRepository.findByEmail(email);
		if (!existUser.isPresent()) {
			throw new ResourceNotFoundException("User not found.");
		}
		if (!existUser.get().isVerified()) {
			throw new CustomException("User not verified.");
		}
<<<<<<< .mine

		try {



=======
		
		boolean matches = passwordEncoder.matches(password, existUser.get().getPassword());
		System.out.println("Password matches: " + matches);
		
		try {
>>>>>>> .theirs
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
<<<<<<< .mine
	    } catch (AuthenticationException ex) {
	        throw new CustomException("Invalid email or password.");
	    }
        String jwtToken = jwtService.generateToken(existUser.get(),existUser.get().getId(),existUser.get().getName());
=======
	    } catch (AuthenticationException ex) {
	        throw new CustomException("Invalid email or password.");
	    }
        String jwtToken = jwtService.generateToken(existUser.get());
>>>>>>> .theirs

		return UserMapper.mapToUserWrapper(existUser.get(),jwtToken);


	}
	
	

	@Override
	public UserWrapper sendOtp(String email) {
		Optional<User> existUser = userRepository.findByEmail(email);
		if (!existUser.isPresent()) {
			throw new ResourceNotFoundException("User not found.");
		}
		constructEmail(existUser.get(), "generalOtp");

		return UserMapper.mapToUserWrapper(existUser.get(),null);
	}

	@Override
	public UserWrapper sendOtpForgotPassword(String email) {
		Optional<User> existUser = userRepository.findByEmail(email);
		if (!existUser.isPresent()) {
			throw new ResourceNotFoundException("User not found.");
		}
		constructEmail(existUser.get(), "forgotPassword");

		return UserMapper.mapToUserWrapper(existUser.get(),null);
	}

	@Override
	public UserWrapper updatePassword(String email, String otp, String newPassword) {
		Optional<User> existUser = userRepository.findByEmail(email);
		if (!existUser.isPresent()) {
			throw new ResourceNotFoundException("User not found.");
		}
		if (existUser.get().getForgotPasswordOtp().equals(otp)) {
			existUser.get().setPassword(newPassword);
			existUser.get().setOtp(null);
			userRepository.save(existUser.get());
			return UserMapper.mapToUserWrapper(existUser.get(),null);
		}
		throw new ResourceNotFoundException("Invalid otp.");

	}

	@Override
	public void sendForgotPasswordLink(String email) {
		Optional<User> existUser = userRepository.findByEmail(email);
		if (existUser.isEmpty()) {
			throw new ResourceNotFoundException("User not found.");
		}
		User user = existUser.get();
		String resetToken = UUID.randomUUID().toString();
		user.setResetToken(resetToken);
		userRepository.save(user);

		String resetUrl = "http://localhost:8081/api/user/reset-password?token=" + resetToken;
		String message = "Click the link to reset your password: " + resetUrl;
		constructEmail(user.getEmail(), "Password Reset Request", message);

	}

	@Override
	public void resetPassword(String token, String password, String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			throw new CustomException("Passwords do not match.");
		}
		System.out.println(token);
		Optional<User> userOptional = userRepository.findByResetToken(token);

		if (userOptional.isEmpty()) {
			throw new CustomException("Invalid reset token.");
		}
		User user = userOptional.get();
		user.setPassword(password); // Ideally, hash the password before saving
		user.setResetToken(null); // Clear the reset token
		userRepository.save(user);
	}

	public void constructEmail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		message.setFrom(sender);

		javaMailSender.send(message);
	}

	public Map<String, String> constructEmail(User existUser, String purpose) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		Map<String, String> jsonResponse = new HashMap<>();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			String content = null;
			String otp;
			String subject = null;
			if (purpose.equals("generalOtp")) {
				subject = "OTP Verification";
				otp = generateOtp();
				content = EmailTemplate.mailMessageForGeneral(otp, existUser.getEmail());

				existUser.setOtp(otp);
				Calendar calender = Calendar.getInstance();
				calender.add(Calendar.MINUTE, 10);
				existUser.setExpirationTimeOfOtp(calender.getTime());
				userRepository.save(existUser);

			} else if (purpose.equals("forgotPassword")) {
				subject = "Forgot Password Verification";
				otp = generateOtp();
				content = EmailTemplate.mailMessageForGeneral(otp, existUser.getEmail());

				existUser.setForgotPasswordOtp(otp);
				Calendar calender = Calendar.getInstance();
				calender.add(Calendar.MINUTE, 10);
				existUser.setExpirationTimeOfForgotPasswordOtp(calender.getTime());
				userRepository.save(existUser);

			}

			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(existUser.getEmail());
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(content, true);
			javaMailSender.send(mimeMessage);
			jsonResponse.put("status", "success");
			jsonResponse.put("message", "Email sent successfully");

		} catch (Exception e) {

			jsonResponse.put("status", "error");
			System.out.println(e.toString());
			jsonResponse.put("message", "Error sending email");
		}
		return jsonResponse;

	}

	private String generateOtp() {
		return String.valueOf((int) (Math.random() * 9000) + 1000);
	}
}
