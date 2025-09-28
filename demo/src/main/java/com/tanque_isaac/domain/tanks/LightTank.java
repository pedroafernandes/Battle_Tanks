package com.tanque_isaac.domain.tanks;

import com.tanque_isaac.domain.Weapon;

public class LightTank extends Tank {
    public LightTank(String name, int hp, Weapon weapon) {
        super(name, hp, "Light", weapon);
    }
    @Override public int calcularRecarga() { return 2; }
}
