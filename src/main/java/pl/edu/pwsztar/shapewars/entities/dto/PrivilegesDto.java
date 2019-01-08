package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.Data;

@Data
public class PrivilegesDto {

    private String selectUsername;
    private boolean admin;
    private String issuerToken;

}
