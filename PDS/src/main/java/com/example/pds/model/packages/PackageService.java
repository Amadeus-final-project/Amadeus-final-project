package com.example.pds.model.packages;

import com.example.pds.model.user.User;
import com.example.pds.model.user.UserRepository;
import com.example.pds.model.user.userDTO.UserReceivePackageDTO;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.UnauthorizedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public PackageSimpleResponseDTO sendPackage(Object id, Object isUser, SendPackageDTO sendPackageDTO) {
        if (isUser == null) {
            throw new BadRequestException("You must login");
        }

        User recipient = userRepository.findByUsername(sendPackageDTO.getRecipient());

        Package currentPackage = new Package();
        currentPackage.setSender(userRepository.getById((int) id));

        currentPackage.setAddress(recipient.getAddress());
        currentPackage.setRecipient(recipient);


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

    public List<PackageComplexResponseDTO> getAllPackages(Object isAdmin, Object isAgent, Object isLogged) {
        if (isLogged == null) {
            throw new BadRequestException("You are not logged in");

        }
        if (isAdmin != null && isAgent != null) {
            throw new UnauthorizedException("You are unauthorized");
        }
        List<PackageComplexResponseDTO> complexPackages = new ArrayList<>();
        List<Package> packages = packageRepository.findAll();
        for (Package package1 : packages) {
            // PackageComplexResponseDTO dto = modelMapper.map(package1, PackageComplexResponseDTO.class);
            //dto.setSender(package1.getSender());
            complexPackages.add(modelMapper.map(package1, PackageComplexResponseDTO.class));
        }
        return complexPackages;

    }

}

