package com.example.InformationalPortal.testConnect.repositories.bank;

import com.example.InformationalPortal.testConnect.models.bank.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankCardRepository extends JpaRepository<BankCard, Integer> {

    Iterable<BankCard> findByCvv (String cvv);

}
