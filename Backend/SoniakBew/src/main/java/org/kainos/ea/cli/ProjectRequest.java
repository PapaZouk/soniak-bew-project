package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ProjectRequest {
    private int tech_lead_id;
    private int client_id;
    private String name;
    private float value;

    private String status;

    private Date start_date;

    private Date complete_date;

    @JsonCreator
    public ProjectRequest(
            @JsonProperty("tech_lead_id") int tech_lead_id,
            @JsonProperty("client_id") int client_id,
            @JsonProperty("name") String name,
            @JsonProperty("value") float value,
            @JsonProperty("status") String status,
            @JsonProperty("start_date") Date start_date,
            @JsonProperty("complete_date") Date complete_date
    ) {
        this.tech_lead_id = tech_lead_id;
        this.client_id = client_id;
        this.name = name;
        this.value = value;
        this.status = status;
        this.start_date = start_date;
        this.complete_date = complete_date;
    }

    public int getTech_lead_id() {
        return tech_lead_id;
    }

    public void setTech_lead_id(int tech_lead_id) {
        this.tech_lead_id = tech_lead_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getComplete_date() {
        return complete_date;
    }

    public void setComplete_date(Date complete_date) {
        this.complete_date = complete_date;
    }
}
