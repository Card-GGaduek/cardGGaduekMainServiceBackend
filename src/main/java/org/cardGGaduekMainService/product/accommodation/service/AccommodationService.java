package org.cardGGaduekMainService.product.accommodation.service;

import org.cardGGaduekMainService.product.accommodation.dto.AccommodationDTO;
import org.cardGGaduekMainService.product.accommodation.dto.AccommodationPageDTO;
import org.springframework.stereotype.Service;

@Service
public interface AccommodationService {
    AccommodationPageDTO getAccommodationPageDetail(Long accommodationId);
}
