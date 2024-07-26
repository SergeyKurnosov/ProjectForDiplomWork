package com.example.InformationalPortal.testConnect.controller.bank;

import com.example.InformationalPortal.testConnect.services.bank.BankService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class BankController {

    private final BankService bankService;


    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/authorizFormB")
    public String formAvtoriz() {
        return "miniBank/formLoginB";
    }

    @PostMapping("/loginB")
    public String loginCard(
            @RequestParam("login") String login,//
            @RequestParam("password") String password,
            @RequestParam("cvv") String cvv) {
        if (bankService.loginOk(login, password, cvv)){
            return "redirect:/myPageB";
        }else {
            return"mains_pages/failing";
        }
    }

    @GetMapping("/myPageB")
    public String myPageB(Model model) {
        bankService.getInfo(model);
        return "miniBank/mainPageB";
    }

    @GetMapping("/registrFormB")
    public String formRegistr() {
        return "miniBank/formRegistrCard_User";
    }

    @PostMapping("/registerB")
    public ModelAndView registerCard(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("tel") String tel,
            @RequestParam("nameBank") String nameBank,
            @RequestParam("numberCard") String numberCard,
            @RequestParam("nameSystem") String nameSystem,
            @RequestParam("NameHold") String NameHold,
            @RequestParam("dateCard") String dateCard,
            @RequestParam("cvv") String cvv) {

        if (bankService.registrOk(login , password , tel , nameBank , numberCard , nameSystem , NameHold , dateCard , cvv)) {
            return new ModelAndView("miniBank/RegistrCardOK");
        } else {
            return new ModelAndView("mains_pages/failing");
        }
    }

    //------------------------------------------------------------------------------------------------------------------


    @GetMapping("/topUpFormB")
    public String formTopUp() {
        return "miniBank/formForTopUp";
    }

    @PostMapping("/TopUp")
    public String topUpB(
            @RequestParam("amount") String amount) {
        if (bankService.topUp(amount)){
            return "privatyPage/forUser/PayOk";
        }else {
            return"mains_pages/failing";
        }
    }




}
