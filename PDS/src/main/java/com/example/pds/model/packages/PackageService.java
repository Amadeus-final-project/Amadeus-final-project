package com.example.pds.model.packages;

import com.example.pds.model.packages.packageDTO.PackageComplexResponseDTO;
import com.example.pds.model.packages.packageDTO.PackageGetMyPackagesDTO;
import com.example.pds.model.packages.packageDTO.PackageSimpleResponseDTO;
import com.example.pds.model.packages.packageDTO.SendPackageDTO;
import com.example.pds.model.user.UserProfile;
import com.example.pds.controllers.profiles.Profile;
import com.example.pds.model.user.UserRepository;
import com.example.pds.controllers.profiles.ProfilesRepository;
import com.example.pds.util.exceptions.NotFoundException;
import com.example.pds.util.exceptions.UnauthorizedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public PackageSimpleResponseDTO sendPackage(int id, SendPackageDTO sendPackageDTO) {
        Profile recipient = profilesRepository.findByUsername(sendPackageDTO.getRecipient());

        Package currentPackage = new Package();
        currentPackage.setSender(userRepository.findByProfileId(id));

        //currentPackage.setAddress(recipient.getAddress());
        currentPackage.setRecipient(userRepository.findByProfileId(recipient.getId()));


        Double volume = sendPackageDTO.getHeight() * sendPackageDTO.getWidth() * sendPackageDTO.getLength();
        currentPackage.setVolume(volume);
        currentPackage.setWeight(currentPackage.getWeight());
        currentPackage.setTrackingNumber(currentPackage.getTrackingNumber());
        currentPackage.setIsFragile(sendPackageDTO.getIsFragile());
        currentPackage.setIsSigned(sendPackageDTO.getIsSigned());
        currentPackage.setDescription(sendPackageDTO.getDescription());
        currentPackage.setWeight(sendPackageDTO.getWeight());

        packageRepository.save(currentPackage);

        return modelMapper.map(currentPackage, PackageSimpleResponseDTO.class);

    }

    public List<PackageComplexResponseDTO> getAllPackages(Pageable page) {
        List<PackageComplexResponseDTO> complexPackages = new ArrayList<>();
        List<Package> packages = packageRepository.findAll(page).getContent();
        for (Package package1 : packages) {
            complexPackages.add(modelMapper.map(package1, PackageComplexResponseDTO.class));
        }
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

