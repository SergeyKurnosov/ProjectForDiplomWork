package com.example.InformationalPortal.testConnect.models.humans;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "home_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String login;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "last_login", length = 30, columnDefinition = "VARCHAR(30) DEFAULT '0'")
    private String lastLogin;

    @Column(name = "owner_id" , insertable=false, updatable=false , nullable = false, unique = true, length = 10)
    private String ownerId;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "owner_id", foreignKey = @ForeignKey(name = "fk_home_user_owner_id"))
    private HomeOwner homeOwner;
}
