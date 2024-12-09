package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity;

import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntityUUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "prixod_tovar")
@Where(clause = "deleted=false")
public class PrixodTovar extends BaseEntityUUID {
    @Column(name = "total_summa")
    private BigDecimal totalSumma;

    private boolean tasdiqlandi;

    public static final String _tasdiqlandi="tasdiqlandi";
}
