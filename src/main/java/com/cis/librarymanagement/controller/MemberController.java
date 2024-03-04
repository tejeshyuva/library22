package com.cis.librarymanagement.controller;

import com.cis.librarymanagement.entity.LibraryMember;
import com.cis.librarymanagement.model.Member;
import com.cis.librarymanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/member", method = RequestMethod.POST)
    public LibraryMember createdMember(@RequestBody Member member) {
        return memberService.createMember(member);
    }

    @RequestMapping(value = "/member/read", method = RequestMethod.GET)
    public String getMemberById(@RequestParam int memberId) {
        return memberService.readMember(memberId).getFirstName();
    }

    @RequestMapping(value = "/member/all", method = RequestMethod.GET)
    public Collection<Member> getAllMembers() {
        return memberService.readAllMembers();
    }

    @RequestMapping(value = "/member/{memberId}", method = RequestMethod.PUT)
    public Member update(@PathVariable int memberId,
                         @RequestBody Member member) {
        return memberService.updateMemberData(memberId, member);
    }

    @RequestMapping(value = "/member/{memberId}", method = RequestMethod.DELETE)
    public Member delete(@PathVariable int memberId) {
        System.out.println("Member Deleted");
        return memberService.deleteMember(memberId);
    }
}
