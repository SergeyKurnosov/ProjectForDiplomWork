package com.example.InformationalPortal.testConnect.services.human;

import com.example.InformationalPortal.testConnect.configs.SimpleEncryption;
import com.example.InformationalPortal.testConnect.models.address.Article;
import com.example.InformationalPortal.testConnect.models.humans.CompanyAdmin;
import com.example.InformationalPortal.testConnect.models.keys.KeysForAdmin;
import com.example.InformationalPortal.testConnect.models.keys.ValueNameRole;
import com.example.InformationalPortal.testConnect.repositories.address.ArticleRepository;
import com.example.InformationalPortal.testConnect.repositories.humans.CompanyAdminRepository;
import com.example.InformationalPortal.testConnect.repositories.keys.KeysForAdminRepository;
import com.example.InformationalPortal.testConnect.repositories.keys.ValueNameRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class AdminServices {

    private final CompanyAdminRepository companyAdminRepository;
    private final ArticleRepository articleRepository;
    private final KeysForAdminRepository keysForAdminRepository;
    private final ValueNameRoleRepository valueNameRoleRepository;


    private CompanyAdmin companyAdmin;


    public AdminServices(CompanyAdminRepository companyAdminRepository, ArticleRepository articleRepository, KeysForAdminRepository keysForAdminRepository, ValueNameRoleRepository valueNameRoleRepository) {
        this.companyAdminRepository = companyAdminRepository;
        this.articleRepository = articleRepository;
        this.keysForAdminRepository = keysForAdminRepository;
        this.valueNameRoleRepository = valueNameRoleRepository;
    }

    public boolean LoginOk(String login,
                           String password) {
        try {
            CompanyAdmin admin = companyAdminRepository.findByLogin(login).get();

            String pass = SimpleEncryption.decrypt(admin.getPassword());

            if (pass.equals(password)) {
                this.companyAdmin = admin;

                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedDate = today.format(formatter);

                companyAdminRepository.updateLastLogin(companyAdmin.getLogin() , formattedDate);

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
        model.addAttribute("login" , companyAdmin.getLogin());
    }


    public boolean createNewArticle(String title,
                           String content) {
        try {
            Article article = new Article(title , content);
            article.setCreationDate( new Date());
            articleRepository.save(article);
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean registrOk(String login, String password , String key) {
        try {
            String pass = SimpleEncryption.encrypt(password);

            KeysForAdmin keysForAdmin = keysForAdminRepository.findByValueKey(key).get();
            ValueNameRole valueNameRole = valueNameRoleRepository.findByNameRole("adm").get();
            CompanyAdmin companyAdmin1 = new CompanyAdmin(0 , login , pass , "0" , "adm" , key ,  keysForAdmin , valueNameRole );
            companyAdminRepository.save(companyAdmin1);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
