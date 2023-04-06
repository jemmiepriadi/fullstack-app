package com.project.projectapi.services.Interface.Jobs;

import com.project.projectapi.dto.Jobs.JobsDTO;
import com.project.projectapi.dto.Jobs.JobsRequestDTO;

import java.util.ArrayList;
import java.util.List;

public interface JobsService {
    List<JobsDTO> getJobs(JobsRequestDTO jobsRequestDTO, Integer page, Integer size);
    JobsDTO getJobDetailById(String id);
}
