package com.kenon.wikihero.models;

import java.io.Serializable;

public class PowerStats implements Serializable {
    private String intelligence;
    private String strength;
    private String speed;
    private String durability;
    private String power;
    private String combat;

    public PowerStats(String intelligence, String strength, String speed, String durability, String power, String combat) {
        this.intelligence = intelligence;
        this.strength = strength;
        this.speed = speed;
        this.durability = durability;
        this.power = power;
        this.combat = combat;
    }

    public String getIntelligence() {
        return intelligence;
    }

    public String getStrength() {
        return strength;
    }

    public String getSpeed() {
        return speed;
    }

    public String getDurability() {
        return durability;
    }

    public String getPower() {
        return power;
    }

    public String getCombat() {
        return combat;
    }
}
