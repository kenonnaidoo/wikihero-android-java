package com.kenon.wikihero.models;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Biography implements Serializable {
    private String fullName;
    private String alterEgos;
    private List <String> aliases;
    private String placeOfBirth;
    private String firstAppearence;
    private String publisher;
    private String alignment;

    public Biography(String fullName, String alterEgos, List<String> aliases, String placeOfBirth, String firstAppearence, String publisher, String alignment) {
        this.fullName = fullName;
        this.alterEgos = alterEgos;
        this.aliases = aliases;
        this.placeOfBirth = placeOfBirth;
        this.firstAppearence = firstAppearence;
        this.publisher = publisher;
        this.alignment = alignment;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAlterEgos() {
        return alterEgos;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public String getFirstAppearence() {
        return firstAppearence;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getAlignment() {
        return alignment;
    }
}
