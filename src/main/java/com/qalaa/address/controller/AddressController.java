package com.qalaa.address.controller;

import com.qalaa.address.model.Address;
import com.qalaa.address.service.AddressService;
import com.qalaa.util.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@SecurityRequirement(name = "bearerAuth")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/address")
    public ResponseEntity<ApiResponse<Address>> addAddress(@RequestAttribute("id") Long userId, @RequestBody Address address, HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "address created", addressService.addAddress(userId, address),request.getRequestURI()));

    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Address>>> getAddressesByUser(@RequestAttribute("id") Long userId,HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "addresses", addressService.getAddressesByUser(userId),request.getRequestURI()));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Address>> updateAddress(@RequestAttribute("id") Long userId,@PathVariable Long addressId, @RequestBody Address addressDetails,HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "address updated", addressService.updateAddress(addressId, addressDetails, userId),request.getRequestURI()));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@RequestAttribute("id") Long userId,@PathVariable Long addressId) {
        addressService.deleteAddress(addressId,userId);
        return ResponseEntity.noContent().build();
    }
}