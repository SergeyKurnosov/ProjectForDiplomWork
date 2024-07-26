package com.example.InformationalPortal.testConnect.models.archive;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "archive_payment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArchivePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_payment", nullable = false)
    private Date datePayment;

    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @Column(name = "price_payment", nullable = false)
    private BigDecimal pricePayment;
}
