package com.example.InformationalPortal.testConnect.repositories.address;

import com.example.InformationalPortal.testConnect.models.address.FutureHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FutureHomeRepository extends JpaRepository<FutureHome, Integer> {
    void deleteById(int id);
}
