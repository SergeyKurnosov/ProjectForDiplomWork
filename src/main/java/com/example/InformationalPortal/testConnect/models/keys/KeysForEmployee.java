package com.example.InformationalPortal.testConnect.models.keys;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "keys_for_employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeysForEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name_role", nullable = false, length = 10)
    private String nameRole;

    @Column(name = "value_key", nullable = false, unique = true, length = 30)
    private String valueKey;

}
