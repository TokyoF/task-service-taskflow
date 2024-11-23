package com.task_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task_management.model.ProjectReference;

public interface ProjectReferenceRepository extends JpaRepository<ProjectReference, Long> {
}
