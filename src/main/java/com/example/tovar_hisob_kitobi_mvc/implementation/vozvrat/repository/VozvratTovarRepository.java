package com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.repository;

import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.entity.VozvratTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.projection.VozvratSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.vozvrat.model.projection.VozvratSummaByCreatedBy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface VozvratTovarRepository extends BaseRepository<VozvratTovar, UUID> {
    @Query(nativeQuery = true, value = """
            select * 
            from vozvrat_tovar 
            where tasdiqlandi=false and updated_at<?1
            """)
    List<VozvratTovar> findAllByUpdatedAtLessThanAndTasdiqlandiFalse(LocalDateTime updatedAt);

    @Query(nativeQuery = true, value = """
           select
                coalesce(s.sum,0)       as sum,
                t.month                 as month,
                t.year                  as year
           from (select sum(summa) sum, cast(date_part('month',vtd.created_at) as integer) as month, cast(date_part('year',vtd.created_at) as integer) as year
                 from vozvrat_tovar_detail vtd
                 inner join vozvrat_tovar vt on vtd.vozvrat_tovar_id=vt.id and vt.tasdiqlandi=true
                 group by year, month) s
           right join (
             	select generate_series(1, 12) as month, :year as year
           )t
               on s.year=t.year and s.month=t.month
           order by t.year, t.month  -- vozvrat
           \s""")
    List<VozvratSumma> findAllVozvratSummaByYear(int year);

    @Query(nativeQuery = true, value = """
            select
                            coalesce(s.sum,0)       as sum,
                            t.month                 as month,
                            t.year                  as year,
            				s.created_by            as created_by,
            				u.familya||' '||u.ism   as full_name
            from (select sum(summa) sum, vtd.created_by as created_by, cast(date_part('month',vtd.created_at) as integer) as month, cast(date_part('year',vtd.created_at) as integer) as year
                  from vozvrat_tovar_detail vtd
                  inner join vozvrat_tovar vt on vtd.vozvrat_tovar_id=vt.id and vt.tasdiqlandi=true
                  group by year, month, vtd.created_by) s
            right join (
                  select generate_series(1, 12) as month, :year as year
            )t
                  on s.year=t.year and s.month=t.month
            inner join users u
            	  on s.created_by=u.id
            order by t.year, t.month  -- vozvrat
           \s""")
    List<VozvratSummaByCreatedBy> findAllVozvratSummaByCreatedByByYear(int year);
}
