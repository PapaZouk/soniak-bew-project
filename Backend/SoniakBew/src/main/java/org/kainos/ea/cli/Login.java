package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Login {
    private String username;
    private String password;
    private int roleId;

    @JsonCreator
    public Login(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("roleId") int roleId
    ) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }
}
