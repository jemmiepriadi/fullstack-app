package com.project.projectapi.controller.Jobs;

import com.project.projectapi.dto.Jobs.JobsDTO;
import com.project.projectapi.dto.Jobs.JobsRequestDTO;
import com.project.projectapi.dto.ResponseDTO;
import com.project.projectapi.model.entities.User;
import com.project.projectapi.services.Interface.AuthServices.AuthService;
import com.project.projectapi.services.Interface.CookieManagerServices;
import com.project.projectapi.services.Interface.Jobs.JobsService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseDTO<List<JobsDTO>> getJobs(
            JobsRequestDTO jobsRequestDTO,
            HttpServletRequest httpServletRequest
    ){
        if(jobsRequestDTO == null){
            System.out.println("ini kebaca ga ya");
        }
        User user = new User();
        try {
            String token = cookieManagerServices.getToken(httpServletRequest);
            System.out.println(token + "jemmi");
            if (StringUtils.isEmpty(token)){
                String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
                if(StringUtils.isEmpty(header)) return null;
                token = header.substring(header.indexOf("Bearer") + "Bearer ".length());
            }
            user = authService.getUserFromToken(token);
        } catch(Exception e) {
            log.info("Cookie token is null");
        }
        List<JobsDTO> jobsDTOs = jobsService.getJobs(jobsRequestDTO);

        return ResponseDTO.ok(jobsDTOs);
    }
}
