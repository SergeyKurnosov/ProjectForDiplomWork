package com.example.InformationalPortal.testConnect.repositories.humans;

import com.example.InformationalPortal.testConnect.models.humans.HomeOwner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface HomeOwnerRepository extends CrudRepository<HomeOwner, Long> {

    Optional<HomeOwner> findByPassportDetails (String detals);
    Optional<HomeOwner> findByOwnerId (String id);
    Optional<HomeOwner> findById (Integer id);

    @Transactional
    void deleteById (Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE HomeOwner h SET h.surname = :surname WHERE h.id = :id")
    void updateSurnameById(Integer id, String surname);
}
