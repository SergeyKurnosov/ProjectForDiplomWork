package com.example.InformationalPortal.testConnect.repositories.address;

import com.example.InformationalPortal.testConnect.models.address.CompanyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyContactRepository extends JpaRepository<CompanyContact, Integer> {

    void deleteById(int id);


}
