package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity;

import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntityUUID;
import com.example.tovar_hisob_kitobi_mvc.implementation.tovar.model.entity.Tovar;
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
@Table(name = "rasxod_tovar_detail")
@Where(clause = "deleted=false")
public class RasxodTovarDetail extends BaseEntityUUID {
    @ManyToOne
    @JoinColumn(name = "rasxod_tovar_id", nullable = false)
    private RasxodTovar rasxodTovar;

    @ManyToOne
    @JoinColumn(name = "tovar_id",nullable = false)
    private Tovar tovar;

    @Column(nullable = false)
    private BigDecimal miqdori;

    @Column(nullable = false)
    private BigDecimal summa;
}
