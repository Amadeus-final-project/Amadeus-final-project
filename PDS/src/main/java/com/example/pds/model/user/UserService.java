package com.example.pds.model.user;

import com.example.pds.util.exceptions.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        if (!confirmPassword.equals(password)){
            throw new BadRequestException("Confirm password should match password");
        }
        String email = registerDTO.getEmail();
        if (!email.matches("^(.+)@(.+)$")){
            throw  new BadRequestException("invalid email address");
        }
        if (userRepository.findByEmail(email)!=null){
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

    public UserSimpleResponseDTO login(User u){
        String username = u.getUsername();
        if ()
    }


}
