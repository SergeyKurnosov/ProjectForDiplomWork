package com.example.InformationalPortal.testConnect.repositories.humans;

import com.example.InformationalPortal.testConnect.models.humans.CompanyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CompanyAdminRepository extends JpaRepository<CompanyAdmin, Integer> {

    Optional<CompanyAdmin> findByLogin (String login);

    @Transactional
    @Modifying
    @Query("UPDATE CompanyAdmin e SET e.lastLogin = ?2 WHERE e.login = ?1")
    int updateLastLogin(String login, String lastLogin);
}


