package org.cardGGaduekMainService.product.categoryPageContent.controller;

import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.product.categoryPageContent.domain.CategoryPageContentVO;
import org.cardGGaduekMainService.product.categoryPageContent.dto.CategoryPageContentDTO;
import org.cardGGaduekMainService.product.categoryPageContent.dto.FinalBenefitResponseDTO;
import org.cardGGaduekMainService.product.categoryPageContent.service.CategoryPageContentService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/category")
public class CategoryPageContentController {
    private final CategoryPageContentService categoryPageContentService;

    @Autowired
    public CategoryPageContentController(CategoryPageContentService categoryPageContentService ){
        this.categoryPageContentService = categoryPageContentService;
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<ApiResponse<List<FinalBenefitResponseDTO>>> getCategoryContents(
            @PathVariable String categoryName,
            @AuthenticationPrincipal LoginMember loginMember) {
        List<FinalBenefitResponseDTO> contentVOList = categoryPageContentService.getBenefitContentForMember(categoryName, loginMember.getId());
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CATEGORY_FETCH_SUCCESS, contentVOList));
    }
}
