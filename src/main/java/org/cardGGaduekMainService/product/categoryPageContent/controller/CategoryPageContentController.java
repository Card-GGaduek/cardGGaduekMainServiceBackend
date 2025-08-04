package org.cardGGaduekMainService.product.categoryPageContent.controller;

import org.cardGGaduekMainService.product.categoryPageContent.domain.CategoryPageContentVO;
import org.cardGGaduekMainService.product.categoryPageContent.dto.CategoryPageContentDTO;
import org.cardGGaduekMainService.product.categoryPageContent.service.CategoryPageContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryPageContentController {
    private final CategoryPageContentService categoryPageContentService;

    @Autowired
    public CategoryPageContentController(CategoryPageContentService categoryPageContentService){
        this.categoryPageContentService = categoryPageContentService;
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<List<CategoryPageContentDTO>> getCategoryContents(
            @PathVariable String categoryName,
            Authentication authentication) {
        Long memberId = getMemberIdFromAuthentication(authentication);
        List<CategoryPageContentDTO> contentVOList = categoryPageContentService.getBenefitContentForMember(categoryName, memberId);

        return ResponseEntity.ok(contentVOList);
    }

    private Long getMemberIdFromAuthentication(Authentication authentication){
        if(authentication == null || !authentication.isAuthenticated()){
            throw new SecurityException("인증되지 않은 사용자입니다.");
        }
        return 2L;
    }
}
