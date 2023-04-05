package com.project.projectapi.dto.Jobs;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JobsRequestDTO {
    private String description;
    private String location;
    private Boolean full_time;
}
