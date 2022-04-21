package com.example.pds.model.employees.driver;

import com.example.pds.controllers.profiles.Profile;
import com.example.pds.model.address.Address;
import com.example.pds.model.address.AddressRepository;
import com.example.pds.model.address.AddressSimpleDTO;
import com.example.pds.model.driversOffices.DriversOffices;
import com.example.pds.model.driversOffices.DriversOfficesRepository;
import com.example.pds.model.employees.driver.driverDTO.DriverEditProfileDTO;
import com.example.pds.model.employees.driver.driverDTO.DriverRequestVacationDTO;
import com.example.pds.model.employees.driver.driverDTO.DriverSimpleResponseDTO;
import com.example.pds.model.employees.employeeStatus.EmployeeStatusRepository;
import com.example.pds.model.offices.Office;
import com.example.pds.model.offices.OfficeRepository;
import com.example.pds.model.packages.Package;
import com.example.pds.model.packages.packageDTO.PackageComplexResponseDTO;
import com.example.pds.model.packages.packageDTO.PackageDriverRelatedInformationDTO;
import com.example.pds.model.packages.PackageRepository;
import com.example.pds.model.packages.statuses.StatusRepository;
import com.example.pds.model.vacations.*;
import com.example.pds.model.vehicle.Vehicle;
import com.example.pds.model.vehicle.VehicleRepository;
import com.example.pds.controllers.profiles.ProfilesRepository;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    DriversOfficesRepository driversOfficesRepository;
    @Autowired
    VacationTypeRepository vacationTypeRepository;
    @Autowired
    EmployeeStatusRepository employeeStatusRepository;

    public void getVehicle(int id, int vehicleId) {
        DriverProfile driver = driverRepository.getByProfileId(id);

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
        DriverProfile driver = driverRepository.getByProfileId(id);
        if (driver.getVehicle() != null) {
            Vehicle vehicle = driver.getVehicle();
            vehicle.setIsAvailable(true);
            vehicleRepository.save(vehicle);
            driver.setVehicle(null);
            driverRepository.save(driver);
        }

    }

    public List<DriverSimpleResponseDTO> getAllDrivers(Pageable page) {
        List<DriverSimpleResponseDTO> simpleDriver = new ArrayList<>();
        List<DriverProfile> drivers = driverRepository.findAll(page).getContent();
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

    public List<PackageComplexResponseDTO> getAllPackagesInMyCity(int id) {
        DriverProfile driverProfile = driverRepository.findByProfileId(id);

        List<Package> listOfPackagesInMyCity = packageRepository.findAll();

        List<PackageComplexResponseDTO> packagesToReturn = new LinkedList<>();

        PackageComplexResponseDTO pack = new PackageComplexResponseDTO();
        for (Package aPackage : listOfPackagesInMyCity) {
            if (aPackage.getCurrentLocation().getAddress().getCity().equals(driverProfile.getWorkingAddress().getCity()) && (aPackage.getStatus() == statusRepository.findStatusById(2))) {
                pack.setCurrentLocation(aPackage.getCurrentLocation());
                pack.setDeliveryOffice(aPackage.getDeliveryOffice());
                packagesToReturn.add(pack);
            }
        }
        return packagesToReturn;
    }

    public String requestVacation(int id, DriverRequestVacationDTO dto) {

        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();
        String description = dto.getDescription();
        String type = dto.getVacationType();

        DriverProfile driver = driverRepository.findByProfileId(id);

        Profile profile = driver.getProfile();

        VacationType vacationType = vacationTypeRepository.findByType(type);

        if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(LocalDate.now())) {
            throw new BadRequestException("All dates must be in the future");
        }

        if (endDate.isBefore(startDate)) {
            throw new BadRequestException("End date must be after start date");
        }

        int lengthOfVacation = (int) ChronoUnit.DAYS.between(startDate, endDate);

        if (vacationType.getType().equals("PAID_LEAVE") && lengthOfVacation > driver.getAvailablePaidLeave()) {
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

    @Transactional
    public void takeAssignedPackages(HashSet<String> officesIDs, int id) {
        DriverProfile driver = driverRepository.findByProfileId(id);

        for (String officesID : officesIDs) {
            int officeId = Integer.parseInt(officesID);
            DriversOffices driversOffices = new DriversOffices();
            driversOffices.setDriver(driverRepository.findByProfileId(id));
            driversOffices.setOffice(officeRepository.getById(officeId));
            driversOfficesRepository.save(driversOffices);
        }

        Set<Integer> route = officesIDs.stream().map(Integer::parseInt).collect(Collectors.toSet());
        List<Package> packages = packageRepository.findAll();
        List<Package> toBePickedUp = new LinkedList<>();

        if (driver.getVehicle() == null) {
            throw new BadRequestException("You dont have a car assigned");
        }
        if (driver.getDriverStatus()== employeeStatusRepository.getById(1)){
            throw new BadRequestException("You are already working");
        }
        driver.setDriverStatus(employeeStatusRepository.getById(1));

        Vehicle vehicle = vehicleRepository.findById(driver.getVehicle().getId());
        for (Package pack : packages) {
            if (pack.getCurrentLocation().getAddress().getCity().equals(driver.getWorkingAddress().getCity()) && (pack.getStatus() == statusRepository.findStatusById(2))) {
                toBePickedUp.add(pack);
            }

        }

        Collections.sort(toBePickedUp, (p1, p2) -> {

            LocalDate p1DueDate = p1.getDueDate();
            LocalDate p2DueDate = p2.getDueDate();

            if (p1DueDate.isBefore(p2DueDate)) {
                return 1;
            } else if (p2DueDate.isBefore(p1DueDate)) {
                return -1;
            } else {
                return 0;
            }
        });

        for (Package pack : toBePickedUp) {

            if (route.contains(pack.getCurrentLocation().getId())) {
                if (vehicle.getCapacity() > pack.getVolume()) {
                    vehicle.setCapacity(vehicle.getCapacity() - pack.getVolume());
                    pack.setDriver(driver);
                    pack.setStatus(statusRepository.findStatusById(3));
                    packageRepository.save(pack);
                    vehicleRepository.save(vehicle);
                }
            }
        }
        driverRepository.save(driver);

    }

    private String generateTrackingNumber() {
        int n = 10;

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }


    public void checkInOffice(int id, int driverID) {
        DriverProfile driverProfile = driverRepository.getByProfileId(driverID);
        Office office = officeRepository.findById(id).orElse(null);
        driverProfile.setLastCheckedIn(office);
        driverRepository.save(driverProfile);
        Vehicle vehicle = driverProfile.getVehicle();
        System.out.println(1);
        List<Package> packages = packageRepository.findAllByDriverAndDeliveryOffice(driverProfile, office);

        for (Package aPack : packages) {
            //status 4 -> delivered
            aPack.setStatus(statusRepository.findStatusById(4));
            aPack.setDriver(null);
            aPack.setCurrentLocation(office);
            packageRepository.save(aPack);

            vehicle.setCapacity(vehicle.getCapacity() + aPack.getVolume());
            vehicleRepository.save(vehicle);
        }
        System.out.println(2);
        DriversOffices driverAndOffice = driversOfficesRepository.findByDriverAndOffice(driverProfile, office);
        if (driverAndOffice != null){
       // driversOfficesRepository.delete(driverAndOffice);
        driversOfficesRepository.flush();
        }
        List<Package> packagesWaitingForDriverInTheCurrentOffice = packageRepository.findAllByCurrentLocationAndStatus(office, statusRepository.findStatusById(2));

        HashSet<Integer> route = new HashSet<>();

        List<DriversOffices> driversOffices = driversOfficesRepository.findAllByDriver(driverProfile);
        if (driversOffices.size() > 0){
        for (DriversOffices driversOffice : driversOffices) {
            route.add(driversOffice.getOffice().getId());
        }
        }
        System.out.println(3);

        for (Package aPackage : packagesWaitingForDriverInTheCurrentOffice) {
            if (route.contains(aPackage.getDeliveryOffice().getId())) {
                if (vehicle.getCapacity() > aPackage.getVolume()) {
                    vehicle.setCapacity(vehicle.getCapacity() - aPackage.getVolume());
                    aPackage.setDriver(driverProfile);
                    aPackage.setStatus(statusRepository.findStatusById(3));
                    aPackage.setTrackingNumber(generateTrackingNumber());
                    packageRepository.save(aPackage);
                    vehicleRepository.save(vehicle);

                }
            }


        }
    }
}



