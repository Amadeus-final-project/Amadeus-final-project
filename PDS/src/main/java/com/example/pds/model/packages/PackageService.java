package com.example.pds.model.packages;

import com.example.pds.model.user.User;
import com.example.pds.model.user.UserRepository;
import com.example.pds.util.exceptions.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageService {
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;

    public PackageSimpleResponseDTO sendPackage(Object id, Object isUser, SendPackageDTO sendPackageDTO) {
        if (isUser == null){
            throw new BadRequestException("You must login");
        }
        Package currentPackage = new Package();
        currentPackage.setSender(userRepository.getById((int)id));
        currentPackage.setRecipient(sendPackageDTO.getRecipient());
        currentPackage.setAddress(sendPackageDTO.getAddress());
        currentPackage.setDeliveryType(sendPackageDTO.getDeliveryType());
        currentPackage.setPackageDimensions(sendPackageDTO.getPackageDimensions());
        currentPackage.setIsFragile(sendPackageDTO.getIsFragile());
        currentPackage.setIsSigned(sendPackageDTO.getIsSigned());
        currentPackage.setDescription(sendPackageDTO.getDescription());
        packageRepository.save(currentPackage);
        System.out.println(currentPackage.getRecipient());
        System.out.println(currentPackage.getSender());
        System.out.println(currentPackage.getIsFragile());
        return modelMapper.map(currentPackage,PackageSimpleResponseDTO.class);

    }
}
