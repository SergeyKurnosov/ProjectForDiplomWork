package com.example.InformationalPortal.testConnect.repositories.keys;

import com.example.InformationalPortal.testConnect.models.keys.ValueNameRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValueNameRoleRepository extends JpaRepository<ValueNameRole, Integer> {

    Optional<ValueNameRole> findByNameRole (String role);

}
