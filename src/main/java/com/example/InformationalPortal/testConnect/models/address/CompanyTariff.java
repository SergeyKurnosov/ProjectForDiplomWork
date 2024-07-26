package com.example.InformationalPortal.testConnect.models.address;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;

@Entity
@Table(name = "company_tariff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyTariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "per_kilowatt_per_hour", nullable = false, unique = true)
    private BigDecimal perKilowattPerHour;
}
