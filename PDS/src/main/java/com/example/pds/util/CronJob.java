package com.example.pds.util;

import com.example.pds.model.employees.employeeStatus.EmployeeStatus;
import com.example.pds.model.employees.agent.AgentProfile;
import com.example.pds.model.employees.agent.AgentRepository;
import com.example.pds.model.employees.driver.DriverProfile;
import com.example.pds.model.employees.driver.DriverRepository;
import com.example.pds.model.employees.employeeStatus.EmployeeStatusRepository;
import com.example.pds.model.packages.Package;
import com.example.pds.model.packages.PackageRepository;
import com.example.pds.controllers.profiles.Profile;
import com.example.pds.model.user.UserRepository;
import com.example.pds.controllers.profiles.ProfilesRepository;
import com.example.pds.model.vacations.Vacation;
import com.example.pds.model.vacations.VacationRepository;
import com.example.pds.model.vacations.VacationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private ProfilesRepository profilesRepository;

    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private EmployeeStatusRepository employeeStatusRepository;

    @Scheduled(cron = "0 0 8 25 12 ?")
    public void christmasWish() {
        List<Profile> users = getAllUsers();
        Set<String> emails = new HashSet<>();
        for (Profile user : users) {
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
    public void newYearWish() {
        List<Profile> users = getAllUsers();
        Set<String> emails = new HashSet<>();
        for (Profile user : users) {
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

    @Scheduled(cron = "0 0 12 * * ?")
    public void packageToReceive() {
        //TODO try findByStatusID
        Set<String> emails = new HashSet<>();
        List<Package> packages = packageRepository.findAllByStatusId(3, null);
        for (Package pack : packages) {
            emails.add(pack.getRecipient().getProfile().getEmail());
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

    @Scheduled(cron = "0 0 0 * * *")
    public void setWorkerStatus() {
        LocalDate currentDate = LocalDate.now();

        List<Vacation> vacationsStartingToday = vacationRepository.findAllByStartDateAndIsApproved(currentDate, true);
        setStatusesToVacation(vacationsStartingToday);

        //.minusDays(1) is used because each check is done at 00:00, so if the current day is 06.06.2022 00:00, and you have a vacation booked until 05.06.2022 (inclusive),
        //your status should be changed to working at exactly 00:00 on 06.06.2022, and you must go to work that day.

        List<Vacation> vacationsEndingToday = vacationRepository.findAllByEndDateAndIsRejected(currentDate.minusDays(1), false);


    }

    private void setStatusesToVacation(List<Vacation> vacationsStartingToday) {
        for (Vacation vacation : vacationsStartingToday) {
            VacationType vacationType = vacation.getVacationType();
            Profile profile = vacation.getProfile();


            Optional<AgentProfile> optionalAgent = agentRepository.findByProfile(profile);
            Optional<DriverProfile> optionalDriver = driverRepository.findByProfile(profile);
            //status 2 -> vacation

            if (optionalDriver.isPresent()) {
                optionalDriver.get().setDriverStatus(employeeStatusRepository.getById(2));
            } else if (optionalAgent.isPresent()) {
                optionalAgent.get().setAgentStatus(employeeStatusRepository.getById(2));
            }

        }
    }

    public List<Profile> getAllUsers() {
        return profilesRepository.findAll();
    }

}
