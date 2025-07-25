package org.cardGGaduekMainService.lab.scheduler;


import org.cardGGaduekMainService.lab.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LabScheduler {
    @Autowired
    private LabService labService;

    // 매일 새벽 2시 실행
    @Scheduled(cron = "0 0 2 * * *")
    public void runDailySpendingCategoryUpdate() {
        labService.updateSpendingCategoryDaily();
    }
}
