package com.cis.librarymanagement.model;

import com.cis.librarymanagement.entity.Address;
import com.cis.librarymanagement.entity.Checkout;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
@Getter
@Setter
public class Member {
    private int memberId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
    private String membershipLevel;
    private Address address;
    private List<Checkout> checkoutList;
}
