package com.example.InformationalPortal.testConnect.repositories.keys;

import com.example.InformationalPortal.testConnect.models.keys.KeysForEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeysForEmployeeRepository extends JpaRepository<KeysForEmployee, Integer> {

    Optional<KeysForEmployee> findByValueKey (String key);

    void deleteById (Integer id);
}
