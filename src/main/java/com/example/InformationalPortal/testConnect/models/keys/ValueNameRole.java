package com.example.InformationalPortal.testConnect.models.keys;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "value_name_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValueNameRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name_role", nullable = false, length = 10)
    private String nameRole;


}
