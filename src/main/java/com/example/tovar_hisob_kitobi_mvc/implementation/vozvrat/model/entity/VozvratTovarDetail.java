package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity;

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
@Table(name = "vozvrat_tovar_detail")
@Where(clause = "deleted=false")
public class VozvratTovarDetail extends BaseEntityUUID {
    @ManyToOne
    @JoinColumn(name = "vozvrat_tovar_id", nullable = false)
    private VozvratTovar vozvratTovar;

    @ManyToOne
    @JoinColumn(name = "tovar_id",nullable = false)
    private Tovar tovar;

    @Column(nullable = false)
    private BigDecimal miqdori;

    @Column(nullable = false)
    private BigDecimal summa;
}
