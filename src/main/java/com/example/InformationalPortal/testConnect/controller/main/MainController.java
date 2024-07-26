package com.example.InformationalPortal.testConnect.controller.main;

import com.example.InformationalPortal.testConnect.models.address.Article;
import com.example.InformationalPortal.testConnect.models.address.CompanyContact;
import com.example.InformationalPortal.testConnect.services.address.ArticleServices;
import com.example.InformationalPortal.testConnect.services.address.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    private final ContactService contactService;
    private final ArticleServices articleServices;

    public MainController(ContactService contactService, ArticleServices articleServices) {
        this.contactService = contactService;
        this.articleServices = articleServices;
    }

    @GetMapping("/contacts")
    public String seeContact(Model model) {
        List<CompanyContact> list = contactService.findAllContacts();
        model.addAttribute("contacts", list);
        return "mains_pages/contact";
    }

    //==================================================================================================================
    @GetMapping("/main_site")
    public String lookAtResult() {
        return "mains_pages/main";
    }
    //==================================================================================================================

    @GetMapping("/services")
    public String lookAtResul() {
        return "mains_pages/services";
    }

    @GetMapping("/failing")
    public String errorResult() {
        return "mains_pages/failing";
    }

    @GetMapping("/news")
    public String lookNews(Model model) {
        List<Article> list = articleServices.articles();
        model.addAttribute("articless" , list);
        return "mains_pages/articless";
    }
}
