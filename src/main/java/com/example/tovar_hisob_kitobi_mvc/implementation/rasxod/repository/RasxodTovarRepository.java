package com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.repository;

import com.example.tovar_hisob_kitobi_mvc.base.repository.BaseRepository;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.entity.RasxodTovar;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSumma;
import com.example.tovar_hisob_kitobi_mvc.implementation.rasxod.model.projection.RasxodSummaByCreatedBy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RasxodTovarRepository extends BaseRepository<RasxodTovar, UUID> {
    @Query(nativeQuery = true, value = """
            select * 
            from rasxod_tovar 
            where tasdiqlandi=false and updated_at<?1
            """)
    List<RasxodTovar> findAllByUpdatedAtLessThanAndTasdiqlandiFalse(LocalDateTime updatedAt);

    @Query(nativeQuery = true, value = """
           select
                coalesce(s.sum,0)       as sum,
                t.month                 as month,
                t.year                  as year
           from (select sum(summa) sum, cast(date_part('month',rtd.created_at) as integer) as month, cast(date_part('year',rtd.created_at) as integer) as year
                 from rasxod_tovar_detail rtd
                 inner join rasxod_tovar rt on rtd.rasxod_tovar_id=rt.id and rt.tasdiqlandi=true
                 group by year, month) s
           right join (
             	select generate_series(1, 12) as month, :year as year
           )t
               on s.year=t.year and s.month=t.month
           order by t.year, t.month  -- rasxod
           \s""")
    List<RasxodSumma> findAllRasxodSummaByYear(int year);

    @Query(nativeQuery = true, value = """
            select
                            coalesce(s.sum,0)       as sum,
                            t.month                 as month,
                            t.year                  as year,
            				s.created_by            as created_by,
            				u.familya||' '||u.ism   as full_name
            from (select sum(summa) sum, rtd.created_by as created_by, cast(date_part('month',rtd.created_at) as integer) as month, cast(date_part('year',rtd.created_at) as integer) as year
                  from rasxod_tovar_detail rtd
                  inner join rasxod_tovar rt on rtd.rasxod_tovar_id=rt.id and rt.tasdiqlandi=true
                  group by year, month, rtd.created_by) s
            right join (
                  select generate_series(1, 12) as month, :year as year
            )t
                  on s.year=t.year and s.month=t.month
            inner join users u
            	  on s.created_by=u.id
            order by t.year, t.month  -- rasxod
           \s""")
    List<RasxodSummaByCreatedBy> findAllRasxodSummaByCreatedByByYear(int year);
}
