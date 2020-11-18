package com.kenon.wikihero.models;

import java.io.Serializable;

public class Work implements Serializable {
    private String occupation;
    private String base;

    public Work(String occupation, String base) {
        this.occupation = occupation;
        this.base = base;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getBase() {
        return base;
    }
}
