package com.example.InformationalPortal.testConnect.models.bank;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bank_balance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    @Column(name = "card_balance")
    private String cardBalance;
}
