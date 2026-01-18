package com.hyunha.stock.stock.domain.port.out;

import aj.org.objectweb.asm.commons.InstructionAdapter;
import com.hyunha.stock.stock.api.dto.GetInvestmentOpinionResponse;

import java.util.List;
import java.util.Optional;

public interface StockCacheReader {
    Optional<List<GetInvestmentOpinionResponse>> getInvestmentOpinion(String symbol);
}
