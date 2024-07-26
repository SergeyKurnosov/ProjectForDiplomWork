package com.example.InformationalPortal.testConnect.repositories.keys;

import com.example.InformationalPortal.testConnect.models.keys.KeysForAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeysForAdminRepository extends JpaRepository<KeysForAdmin, Integer> {

    Optional<KeysForAdmin> findByValueKey (String key);

    void deleteById (Integer id);

}
