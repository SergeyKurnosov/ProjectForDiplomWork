package com.example.InformationalPortal.testConnect.models.address;

import com.example.InformationalPortal.testConnect.models.humans.HomeOwner;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "home_address")
public class HomeAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "home_address", nullable = false, unique = true, length = 50)
    private String homeAddress;

    @Column(name = "home_sector", nullable = false)
    private Integer homeSector;

    @Column(name = "home_area", precision = 10, scale = 2)
    private BigDecimal homeArea;

    @Column(name = "count_residents", nullable = false)
    private Integer countResidents;

    @Column(name = "areas_payment", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) DEFAULT 0")
    private BigDecimal areasPayment;

    @Column(name = "to_pay", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) DEFAULT 0")
    private BigDecimal toPay;

    @Column(name = "personal_account", nullable = false, unique = true, length = 20)
    private String personalAccount;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "owner_id", nullable = false)
    private HomeOwner homeOwner;

    @ManyToOne
    @JoinColumn(name = "home_sector", referencedColumnName = "sector", nullable = false, insertable = false, updatable = false)
    private Sector sector;
}
