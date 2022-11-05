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
@Table(name="projects")
@Where(clause="is_deleted=false")
public class Project extends BaseEntity{

    @Column(nullable = false,updatable = false)
    private String projectCode; // projectCode is unique to each project -should not be updated

    private String projectName;

    @Column(columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(columnDefinition = "DATE")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Status projectStatus;

    private String projectDetail;

    @ManyToOne(fetch=FetchType.LAZY) // many projects can be assigned to one User
    @JoinColumn(name="manager_id")
    private User assignedManager;
}
