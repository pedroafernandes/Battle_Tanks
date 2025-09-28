package com.tanque_isaac.domain.tanks;

import com.tanque_isaac.domain.Weapon;

public class HeavyTank extends Tank {
    public HeavyTank(String name, int hp, Weapon weapon) {
        super(name, hp, "Heavy", weapon);
    }
    @Override public int calcularRecarga() { return 6; }
}
