
package com.task_management.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.task_management.Utils.JsonUtils;
import com.task_management.events.ProjectEvent;
import com.task_management.model.ProjectReference;
import com.task_management.repository.ProjectReferenceRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectConsumer {

  private final ProjectReferenceRepository projectReferenceRepository;

  @KafkaListener(topics = "project-topic")
  public void handleOrdersNotifications(String message) {
    try {
      // Deserializar el mensaje JSON recibido
      var projectEvent = JsonUtils.fromJson(message, ProjectEvent.class);

      // Buscar el proyecto en la base de datos
      var existingProject = projectReferenceRepository.findById(projectEvent.id());

      if (existingProject.isPresent()) {
        // Si el proyecto existe, verificar si hay cambios
        var project = existingProject.get();
        boolean hasChanges = false;

        if (!project.getName().equals(projectEvent.name())) {
          project.setName(projectEvent.name());
          hasChanges = true;
        }

        if (project.isActive() != projectEvent.active()) {
          project.setActive(projectEvent.active());
          hasChanges = true;
        }

        if (hasChanges) {
          projectReferenceRepository.save(project); // Actualizar en la base de datos
          log.info("Proyecto actualizado: ID = {}, Name = '{}', Active = {}",
              project.getId(), project.getName(), project.isActive());
        } else {
          log.info("No hay cambios en el proyecto: ID = {}", project.getId());
        }
      } else {
        // Si el proyecto no existe, crearlo
        var newProject = new ProjectReference(
            projectEvent.id(),
            projectEvent.name(),
            projectEvent.active()
        );

        projectReferenceRepository.save(newProject);
        log.info("Nuevo proyecto registrado: ID = {}, Name = '{}', Active = {}",
            newProject.getId(), newProject.getName(), newProject.isActive());
      }
    } catch (Exception e) {
      log.error("Error al procesar el mensaje de Kafka: {}", e.getMessage());
    }
  }
}

