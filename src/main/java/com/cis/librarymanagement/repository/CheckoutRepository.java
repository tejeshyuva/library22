package com.cis.librarymanagement.repository;

import com.cis.librarymanagement.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Integer> {
}
