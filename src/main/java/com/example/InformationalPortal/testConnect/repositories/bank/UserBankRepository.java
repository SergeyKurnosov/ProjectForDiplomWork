package com.example.InformationalPortal.testConnect.repositories.bank;

import com.example.InformationalPortal.testConnect.models.bank.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBankRepository extends JpaRepository<UserBank, Integer> {
    Optional<UserBank> findByLogin (String login);
}
