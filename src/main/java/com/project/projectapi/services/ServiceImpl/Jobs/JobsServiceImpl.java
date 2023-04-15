package com.project.projectapi.services.ServiceImpl.Jobs;

import com.project.projectapi.controller.Auth.LoginController;
import com.project.projectapi.dto.Jobs.JobsDTO;
import com.project.projectapi.dto.Jobs.JobsRequestDTO;
import com.project.projectapi.services.Interface.Jobs.JobsService;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JobsServiceImpl implements JobsService {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    public List<JobsDTO> getJobs(JobsRequestDTO jobsRequestDTO, Integer page, Integer size) {
        Flux<JobsDTO> jobsDTOFlux = WebClient.create("http://dev3.dansmultipro.co.id/api/recruitment/positions.json").get()
                .retrieve()
                .bodyToFlux(JobsDTO.class);
        if(page!=null && size == null){
            jobsDTOFlux = WebClient.create("http://dev3.dansmultipro.co.id/api/recruitment/positions.json?page="+ page)
                    .get()
                    .retrieve()
                    .bodyToFlux(JobsDTO.class);
        }
        if(page == null && size !=null){
            page = 1;
        }
        System.out.println( "hahaha");

        List<JobsDTO> jobsDTOS = jobsDTOFlux.collect(Collectors.toList()).block();

        if (!Objects.isNull(jobsRequestDTO)){
            jobsDTOS = jobsDTOS.stream().filter(jobsDTO -> !StringUtils.isEmpty(jobsRequestDTO.getDescription()) ? jobsDTO.getDescription().toLowerCase().contains(jobsRequestDTO.getDescription().toLowerCase()) : jobsDTO.getDescription()== jobsDTO.getDescription()).filter(jobsDTO -> !StringUtils.isEmpty(jobsRequestDTO.getLocation()) ? jobsDTO.getLocation().toLowerCase().contains(jobsRequestDTO.getLocation().toLowerCase()) : jobsDTO.getLocation()== jobsDTO.getLocation()).filter(jobsDTO -> jobsRequestDTO.getFull_time() == null ? jobsDTO.getType() == "Full Time" || jobsDTO.getType() != "Full Time" : BooleanUtils.isTrue(jobsRequestDTO.getFull_time()) ? jobsDTO.getType() != "Full Time" : jobsDTO.getType() == "Full Time").collect(Collectors.toList());
        }
        if(page !=null && size !=null){
            jobsDTOS = toPage(jobsDTOS, size, page);
        }
        return jobsDTOS;
    }

    List<JobsDTO> toPage(List<JobsDTO> jobsDTOS, int size, int page){
        int totalpages = jobsDTOS.size() / size;
        int max = size*(page+1)-size;
        int min = size*page-size;
        if(page>totalpages){
            max = jobsDTOS.size();
        }
        Pageable pageable =  PageRequest.of(page, size);
        Page<JobsDTO> jobsDTOPage = new PageImpl<>(jobsDTOS.subList(min,max),pageable, jobsDTOS.size());
        return jobsDTOPage.getContent();
    }

    @Override
    public JobsDTO getJobDetailById(String id) {
        Mono<JobsDTO> jobsDTOMono = WebClient.create("http://dev3.dansmultipro.co.id/api/recruitment/positions/"+id).get()
                .retrieve()
                .bodyToMono(JobsDTO.class);
        JobsDTO jobsDTO=jobsDTOMono.block();
        return jobsDTO;
    }
}
