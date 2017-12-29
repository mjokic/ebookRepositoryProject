package com.example.ebookrepository.schedules;

import com.example.ebookrepository.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TmpCleanerScheduler {

    private final StorageService storageService;

    @Autowired
    public TmpCleanerScheduler(StorageService storageService){
        this.storageService = storageService;
    }

    @Scheduled(fixedDelay = 1000000, initialDelay = 5000)
    public void cleanFilesTmp(){
        storageService.clean();
    }

}
