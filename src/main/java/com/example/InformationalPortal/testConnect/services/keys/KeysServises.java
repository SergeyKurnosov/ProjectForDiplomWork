package com.example.InformationalPortal.testConnect.services.keys;

import com.example.InformationalPortal.testConnect.models.keys.KeysForAdmin;
import com.example.InformationalPortal.testConnect.models.keys.KeysForEmployee;
import com.example.InformationalPortal.testConnect.repositories.keys.KeysForAdminRepository;
import com.example.InformationalPortal.testConnect.repositories.keys.KeysForEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeysServises {
    private final KeysForEmployeeRepository keysForEmployeeRepository;
    private final KeysForAdminRepository keysForAdminRepository;


    public KeysServises(KeysForEmployeeRepository keysForEmployeeRepository, KeysForAdminRepository keysForAdminRepository) {
        this.keysForEmployeeRepository = keysForEmployeeRepository;
        this.keysForAdminRepository = keysForAdminRepository;
    }

    //-------------------------------------------------------------------------
    public List<KeysForAdmin> getAllKeysA(){
        return keysForAdminRepository.findAll();
    }

    public void deleteKeyA(Integer id){
        keysForAdminRepository.deleteById(id);
    }

    public boolean crateNewKeyA(String value){

        try {
            KeysForAdmin keysForAdmin = new KeysForAdmin(0 , "adm" , value);
            keysForAdminRepository.save(keysForAdmin);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    //-------------------------------------------------------------------------

    public List<KeysForEmployee> getAllKeysE(){
        return keysForEmployeeRepository.findAll();
    }

    public void deleteKeyE(Integer id){
        keysForEmployeeRepository.deleteById(id);
    }

    public boolean crateNewKeyE(String value){

        try {
            KeysForEmployee keysForEmployee = new KeysForEmployee(0 , "emp" , value);
            keysForEmployeeRepository.save(keysForEmployee);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}
