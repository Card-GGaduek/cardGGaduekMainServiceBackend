package org.cardGGaduekMainService.product.rooms.service;

import org.cardGGaduekMainService.product.rooms.domain.RoomsVO;
import org.cardGGaduekMainService.product.rooms.dto.RoomsDTO;
import org.cardGGaduekMainService.product.rooms.mapper.RoomsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsServiceImpl implements RoomsService{
    private final RoomsMapper roomsMapper;

    public RoomsServiceImpl(RoomsMapper roomsMapper){
        this.roomsMapper = roomsMapper;
    }
}
