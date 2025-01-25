package com.qalaa.address.service;

import com.qalaa.address.model.Address;
import com.qalaa.address.repository.AddressRepository;
import com.qalaa.exception.CustomException;
import com.qalaa.user.model.User;
import com.qalaa.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address addAddress(Long userId, Address address) {
        User user = userService.getUser(userId);

        address.setUser(user);
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAddressesByUser(Long userId) {
        userService.getUser(userId);

        return addressRepository.findByUserId(userId);
    }

    @Override
    public Address updateAddress(Long addressId, Address addressDetails, Long userId) {
        userService.getUser(userId);
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));
        if (!address.getUser().getId().equals(userId)) {
            throw new CustomException("You are not authorized to update this address");
        }
        address.setStreet(addressDetails.getStreet());
        address.setCity(addressDetails.getCity());
        address.setState(addressDetails.getState());
        address.setCountry(addressDetails.getCountry());
        address.setPostalCode(addressDetails.getPostalCode());
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long addressId, Long userId) {
        userService.getUser(userId);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));
        if (!address.getUser().getId().equals(userId)) {
            throw new CustomException("You are not authorized to delete this address");
        }
        addressRepository.deleteById(addressId);
    }
}
