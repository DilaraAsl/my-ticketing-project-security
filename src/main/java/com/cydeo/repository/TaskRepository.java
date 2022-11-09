package com.cydeo.repository;

import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
@Query("select count(t) from Task t where t.project.projectCode=?1 and t.taskStatus<>'COMPLETE' ")
    int totalNonCompletedTasks(String projectCode);

@Query("select count(t) from Task t where t.project.projectCode=?1 and t.taskStatus='COMPLETE'")
    int totalCompletedTasks(String projectCode);

 List<Task> findAllByProject(Project project); // we can pass entities directly in a derived query
    // we cannot pass an entity directly to native or jpql query instead we can pass the projectId
}
