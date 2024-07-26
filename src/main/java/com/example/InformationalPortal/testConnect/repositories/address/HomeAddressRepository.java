package com.example.InformationalPortal.testConnect.repositories.address;

import com.example.InformationalPortal.testConnect.models.address.HomeAddress;
import com.example.InformationalPortal.testConnect.models.address.Sector;
import com.example.InformationalPortal.testConnect.models.humans.HomeOwner;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface HomeAddressRepository extends JpaRepository<HomeAddress, Integer> {
    Optional<HomeAddress> findByPersonalAccount(String personalAccount);

    Optional<HomeAddress> findByHomeAddress(String address);

    Optional<HomeAddress> findByHomeOwner (HomeOwner homeOwner);

    Iterable<HomeAddress> findBySector(Sector sector);

    void deleteById(Integer id);

    // Обновить количество жителей по ID
    @Transactional
    @Modifying
    @Query("UPDATE HomeAddress h SET h.countResidents = :countResidents WHERE h.id = :id")
    void updateCountResidentsById(Integer id, Integer countResidents);

    // Обновить ID владельца по ID
    @Transactional
    @Modifying
    @Query("UPDATE HomeAddress h SET h.homeOwner.ownerId = :ownerId WHERE h.id = :id")
    void updateOwnerIdById(Integer id, String ownerId);







    // 2. Изменение to_pay по personal_account
    @Transactional
    @Modifying
    @Query("UPDATE HomeAddress h SET h.toPay = :toPay WHERE h.id = :id")
    void updateToPayById(Integer id, BigDecimal toPay);

    // 3. Изменение areas_payment по personal_account
    @Transactional
    @Modifying
    @Query("UPDATE HomeAddress h SET h.areasPayment = :areasPayment WHERE h.id = :id")
    void updateAreasPaymentById(Integer id, BigDecimal areasPayment);

}
