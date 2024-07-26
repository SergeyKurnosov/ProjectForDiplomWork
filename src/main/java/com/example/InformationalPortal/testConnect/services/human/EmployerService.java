package com.example.InformationalPortal.testConnect.services.human;

import com.example.InformationalPortal.testConnect.configs.SimpleEncryption;
import com.example.InformationalPortal.testConnect.models.address.CompanyTariff;
import com.example.InformationalPortal.testConnect.models.address.HomeAddress;
import com.example.InformationalPortal.testConnect.models.address.Sector;
import com.example.InformationalPortal.testConnect.models.archive.ArchiveAccrual;
import com.example.InformationalPortal.testConnect.models.humans.CompanyEmployer;
import com.example.InformationalPortal.testConnect.models.keys.KeysForEmployee;
import com.example.InformationalPortal.testConnect.models.keys.ValueNameRole;
import com.example.InformationalPortal.testConnect.repositories.address.CompanyTariffRepository;
import com.example.InformationalPortal.testConnect.repositories.address.HomeAddressRepository;
import com.example.InformationalPortal.testConnect.repositories.address.SectorRepository;
import com.example.InformationalPortal.testConnect.repositories.archive.ArchiveAccrualsRepository;
import com.example.InformationalPortal.testConnect.repositories.humans.CompanyEmployerRepository;
import com.example.InformationalPortal.testConnect.repositories.keys.KeysForEmployeeRepository;
import com.example.InformationalPortal.testConnect.repositories.keys.ValueNameRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class EmployerService {

    private final SectorRepository sectorRepository;
    private final CompanyEmployerRepository companyEmployerRepository;
    private final KeysForEmployeeRepository keysForEmployeeRepository;
    private final ValueNameRoleRepository valueNameRoleRepository;
    private final HomeAddressRepository homeAddressRepository;
    private final CompanyTariffRepository companyTariffRepository;
    private final ArchiveAccrualsRepository archiveAccrualsRepository;

    private CompanyEmployer companyEmployer;
    private Sector sectorE;
    private List<HomeAddress> homeAddresses;
    private Integer empId;

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public EmployerService(SectorRepository sectorRepository, CompanyEmployerRepository companyEmployerRepository, KeysForEmployeeRepository keysForEmployeeRepository, ValueNameRoleRepository valueNameRoleRepository, HomeAddressRepository homeAddressRepository, CompanyTariffRepository companyTariffRepository, ArchiveAccrualsRepository archiveAccrualsRepository) {
        this.sectorRepository = sectorRepository;
        this.companyEmployerRepository = companyEmployerRepository;
        this.keysForEmployeeRepository = keysForEmployeeRepository;
        this.valueNameRoleRepository = valueNameRoleRepository;
        this.homeAddressRepository = homeAddressRepository;
        this.companyTariffRepository = companyTariffRepository;
        this.archiveAccrualsRepository = archiveAccrualsRepository;
    }


    public List<CompanyEmployer> getEmployers(String sector) {
        return (List<CompanyEmployer>) companyEmployerRepository.findByEmployerSector(Integer.parseInt(sector));
    }

    public boolean LoginOk(String login,
                           String password,
                           Integer sector) {
        try {
            CompanyEmployer employer = companyEmployerRepository.findByLogin(login).get();
            this.sectorE = sectorRepository.findBySector(sector).get();
            this.homeAddresses = (List<HomeAddress>) homeAddressRepository.findBySector(sectorE);

            String pass = SimpleEncryption.decrypt(employer.getPassword());

            if (employer != null && pass.equals(password) && employer.getSector().getSector() == sector) {
                this.companyEmployer = employer;

                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedDate = today.format(formatter);

                companyEmployerRepository.updateLastLogin(companyEmployer.getLogin(), formattedDate);

                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void getInfo(Model model) {
        model.addAttribute("login", companyEmployer.getLogin());
        model.addAttribute("sector", companyEmployer.getSector().getSector());
    }

    public void getAddress(Model model) {
        List<HomeAddress> list = (List<HomeAddress>) homeAddressRepository.findBySector(sectorE);
        model.addAttribute("homeAddresses", list);
    }

    public boolean createInfoForReceipt(String name, String accountNumber, String count) {
        Date formattedDate = null;
        try {
            Date today = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
            String formattedDateString = formatter.format(today);
            formattedDate = formatter.parse(formattedDateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        CompanyTariff price = companyTariffRepository.findFirstRow();
        double priceDouble = price.getPerKilowattPerHour().doubleValue();
        BigDecimal sum = new BigDecimal(String.valueOf(priceDouble * Integer.parseInt(count)));
        HomeAddress homeAddress = homeAddressRepository.findByPersonalAccount(accountNumber).get();
        try {
            ArchiveAccrual accrualReceiptNew = new ArchiveAccrual(0, formattedDate, name, Integer.parseInt(count), sum);
            archiveAccrualsRepository.save(accrualReceiptNew);
            BigDecimal result = homeAddress.getAreasPayment().add(sum);
            homeAddressRepository.updateAreasPaymentById(homeAddress.getId(), result);
            homeAddressRepository.updateToPayById(homeAddress.getId(), result);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;

    }


    public void deleteEmployer(Integer id) {
        companyEmployerRepository.deleteById(id);
    }


    public boolean createNewEmployer(String login, String password, String key, Integer sector) {
        try {

            String pass = SimpleEncryption.encrypt(password);

            KeysForEmployee keysForEmployee = keysForEmployeeRepository.findByValueKey(key).get();
            ValueNameRole valueNameRole = valueNameRoleRepository.findByNameRole("emp").get();
            Sector sectorEmployer = sectorRepository.findBySector(sector).get();
            CompanyEmployer companyEmployer = new CompanyEmployer(0, login, pass, "0", "emp", key, sector, keysForEmployee, valueNameRole, sectorEmployer);
            companyEmployerRepository.save(companyEmployer);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean changeEmployer(Integer sector) {

        try {
            companyEmployerRepository.updateEmployerSectorById(empId, sector);
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}