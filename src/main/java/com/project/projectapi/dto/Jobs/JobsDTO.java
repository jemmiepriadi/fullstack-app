package com.project.projectapi.dto.Jobs;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter @Setter
public class JobsDTO {
    private String id;
    private String type;
    private String url;
    private ZonedDateTime created_At;
    private String company;
    private String company_url;
    private String location;
    private String title;
    private String description;
    private String how_to_apply;
    private String company_logo;
}
