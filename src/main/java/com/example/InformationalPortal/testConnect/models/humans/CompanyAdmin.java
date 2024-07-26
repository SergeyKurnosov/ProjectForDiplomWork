package com.example.InformationalPortal.testConnect.models.humans;

import com.example.InformationalPortal.testConnect.models.keys.KeysForAdmin;
import com.example.InformationalPortal.testConnect.models.keys.ValueNameRole;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "company_admin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login", nullable = false, unique = true, length = 50)
    private String login;

    @Column(name = "password", nullable = false, unique = true, length = 100)
    private String password;

    @Column(name = "last_login", length = 30)
    private String lastLogin;

    @Column(name = "company_role", nullable = false, length = 10)
    private String companyRole;

    @Column(name = "admin_key", nullable = false, length = 10)
    private String adminKey;

    @ManyToOne
    @JoinColumn(name = "admin_key", referencedColumnName = "value_key", insertable = false, updatable = false)
    private KeysForAdmin keysForAdmin;

    @ManyToOne
    @JoinColumn(name = "company_role", referencedColumnName = "name_role", insertable = false, updatable = false)
    private ValueNameRole valueNameRole;
}
