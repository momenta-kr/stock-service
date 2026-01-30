package com.hyunha.stock.stock.api;

import com.hyunha.stock.stock.api.dto.GetThemesResponse;
import com.hyunha.stock.stock.application.ThemeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/stocks/v1/themes")
@RestController
public class ThemeController {

    private final ThemeQueryService themeQueryService;

    @GetMapping
    public List<GetThemesResponse> getThemes() {
        return themeQueryService.getThemes();

    }
}
