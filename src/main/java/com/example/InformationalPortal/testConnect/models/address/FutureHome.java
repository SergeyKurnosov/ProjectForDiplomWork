package com.example.InformationalPortal.testConnect.models.address;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;

@Entity
@Table(name = "future_homes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FutureHome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "home_address", nullable = false, unique = true)
    private String homeAddress;

    @Column(name = "home_sector", nullable = false)
    private int homeSector;

    @Column(name = "home_area")
    private BigDecimal homeArea;

    @Column(name = "personal_account", nullable = false, unique = true)
    private String personalAccount;
}
