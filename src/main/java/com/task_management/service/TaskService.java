
package com.task_management.service;

import com.task_management.dto.TaskRequest;
import com.task_management.dto.TaskResponse;
import com.task_management.exception.TaskNotFoundException;
import com.task_management.model.TaskEntity;
import com.task_management.repository.ProjectReferenceRepository;
import com.task_management.repository.TaskRepository;
import com.task_management.model.TaskStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  private final ProjectReferenceRepository projectReferenceRepository;

  /**
   * Crea una nueva tarea.
   *
   * @param request Datos de la tarea.
   * @return Tarea creada.
   */

  public TaskResponse createTask(TaskRequest request) {
    // Validar el proyecto
    var project = projectReferenceRepository.findById(request.getProjectId())
        .orElseThrow(
            () -> new IllegalArgumentException("El proyecto con ID " + request.getProjectId() + " no existe."));

    if (!project.isActive()) {
      throw new IllegalArgumentException("El proyecto con ID " + request.getProjectId() + " no está activo.");
    }

    // Crear la tarea si el proyecto es válido
    TaskEntity task = TaskEntity.builder()
        .title(request.getTitle())
        .description(request.getDescription())
        .projectId(request.getProjectId())
        .status(request.getStatus())
        .assignedTo(request.getAssignedTo())
        .build();

    TaskEntity savedTask = taskRepository.save(task);

    return convertToResponse(savedTask);
  }

  public void updateTasksByProject(Long projectId, String projectStatus) {
    // Validar el proyecto
    var project = projectReferenceRepository.findById(projectId)
        .orElseThrow(() -> new IllegalArgumentException("El proyecto con ID " + projectId + " no existe."));

    if (!project.isActive()) {
      throw new IllegalArgumentException("El proyecto con ID " + projectId + " no está activo.");
    }

    // Convertir el estado del proyecto (String) a TaskStatus
    TaskStatus status;
    try {
      status = TaskStatus.valueOf(projectStatus); // Convierte el String a un enum
    } catch (IllegalArgumentException e) {
      log.error("Estado del proyecto inválido: {}", projectStatus);
      throw new RuntimeException("Estado del proyecto inválido: " + projectStatus);
    }

    // Buscar todas las tareas asociadas al proyecto
    List<TaskEntity> tasks = taskRepository.findByProjectId(projectId);

    if (tasks.isEmpty()) {
      throw new TaskNotFoundException("No se encontraron tareas asociadas al proyecto con ID: " + projectId);
    }

    // Actualizar el estado de las tareas
    tasks.forEach(task -> {
      task.setStatus(status); // Asignar el estado convertido
      log.info("Actualizando tarea con ID: {} al estado: {}", task.getId(), status);
    });

    // Guardar cambios
    taskRepository.saveAll(tasks);
    log.info("Tareas asociadas al proyecto {} actualizadas exitosamente.", projectId);
  }

  /*
   * Obtiene todas las tareas.
   *
   * @return Lista de tareas.
   */
  public List<TaskResponse> getAllTasks() {
    return taskRepository.findAll().stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
  }

  /**
   * Obtiene todas las tareas de un proyecto específico.
   *
   * @param projectId ID del proyecto.
   * @return Lista de tareas asociadas al proyecto.
   */
  public List<TaskResponse> getTasksByProjectId(Long projectId) {
    return taskRepository.findByProjectId(projectId).stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
  }

  /**
   * Obtiene una tarea por ID.
   *
   * @param id ID de la tarea.
   * @return Tarea encontrada.
   * @throws TaskNotFoundException Si la tarea no existe.
   */
  public TaskResponse getTaskById(Long id) {
    TaskEntity task = taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada con ID: " + id));
    return convertToResponse(task);
  }

  /**
   * Actualiza una tarea existente.
   *
   * @param id      ID de la tarea.
   * @param request Datos a actualizar.
   * @return Tarea actualizada.
   * @throws TaskNotFoundException Si la tarea no existe.
   */

  public TaskResponse updateTask(Long id, TaskRequest request) {
    // Buscar la tarea
    TaskEntity task = taskRepository.findById(id)
        .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada con ID: " + id));

    // Validar el proyecto asociado
    var project = projectReferenceRepository.findById(task.getProjectId())
        .orElseThrow(
            () -> new IllegalArgumentException("El proyecto asociado con ID " + task.getProjectId() + " no existe."));

    if (!project.isActive()) {
      throw new IllegalArgumentException("El proyecto asociado con ID " + task.getProjectId() + " no está activo.");
    }

    // Actualizar los datos de la tarea
    task.setTitle(request.getTitle());
    task.setDescription(request.getDescription());
    task.setStatus(request.getStatus());
    task.setAssignedTo(request.getAssignedTo());

    TaskEntity updatedTask = taskRepository.save(task);

    return convertToResponse(updatedTask);
  }

  /**
   * Elimina una tarea por ID.
   *
   * @param id ID de la tarea.
   * @throws TaskNotFoundException Si la tarea no existe.
   */
  public void deleteTask(Long id) {
    if (!taskRepository.existsById(id)) {
      throw new TaskNotFoundException("Tarea no encontrada con ID: " + id);
    }
    taskRepository.deleteById(id);
  }

  /**
   * Convierte una entidad TaskEntity a un DTO TaskResponse.
   *
   * @param task Entidad de la tarea.
   * @return DTO de respuesta.
   */
  private TaskResponse convertToResponse(TaskEntity task) {
    return TaskResponse.builder()
        .id(task.getId())
        .title(task.getTitle())
        .description(task.getDescription())
        .projectId(task.getProjectId())
        .status(task.getStatus())
        .assignedTo(task.getAssignedTo())
        .build();
  }
}
