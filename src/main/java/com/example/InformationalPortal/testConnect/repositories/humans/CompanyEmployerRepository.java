package com.example.InformationalPortal.testConnect.repositories.humans;


import com.example.InformationalPortal.testConnect.models.humans.CompanyEmployer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CompanyEmployerRepository  extends JpaRepository<CompanyEmployer, Integer> {

    Iterable<CompanyEmployer> findByEmployerSector (Integer sector);
    Optional<CompanyEmployer> findByLogin (String login);

    @Transactional
    void deleteById (Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE CompanyEmployer e SET e.lastLogin = ?2 WHERE e.login = ?1")
    int updateLastLogin(String login, String lastLogin);


    @Transactional
    @Modifying
    @Query("UPDATE CompanyEmployer e SET e.employerSector = :employerSector WHERE e.id = :id")
    void updateEmployerSectorById(Integer id, Integer employerSector);
}
