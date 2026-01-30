package com.hyunha.stock.stock.infra.jpa.repo;

import com.hyunha.stock.stock.infra.jpa.entity.Stock;
import com.hyunha.stock.stock.infra.jpa.entity.ThemeMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ThemeMemberRepository extends JpaRepository<ThemeMember, Long> {
    List<ThemeMember> findByStockIn(List<Stock> stocks);

    @Query("""
      select distinct tm
      from ThemeMember tm
      join fetch tm.theme t
      join fetch tm.stock s
    """)
    List<ThemeMember> findAllWithTheme();
}
