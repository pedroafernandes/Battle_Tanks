package com.tanque_isaac.domain.tanks;

import com.tanque_isaac.domain.Weapon;

public abstract class Tank {
    protected String name;
    protected int hp;
    protected String type;     // Light, Medium, Heavy
    protected Weapon weapon;

    public Tank(String name, int hp, String type, Weapon weapon) {
        this.name = name;
        this.hp = hp;
        this.type = type;
        this.weapon = weapon;
    }

    public abstract int calcularRecarga();

    public void receberDano(int dano) {
        this.hp -= dano;
        if (hp < 0) hp = 0;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public String getType() { return type; }
    public Weapon getWeapon() { return weapon; }
    public void setWeapon(Weapon weapon) { this.weapon = weapon; }
}
