package com.example.pds.model.user;

import com.example.pds.model.packages.Package;
import com.example.pds.model.packages.packageDTO.PackageGetMyPackagesDTO;
import com.example.pds.model.roles.RoleRepository;
import com.example.pds.model.transaction.Transaction;
import com.example.pds.model.transaction.TransactionResponseDTO;
import com.example.pds.controllers.profiles.Profile;
import com.example.pds.model.packages.PackageRepository;
import com.example.pds.model.transaction.TransactionRepository;
import com.example.pds.model.user.userDTO.*;
import com.example.pds.controllers.profiles.ProfilesRepository;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import com.example.pds.util.exceptions.UnauthorizedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ProfilesRepository profilesRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public UserSimpleResponseDTO register(RegisterDTO registerDTO) {
        String firstName = registerDTO.getFirstName();
        String lastName = registerDTO.getLastName();
        String username = registerDTO.getUsername();
        String email = registerDTO.getEmail();
        String password = registerDTO.getPassword();
        String confirmPassword = registerDTO.getConfirmPassword();

        if (profilesRepository.findByUsername(username) != null) {
            throw new BadRequestException("Username already exists");
        }

        if (!confirmPassword.equals(password)) {
            throw new BadRequestException("Passwords don't match");
        }

        if (profilesRepository.findByEmail(email) != null) {
            throw new BadRequestException("Email already exists");
        }

        Profile profile = new Profile();
        profile.setUsername(username);
        profile.setPassword(passwordEncoder.encode(password));
        profile.setEmail(email);
        profile.setRole(roleRepository.findRoleById(1));
        profilesRepository.save(profile);

        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(firstName);
        userProfile.setLastName(lastName);
        userProfile.setProfile(profile);
        userRepository.save(userProfile);

        return modelMapper.map(userProfile, UserSimpleResponseDTO.class);
    }

    public void forgottenPassword(String email) {
        Profile profile = profilesRepository.findByEmail(email);
        if (profile == null) {
            throw new NotFoundException("Wrong email");
        }
        String token = createToken();
        profile.setPassword(passwordEncoder.encode(token));
        profilesRepository.save(profile);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pdsamadeus@abv.bg");
        message.setTo(email);
        message.setSubject("Reset password token");
        message.setText("Your new password is: " + token);
        javaMailSender.send(message);
    }


    public UserSimpleResponseDTO changePassword(UserChangePasswordDTO userChangePasswordDTO, int id) {

        Profile user = profilesRepository.getById(id);
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
        profilesRepository.save(user);
        return modelMapper.map(user, UserSimpleResponseDTO.class);
    }
@Transactional
    public UserComplexResponseDTO editProfile(int id, UserProfileChangeDTO userComplexResponseDTO) {

        UserProfile userProfile = userRepository.findByProfileId(id);
        Profile profile = profilesRepository.findById(id);
        if (!userProfile.getFirstName().equals(userComplexResponseDTO.getFirstName())) {
            userProfile.setFirstName(userComplexResponseDTO.getFirstName());
        }
        if (!userProfile.getLastName().equals(userComplexResponseDTO.getLastName())) {
            userProfile.setLastName(userComplexResponseDTO.getLastName());
        }
        if (!profile.getEmail().equals(userComplexResponseDTO.getEmail())) {
            String email = userComplexResponseDTO.getEmail();

            if (profilesRepository.findByEmail(email) != null) {
                throw new BadRequestException("Email already exists");
            }
            profile.setEmail(email);
        }
        if (userProfile.getPhoneNumber() == null) {
            userProfile.setPhoneNumber(userComplexResponseDTO.getPhoneNumber());
        }
        if (!userProfile.getPhoneNumber().equals(userComplexResponseDTO.getPhoneNumber())) {
            userProfile.setPhoneNumber(userComplexResponseDTO.getPhoneNumber());
        }
        userProfile.setPhoneNumber(userComplexResponseDTO.getPhoneNumber());

        userRepository.save(userProfile);
        profilesRepository.save(profile);
        return modelMapper.map(profile, UserComplexResponseDTO.class);
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

    public List<PackageGetMyPackagesDTO> getAllPackages(int id, Pageable page) {

        UserProfile userProfile = userRepository.findByProfileId(id);
        List<Package> packages = packageRepository.findAllByRecipient(userProfile, page).getContent();

        List<PackageGetMyPackagesDTO> packagesToReturn = new ArrayList<>();
        for (Package aPackage : packages) {
            PackageGetMyPackagesDTO dto = modelMapper.map(aPackage, PackageGetMyPackagesDTO.class);
            packagesToReturn.add(dto);
        }
        return packagesToReturn;
    }

    public PackageGetMyPackagesDTO getPackageBydId(int id, int userId) {
        if (packageRepository.findById(id) == null) {
            throw new NotFoundException("Package doesn't exist");
        }
        Package pack = packageRepository.findById(id);
        if  (userId != pack.getRecipient().getId()) {
            throw new UnauthorizedException("Not your package");
        }
        return modelMapper.map(pack, PackageGetMyPackagesDTO.class);


    }

   public List<TransactionResponseDTO> getAllTransactions(int id, Pageable page) {

        UserProfile profile = userRepository.findByProfileId(id);
        List<Transaction> packages = transactionRepository.findAllByPayer(profilesRepository.getById(id), page);

        List<TransactionResponseDTO> packagesToReturn = new ArrayList<>();
        for (Transaction transaction : packages) {
            TransactionResponseDTO dto = modelMapper.map(transaction, TransactionResponseDTO.class);
            packagesToReturn.add(dto);
        }
        return packagesToReturn;
    }
}


