package com.example.tovar_hisob_kitobi_mvc.implementation.prixod.repository;

import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.entity.PrixodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection.PrixodSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.prixod.model.projection.PrixodSummaByCreatedBy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PrixodTovarRepository extends BaseRepository<PrixodTovar, UUID> {
    @Query(nativeQuery = true, value = """
            select * 
            from prixod_tovar 
            where tasdiqlandi=false and updated_at<?1
            """)
    List<PrixodTovar> findAllByUpdatedAtLessThanAndTasdiqlandiFalse(LocalDateTime updatedAt);


    @Query(nativeQuery = true, value = """
           select
                coalesce(s.sum,0)       as sum,
                t.month                 as month,
                t.year                  as year
           from (select sum(summa) sum, cast(date_part('month',ptd.created_at) as integer) as month, cast(date_part('year',ptd.created_at) as integer) as year
                 from prixod_tovar_detail ptd
                 inner join prixod_tovar pt on ptd.prixod_tovar_id=pt.id and pt.tasdiqlandi=true
                 group by year, month) s
           right join (
             	select generate_series(1, 12) as month, :year as year
           )t
               on s.year=t.year and s.month=t.month
           order by t.year, t.month  -- prixod
           \s""")
    List<PrixodSumma> findAllPrixodSummaByYear(int year);

    @Query(nativeQuery = true, value = """
            select
                            coalesce(s.sum,0)       as sum,
                            t.month                 as month,
                            t.year                  as year,
            				s.created_by            as created_by,
            				u.familya||' '||u.ism   as full_name
            from (select sum(summa) sum, ptd.created_by as created_by, cast(date_part('month',ptd.created_at) as integer) as month, cast(date_part('year',ptd.created_at) as integer) as year
                  from prixod_tovar_detail ptd
                  inner join prixod_tovar pt on ptd.prixod_tovar_id=pt.id and pt.tasdiqlandi=true
                  group by year, month, ptd.created_by) s
            right join (
                  select generate_series(1, 12) as month, :year as year
            )t
                  on s.year=t.year and s.month=t.month
            inner join users u
            	  on s.created_by=u.id
            order by t.year, t.month  -- prixod
           \s""")
    List<PrixodSummaByCreatedBy> findAllPrixodSummaByCreatedByByYear(int year);
}
