package com.hyunha.stock.stock.infra;

import com.hyunha.stock.stock.api.dto.GetThemesResponse;
import com.hyunha.stock.stock.domain.port.out.ThemeQueryPort;
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

    public List<GetThemesResponse> getThemes() {
        Map<Theme, Long> countByTheme = themeMemberRepository.findAllWithTheme()
                .stream()
                .collect(Collectors.groupingBy(ThemeMember::getTheme, Collectors.counting()));

        return countByTheme.entrySet().stream()
                .map(entry -> new GetThemesResponse(
                        entry.getKey().getCode(),
                        entry.getKey().getName(),
                        entry.getValue())
                )
                .toList();
    }
}
