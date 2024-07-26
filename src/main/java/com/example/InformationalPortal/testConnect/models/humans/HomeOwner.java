package com.example.InformationalPortal.testConnect.models.humans;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "home_owner")
public class HomeOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "surname", nullable = false, length = 100)
    private String surname;

    @Column(name = "date_birth", nullable = false)
    private Date dateBirth;

    @Column(name = "passport_details", nullable = false, unique = true, length = 20)
    private String passportDetails;

    @Column(name = "owner_id", nullable = false, unique = true, length = 10)
    private String ownerId;

}
