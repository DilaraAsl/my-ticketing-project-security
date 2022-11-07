package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;

        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        return taskRepository.findAll().stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO taskDTO) {
        taskDTO.setTaskStatus(Status.OPEN); // first set the status to open
        taskDTO.setAssignedDate(LocalDate.now()); // set the date
        taskRepository.save(taskMapper.convertToEntity(taskDTO)); //save it to the db

    }

    @Override
    public void update(TaskDTO taskDTO) {
        Optional<Task> task=taskRepository.findById(taskDTO.getId());
        Task updatedTask=taskMapper.convertToEntity(taskDTO);

        if(task.isPresent()) { //isPresent() method of Optional Interface
            updatedTask.setTaskStatus(task.get().getTaskStatus()); // set the project status
            updatedTask.setAssignedDate(task.get().getAssignedDate()); // set the assigned date -- so that we do not have these fields set to null
            taskRepository.save(updatedTask);
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Task> task=taskRepository.findById(id);

        if(task.isPresent()){
            task.get().setIsDeleted(true); // soft delete the task
            taskRepository.save(task.get()); // get() optional interface
        }

    }

    @Override
    public TaskDTO findById(Long id) {
        return null;
    }

    @Override
    public int totalNonCompletedTask(String projectCode) {
        return 0;
    }

    @Override
    public int totalCompletedTask(String projectCode) {
        return 0;
    }

    @Override
    public void deleteByProject(ProjectDTO projectDTO) {

    }

    @Override
    public void completeByProject(ProjectDTO projectDTO) {

    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {
        return null;
    }

    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {
        return null;
    }
}
