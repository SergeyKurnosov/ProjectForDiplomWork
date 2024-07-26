package com.example.InformationalPortal.testConnect.models.bank;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bank_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bank_name", nullable = false, length = 100)
    private String bankName;

    @Column(name = "number", nullable = false, unique = true, length = 16)
    private String cardNumber;

    @Column(name = "payment_system", nullable = false, length = 50)
    private String paymentSystem;

    @Column(name = "card_holder_name", nullable = false, length = 100)
    private String cardHolderName;

    @Column(name = "expiry_date", nullable = false, length = 10)
    private String expiryDate;

    @Column(name = "cvv", nullable = false, length = 3)
    private String cvv;
}
