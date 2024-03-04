package com.cis.librarymanagement.repository;

import com.cis.librarymanagement.entity.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<LibraryMember, Integer> {
}
