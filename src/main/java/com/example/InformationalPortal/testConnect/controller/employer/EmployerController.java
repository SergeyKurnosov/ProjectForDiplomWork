package com.example.InformationalPortal.testConnect.controller.employer;

import com.example.InformationalPortal.testConnect.models.archive.ArchiveAccrual;
import com.example.InformationalPortal.testConnect.models.archive.ArchivePayment;
import com.example.InformationalPortal.testConnect.services.archive.ArchiveServices;
import com.example.InformationalPortal.testConnect.services.human.EmployerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmployerController {
    private final EmployerService employerService;
    private final ArchiveServices archiveServices;

    public EmployerController(EmployerService employerService, ArchiveServices archiveServices) {
        this.employerService = employerService;
        this.archiveServices = archiveServices;
    }


    @GetMapping("/authorizFormE")
    public String formE() {
        return "registration_authorization/authorizEmployer";
    }

    @PostMapping("/loginE")
    public String loginEmployer(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("sector") Integer sector) {
        if (employerService.LoginOk(login, password, sector)) {
            return "redirect:/myPageE";
        } else {
            return "mains_pages/failing";
        }
    }

    //-----------------------------------------------------------------------------------------------------------------

    @GetMapping("/myPageE")
    public String loginOkE(Model model) {
        employerService.getInfo(model);
        return "privatyPage/forEmployer/mainPageE";
    }

    //-----------------------------------------------------------------------------------------------------------------
    @GetMapping("/myAddress")
    public String myAddress(Model model) {
        employerService.getAddress(model);
        return "privatyPage/forEmployer/AddressMySectors";
    }
//--------------------------------------------------------------------------------------------------------------------

    @GetMapping("/formCreateReceipt")
    public String formCreateReceipt() {
        return "privatyPage/forEmployer/createReceipt";
    }


    @PostMapping("/createReceipt")
    public String createReceipt(
            @RequestParam("name") String name,
            @RequestParam("address") String accountNumber,
            @RequestParam("energy") String count) {
        if (employerService.createInfoForReceipt(name, accountNumber, count)) {
            return "privatyPage/forEmployer/ReceiptOk";
        } else {
            return "mains_pages/failing";
        }
    }
//--------------------------------------------------------------------------------------------------------------------
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    @GetMapping("/all-accrualsFormE")
    public String formForAccrualsE() {
        return "privatyPage/forEmployer/formForAccrualsE";
    }

    @PostMapping("/all-accrualsE")
    public String lookAccrualsE(
            @RequestParam("id") String ownerId, Model model) {
        archiveServices.setOwnerId(ownerId);
        List<ArchiveAccrual> list = archiveServices.getAllAccrualsNew_Old(ownerId);
        model.addAttribute("accruals", list);
        return "privatyPage/forEmployer/AccrualsE";
    }

    @GetMapping("/all-accrualsE_Desc")
    public String lookAccrualsE_D( Model model) {
        List<ArchiveAccrual> list = archiveServices.getAllAccrualsOld_New(archiveServices.getOwnerId());
        model.addAttribute("accruals", list);
        return "privatyPage/forEmployer/AccrualsE";
    }


    //-------------------------------------------------------------------------------------------------------------------

    @GetMapping("/all-paymentFormE")
    public String formForPaymentE() {
        return "privatyPage/forEmployer/formForPaymentsE";
    }

    @PostMapping("/all-paymentE")
    public String lookPaymentsE(
            @RequestParam("id") String ownerId, Model model) {
        archiveServices.setOwnerId(ownerId);
        List<ArchivePayment> list = archiveServices.getAllPaymentsNew_Old(ownerId);
        model.addAttribute("payments", list);
        return "privatyPage/forEmployer/PaymentsE";
    }

    @GetMapping("/all-paymentsE_Desc")
    public String lookPaymentsE_D( Model model) {
        List<ArchivePayment> list = archiveServices.getAllPaymentsOld_New(archiveServices.getOwnerId());
        model.addAttribute("payments", list);
        return "privatyPage/forEmployer/PaymentsE";
    }


}
