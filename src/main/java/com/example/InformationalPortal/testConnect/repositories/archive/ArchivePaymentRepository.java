package com.example.InformationalPortal.testConnect.repositories.archive;

import com.example.InformationalPortal.testConnect.models.archive.ArchivePayment;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ArchivePaymentRepository extends JpaRepository<ArchivePayment, Integer> {

    Iterable<ArchivePayment> findByOwnerId(String ownerId);


    // Метод для сортировки по дате, сначала новые для конкретного owner_id
    @Query("SELECT a FROM ArchivePayment a WHERE a.ownerId = :ownerId ORDER BY a.datePayment DESC")
    List<ArchivePayment> findAllByOwnerIdOrderByDatePaymentDesc(String ownerId);

    // Метод для сортировки по дате, сначала старые для конкретного owner_id
    @Query("SELECT a FROM ArchivePayment a WHERE a.ownerId = :ownerId ORDER BY a.datePayment ASC")
    List<ArchivePayment> findAllByOwnerIdOOrderByDatePaymentAsc(String ownerId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ArchivePayment a WHERE a.ownerId = :ownerId")

    void deleteByOwnerId( String ownerId);

    void deleteById (Integer id);

    Iterable<ArchivePayment> findAllByOwnerId(String ownerId);
}
