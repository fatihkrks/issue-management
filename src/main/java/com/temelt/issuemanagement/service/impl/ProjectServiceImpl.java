package com.temelt.issuemanagement.service.impl;

import com.temelt.issuemanagement.dto.ProjectDto;
import com.temelt.issuemanagement.entity.Project;
import com.temelt.issuemanagement.repository.ProjectRepository;
import com.temelt.issuemanagement.service.ProjectService;
import com.temelt.issuemanagement.util.TPage;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by temelt on 4.02.2019.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProjectDto save(ProjectDto project) {

        Project projectCheck=projectRepository.getByProjectCode(project.getProjectCode());
        if (projectCheck!=null)
            throw new IllegalArgumentException("Project code already exist");

        Project p = modelMapper.map(project, Project.class);
        p = projectRepository.save(p);
        project.setId(p.getId());
        return project;
    }

    @Override
    public ProjectDto getById(Long id) {
        Project p = projectRepository.getOne(id);
        return modelMapper.map(p, ProjectDto.class);
    }

    @Override
    public ProjectDto getByProjectCode(String projectCode) {
        return null;
    }

    @Override
    public List<ProjectDto> getByProjectCodeContains(String projectCode) {
        return null;
    }

    @Override
    public TPage<ProjectDto> getAllPageable(Pageable pageable) {
        Page<Project> data = projectRepository.findAll(pageable);
        TPage<ProjectDto> respnose = new TPage<ProjectDto>();
        respnose.setStat(data, Arrays.asList(modelMapper.map(data.getContent(), ProjectDto[].class)));
        return respnose;
    }

    @Override
    public Boolean delete(ProjectDto project) {
        return null;
    }

    public Boolean delete(Long id){
        projectRepository.deleteById(id);
        return true;
    }

    @Override
    public ProjectDto update(Long id, ProjectDto projectDto) {
       Project projectDb=projectRepository.getOne(id);
       if (projectDb==null){
           throw new IllegalArgumentException("Project Does Not exist ID:+id");
       }
        Project projectCheck=projectRepository.getByProjectCodeAndIdNot(projectDto.getProjectCode(),id);
        if (projectCheck!=null )
            throw new IllegalArgumentException("Project code already exist");

        projectDb.setProjectCode(projectDto.getProjectCode());
        projectDb.setProjectName(projectDto.getProjectName());
        projectRepository.save(projectDb);
        return modelMapper.map(projectDb,ProjectDto.class);
    }

}
