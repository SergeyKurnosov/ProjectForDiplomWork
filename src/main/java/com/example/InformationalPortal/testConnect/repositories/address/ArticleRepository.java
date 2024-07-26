package com.example.InformationalPortal.testConnect.repositories.address;

import com.example.InformationalPortal.testConnect.models.address.Article;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Transactional
    void deleteByTitle(String title);

    @Transactional
    void deleteById(Long id);
}
