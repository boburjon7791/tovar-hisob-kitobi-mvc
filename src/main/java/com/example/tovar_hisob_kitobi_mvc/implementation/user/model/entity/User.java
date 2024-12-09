package com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity;

import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntityLongID;
import com.example.tovar_hisob_kitobi_mvc.implementation.user.model.entity.enums.Lavozim;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Where(clause = "deleted=false")
public class User extends BaseEntityLongID {
    @Column(nullable = false)
    private String ism;

    @Column(nullable = false)
    private String familya;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Lavozim lavozim;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String parol;

    @Column(nullable = false, name = "telefon_raqam")
    private String telefonRaqam;
}
