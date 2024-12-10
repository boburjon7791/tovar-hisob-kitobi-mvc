package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity;

import com.example.tovar_hisob_kitobi_mvc.base.model.entity.BaseEntityUUID;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
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
@Table(name = "vozvrat_tovar")
@Where(clause = "deleted=false")
public class VozvratTovar extends BaseEntityUUID {
    @Column(name = "total_summa")
    private BigDecimal totalSumma;

    @ManyToOne
    @JoinColumn(name = "rasxod_tovar_id", nullable = false)
    private RasxodTovar rasxodTovar;

    private boolean tasdiqlandi;

    public static final String _tasdiqlandi="tasdiqlandi";
}
