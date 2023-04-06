package com.project.projectapi.controller.Jobs;

import com.project.projectapi.dto.Jobs.JobsDTO;
import com.project.projectapi.dto.Jobs.JobsRequestDTO;
import com.project.projectapi.dto.PaginationDTO;
import com.project.projectapi.dto.ResponseDTO;
import com.project.projectapi.model.entities.User;
import com.project.projectapi.services.Interface.AuthServices.AuthService;
import com.project.projectapi.services.Interface.CookieManagerServices;
import com.project.projectapi.services.Interface.Jobs.JobsService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class JobsController {

    private JobsService jobsService;
    private AuthService authService;
    private CookieManagerServices cookieManagerServices;

    Logger log = LoggerFactory.getLogger(JobsController.class);

    public JobsController(JobsService jobsService, AuthService authService, CookieManagerServices cookieManagerServices) {
        this.jobsService = jobsService;
        this.authService = authService;
        this.cookieManagerServices = cookieManagerServices;
    }

    @GetMapping("/jobList")
    public ResponseDTO<PaginationDTO<JobsDTO>> getJobs(
            JobsRequestDTO jobsRequestDTO,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            HttpServletRequest httpServletRequest
    ){
        User user = new User();
        try {
            String token = cookieManagerServices.getToken(httpServletRequest);
            if (StringUtils.isEmpty(token)){
                String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

                if(StringUtils.isEmpty(header)) return null;
                token = header.substring(header.indexOf("Bearer") + "Bearer ".length());
            }
            user = authService.getUserFromToken(token);
        } catch(Exception e) {
            log.info("Cookie token is null");
        }
        List<JobsDTO> jobsDTOs = jobsService.getJobs(jobsRequestDTO, page, size);
        PaginationDTO<JobsDTO> paginationDTO = new PaginationDTO<>();
        paginationDTO.setData(jobsDTOs);
        Page<JobsDTO> jobsDTOPage = new PageImpl<>(jobsDTOs);
        paginationDTO.setTotalPages(jobsDTOPage.getTotalPages());
        paginationDTO.setTotalElements(jobsDTOPage.getTotalElements());
        paginationDTO.setNumber(jobsDTOPage.getNumber());

        return ResponseDTO.ok(paginationDTO);
    }

    @GetMapping("/jobList/{id}")
    public ResponseDTO<JobsDTO> getJobsById(
            @PathVariable String id
    ){
        JobsDTO jobsDTO = jobsService.getJobDetailById(id);
        return ResponseDTO.ok(jobsDTO);
    }
}
