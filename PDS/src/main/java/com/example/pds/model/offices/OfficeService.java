package com.example.pds.model.offices;

import com.example.pds.model.address.Address;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.LinkedList;
import java.util.List;

@Service
public class OfficeService {
    @Autowired
    OfficeRepository officeRepository;
    @Autowired
    ModelMapper modelMapper;


    public List<OfficeComplexResponseDTO> getAllOffices(){
     List<Office> allOffices =  officeRepository.findAll();
     List<OfficeComplexResponseDTO> officesDTO= new LinkedList<>();
        for (Office allOffice : allOffices) {
     OfficeComplexResponseDTO dto = new OfficeComplexResponseDTO();
            dto.setCity(allOffice.getAddress().getCity());
            dto.setCountry(allOffice.getAddress().getCountry());
            dto.setRegion(allOffice.getAddress().getRegion());
            dto.setPostcode(allOffice.getAddress().getPostCode());
            dto.setName(allOffice.getName());
            officesDTO.add(dto);
        }
        return officesDTO;
    }

    public OfficeComplexResponseDTO getOfficeById(int id) {
        OfficeComplexResponseDTO dto = new OfficeComplexResponseDTO();
        if (!officeRepository.findById(id).equals(null)) {
            Office office = officeRepository.getById(id);
            dto.setCity(office.getAddress().getCity());
            dto.setCountry(office.getAddress().getCountry());
            dto.setRegion(office.getAddress().getRegion());
            dto.setPostcode(office.getAddress().getPostCode());
            dto.setName(office.getName());
        }
        return dto;
    }
}
