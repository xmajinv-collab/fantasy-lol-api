package com.fantasylol.fantasy_api.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fantasylol.fantasy_api.service.MercadoService;

@Component
public class MercadoScheduler {

    private final MercadoService mercadoService;

    public MercadoScheduler(MercadoService mercadoService) {
        this.mercadoService = mercadoService;
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void actualizarMercadoDiario() {

        mercadoService.generarMercadoDiario();

    }

}