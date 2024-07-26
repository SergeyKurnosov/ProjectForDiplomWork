package com.example.InformationalPortal.testConnect.repositories.address;

import com.example.InformationalPortal.testConnect.models.address.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector , Integer> {
    Optional<Sector> findBySector (Integer sector);

    void deleteById (Integer id);
}
