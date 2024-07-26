package com.example.InformationalPortal.testConnect.services.address;

import com.example.InformationalPortal.testConnect.models.address.CompanyContact;
import com.example.InformationalPortal.testConnect.repositories.address.CompanyContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final CompanyContactRepository companyContactRepository;

    public ContactService(CompanyContactRepository companyContactRepository) {
        this.companyContactRepository = companyContactRepository;
    }

    public List<CompanyContact> findAllContacts() {
        return companyContactRepository.findAll();
    }

    public void deleteContact(Integer id) {
        companyContactRepository.deleteById(id);
    }

    public boolean createContact(String name , String phone , String address) {

        try {
            CompanyContact companyContact = new CompanyContact(0 , name , phone , address);
            companyContactRepository.save(companyContact);
            return true;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}
