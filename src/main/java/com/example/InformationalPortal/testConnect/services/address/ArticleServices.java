package com.example.InformationalPortal.testConnect.services.address;

import com.example.InformationalPortal.testConnect.models.address.Article;
import com.example.InformationalPortal.testConnect.repositories.address.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServices {

    private final ArticleRepository articleRepository;

    public ArticleServices(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> articles() {
        return articleRepository.findAll();
    }

    public void deleteArticle (int id){
        Long idLong = (long) id;
        articleRepository.deleteById(idLong);
    }
}
