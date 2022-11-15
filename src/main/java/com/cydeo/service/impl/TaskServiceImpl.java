package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectMapper projectMapper, @Lazy UserService userService, UserMapper userMapper) {
        // @Lazy do not inject UserService it is not ready yet
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @Override
    public List<TaskDTO> listAllTasks() {
        return taskRepository.findAll().stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO dto) {
       dto.setTaskStatus(Status.OPEN); // first set the status to open
        dto.setAssignedDate(LocalDate.now()); // set the date
        taskRepository.save(taskMapper.convertToEntity(dto)); //save it to the db

    }

    @Override
    public void update(TaskDTO dto) {
        Optional<Task> task=taskRepository.findById(dto.getId());
        Task updatedTask=taskMapper.convertToEntity(dto);

        if(task.isPresent()) { //isPresent() method of Optional Interface
            updatedTask.setTaskStatus(dto.getTaskStatus()==null?task.get().getTaskStatus():dto.getTaskStatus()); // set the project status
            //if taskDTO status is null get the status from the db else get taskDTO status
            updatedTask.setAssignedDate(task.get().getAssignedDate()); // set the assigned date -- so that we do not have these fields set to null
            taskRepository.save(updatedTask);
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Task> foundTask=taskRepository.findById(id);

        if(foundTask.isPresent()){
            foundTask.get().setIsDeleted(true); // soft delete the task
            taskRepository.save(foundTask.get()); // get() optional interface
        }

    }

    @Override
    public TaskDTO findById(Long id) {
      Optional <Task> task=taskRepository.findById(id);
      if(task.isPresent()){
          return taskMapper.convertToDto(task.get()); // if task is present convert and return dto
      }
       return null;
    }

    @Override
    public int totalNonCompletedTask(String projectCode) {
        return taskRepository.totalNonCompletedTasks(projectCode);
    }

    @Override
    public int totalCompletedTask(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO projectDTO) {
        Project project = projectMapper.convertToEntity(projectDTO);
        List<Task> tasks = taskRepository.findAllByProject(project);
        tasks.forEach(task -> delete(task.getId()));
    }

    @Override
    public void completeByProject(ProjectDTO projectDTO) {
        // mark all tasks as complete when a project is completed
        Project project=projectMapper.convertToEntity(projectDTO);
        List<Task> tasks=taskRepository.findAllByProject(project);
        tasks.stream().map(taskMapper::convertToDto).forEach(taskDTO -> {
            taskDTO.setTaskStatus(Status.COMPLETE);
            update(taskDTO);
        });


    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO loggedInUser = userService.findByUserName(username);
        List<Task> tasks = taskRepository.
                findAllByTaskStatusIsNotAndAssignedEmployee(status, userMapper.convertToEntity(loggedInUser));
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO loggedInUser = userService.findByUserName(username);
        List<Task> tasks = taskRepository.
                findAllByTaskStatusAndAssignedEmployee(status, userMapper.convertToEntity(loggedInUser));
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO assignedEmployee) {
        List<Task> tasks = taskRepository
                .findAllByTaskStatusIsNotAndAssignedEmployee(Status.COMPLETE, userMapper.convertToEntity(assignedEmployee));
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

}
