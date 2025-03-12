package com.example.PING.portfolio.dto.response;

import com.example.PING.portfolio.entity.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class PortfolioPageResponse {
    private List<PortfolioResponse> portfolios; // Portfolio List
    private int totalPages; // 총 페이지 수
    private long totalElements; // 페이지 내 Portfolio 수
    private int currentPage;    // 현재 페이지 번호

    public static PortfolioPageResponse from(List<PortfolioResponse> portfolioResponses, Page<Portfolio> portfolioPage) {
        return new PortfolioPageResponse(
                portfolioResponses,
                portfolioPage.getTotalPages(),
                portfolioPage.getTotalElements(),
                portfolioPage.getNumber()
        );
    }
}
