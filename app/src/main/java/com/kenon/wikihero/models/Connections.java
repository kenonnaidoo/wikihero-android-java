package com.kenon.wikihero.models;

import java.io.Serializable;

public class Connections implements Serializable {
    private String groupAffiliation;
    private String relatives;

    public Connections(String groupAffiliation, String relatives) {
        this.groupAffiliation = groupAffiliation;
        this.relatives = relatives;
    }

    public String getGroupAffiliation() {
        return groupAffiliation;
    }

    public String getRelatives() {
        return relatives;
    }
}
