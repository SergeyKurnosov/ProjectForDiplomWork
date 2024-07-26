package com.example.InformationalPortal.testConnect.repositories.humans;

import com.example.InformationalPortal.testConnect.models.humans.HomeUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface HomeUserRepository extends CrudRepository<HomeUser, Long> {


    // Метод для поиска пользователя по логину с использованием Optional
    Optional<HomeUser> findByLogin(String login);

    @Transactional
    void deleteById (Integer id);

    // Метод для обновления времени последнего входа
    @Transactional
    @Modifying
    @Query("UPDATE HomeUser u SET u.lastLogin = ?2 WHERE u.login = ?1")
    int updateLastLogin(String login, String lastLogin);



    @Transactional
    @Modifying
    @Query("DELETE FROM HomeUser h WHERE h.ownerId = :ownerId")
    void deleteByOwnerId( String ownerId);

}
