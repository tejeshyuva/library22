package com.cis.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "address")
@Entity
@Data
public class Address {
    @Id
    @Column(name = "address_id")
    private int addressId;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private int zip;
    @OneToMany(mappedBy = "address")
    private List<LibraryMember> memberList;
}
