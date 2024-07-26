package com.example.InformationalPortal.testConnect.services.human;

import com.example.InformationalPortal.testConnect.configs.SimpleEncryption;
import com.example.InformationalPortal.testConnect.models.address.CompanyTariff;
import com.example.InformationalPortal.testConnect.models.address.HomeAddress;
import com.example.InformationalPortal.testConnect.models.archive.ArchiveAccrual;
import com.example.InformationalPortal.testConnect.models.archive.ArchivePayment;
import com.example.InformationalPortal.testConnect.models.humans.HomeOwner;
import com.example.InformationalPortal.testConnect.models.humans.HomeUser;
import com.example.InformationalPortal.testConnect.models.keys.ReceiptData;
import com.example.InformationalPortal.testConnect.repositories.address.CompanyTariffRepository;
import com.example.InformationalPortal.testConnect.repositories.archive.ArchiveAccrualsRepository;
import com.example.InformationalPortal.testConnect.repositories.archive.ArchivePaymentRepository;
import com.example.InformationalPortal.testConnect.repositories.humans.HomeOwnerRepository;
import com.example.InformationalPortal.testConnect.repositories.humans.HomeUserRepository;
import com.example.InformationalPortal.testConnect.services.address.AddressSrvices;
import com.example.InformationalPortal.testConnect.services.archive.ArchiveServices;
import com.example.InformationalPortal.testConnect.services.bank.BankService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClientServices {
    private final HomeOwnerRepository homeOwnerRepository;
    private final HomeUserRepository homeUserRepository;
    private final AddressSrvices addressSrvices;
    private final ArchiveAccrualsRepository archiveAccrualsRepository;
    private final ArchivePaymentRepository archivePaymentRepository;
    private final CompanyTariffRepository companyTariffRepository;
    private final ArchiveServices archiveServices;
    private final BankService bankService;

    private HomeAddress homeAddressH;
    private HomeUser homeUserU;
    private HomeOwner homeOwnerO;
    private String ownerId;
    private Integer idOwner;
    private SimpleEncryption encryption;
    private String personalAccount;


    public void setIdOwner(Integer idOwner) {
        this.idOwner = idOwner;
    }

    public ClientServices(HomeOwnerRepository homeOwnerRepository, HomeUserRepository homeUserRepository, AddressSrvices addressSrvices, ArchiveAccrualsRepository archiveAccrualsRepository, ArchivePaymentRepository archivePaymentRepository, CompanyTariffRepository companyTariffRepository, ArchiveServices archiveServices, BankService bankService) {
        this.homeOwnerRepository = homeOwnerRepository;
        this.homeUserRepository = homeUserRepository;
        this.addressSrvices = addressSrvices;
        this.archiveAccrualsRepository = archiveAccrualsRepository;
        this.archivePaymentRepository = archivePaymentRepository;
        this.companyTariffRepository = companyTariffRepository;
        this.archiveServices = archiveServices;
        this.bankService = bankService;
    }

    public HomeOwner getOwnerByPassport(String passport) {
        return homeOwnerRepository.findByPassportDetails(passport).get();
    }


    public void saveNewUser(HomeUser homeUser) {
        homeUserRepository.save(homeUser);
    }

    public boolean RegistrOk(String login,
                             String password,
                             String address,
                             String ownerName,
                             String ownerSurname,
                             String accountNumber,
                             String passportData) {

        try {
            HomeOwner homeOwner = getOwnerByPassport(passportData);
            if (homeOwner.getName().equals(ownerName) && homeOwner.getSurname().equals(ownerSurname)) {

                HomeAddress homeAddress = addressSrvices.getHomeAddressPersonalAccount(accountNumber);
                if (homeAddress.getHomeAddress().equals(address)) {

                    String pass = encryption.encrypt(password);

                    HomeUser homeUser = new HomeUser(0, login, pass, "0", homeOwner.getOwnerId(), homeOwner);
                    saveNewUser(homeUser);

                } else {
                    System.out.println("error");
                    return false;
                }

            } else {
                System.out.println("error");
                return false;
            }

            return true;
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }
        System.out.println("error");
        return false;
    }

    public boolean LoginOk(String login,
                           String password,
                           String accountNumber) {
        try {

            HomeUser homeUser = homeUserRepository.findByLogin(login).get();
            HomeAddress homeAddress = addressSrvices.getHomeAddressPersonalAccount(accountNumber);
            String pass = SimpleEncryption.decrypt(homeUser.getPassword());
            if (pass.equals(password) && homeAddress.getHomeOwner().getOwnerId().equals(homeUser.getOwnerId())) {
                HomeOwner homeOwner = homeOwnerRepository.findByOwnerId(homeAddress.getHomeOwner().getOwnerId()).get();
                this.homeAddressH = homeAddress;
                this.homeUserU = homeUser;
                this.homeOwnerO = homeOwner;

                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedDate = today.format(formatter);

                homeUserRepository.updateLastLogin(homeUserU.getLogin(), formattedDate);
                this.personalAccount = accountNumber;
                return true;


            } else {
                return false;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    public void getInfo(Model model) {
        HomeAddress address = addressSrvices.getHomeAddressPersonalAccount(personalAccount);
        model.addAttribute("name", homeOwnerO.getName());
        model.addAttribute("surname", homeOwnerO.getSurname());
        model.addAttribute("address", homeAddressH.getHomeAddress());
        model.addAttribute("sector", homeAddressH.getSector().getSector());
        model.addAttribute("area", homeAddressH.getHomeArea());
        model.addAttribute("countHumans", homeAddressH.getCountResidents());
        model.addAttribute("toPay", address.getToPay());

        this.ownerId = homeOwnerO.getOwnerId();


    }

    public void getInfoAccrualsPayments(Model model) {
        List<ArchiveAccrual> archiveAccruals = (List<ArchiveAccrual>) archiveServices.getAllAccrualsNew_Old(ownerId);
        model.addAttribute("accruals", archiveAccruals);

        List<ArchivePayment> archivePayments = (List<ArchivePayment>) archiveServices.getAllPaymentsNew_Old(ownerId);
        model.addAttribute("payments", archivePayments);
    }

    public void getInfoAccrualsPaymentsDESC(Model model) {
        List<ArchiveAccrual> archiveAccruals = (List<ArchiveAccrual>) archiveServices.getAllAccrualsOld_New(ownerId);
        model.addAttribute("accruals", archiveAccruals);

        List<ArchivePayment> archivePayments = (List<ArchivePayment>) archiveServices.getAllPaymentsOld_New(ownerId);
        model.addAttribute("payments", archivePayments);
    }

    public boolean createReceipt(Model model) {
        try {
            ArchiveAccrual accrual = archiveAccrualsRepository.findLatestByOwnerId(ownerId).get();
            CompanyTariff price = companyTariffRepository.findFirstRow();
            HomeAddress address = addressSrvices.getHomeAddressPersonalAccount(personalAccount);
            ReceiptData receiptData = new ReceiptData(ownerId, accrual.getDateAccruals(), accrual.getKilowatt(), price.getPerKilowattPerHour(), address.getAreasPayment(), homeAddressH.getPersonalAccount());

            model.addAttribute("receiptData", receiptData);
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
        return true;
    }

    public List<HomeOwner> getAllOwners() {
        return (List<HomeOwner>) homeOwnerRepository.findAll();
    }

    public void deleteOwner(Integer id) {
        HomeOwner owner = homeOwnerRepository.findById(id).get();
        homeUserRepository.deleteByOwnerId(owner.getOwnerId());
        archiveAccrualsRepository.deleteByOwnerId(owner.getOwnerId());
        archivePaymentRepository.deleteByOwnerId(owner.getOwnerId());
        homeOwnerRepository.deleteById(id);
    }

    public boolean createNewOwner(String name, String surname, String birth, String pasport, String idOwner) {
        try {

            String dateString = birth;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            try {
                date = formatter.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }

            HomeOwner homeOwner = new HomeOwner(0, name, surname, date, pasport, idOwner);
            homeOwnerRepository.save(homeOwner);

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean changeOwner(String surname) {

        try {
            homeOwnerRepository.updateSurnameById(idOwner, surname);
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<HomeUser> getAllUsers() {
        return (List<HomeUser>) homeUserRepository.findAll();
    }

    public void deleteUser(Integer id) {
        homeUserRepository.deleteById(id);
    }


    public boolean pay() {
        try {
            HomeAddress homeAddress = addressSrvices.getHomeAddressHomeOwner(homeOwnerO);

            BigDecimal amount = new BigDecimal(bankService.getBalance().getCardBalance()); // баланс карты
            BigDecimal forPay = homeAddress.getToPay();
            BigDecimal areasPay = homeAddress.getAreasPayment();
            BigDecimal payment = null;


            if (amount.compareTo(forPay) > 0 && amount.compareTo(areasPay) > 0) {
                payment = forPay;

                addressSrvices.setAreasPayHomeAddress(homeAddress.getId(), new BigDecimal(0));
                addressSrvices.setToPayHomeAddress(homeAddress.getId(), new BigDecimal(0));

                bankService.debit(String.valueOf(forPay));

            } else {
                payment = amount;

                addressSrvices.setAreasPayHomeAddress(homeAddress.getId(), areasPay.subtract(amount));
                addressSrvices.setToPayHomeAddress(homeAddress.getId(), forPay.subtract(amount));

                bankService.cardEmpty();

            }


            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            String formattedDate = today.format(formatter);
            LocalDateTime localDateTime = today.atStartOfDay();
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

            ArchivePayment archivePayment = new ArchivePayment(0, date, homeOwnerO.getOwnerId(), payment);
            archivePaymentRepository.save(archivePayment);

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<HomeOwner> getOneOwner(String ownerId){
        List<HomeOwner> list = new ArrayList<>();
        list.add(homeOwnerRepository.findByOwnerId(ownerId).get());
        return list;
    }

}
