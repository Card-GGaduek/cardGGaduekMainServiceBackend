package org.cardGGaduekMainService.product.rooms.controller;

import org.cardGGaduekMainService.product.rooms.service.RoomsService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomsController {
    private final RoomsService roomsService;

    public RoomsController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }
}

