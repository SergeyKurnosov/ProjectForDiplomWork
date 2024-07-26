package com.example.InformationalPortal.testConnect.services.address;

import com.example.InformationalPortal.testConnect.models.address.FutureHome;
import com.example.InformationalPortal.testConnect.models.address.HomeAddress;
import com.example.InformationalPortal.testConnect.models.address.Sector;
import com.example.InformationalPortal.testConnect.models.humans.HomeOwner;
import com.example.InformationalPortal.testConnect.repositories.address.FutureHomeRepository;
import com.example.InformationalPortal.testConnect.repositories.address.HomeAddressRepository;
import com.example.InformationalPortal.testConnect.repositories.address.SectorRepository;
import com.example.InformationalPortal.testConnect.repositories.humans.HomeOwnerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AddressSrvices {

    private final HomeAddressRepository homeAddressRepository;
    private final HomeOwnerRepository homeOwnerRepository;
    private final SectorRepository sectorRepository;
    private final FutureHomeRepository futureHomeRepository;


    private Integer AddressID ;

    public void setAddressID(Integer addressID) {
        AddressID = addressID;
    }

    public AddressSrvices(HomeAddressRepository homeAddressRepository, HomeOwnerRepository homeOwnerRepository, SectorRepository sectorRepository, FutureHomeRepository futureHomeRepository) {
        this.homeAddressRepository = homeAddressRepository;
        this.homeOwnerRepository = homeOwnerRepository;
        this.sectorRepository = sectorRepository;
        this.futureHomeRepository = futureHomeRepository;
    }


    public void setToPayHomeAddress (Integer id , BigDecimal amount){
        homeAddressRepository.updateToPayById(id , amount);
    }

    public void setAreasPayHomeAddress (Integer id , BigDecimal amount){
        homeAddressRepository.updateAreasPaymentById(id , amount);
    }

    public HomeAddress getHomeAddressPersonalAccount (String persAcc){
        return homeAddressRepository.findByPersonalAccount(persAcc).get();
    }

    public HomeAddress getHomeAddressHomeOwner (HomeOwner homeOwner){
        return homeAddressRepository.findByHomeOwner(homeOwner).get();
    }

    public List<HomeAddress> getAddress (String sector){
        Sector sector1 = sectorRepository.findBySector(Integer.parseInt(sector)).get();
        return (List<HomeAddress>) homeAddressRepository.findBySector(sector1);
    }

    public void deleteAddress (Integer id){
        homeAddressRepository.deleteById(id);
    }


    public boolean createNewAddress(String address , Integer sector , BigDecimal area , Integer countResidents , String personalAccount , String ownerId) {
        try {
            HomeOwner owner = homeOwnerRepository.findByOwnerId(ownerId).get();
            Sector sectorAdres = sectorRepository.findBySector(sector).get();

            HomeAddress homeAddress = new HomeAddress(0 , address , sector , area , countResidents , BigDecimal.valueOf(0.0) , BigDecimal.valueOf(0.0)  , personalAccount , owner , sectorAdres);

            homeAddressRepository.save(homeAddress);


            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean changeAddressOi(String Ownerid){

        try {
            homeAddressRepository.updateOwnerIdById(AddressID , Ownerid);
            return true;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean changeAddressCR(Integer count){

        try {
            homeAddressRepository.updateCountResidentsById(AddressID , count);
            return true;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    //----------------------------------------------------------------------------------------------------------------

    public List<FutureHome> getAllFAddress (){
        return futureHomeRepository.findAll();
    }

    public void deleteFAddress (Integer id){
        futureHomeRepository.deleteById(id);
    }

    public boolean createNewFAddress(String address , Integer sector , BigDecimal area ,  String personalAccount ) {
        try {

           FutureHome futureHome = new FutureHome(0 , address , sector , area , personalAccount);

           futureHomeRepository.save(futureHome);

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    //-----------------------------------------------------------------------------------------------------------------

    public List<Sector> getAllSectors (){
        return sectorRepository.findAll();
    }

    public void deleteSector(Integer id){
        sectorRepository.deleteById(id);
    }

    public boolean createNewSector(Integer valSector){
        try{
            Sector sector = new Sector(0 , valSector);
            sectorRepository.save(sector);

            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}
