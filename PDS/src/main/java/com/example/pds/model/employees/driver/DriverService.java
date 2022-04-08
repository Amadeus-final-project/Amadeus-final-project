package com.example.pds.model.employees.driver;

import com.example.pds.config.CheckViolations;
import com.example.pds.controllers.profiles.Profile;
import com.example.pds.model.address.Address;
import com.example.pds.model.address.AddressRepository;
import com.example.pds.model.address.AddressSimpleDTO;
import com.example.pds.model.employees.driver.driverDTO.DriverEditProfileDTO;
import com.example.pds.model.employees.driver.driverDTO.DriverSimpleResponseDTO;
import com.example.pds.model.offices.OfficeRepository;
import com.example.pds.model.packages.Package;
import com.example.pds.model.packages.PackageDriverRelatedInformationDTO;
import com.example.pds.model.packages.PackageRepository;
import com.example.pds.model.vacations.*;
import com.example.pds.model.vehicle.Vehicle;
import com.example.pds.model.vehicle.VehicleRepository;
import com.example.pds.controllers.profiles.ProfilesRepository;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Validator validator;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ProfilesRepository profilesRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private VacationRepository vacationRepository;


    public void getVehicle(int id, int vehicleId) {
        DriverProfile driver = driverRepository.getById(id);

        Vehicle vehicle = vehicleRepository.getById(vehicleId);
        if (driver.getVehicle() != null) {
            Vehicle oldVehicle = vehicleRepository.getById(driver.getVehicle().getId());
            oldVehicle.setIsAvailable(true);
            vehicleRepository.save(oldVehicle);
        }
        if (!vehicle.getIsAvailable()) {
            throw new BadRequestException("Vehicle is not available");
        }
        driver.setVehicle(vehicle);
        driverRepository.save(driver);
        vehicle.setIsAvailable(false);
        vehicleRepository.save(vehicle);
    }

    public void releaseVehicle(int id) {
        DriverProfile driver = driverRepository.getById((id));
        if (driver.getVehicle() != null) {
            Vehicle vehicle = driver.getVehicle();
            vehicle.setIsAvailable(true);
            vehicleRepository.save(vehicle);
            driver.setVehicle(null);
            driverRepository.save(driver);
        }

    }

    public List<DriverSimpleResponseDTO> getAllDrivers() {
        List<DriverSimpleResponseDTO> simpleDriver = new ArrayList<>();
        List<DriverProfile> drivers = driverRepository.findAll();
        for (DriverProfile driver : drivers) {
            simpleDriver.add(modelMapper.map(driver, DriverSimpleResponseDTO.class));
        }
        return simpleDriver;
    }

    public DriverSimpleResponseDTO getDriverById(int driverId) {
        if (driverRepository.findById(driverId) == null) {
            throw new NotFoundException("Driver does not exist");
        }
        DriverProfile driver = driverRepository.getById(driverId);
        return modelMapper.map(driver, DriverSimpleResponseDTO.class);
    }

    //    public EmployeeSimpleResponseDTO editProfile(Object id, EmployeeProfileChangeDTO employeeProfileChangeDTO, Object isDriver) {
//
    public void editProfile(int id, DriverEditProfileDTO driverDTO) {

        CheckViolations.check(validator, driverDTO);

        DriverProfile driver = driverRepository.findByProfileId(id);

        if (!driver.getFirstName().equals(driverDTO.getFirstName())) {
            driver.setFirstName(driverDTO.getFirstName());
        }
        if (!driver.getLastName().equals(driverDTO.getLastName())) {
            driver.setLastName(driverDTO.getLastName());
        }

        if (!driver.getPhoneNumber().equals(driverDTO.getPhoneNumber())) {
            driver.setPhoneNumber(driverDTO.getPhoneNumber());
        }
        driverRepository.save(driver);
    }

    @Transactional
    public AddressSimpleDTO addWorkingAddress(AddressSimpleDTO addressSimpleDTO, int id) {
        DriverProfile driver = driverRepository.findByProfileId(id);
        Address address = modelMapper.map(addressSimpleDTO, Address.class);
        driver.setWorkingAddress(address);
        addressRepository.save(address);
        return modelMapper.map(address, AddressSimpleDTO.class);
    }

    public List<PackageDriverRelatedInformationDTO> getAllPackagesInMyCity(int id) {
        DriverProfile driverProfile = driverRepository.findByProfileId(id);

        List<Package> listOfPackagesInMyCity = packageRepository.findAll();

        List<PackageDriverRelatedInformationDTO> packagesToReturn = new LinkedList<>();

        PackageDriverRelatedInformationDTO pack = new PackageDriverRelatedInformationDTO();
        for (Package aPackage : listOfPackagesInMyCity) {
            if (aPackage.getOffice().getAddress().getCity().equals(driverProfile.getWorkingAddress().getCity())){
                pack.setCurrentLocation(aPackage.getOffice());
                pack.setDeliveryAddress(aPackage.getAddress());
                packagesToReturn.add(pack);
            }
        }
        return packagesToReturn;
    }

    public String requestVacation(int id, LocalDate startDate, LocalDate endDate, String description, VacationType vacationType ) {

        DriverProfile driver = driverRepository.findByProfileId(id);

        Profile profile = driver.getProfile();


        if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(LocalDate.now())) {
            throw new BadRequestException("All dates must be in the future");
        }

        if (endDate.isBefore(startDate)) {
            throw new BadRequestException("End date must be after start date");
        }

        int lengthOfVacation = (int) ChronoUnit.DAYS.between(startDate, endDate);

        if (vacationType.toString().equals("PAID_LEAVE") && lengthOfVacation > driver.getAvailablePaidLeave()) {
            throw new BadRequestException("Not enough available paid leave days.");
        }

        Vacation vacation = new Vacation(startDate, endDate, description, vacationType, profile);

        this.vacationRepository.save(vacation);

        return "Vacation booked successfully.";

    }


    public List<VacationSimpleInfoDTO> getAllMyVacations(int id) {
        List<Vacation> vacations = vacationRepository.getAllByProfileId(id);
        List<VacationSimpleInfoDTO> DTOs = new ArrayList<>();

        for (Vacation vacation : vacations) {
            VacationSimpleInfoDTO dto = new VacationSimpleInfoDTO();

            dto.setStartDate(vacation.getStartDate());
            dto.setEndDate(vacation.getEndDate());
            dto.setApproved(vacation.isApproved());
            dto.setDescription(vacation.getDescription());
            DTOs.add(dto);
        }

        return DTOs;

    }
}



