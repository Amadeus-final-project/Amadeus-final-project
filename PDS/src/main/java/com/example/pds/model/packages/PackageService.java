package com.example.pds.model.packages;

import com.example.pds.model.offices.OfficeRepository;
import com.example.pds.model.packages.packageDTO.PackageComplexResponseDTO;
import com.example.pds.model.packages.packageDTO.PackageGetMyPackagesDTO;
import com.example.pds.model.packages.packageDTO.PackageSimpleResponseDTO;
import com.example.pds.model.packages.packageDTO.SendPackageDTO;
import com.example.pds.model.packages.statuses.Status;
import com.example.pds.model.packages.statuses.StatusRepository;
import com.example.pds.model.transaction.Transaction;
import com.example.pds.model.transaction.TransactionRepository;
import com.example.pds.model.user.UserProfile;
import com.example.pds.controllers.profiles.Profile;
import com.example.pds.model.user.UserRepository;
import com.example.pds.controllers.profiles.ProfilesRepository;
import com.example.pds.util.exceptions.NotFoundException;
import com.example.pds.util.exceptions.UnauthorizedException;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PackageService {
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfilesRepository profilesRepository;
    @Autowired
    OfficeRepository officeRepository;
    @Autowired
    DeliveryTypeRepository deliveryTypeRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Transactional
    public PackageSimpleResponseDTO sendPackage(int id, SendPackageDTO sendPackageDTO) {
        Package currentPackage = new Package();
        Transaction transaction = new Transaction();
        transaction.setPaidAt(LocalDate.now());
        transaction.setPayer(profilesRepository.findById(id));
        transaction.setPaymentType(sendPackageDTO.getPaymentType());
        int i = sendPackageDTO.getDeliveryType();


        // 2 == express
        if (sendPackageDTO.getDeliveryType() == 2 ){ // multiply by 1.5 because it's express delivery
            transaction.setPrice(BigDecimal.valueOf(sendPackageDTO.getWeight()*2.3*1.5));
        }else
            transaction.setPrice(BigDecimal.valueOf(sendPackageDTO.getWeight()*2.3));
        transactionRepository.save(transaction);

        currentPackage.setSender(userRepository.findByProfileId(id));
        currentPackage.setRecipient(userRepository.findByProfileId(profilesRepository.findByEmail(sendPackageDTO.getRecipient()).getId()));
        currentPackage.setDeliveryOffice(officeRepository.findByName(sendPackageDTO.getDeliveryOffice()));
        currentPackage.setTrackingNumber(String.valueOf(LocalDateTime.now().getNano()));
        currentPackage.setIsFragile(sendPackageDTO.getIsFragile());
        currentPackage.setIsSigned(sendPackageDTO.getIsSigned());
        currentPackage.setDescription(sendPackageDTO.getDescription());
        currentPackage.setWeight(sendPackageDTO.getWeight());
        currentPackage.setOffice(officeRepository.findByName(sendPackageDTO.getDeliveryToOffice()));
        currentPackage.setStatus(statusRepository.findStatusById(1));
        currentPackage.setTransaction(transaction);

        Double volume = sendPackageDTO.getHeight() * sendPackageDTO.getWidth() * sendPackageDTO.getLength();
        currentPackage.setVolume(volume);

        DeliveryType deliveryType = deliveryTypeRepository.getById(sendPackageDTO.getDeliveryType());
        currentPackage.setDeliveryType(deliveryType);

        if (deliveryType.getId() == 1){
        currentPackage.setDueDate(LocalDate.now().plusDays(1));
        }else {
            currentPackage.setDueDate(LocalDate.now().plusDays(2));
        }

        packageRepository.save(currentPackage);

        return modelMapper.map(currentPackage, PackageSimpleResponseDTO.class);

    }

    public List<PackageComplexResponseDTO> getAllPackages(Pageable page) {
        List<PackageComplexResponseDTO> complexPackages = new ArrayList<>();
        System.out.println(1);
        List<Package> packages = packageRepository.findAll(page).getContent();
        System.out.println(2);
        for (Package package1 : packages) {
            complexPackages.add(modelMapper.map(package1, PackageComplexResponseDTO.class));
        }
        System.out.println(3);
        return complexPackages;
    }


    public PackageComplexResponseDTO getPackage(int id) {

        if (packageRepository.findById(id) == null) {
            throw new NotFoundException("Package does not exist");
        }
        Package package1 = packageRepository.getById(id);
        return modelMapper.map(package1, PackageComplexResponseDTO.class);
    }

    public List<PackageGetMyPackagesDTO> getAllPendingPackages(Pageable page) {
        List<PackageGetMyPackagesDTO> packageToReturn = new ArrayList<>();
        List<Package> packages = packageRepository.findAllByStatusId(1, page);
        for (Package pack : packages) {
            packageToReturn.add(modelMapper.map(pack, PackageGetMyPackagesDTO.class));

        }
        return packageToReturn;
    }

    public List<PackageGetMyPackagesDTO> getAllMyPackages(int userId, Pageable page) {
        UserProfile user = userRepository.findByProfileId(userId);
        List<Package> myPackages = packageRepository.findAllByRecipient(user, page).getContent();
        List <PackageGetMyPackagesDTO> dtoList = new ArrayList<>();
        for (Package pack : myPackages) {
            dtoList.add(modelMapper.map(pack, PackageGetMyPackagesDTO.class));
        }
        return dtoList;
    }
    public PackageGetMyPackagesDTO getMyPackage(int id, int userID){
        UserProfile user = userRepository.findByProfileId(id);
        if (id != userID){
            throw new UnauthorizedException("Not your package");
        }
        Package myPackage = packageRepository.findById(id);
        return modelMapper.map(myPackage,PackageGetMyPackagesDTO.class);
    }

}

