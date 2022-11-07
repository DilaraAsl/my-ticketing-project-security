package com.cydeo.entity;

import com.cydeo.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="tasks")
@Where(clause="is_deleted=false")// show the task in UI if is_deleted is false
public class Task extends BaseEntity{
    @Column(columnDefinition = "DATE")
    LocalDate assignedDate;

    String taskDetail;

    @Enumerated(EnumType.STRING)
    Status taskStatus;

    String taskSubject;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="assigned_employee_id")
    User assignedEmployee;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="project_id")
    Project project;
}
