package com.hyunha.stock.stock.domain.port.out;

import com.hyunha.stock.stock.api.dto.GetThemesResponse;

import java.util.List;

public interface ThemeQueryPort {
    List<GetThemesResponse> getThemes();

}
