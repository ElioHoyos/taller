package com.example.demo.entity;

import com.example.demo.entity.enums.DocumentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // === OJO: camelCase en la propiedad Java, columna original en la BD ===
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;

    @Column(name = "document_number")
    private String documentNumber;

    private String address;
    private String cellphone;
    private String email;

    private String weight;
    private String login;

    // hash de contraseña
    @Column(name = "key")
    private String key;

    private String image;

    @Column(name = "birthday_date")
    private LocalDate birthdayDate;

    private Boolean state;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    // === Relación con rol vía tabla user_permission (id_user, id_rol) ===
    @ManyToMany
    @JoinTable(
            name = "user_permission",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    @Builder.Default
    private Set<Rol> roles = new HashSet<>();
}
