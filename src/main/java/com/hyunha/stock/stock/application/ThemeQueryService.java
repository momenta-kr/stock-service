package com.hyunha.stock.stock.application;

import com.hyunha.stock.stock.api.dto.GetThemesResponse;
import com.hyunha.stock.stock.domain.port.out.ThemeQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ThemeQueryService {

    private final ThemeQueryPort themeQueryPort;

    public List<GetThemesResponse> getThemes() {
        return themeQueryPort.getThemes();
    }
}
