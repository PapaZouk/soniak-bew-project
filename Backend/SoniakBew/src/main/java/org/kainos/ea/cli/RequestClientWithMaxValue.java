package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestClientWithMaxValue {
    private String ClientName;

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getTotal_project_value() {
        return total_project_value;
    }

    public void setTotal_project_value(String total_project_value) {
        this.total_project_value = total_project_value;
    }

    private String total_project_value;

    @JsonCreator
    public RequestClientWithMaxValue(
            @JsonProperty("ClientName") String ClientName,
            @JsonProperty("total_project_value") String total_project_value
    ) {
        this.ClientName = ClientName;
        this.total_project_value = total_project_value;
    }



}
