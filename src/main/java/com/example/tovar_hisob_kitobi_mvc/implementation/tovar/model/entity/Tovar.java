package com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.entity;

import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntityLongID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tovar")
@Where(clause = "deleted=false")
public class Tovar extends BaseEntityLongID {
    @Column(nullable = false)
    private String nomi;

    @Column(nullable = false)
    private BigDecimal ostatkasi=BigDecimal.ZERO;

    @Column
    private String rasmi;

    @Column(name = "shtrix_kod")
    private String shtrixKod;

    @Column(name = "prixod_summa", nullable = false)
    private BigDecimal prixodSumma;

    @Column(name = "rasxod_summa")
    private BigDecimal rasxodSumma;

    @Column(name = "olchov_birligi", nullable = false)
    private String olchovBirligi;

    public static final String _rasmi = "rasmi";
    public static final String _nomi="nomi";
}
