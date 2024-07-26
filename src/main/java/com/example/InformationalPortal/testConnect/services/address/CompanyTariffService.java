package com.example.InformationalPortal.testConnect.services.address;

import com.example.InformationalPortal.testConnect.models.address.CompanyTariff;
import com.example.InformationalPortal.testConnect.repositories.address.CompanyTariffRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CompanyTariffService {

    private final CompanyTariffRepository companyTariffRepository;

    public CompanyTariffService(CompanyTariffRepository companyTariffRepository) {
        this.companyTariffRepository = companyTariffRepository;
    }


    public List<CompanyTariff> getAllTariffs(){
        return companyTariffRepository.findAll();
    }

    public void deleteTariffs(Integer id){
        companyTariffRepository.deleteById(id);
    }

    public boolean createNewTarriff (BigDecimal price){

        try {
            CompanyTariff companyTariff = new CompanyTariff(0 , price);
            companyTariffRepository.save(companyTariff);
            return true ;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
