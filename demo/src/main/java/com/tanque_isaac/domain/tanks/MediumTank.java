package com.tanque_isaac.domain.tanks;

import com.tanque_isaac.domain.Weapon;

public class MediumTank extends Tank {
    public MediumTank(String name, int hp, Weapon weapon) {
        super(name, hp, "Medium", weapon);
    }
    @Override public int calcularRecarga() { return 4; }
}
