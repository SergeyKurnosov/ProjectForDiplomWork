package com.example.InformationalPortal.testConnect.controller.admin;

import com.example.InformationalPortal.testConnect.models.address.*;
import com.example.InformationalPortal.testConnect.models.archive.ArchiveAccrual;
import com.example.InformationalPortal.testConnect.models.archive.ArchivePayment;
import com.example.InformationalPortal.testConnect.models.humans.CompanyEmployer;
import com.example.InformationalPortal.testConnect.models.humans.HomeOwner;
import com.example.InformationalPortal.testConnect.models.humans.HomeUser;
import com.example.InformationalPortal.testConnect.models.keys.KeysForAdmin;
import com.example.InformationalPortal.testConnect.models.keys.KeysForEmployee;
import com.example.InformationalPortal.testConnect.services.address.AddressSrvices;
import com.example.InformationalPortal.testConnect.services.address.ArticleServices;
import com.example.InformationalPortal.testConnect.services.address.CompanyTariffService;
import com.example.InformationalPortal.testConnect.services.address.ContactService;
import com.example.InformationalPortal.testConnect.services.archive.ArchiveServices;
import com.example.InformationalPortal.testConnect.services.human.AdminServices;
import com.example.InformationalPortal.testConnect.services.human.ClientServices;
import com.example.InformationalPortal.testConnect.services.human.EmployerService;
import com.example.InformationalPortal.testConnect.services.keys.KeysServises;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class AdminController {
    private final AdminServices adminServices;
    private final ArticleServices articleServices;
    private final EmployerService employerService;
    private final AddressSrvices addressSrvices;
    private final ClientServices clientServices;
    private final ArchiveServices archiveServices;
    private final CompanyTariffService companyTariffService;
    private final KeysServises keysServises;
    private final ContactService contactService;

    public AdminController(AdminServices adminServices, ArticleServices articleServices, EmployerService employerService, AddressSrvices addressSrvices, ClientServices clientServices, ArchiveServices archiveServices, CompanyTariffService companyTariffService, KeysServises keysServises, ContactService contactService) {
        this.adminServices = adminServices;
        this.articleServices = articleServices;
        this.employerService = employerService;
        this.addressSrvices = addressSrvices;
        this.clientServices = clientServices;
        this.archiveServices = archiveServices;
        this.companyTariffService = companyTariffService;
        this.keysServises = keysServises;
        this.contactService = contactService;
    }
    //==================================================================================================================
    @GetMapping("/authorizFormA")
    public String formA() {
        return "registration_authorization/authorizAdmin";
    }
    //==================================================================================================================
    @GetMapping("/registrFormA")
    public String formRA() {
        return "registration_authorization/registrAdmin";
    }

    @PostMapping("/registerA")
    public ModelAndView registerAdmin(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("employeeKey") String key) {

        if (adminServices.registrOk(login , password , key)){
            return new ModelAndView("redirect:/authorizFormA");
        }else {
            return new ModelAndView("mains_pages/failing");
        }
    }

    @GetMapping("/panelA")
    public String panelA() {
        return "privatyPage/forAdmin/AdminPanel";
    }

    @PostMapping("/loginA")
    public String loginAdmin(
            @RequestParam("login") String login,
            @RequestParam("password") String password) {
        if (adminServices.LoginOk(login, password)) {
            return "redirect:/myPageA";
        } else {
            return "mains_pages/failing";
        }
    }

    @GetMapping("/myPageA")
    public String loginOkA(Model model) {
        adminServices.getInfo(model);
        return "privatyPage/forAdmin/mainPageA";
    }

    @GetMapping("/all-articless")
    public String lookArticless(Model model) {
        List<Article> list = articleServices.articles();
        model.addAttribute("articless", list);
        return "privatyPage/forAdmin/ArticlessA";
    }

    @GetMapping("/new-article")
    public String formnewArticleA() {
        return "privatyPage/forAdmin/formNewAricle";
    }

    @PostMapping("/createArticle")
    public String createArticle(
            @RequestParam("title") String title,
            @RequestParam("content") String content) {
        if (adminServices.createNewArticle(title, content)) {
            return "redirect:/all-articless";
        } else {
            return "mains_pages/failing";
        }
    }

    @PostMapping("/delete-article")
    public String deleteArticle(@RequestParam("articleId") int articleId) {
        articleServices.deleteArticle(articleId);
        return "redirect:/all-articless";
    }

    //-------------------------------------------

    @PostMapping("/delete-employer")
    public String deleteEmployer(@RequestParam("employerId") int employerId) {
        employerService.deleteEmployer(employerId);
        return "redirect:/all-employers";
    }

    @GetMapping("/get-employers")
    public String formEmployersA() {
        return "privatyPage/forAdmin/formForEmployerA";
    }

    @PostMapping("/all-employers")
    public String lookEmployers(
            @RequestParam("sec") String sector ,
            Model model) {
        List<CompanyEmployer> list = employerService.getEmployers(sector);
        model.addAttribute("employers", list);
        return "privatyPage/forAdmin/EmployersA";
    }

    @PostMapping("/update-employer")  // update-employer
    public String formUpdateEmployerA(@RequestParam("employerId") int employerId) {  // employerId
        employerService.setEmpId(employerId);
        return "privatyPage/forAdmin/formUpdateEmployer";
    }

    @PostMapping("/changeEmployer")
    public String changeEmployer(
            @RequestParam("sector") Integer sector) {
        if (employerService.changeEmployer(sector)) {
            return "privatyPage/forAdmin/ChangeOk";
        } else {
            return "mains_pages/failing";
        }
    }

    @GetMapping("/new-employer")
    public String formnewEmployerA() {
        return "registration_authorization/registrEmployer";
    }

    @PostMapping("/createEmployer")
    public String createEmployer(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("employeeKey") String key,
            @RequestParam("employeeSector") Integer sector) {
        if (employerService.createNewEmployer(login, password, key, sector)) {
            return "privatyPage/forAdmin/CreateEmployerOk";
        } else {
            return "mains_pages/failing";
        }
    }

    //---------------------------------------------

    @GetMapping("/get-address")
    public String formAddressA() {
        return "privatyPage/forAdmin/formForAddressA";
    }

    @PostMapping("/all-address")
    public String lookAddress(
            @RequestParam("sec") String sector ,
            Model model) {
        List<HomeAddress> list = addressSrvices.getAddress(sector);
        model.addAttribute("address", list);
        return "privatyPage/forAdmin/AddressA";
    }

    @PostMapping("/delete-address")
    public String deleteAddress(@RequestParam("addressId") int addressId) {
        addressSrvices.deleteAddress(addressId);
        return "redirect:/all-address";
    }

    @GetMapping("/new-address")
    public String formnewAddressA() {
        return "privatyPage/forAdmin/formNewAddress";
    }

    @PostMapping("/createAddress")
    public String createAddress(
            @RequestParam("homeAddress") String address,
            @RequestParam("homeSector") Integer sector,
            @RequestParam("homeArea") BigDecimal area,
            @RequestParam("countResidents") Integer countResidents,
            @RequestParam("personalAccount") String personalAccount,
            @RequestParam("ownerId") String ownerId) {
        if (addressSrvices.createNewAddress(address, sector, area, countResidents, personalAccount, ownerId)) {
            return "privatyPage/forAdmin/CreateAddressOk";
        } else {
            return "mains_pages/failing";
        }
    }

    @PostMapping("/update-addressIDOwner")  //
    public String formUpdateAddressIOA(@RequestParam("addressId") int addressId) {  //
        addressSrvices.setAddressID(addressId);
        return "privatyPage/forAdmin/formUpdateAddressOwnerId";
    }

    @PostMapping("/update-AddressCountResidents")
    public String formUpdateAddressCRA(@RequestParam("addressId") int addressId) {  //
        addressSrvices.setAddressID(addressId);
        return "privatyPage/forAdmin/formUpdateAddressCountResidents";
    }

    @PostMapping("/changeAddressOI")
    public String changeAddressOI(
            @RequestParam("id") String OwnerId) {
        if (addressSrvices.changeAddressOi(OwnerId)) {
            return "privatyPage/forAdmin/ChangeOk";
        } else {
            return "mains_pages/failing";
        }
    }

    @PostMapping("/changeAddressCR")
    public String changeAddressCR(
            @RequestParam("count") Integer count) {
        if (addressSrvices.changeAddressCR(count)) {
            return "privatyPage/forAdmin/ChangeOk";
        } else {
            return "mains_pages/failing";
        }
    }

    //---------------------------------------------

    @GetMapping("/all-owners")
    public String lookOwners(Model model) {
        List<HomeOwner> list = clientServices.getAllOwners();
        model.addAttribute("owners", list);
        return "privatyPage/forAdmin/OwnersA";
    }


    @PostMapping("/delete-owner")
    public String deleteOwner(@RequestParam("Id") int Id) {
        clientServices.deleteOwner(Id);
        return "redirect:/all-owners";
    }

    @PostMapping("/get-owner")
    public String lookOwner(@RequestParam("id") String Id ,
                            Model model) {
        List<HomeOwner> list = clientServices.getOneOwner(Id);
        model.addAttribute("owners", list);
        return "privatyPage/forAdmin/OwnerA";
    }

    @GetMapping("/new-owner")
    public String formnewOwnerA() {
        return "privatyPage/forAdmin/formNewOwner";
    }

    @PostMapping("/createOwner")
    public String createOwner(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("date_birth") String birth,
            @RequestParam("passport_details") String pasport,
            @RequestParam("owner_id") String ownerId) {
        if (clientServices.createNewOwner(name, surname, birth, pasport, ownerId)) {
            return "redirect:/all-owners";
        } else {
            return "mains_pages/failing";
        }
    }

    @PostMapping("/update-Owner")  //
    public String formUpdateOwnerA(@RequestParam("Id") Integer Id) {  //
        clientServices.setIdOwner(Id);
        return "privatyPage/forAdmin/formUpdateOwner";

    }

    @PostMapping("/changeOwner")
    public String changeOwner(
            @RequestParam("surname") String newSurname) {
        if (clientServices.changeOwner(newSurname)) {
            return "redirect:/all-owners";
        } else {
            return "mains_pages/failing";
        }
    }
    //-----------------------------------------------------------------------------------------------

    @GetMapping("/all-users")
    public String lookUsers(Model model) {
        List<HomeUser> list = clientServices.getAllUsers();
        model.addAttribute("users", list);
        return "privatyPage/forAdmin/UsersA";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam("userId") int Id) {
        clientServices.deleteUser(Id);
        return "redirect:/all-users";
    }
    //-------------------------------------------------------------------------------------------------

    @GetMapping("/all-futureAddress")
    public String lookFAddress(Model model) {
        List<FutureHome> list = addressSrvices.getAllFAddress();
        model.addAttribute("address", list);
        return "privatyPage/forAdmin/FutureAddressA";
    }

    @PostMapping("/delete-Faddress")
    public String deleteFAddress(@RequestParam("addressId") int addressId) {
        addressSrvices.deleteFAddress(addressId);
        return "redirect:/all-futureAddress";
    }

    @GetMapping("/new-Faddress")
    public String formnewFAddressA() {
        return "privatyPage/forAdmin/formNewFAddress";
    }

    @PostMapping("/createFAddress")
    public String createAddress(
            @RequestParam("homeAddress") String address,
            @RequestParam("homeSector") Integer sector,
            @RequestParam("homeArea") BigDecimal area,
            @RequestParam("personalAccount") String personalAccount) {
        if (addressSrvices.createNewFAddress(address, sector, area, personalAccount)) {
            return "redirect:/all-futureAddress";
        } else {
            return "mains_pages/failing";
        }
    }

    //-------------------------------------------------------------------------------------------------------------------
    @GetMapping("/all-accrualsForm")
    public String formForAccrualsA() {
        return "privatyPage/forAdmin/formForAccrualsA";
    }

    @PostMapping("/all-accruals")
    public String lookAccruals(
            @RequestParam("id") String ownerId, Model model) {
        archiveServices.setOwnerId(ownerId);
        List<ArchiveAccrual> list = archiveServices.getAllAccruals(ownerId);
        model.addAttribute("accruals", list);
        return "privatyPage/forAdmin/AccrualsA";
    }

    @GetMapping("/all-accruals2")
    public String lookAccruals2(Model model) {
        List<ArchiveAccrual> list = archiveServices.getAllAccruals(archiveServices.getOwnerId());
        model.addAttribute("accruals", list);
        return "privatyPage/forAdmin/AccrualsA";
    }


    @PostMapping("/delete-accrual")
    public String deleteAccrual(@RequestParam("accrualId") int Id) {
        archiveServices.deleteAccrual(Id);
        return "redirect:/all-accruals2";
    }


    //-------------------------------------------------------------------------------------------------------------------

    @GetMapping("/all-paymentForm")
    public String formForPaymentA() {
        return "privatyPage/forAdmin/formForPaymentsA";
    }

    @PostMapping("/all-payment")
    public String lookPayments(
            @RequestParam("id") String ownerId, Model model) {
        archiveServices.setOwnerId(ownerId);
        List<ArchivePayment> list = archiveServices.getAllPayments(ownerId);
        model.addAttribute("payments", list);
        return "privatyPage/forAdmin/PaymentsA";
    }

    @GetMapping("/all-payment2")
    public String lookPayments2(Model model) {
        List<ArchivePayment> list = archiveServices.getAllPayments(archiveServices.getOwnerId());
        model.addAttribute("payments", list);
        return "privatyPage/forAdmin/PaymentsA";
    }

    @PostMapping("/delete-payment")
    public String deletePayment(@RequestParam("paymentId") int Id) {
        archiveServices.deletePayment(Id);
        return "redirect:/all-payment2";
    }

    //-------------------------------------------------------------------------------------------------------------------

    @GetMapping("/all-tariffs")
    public String lookTariffs(Model model) {
        List<CompanyTariff> list = companyTariffService.getAllTariffs();
        model.addAttribute("tariffs", list);
        return "privatyPage/forAdmin/TariffsA";
    }

    @PostMapping("/delete-tariff")
    public String deleteTariff(@RequestParam("tariffId") int tariffId) {
        companyTariffService.deleteTariffs(tariffId);
        return "redirect:/all-tariffs";
    }

    @GetMapping("/new-tariff")
    public String formnewFTariffA() {
        return "privatyPage/forAdmin/formNewTariff";
    }

    @PostMapping("/createTariff")
    public String createTariff(
            @RequestParam("title") BigDecimal price) {
        if (companyTariffService.createNewTarriff(price)) {
            return "redirect:/all-tariffs";
        } else {
            return "mains_pages/failing";
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    @GetMapping("/all-sectors")
    public String lookSectors(Model model) {
        List<Sector> list = addressSrvices.getAllSectors();
        model.addAttribute("sectors", list);
        return "privatyPage/forAdmin/SectorsA";
    }

    @PostMapping("/delete-sector")
    public String deleteSector(@RequestParam("sectorId") int sectorId) {
        addressSrvices.deleteSector(sectorId);
        return "redirect:/all-sectors";
    }

    @GetMapping("/new-sector")
    public String formnewFSectorA() {
        return "privatyPage/forAdmin/formNewSector";
    }

    @PostMapping("/createSector")
    public String createSector(
            @RequestParam("title") Integer value) {
        if (addressSrvices.createNewSector(value)) {
            return "redirect:/all-sectors";
        } else {
            return "mains_pages/failing";
        }
    }
//---------------------------------------------------------------------------------------------------------------------

    @GetMapping("/all-keysA")
    public String lookKeysA(Model model) {
        List<KeysForAdmin> list = keysServises.getAllKeysA();
        model.addAttribute("keys", list);
        return "privatyPage/forAdmin/KeysAA";
    }

    @PostMapping("/delete-keyA")
    public String deleteKeyA(@RequestParam("keyId") int keyId) {
        keysServises.deleteKeyA(keyId);
        return "redirect:/all-keysA";
    }

    @GetMapping("/new-keysA")
    public String formnewFKeyA() {
        return "privatyPage/forAdmin/formNewKeyA";
    }

    @PostMapping("/createKeyA")
    public String createKeyA(
            @RequestParam("title") String value) {
        if (keysServises.crateNewKeyA(value)) {
            return "redirect:/all-keysA";
        } else {
            return "mains_pages/failing";
        }
    }
    //-----------------------------------------------------------------------------------------------------------------


    @GetMapping("/all-keysE")
    public String lookKeysE(Model model) {
        List<KeysForEmployee> list = keysServises.getAllKeysE();
        model.addAttribute("keys", list);
        return "privatyPage/forAdmin/KeysEA";
    }

    @PostMapping("/delete-keyE")
    public String deleteKeyE(@RequestParam("keyId") int keyId) {
        keysServises.deleteKeyE(keyId);
        return "redirect:/all-keysE";
    }

    @GetMapping("/new-keysE")
    public String formnewFKeyE() {
        return "privatyPage/forAdmin/formNewKeyE";
    }

    @PostMapping("/createKeyE")
    public String createKeyE(
            @RequestParam("title") String value) {
        if (keysServises.crateNewKeyE(value)) {
            return "redirect:/all-keysE";
        } else {
            return "mains_pages/failing";
        }
    }

    //--------------------------------------------------------------------------------------------------------------

    @GetMapping("/all-contacts")
    public String lookContacts(Model model) {
        List<CompanyContact> list = contactService.findAllContacts();
        model.addAttribute("contacts", list);
        return "privatyPage/forAdmin/ContactsA";
    }

    @PostMapping("/delete-contact")
    public String deleteContact(@RequestParam("contactId") int contactId) {
        contactService.deleteContact(contactId);
        return "redirect:/all-contacts";
    }

    @GetMapping("/new-contact")
    public String formnewContact() {
        return "privatyPage/forAdmin/formNewContact";
    }

    @PostMapping("/createContact")
    public String createContact(
            @RequestParam("name") String name,
            @RequestParam("tel") String tel,
            @RequestParam("address") String address
    ) {
        if (contactService.createContact(name, tel, address)) {
            return "redirect:/all-contacts";
        } else {
            return "mains_pages/failing";
        }
    }
}
