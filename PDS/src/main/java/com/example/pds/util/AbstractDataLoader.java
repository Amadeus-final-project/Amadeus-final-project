package com.example.pds.util;

import com.example.pds.controllers.profiles.Profile;
import com.example.pds.controllers.profiles.ProfilesRepository;
import com.example.pds.model.address.Address;
import com.example.pds.model.address.AddressRepository;
import com.example.pds.model.packages.Package;
import com.example.pds.model.packages.PackageRepository;
import com.example.pds.model.roles.Role;
import com.example.pds.model.user.UserProfile;
import com.example.pds.model.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.pds.model.roles.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Component
public class AbstractDataLoader implements CommandLineRunner {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PackageRepository packageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfilesRepository profilesRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        loadRoleData();
        loadAddressData();
        loadProfileData();
        loadUserProfileData();
        loadPackageData();
    }

    void loadRoleData() {
        if (roleRepository.count() == 0){
            Role role1 = new Role();
            role1.setId(1);
            role1.setName("USER");

            Role role2 = new Role();
            role1.setId(2);
            role1.setName("DRIVER");

            Role role3 = new Role();
            role1.setId(3);
            role1.setName("AGENT");

            Role role4 = new Role();
            role1.setId(4);
            role1.setName("ADMIN");

            roleRepository.saveAndFlush(role1);
            roleRepository.saveAndFlush(role2);
            roleRepository.saveAndFlush(role3);
            roleRepository.saveAndFlush(role4);
        }
    }

    void loadProfileData() {
        if (profilesRepository.count() == 0) {

            Profile profile1 = new Profile();
            profile1.setUsername("Asparuh");
            profile1.setPassword("1234");
            profile1.setEmail("Email");
            profile1.setRole(roleRepository.findRoleById(1));

            Profile profile2 = new Profile();
            profile2.setUsername("Artur");
            profile2.setPassword("1234");
            profile2.setEmail("OtherEmail");
            profile2.setRole(roleRepository.findRoleById(2));

            Profile profile3 = new Profile();
            profile3.setUsername("Spiridon");
            profile3.setPassword("1234");
            profile3.setEmail("EmailEmail");
            profile3.setRole(roleRepository.findRoleById(3));

            Profile profile4 = new Profile();
            profile4.setUsername("Margarit");
            profile4.setPassword("12345678");
            profile4.setEmail("SomeEmail");
            profile4.setRole(roleRepository.findRoleById(4));

            profilesRepository.saveAndFlush(profile1);
            profilesRepository.saveAndFlush(profile2);
            profilesRepository.saveAndFlush(profile3);
            profilesRepository.saveAndFlush(profile4);

        }
    }

    void loadUserProfileData() {
        if (userRepository.count() == 0) {

            UserProfile userProfile1 = new UserProfile();
            UserProfile userProfile2 = new UserProfile();
            UserProfile userProfile3 = new UserProfile();
            UserProfile userProfile4 = new UserProfile();

            userProfile1.setFirstName("Tosho");
            userProfile1.setLastName("Toshev");
            userProfile1.setProfile(profilesRepository.findById(1));

            userProfile2.setFirstName("Gosho");
            userProfile2.setLastName("Goshev");
            userProfile2.setProfile(profilesRepository.findById(2));

            userProfile3.setFirstName("Petko");
            userProfile3.setLastName("Petkov");
            userProfile3.setProfile(profilesRepository.findById(3));

            userProfile4.setFirstName("Gruiu");
            userProfile4.setLastName("Gruev");
            userProfile4.setProfile(profilesRepository.findById(4));

            userRepository.saveAndFlush(userProfile1);
            userRepository.saveAndFlush(userProfile2);
            userRepository.saveAndFlush(userProfile3);
            userRepository.saveAndFlush(userProfile4);

        }
    }

    void loadPackageData() {
        if (packageRepository.count() == 0) {
            List<UserProfile> allUsers = this.userRepository.findAll();
            for(UserProfile user : allUsers) {
                Package packageCurrent = new Package();
         packageCurrent.setSender(user);
         packageRepository.saveAndFlush(packageCurrent);
            }
        }
    }

    private void loadAddressData() {
        if (addressRepository.count() == 0) {
            Address address1 = new Address();
            address1.setCountry("Bulgaria");
            address1.setCity("Sofia");
            address1.setPostCode("1000");
            address1.setRegion("Malashevci");
            Address address2 = new Address();
            address2.setCountry("Bulgaria");
            address2.setCity("Plovdiv");
            address2.setPostCode("6542");
            address2.setRegion("H.Botev");
            Address address3 = new Address();
            address3.setCountry("Bulgaria");
            address3.setCity("Sofia");
            address3.setPostCode("1000");
            address3.setRegion("pette kiusheta");

            addressRepository.saveAndFlush(address1);
            addressRepository.saveAndFlush(address2);
            addressRepository.saveAndFlush(address3);
        }
    }


}