package com.kenon.wikihero.models;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

public class Appearence implements Serializable {
    private String gender;
    private String race;
    private String height;
    private String weight;
    private String eyeColor;
    private String hairColor;

    public Appearence(String gender, String race, String height, String weight, String eyeColor, String hairColor) {
        this.gender = gender;
        this.race = race;
        this.height = height;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
    }

    public String getGender() {
        return gender;
    }

    public String getRace() {
        return race;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }
}
