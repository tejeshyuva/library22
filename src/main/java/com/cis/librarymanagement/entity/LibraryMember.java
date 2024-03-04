package com.cis.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Table(name = "library_member")
@Entity
@Data
public class LibraryMember {

    @Id
    @Column(name = "member_id")
    private Integer memberId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email_id")
    private String emailId;
    @Column(name = "phone_no")
    private String phoneNumber;
    @Column(name = "membership_level")
    private String membershipLevel;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany
    private List<Checkout> checkoutList;
}
