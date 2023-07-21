package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class Project {

    private int id;
    private int tech_lead_id;
    private String techLeadName;
    private int client_id;
    private String name;
    private float value;

    private String status;

    private Date start_date;

    private Date complete_date;

    @JsonCreator
    public Project(
            @JsonProperty("id") int id,
            @JsonProperty("tech_lead_id") int tech_lead_id,
            @JsonProperty("techLeadName") String techLeadName,
            @JsonProperty("client_id") int client_id,
            @JsonProperty("name") String name,
            @JsonProperty("value") float value,
            @JsonProperty("status") String status,
            @JsonProperty("start_date") Date start_date,
            @JsonProperty("complete_date") Date complete_date
    ) {
        this.id = id;
        this.tech_lead_id = tech_lead_id;
        this.techLeadName = techLeadName;
        this.client_id = client_id;
        this.name = name;
        this.value = value;
        this.status = status;
        this.start_date = start_date;
        this.complete_date = complete_date;
    }
}
