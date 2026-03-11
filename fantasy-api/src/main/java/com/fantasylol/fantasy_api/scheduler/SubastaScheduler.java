package com.fantasylol.fantasy_api.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SubastaScheduler {

    @Scheduled(fixedRate = 60000)
    public void revisarSubastas(){

        System.out.println("Revisando subastas...");
    }
}