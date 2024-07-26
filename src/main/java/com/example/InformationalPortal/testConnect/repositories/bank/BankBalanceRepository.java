package com.example.InformationalPortal.testConnect.repositories.bank;

import com.example.InformationalPortal.testConnect.models.bank.BankBalance;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface BankBalanceRepository extends CrudRepository<BankBalance, Integer> {

    Optional<BankBalance> findByCardNumber (String cardNumber);

    @Transactional
    @Modifying
    @Query("UPDATE BankBalance h SET h.cardBalance = :toPay WHERE h.cardNumber = :num")
    void updateBankBalanceByCardNumber (String num, String toPay);

}
