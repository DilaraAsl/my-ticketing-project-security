package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service


public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.taskService = taskService;
    }


    @Override
    public ProjectDTO getByProjectCode(String code) {
        Project project=projectRepository.findByProjectCode(code);
        return projectMapper.convertToDto(project);
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project> projectList=projectRepository.findAll();
        return projectList.stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {
        dto.setProjectStatus(Status.OPEN); // set the project status when saving the  project
        // when converting from dto to entity manager id becomes null it should not be null
        projectRepository.save(projectMapper.convertToEntity(dto));// JpaRepository has a save method

    }

    @Override
    public void update(ProjectDTO dto) {
        // find the project
       Project project= projectRepository.findByProjectCode(dto.getProjectCode());

        // map the projectDTO to project entity
        Project updatedProject=projectMapper.convertToEntity(dto);
        // set the updated project id field
        updatedProject.setId(project.getId());

        //set the status of the project
        updatedProject.setProjectStatus(project.getProjectStatus());

        //save the updated project in the database
        projectRepository.save(updatedProject);

    }

    @Override
    public void delete(String code) {
        Project project=projectRepository.findByProjectCode(code);
        project.setIsDeleted(true); // soft delete

        // when my project is deleted save it in this new format -- projectCode and projectId are both unique
        // we can use the same project code again for a new project
        project.setProjectCode(project.getProjectCode()+"-"+project.getId());
        projectRepository.save(project);
        // when we delete a project the tasks that are under that project should be deleted as well

        taskService.deleteByProject(projectMapper.convertToDto(project));


    }

    @Override
    public void complete(String code) {

        Project project=projectRepository.findByProjectCode(code);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);

        // when we set the project status complete all
        // the tasks under this project their status should be marked as complete

        taskService.completeByProject(projectMapper.convertToDto(project));
    }
}
