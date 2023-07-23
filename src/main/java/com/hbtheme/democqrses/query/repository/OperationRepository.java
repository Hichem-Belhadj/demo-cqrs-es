package com.hbtheme.democqrses.query.repository;

import com.hbtheme.democqrses.query.models.Account;
import com.hbtheme.democqrses.query.models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
