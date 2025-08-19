package org.cardGGaduekMainService.product.categoryPageContent.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.product.categoryPageContent.dto.FinalBenefitResponseDTO;
import org.cardGGaduekMainService.product.categoryPageContent.service.CategoryPageContentService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "카테고리 컨텐츠")
@Log4j2
@RestController
@RequestMapping("/api/category")
public class CategoryPageContentController {
    private final CategoryPageContentService categoryPageContentService;

    @Autowired
    public CategoryPageContentController(CategoryPageContentService categoryPageContentService ){
        this.categoryPageContentService = categoryPageContentService;
    }

    @ApiOperation(value = "", notes = "")
    @ApiResponses(
            @ApiResponse(code = 200, message = "")
    )
    @GetMapping("/{categoryName}")
    public ResponseEntity<CustomApiResponse<List<FinalBenefitResponseDTO>>> getCategoryContents(
            @PathVariable String categoryName,
            @AuthenticationPrincipal LoginMember loginMember) {
        List<FinalBenefitResponseDTO> contentVOList = categoryPageContentService.getBenefitContentForMember(categoryName, loginMember.getId());
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CATEGORY_FETCH_SUCCESS, contentVOList));
    }
}
