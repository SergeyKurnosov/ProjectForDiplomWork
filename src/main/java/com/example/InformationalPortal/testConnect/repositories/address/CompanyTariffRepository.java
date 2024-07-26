package com.example.InformationalPortal.testConnect.repositories.address;

import com.example.InformationalPortal.testConnect.models.address.CompanyTariff;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompanyTariffRepository extends JpaRepository<CompanyTariff, Integer> {

    Optional<CompanyTariff> findById (Integer id);

    @Query("SELECT c FROM CompanyTariff c ORDER BY c.id ASC")
    CompanyTariff findFirstRow();

    void deleteById(Integer id);
}
