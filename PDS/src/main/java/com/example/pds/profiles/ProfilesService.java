package com.example.pds.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProfilesService implements UserDetailsService {
    @Autowired
    ProfilesRepository profilesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.profilesRepository.findByUsername(username);
    }
}
