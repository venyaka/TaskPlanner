package com.veniamin.taskplanner.dto.responce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpStackResponse {

    private String city;
}
