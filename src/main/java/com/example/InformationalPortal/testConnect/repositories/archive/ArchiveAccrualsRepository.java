package com.example.InformationalPortal.testConnect.repositories.archive;

import com.example.InformationalPortal.testConnect.models.archive.ArchiveAccrual;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArchiveAccrualsRepository extends JpaRepository<ArchiveAccrual, Integer> {

    @Query("SELECT a FROM ArchiveAccrual a WHERE a.ownerId = :ownerId ORDER BY a.dateAccruals DESC limit 1")
    Optional<ArchiveAccrual> findLatestByOwnerId(String ownerId);

    Iterable<ArchiveAccrual> findByOwnerId(String ownerId);


    // Метод для сортировки по дате, сначала новые для конкретного owner_id
    @Query("SELECT a FROM ArchiveAccrual a WHERE a.ownerId = :ownerId ORDER BY a.dateAccruals DESC")
    List<ArchiveAccrual> findAllByOwnerIdOrderByDateAccrualsDesc(String ownerId);

    // Метод для сортировки по дате, сначала старые для конкретного owner_id
    @Query("SELECT a FROM ArchiveAccrual a WHERE a.ownerId = :ownerId ORDER BY a.dateAccruals ASC")
    List<ArchiveAccrual> findAllByOwnerIdOrderByDateAccrualsAsc(String ownerId);


    @Transactional
    @Modifying
    @Query("DELETE FROM ArchiveAccrual a WHERE a.ownerId = :ownerId")
    void deleteByOwnerId( String ownerId);

    void deleteById (Integer id);



    Iterable<ArchiveAccrual> findAllByOwnerId(String ownerId);

}
