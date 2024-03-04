package com.cis.librarymanagement.service;

import com.cis.librarymanagement.entity.Address;
import com.cis.librarymanagement.entity.Checkout;
import com.cis.librarymanagement.entity.LibraryMember;
import com.cis.librarymanagement.model.Member;
import com.cis.librarymanagement.repository.AddressRepository;
import com.cis.librarymanagement.repository.CheckoutRepository;
import com.cis.librarymanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CheckoutRepository checkoutRepository;

    private final Map<Integer, Member> integerMemberMap = new HashMap<>();
    public LibraryMember createMember(Member member) {
        int memberId = Math.abs(new Random().nextInt());
        LibraryMember libraryMember = new LibraryMember();
        libraryMember.setMemberId(memberId);
        libraryMember.setFirstName(member.getFirstName());
        libraryMember.setLastName(member.getLastName());
        libraryMember.setEmailId(member.getEmailId());
        libraryMember.setPhoneNumber(member.getPhoneNumber());
        libraryMember.setMembershipLevel(member.getMembershipLevel());

        Address address = member.getAddress();
        if(address != null) {
            Optional<Address> addressOptional = addressRepository.findById(address
                    .getAddressId());
            if(addressOptional.isPresent()) {
                libraryMember.setAddress(addressOptional.get());
            }
            else {
                Address savedAddress = addressRepository.save(address);
                libraryMember.setAddress(savedAddress);
            }
        }
        System.out.println(member.getCheckoutList());
        if(member.getCheckoutList() != null) {
            List<Checkout> checkoutList = member.getCheckoutList()
                    .stream().map(cd -> {
                        Checkout checkout = new Checkout();
                        checkout.setId(cd.getId());
                        checkout.setLibraryMember(cd.getLibraryMember());
                        checkout.setBookIsbn(cd.getBookIsbn());
                        checkout.setCheckoutDate(new Date());
                        checkout.setDueDate(cd.getDueDate());
                        checkout.setReturned(false);
                        return checkout;
                    }).toList();
            checkoutRepository.saveAll(checkoutList);
            libraryMember.setCheckoutList(checkoutList);
        }

        //libraryMember.setCheckoutList(member.getCheckoutList());
        return memberRepository.save(libraryMember);
    }

    public Member readMember(Integer memberId) {

        Optional<LibraryMember> memberOptional =
                memberRepository.findById(memberId);
        LibraryMember libraryMember =
                memberOptional.orElse(new LibraryMember());

        Member member = new Member();
        member.setMemberId(libraryMember.getMemberId());
        member.setMembershipLevel(libraryMember.getMembershipLevel());
        member.setEmailId(libraryMember.getEmailId());
        member.setFirstName(libraryMember.getFirstName());
        member.setLastName(libraryMember.getLastName());
        member.setPhoneNumber(libraryMember.getPhoneNumber());

        Address address = new Address();
        address.setAddressId(libraryMember.getAddress().getAddressId());
        address.setLine1(libraryMember.getAddress().getLine1());
        address.setLine2(libraryMember.getAddress().getLine2());
        address.setCity(libraryMember.getAddress().getCity());
        address.setState(libraryMember.getAddress().getState());
        address.setZip(libraryMember.getAddress().getZip());

        List<Checkout> checkoutList =
                libraryMember.getCheckoutList().stream().map(c -> {
                    Checkout cdo = new Checkout();
                    cdo.setId(c.getId());
                    cdo.setBookIsbn(c.getBookIsbn());
                    cdo.setCheckoutDate(c.getCheckoutDate());
                    cdo.setDueDate(c.getDueDate());
                    cdo.setReturned(c.isReturned());
                    return  cdo;
                }).collect(Collectors.toList());

        member.setAddress(address);
        member.setCheckoutList(checkoutList);

        return member;
    }



    /**public Member readMember(int memberId) {
        return integerMemberMap.get(memberId);
    }**/

    public List<Member> readAllMembers() {
        List<LibraryMember> libraryMemberList =
                memberRepository.findAll();

        return libraryMemberList.stream()
                .map(this::convertModel)
                .toList();
    }

    private Member convertModel(LibraryMember libraryMember) {
        Member member = new Member();
        member.setMemberId(libraryMember.getMemberId());
        member.setMembershipLevel(libraryMember.getMembershipLevel());
        member.setEmailId(libraryMember.getEmailId());
        member.setFirstName(libraryMember.getFirstName());
        member.setLastName(libraryMember.getLastName());
        member.setPhoneNumber(libraryMember.getPhoneNumber());

        Address address = new Address();
        address.setAddressId(libraryMember.getAddress().getAddressId());
        address.setLine1(libraryMember.getAddress().getLine1());
        address.setLine2(libraryMember.getAddress().getLine2());
        address.setCity(libraryMember.getAddress().getCity());
        address.setState(libraryMember.getAddress().getState());
        address.setZip(libraryMember.getAddress().getZip());

        List<Checkout> checkoutList =
                libraryMember.getCheckoutList().stream().map(c -> {
                    Checkout cdo = new Checkout();
                    cdo.setId(c.getId());
                    cdo.setBookIsbn(c.getBookIsbn());
                    cdo.setCheckoutDate(c.getCheckoutDate());
                    cdo.setDueDate(c.getDueDate());
                    cdo.setReturned(c.isReturned());
                    return  cdo;
                }).collect(Collectors.toList());

        member.setAddress(address);
        member.setCheckoutList(checkoutList);

        return member;
    }

    public Member updateMemberData(int memberId, Member updatedMember) {
        Optional<LibraryMember> existingMemberOptional = memberRepository.findById(memberId);
        if(existingMemberOptional.isPresent()) {
            LibraryMember existingMember = existingMemberOptional.get();
            if(!updatedMember.getFirstName().isEmpty()) {
                existingMember.setFirstName(updatedMember.getFirstName());
            }
            else if(!updatedMember.getLastName().isEmpty()) {
                existingMember.setLastName(updatedMember.getLastName());
            }
            else if(!updatedMember.getEmailId().isEmpty()) {
                existingMember.setEmailId(updatedMember.getEmailId());
            }
            else if(!updatedMember.getPhoneNumber().isEmpty()) {
                existingMember.setPhoneNumber(updatedMember.getPhoneNumber());
            }
            else if(!updatedMember.getMembershipLevel().isEmpty()) {
                existingMember.setMembershipLevel(updatedMember.getMembershipLevel());
            }
            LibraryMember savedLibraryMember = memberRepository.save(existingMember);
            System.out.println("updated successfully");
            return convertToMember(savedLibraryMember);
        }
        return null;
    }

    private Member convertToMember(LibraryMember libraryMember) {
        return new Member();
    }

    public Member deleteMember(int memberId) {
        if(memberRepository.existsById(memberId)) {
            memberRepository.deleteById(memberId);
        }
        return null;
    }
}
