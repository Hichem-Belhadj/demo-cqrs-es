package com.hbtheme.democqrses.query.repository;

import com.hbtheme.democqrses.query.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
