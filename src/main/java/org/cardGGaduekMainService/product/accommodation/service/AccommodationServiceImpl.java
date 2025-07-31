package org.cardGGaduekMainService.product.accommodation.service;

import org.cardGGaduekMainService.product.accommodation.dto.AccommodationDTO;
import org.cardGGaduekMainService.product.accommodation.dto.AccommodationPageDTO;
import org.cardGGaduekMainService.product.accommodation.mapper.AccommodationMapper;
import org.cardGGaduekMainService.product.rooms.dto.RoomsDTO;
import org.cardGGaduekMainService.product.rooms.mapper.RoomsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationServiceImpl implements AccommodationService{
    private final AccommodationMapper accommodationMapper;
    private final RoomsMapper roomsMapper;

    public AccommodationServiceImpl(AccommodationMapper accommodationMapper, RoomsMapper roomsMapper) {
        this.accommodationMapper = accommodationMapper;
        this.roomsMapper = roomsMapper;
    }

    @Override
    public AccommodationPageDTO getAccommodationPageDetail(Long accommodationId) {
        AccommodationDTO accommodation = accommodationMapper.findById(accommodationId);
        if (accommodation == null) {
            return null;
        }

        List<RoomsDTO> rooms = roomsMapper.findByAccommodationId(accommodationId);

        AccommodationPageDTO pageDTO = new AccommodationPageDTO();
        pageDTO.setId(accommodation.getId());
        pageDTO.setName(accommodation.getName());
        pageDTO.setType(accommodation.getType());
        pageDTO.setAddress(accommodation.getAddress());
        pageDTO.setCheckInTime(accommodation.getCheckInTime());
        pageDTO.setCheckOutTime(accommodation.getCheckOutTime());
        pageDTO.setRooms(rooms);

        return pageDTO;
    }
}