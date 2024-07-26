package com.example.InformationalPortal.testConnect.controller.user;

import com.example.InformationalPortal.testConnect.services.bank.BankService;
import com.example.InformationalPortal.testConnect.services.human.ClientServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserController {
    private final ClientServices clientServices;
    private final BankService bankService;

    public UserController(ClientServices clientServices, BankService bankService) {
        this.clientServices = clientServices;
        this.bankService = bankService;
    }

    @GetMapping("/registrFormU")
    public String formR() {
        return "registration_authorization/registrUser";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("address") String address,
            @RequestParam("name") String ownerName,
            @RequestParam("surname") String ownerSurname,
            @RequestParam("account") String accountNumber,
            @RequestParam("passport") String passportData) {

        if (clientServices.RegistrOk(login , password , address , ownerName , ownerSurname , accountNumber , passportData)){
            return new ModelAndView("mains_pages/RegistrOk");
        }else {
            return new ModelAndView("mains_pages/failing");
        }
    }

    @GetMapping("/authorizFormU")
    public String formU() {
        return "registration_authorization/authorizUser";
    }

    @PostMapping("/loginU")
    public String loginUser(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("accountNumber") String address) {
        if (clientServices.LoginOk(login , password , address)){
            return "redirect:/myPageU";
        }else {
            return"mains_pages/failing";
        }
    }
    @GetMapping("/myPageU")
    public String loginOkU(Model model) {
        clientServices.getInfo(model);
        return "privatyPage/forUser/mainPageU";
    }


    @GetMapping("/myAccrualsU")
    public String accrualsU(Model model) {
        clientServices.getInfoAccrualsPayments(model);
        return "privatyPage/forUser/MyAccrualsPayments";
    }

    @GetMapping("/myAccrualsUDesc")
    public String accrualsUDesc(Model model) {
        clientServices.getInfoAccrualsPaymentsDESC(model);
        return "privatyPage/forUser/MyAccrualsPayments";
    }

    @GetMapping("/myReceiptU")
    public String receiptU(Model model) {
        if (clientServices.createReceipt(model)){
            return "privatyPage/forUser/Receipt";
        }else {
            return "privatyPage/forUser/ReceiptNotFound";
        }
    }

    @GetMapping("/formToPay")
    public String formToPay() {
        return "privatyPage/forUser/formForPay";
    }

    @PostMapping("/toPay")
    public String loginCard(
            @RequestParam("login") String login,//
            @RequestParam("password") String password,
            @RequestParam("cvv") String cvv) {
        if (bankService.loginOk(login, password, cvv)){
            if (clientServices.pay()){
                return "privatyPage/forUser/PayOk";
            }else {
                return"mains_pages/failing";
            }
        }else {
            return"mains_pages/failing";
        }
    }

}
