package com.evans.mobileappws.service.impl;

import com.evans.mobileappws.dto.AddressDto;
import com.evans.mobileappws.entity.AddressEntity;
import com.evans.mobileappws.entity.UserEntity;
import com.evans.mobileappws.repository.AddressRepository;
import com.evans.mobileappws.repository.UserRepository;
import com.evans.mobileappws.service.AddressService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAddresses(String userId) {
        List<AddressDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) return returnValue;
        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for (AddressEntity addressEntity : addresses){
            returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
        }
        return returnValue;
    }

    @Override
    public AddressDto getAddress(String addressId) {
        AddressDto addressDto = null;
        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        if (addressEntity != null){
            addressDto = new ModelMapper().map(addressEntity, AddressDto.class);
        }
        return addressDto;
    }
}
