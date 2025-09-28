package com.tanque_isaac.match;

import java.util.List;

import com.tanque_isaac.domain.tanks.Tank;

public class Match {
    private List<Tank> participantes;

    public Match(List<Tank> participantes) {
        this.participantes = participantes;
    }

    public void executar() {
        System.out.println("[TODO] Simulação da partida");
    }
}
