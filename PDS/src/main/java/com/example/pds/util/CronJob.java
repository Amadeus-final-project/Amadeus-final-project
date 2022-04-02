package com.example.pds.util;

import com.example.pds.model.entity.Package;
import com.example.pds.repository.PackageRepository;
import com.example.pds.model.entity.User;
import com.example.pds.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableScheduling
public class CronJob {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PackageRepository packageRepository;


    @Scheduled(cron = "0 0 8 25 12 ?")
    public void christmasWish(){
        List<User> users = getAllUsers();
        Set<String> emails = new HashSet<>();
        for (User user : users) {
            emails.add(user.getEmail());
        }
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("pdsamadeus@abv.bg");
        msg.setSubject("Christmas wish");
        msg.setText("Marry Christmas from the members of PDS!");
        for (String email : emails) {
            msg.setTo(email);
            javaMailSender.send(msg);

        }
    }
    @Scheduled(cron = "0 0 0 1 1 ?")
    public void newYearWish(){
        List<User> users = getAllUsers();
        Set<String> emails = new HashSet<>();
        for (User user : users) {
            emails.add(user.getEmail());
        }
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("pdsamadeus@abv.bg");
        msg.setSubject("New year wish");
        msg.setText("Happy New Year from the members of PDS!");
        for (String email : emails) {
            msg.setTo(email);
            javaMailSender.send(msg);
        }
    }
    @Scheduled(cron="0 0 12 * * ?")
    public void packageToReceive(){
        //TODO try findByStatusID
        Set<String> emails = new HashSet<>();
        List<Package> packages = packageRepository.findAllByStatusId(3);
        for (Package pack : packages) {
            emails.add(pack.getRecipient().getEmail());
        }
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("pdsamadeus@abv.bg");
        msg.setSubject("Package to receive");
        msg.setText("You have package to receive");
        for (String email : emails) {
            msg.setTo(email);
            javaMailSender.send(msg);
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
