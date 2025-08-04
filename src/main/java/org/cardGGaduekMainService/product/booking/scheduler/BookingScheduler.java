package org.cardGGaduekMainService.product.booking.scheduler;

import org.cardGGaduekMainService.product.booking.mapper.BookingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BookingScheduler {
    private static final Logger log = LoggerFactory.getLogger(BookingScheduler.class);
    private final BookingMapper bookingMapper;

    @Autowired
    public BookingScheduler(BookingMapper bookingMapper){
        this.bookingMapper = bookingMapper;
    }

    @Scheduled(cron = "0 * * * * *")
    public void cleanupPendingBookings(){
        log.info(">>>>  만료된 PENDING 예약 정리 스케줄러 실행...");
        bookingMapper.cancelOldPendingBookings();
    }
}
