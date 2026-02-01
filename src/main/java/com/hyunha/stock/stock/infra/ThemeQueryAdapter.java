package com.hyunha.stock.stock.infra;

import com.hyunha.stock.stock.infra.jpa.dto.ThemeResponse;
import com.hyunha.stock.stock.domain.port.out.ThemeQueryPort;
import com.hyunha.stock.stock.infra.jpa.entity.Stock;
import com.hyunha.stock.stock.infra.jpa.entity.Theme;
import com.hyunha.stock.stock.infra.jpa.entity.ThemeMember;
import com.hyunha.stock.stock.infra.jpa.repo.ThemeMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ThemeQueryAdapter implements ThemeQueryPort {

    private final ThemeMemberRepository themeMemberRepository;

    public List<ThemeResponse> getThemes() {
        List<ThemeMember> themeMemberList = themeMemberRepository.findAllWithTheme();

        Map<Theme, Long> countByTheme = themeMemberList.stream()
                .collect(Collectors.groupingBy(ThemeMember::getTheme, Collectors.counting()));

        Map<String, List<Stock>> stocksByThemeCode = themeMemberList.stream()
                .collect(Collectors.groupingBy(
                        tm -> tm.getTheme().getCode(),                 // key: themeCode
                        Collectors.mapping(
                                ThemeMember::getStock,       // value: symbol (없으면 getStockCode())
                                Collectors.toList()
                        )
                ));

        return countByTheme.entrySet().stream()
                .map(entry -> new ThemeResponse(
                                entry.getKey().getCode(),
                                entry.getKey().getName(),
                                entry.getValue(),
                                stocksByThemeCode.get(entry.getKey().getCode()).stream()
                                        .map(stock -> new ThemeResponse.Stock(stock.getId().getSymbol(), stock.getNameKo()))
                                        .toList()
                        )

                )
                .toList();
    }
}
