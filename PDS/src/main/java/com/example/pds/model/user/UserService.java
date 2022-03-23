package com.example.pds.model.user;

import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import com.example.pds.util.exceptions.UnauthorizedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;

    public UserSimpleResponseDTO register(RegisterDTO registerDTO) {
        String firstName = registerDTO.getFirstName();
        if (firstName.isBlank()) {
            throw new BadRequestException("First name is mandatory");
        } else if (!firstName.matches("^[A-Za-zА-Яа-я]+")) {
            throw new BadRequestException("Last name should be only letters");
        }
        String lastName = registerDTO.getLastName();
        if (lastName.isBlank()) {

            throw new BadRequestException("Last name is mandatory");
        } else if (!lastName.matches("^[A-Za-zА-Яа-я]+")) {
            throw new BadRequestException("Last name should be only letters");
        }
        String username = registerDTO.getUsername();
        if (username.trim().length() < 5) {
            throw new BadRequestException("Username should be at least 5 symbols");
        } else if (userRepository.findByUsername(registerDTO.getUsername()) != null) {
            throw new BadRequestException("Username already exists ");
        }
        String password = registerDTO.getPassword();
        if (password.length() < 8) {
            throw new BadRequestException("Password must be at least 8 symbols");
        }
        String confirmPassword = registerDTO.getConfirmPassword();
        if (!confirmPassword.equals(password)) {
            throw new BadRequestException("Confirm password should match password");
        }
        String email = registerDTO.getEmail();
        if (!email.matches("^(.+)@(.+)$")) {
            throw new BadRequestException("invalid email address");
        }
        if (userRepository.findByEmail(email) != null) {
            throw new BadRequestException("Email already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(username);
        userRepository.save(user);

        return modelMapper.map(user, UserSimpleResponseDTO.class);
    }

    public UserSimpleResponseDTO login(User u) {
        String username = u.getUsername();
        if (username.trim().length() < 5) {
            throw new BadRequestException("Username should be at least 5 symbols");
        }
        String password = u.getPassword();
        if (password.length() < 8) {
            throw new BadRequestException("Password must be at least 8 symbols");
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("No username found");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Wrong credentials");
        }

        return modelMapper.map(user, UserSimpleResponseDTO.class);
    }

    public void forgottenPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("Wrong email");
        }
        String token = createToken();
        user.setPassword(passwordEncoder.encode(token));
        userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pdsamadeus@abv.bg");
        message.setTo(email);
        message.setSubject("Reset password token");
        message.setText(token);
        javaMailSender.send(message);
    }

    public UserSimpleResponseDTO changePassword(UserChangePasswordDTO userChangePasswordDTO, Object isLogged, Object id) {

        if (isLogged == null) {
            throw new UnauthorizedException("You must Login first");
        }
        User user = userRepository.getById((int) id);
        String oldPass = userChangePasswordDTO.getOldPass();
        String newPass = userChangePasswordDTO.getNewPass();
        String confirmPass = userChangePasswordDTO.getConfirmPass();

        if (!passwordEncoder.matches(oldPass, user.getPassword())) {
            throw new BadRequestException("Password incorrect");
        }
        if (newPass.length() < 8) {
            throw new BadRequestException("Length must be at least 8 symbols");
        }
        if (!newPass.equals(confirmPass)) {
            throw new BadRequestException("Passwords don't match");
        }
        user.setPassword(passwordEncoder.encode(newPass));
        userRepository.save(user);
        return modelMapper.map(user, UserSimpleResponseDTO.class);
    }

    public UserComplexResponseDTO editProfile(Object id, UserProfileChangeDTO userComplexResponseDTO) {
        if (id == null) {
            throw new BadRequestException("You must login first");
        }
        User user = userRepository.getById((int) id);
        if (!user.getFirstName().equals(userComplexResponseDTO.getFirstName())) {
            user.setFirstName(userComplexResponseDTO.getFirstName());
        }
        if (!user.getLastName().equals(userComplexResponseDTO.getLastName())) {
            user.setLastName(userComplexResponseDTO.getLastName());
        }
        if (!user.getEmail().equals(userComplexResponseDTO.getEmail())) {
            user.setEmail(userComplexResponseDTO.getEmail());
        }
        if (user.getPhoneNumber() == null) {
            user.setPhoneNumber(userComplexResponseDTO.getPhoneNumber());
        }
        if (!user.getPhoneNumber().equals(userComplexResponseDTO.getPhoneNumber())) {
            user.setPhoneNumber(userComplexResponseDTO.getPhoneNumber());
        }
        user.setPhoneNumber(userComplexResponseDTO.getPhoneNumber());

        userRepository.save(user);
        return modelMapper.map(user, UserComplexResponseDTO.class);
    }

    private String createToken() {
        String token = null;
        Random random = new Random();
        int chance = random.nextInt();
        int finalNumber = 0;
        for (int i = 0; i < 10; i++) {
            chance = random.nextInt(100) + 25;
            finalNumber += chance * i * (10000);
        }
        token = finalNumber + "";
        return token;
    }
}


