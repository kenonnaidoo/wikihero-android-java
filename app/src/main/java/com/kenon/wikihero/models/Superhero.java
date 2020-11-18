package com.kenon.wikihero.models;

import java.io.Serializable;

public class Superhero implements Serializable {
    private String id;
    private String name;
    private PowerStats powerStats;
    private Biography biography;
    private Appearence appearence;
    private Work work;
    private Connections connections;
    private String imageUrl;
    private String json;

    public Superhero(String id, String name, PowerStats powerStats, Biography biography, Appearence appearence, Work work, Connections connections, String imageUrl, String json) {
        this.id = id;
        this.name = name;
        this.powerStats = powerStats;
        this.biography = biography;
        this.appearence = appearence;
        this.work = work;
        this.connections = connections;
        this.imageUrl = imageUrl;
        this.json = json;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PowerStats getPowerStats() {
        return powerStats;
    }

    public Biography getBiography() {
        return biography;
    }

    public Appearence getAppearence() {
        return appearence;
    }

    public Work getWork() {
        return work;
    }

    public Connections getConnections() {
        return connections;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getJson() { return json; }
}
