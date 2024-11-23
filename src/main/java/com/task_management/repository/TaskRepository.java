
package com.task_management.repository;

import com.task_management.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

  /**
   * Encuentra todas las tareas asociadas a un proyecto específico.
   * 
   * @param projectId ID del proyecto.
   * @return Lista de tareas asociadas al proyecto.
   */
  List<TaskEntity> findByProjectId(Long projectId);
}
