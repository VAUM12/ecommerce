package com.qalaa.address.service;

import com.qalaa.address.model.Address;

import java.util.List;

public interface AddressService {
    public Address addAddress(Long userId, Address address) ;

    List<Address> getAddressesByUser(Long userId);

    Address updateAddress(Long addressId, Address addressDetails,Long userId);

    void deleteAddress(Long addressId,Long userId);
}
