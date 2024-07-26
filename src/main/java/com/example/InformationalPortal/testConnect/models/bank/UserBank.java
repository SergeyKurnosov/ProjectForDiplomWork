package com.example.InformationalPortal.testConnect.models.bank;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_bank")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login",length = 100, unique = true, nullable = false)
    private String login;

    @Column(name = "password",length = 100, unique = true, nullable = false)
    private String password;

    @Column(name = "tel",length = 20, unique = true, nullable = false)
    private String tel;

    @Column(name = "card_number",length = 16, unique = true, nullable = false)
    private String cardNumber;
}
