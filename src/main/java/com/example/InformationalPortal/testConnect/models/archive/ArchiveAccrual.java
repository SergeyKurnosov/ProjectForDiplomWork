package com.example.InformationalPortal.testConnect.models.archive;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "archive_accruals")
public class ArchiveAccrual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_accruals", nullable = false)
    private Date dateAccruals;

    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @Column(name = "kilowatt", nullable = false)
    private int kilowatt;

    @Column(name = "price_payment", nullable = false)
    private BigDecimal pricePayment;


}
