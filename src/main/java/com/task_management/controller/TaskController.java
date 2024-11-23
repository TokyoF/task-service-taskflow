
package com.task_management.controller;

import com.task_management.dto.TaskRequest;
import com.task_management.dto.TaskResponse;
import com.task_management.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  /**
   * Crea una nueva tarea.
   * 
   * @param request Datos de la tarea.
   * @return Tarea creada.
   */
  @PostMapping
  public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request) {
    TaskResponse task = taskService.createTask(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(task);
  }

  /**
   * Obtiene todas las tareas.
   * 
   * @return Lista de tareas.
   */
  @GetMapping
  public ResponseEntity<List<TaskResponse>> getAllTasks() {
    List<TaskResponse> tasks = taskService.getAllTasks();
    return ResponseEntity.ok(tasks);
  }

  /**
   * Obtiene las tareas de un proyecto específico.
   * 
   * @param projectId ID del proyecto.
   * @return Lista de tareas asociadas al proyecto.
   */
  @GetMapping("/project/{projectId}")
  public ResponseEntity<List<TaskResponse>> getTasksByProjectId(@PathVariable Long projectId) {
    List<TaskResponse> tasks = taskService.getTasksByProjectId(projectId);
    return ResponseEntity.ok(tasks);
  }

  /**
   * Obtiene una tarea por ID.
   * 
   * @param id ID de la tarea.
   * @return Tarea encontrada.
   */
  @GetMapping("/{id}")
  public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
    TaskResponse task = taskService.getTaskById(id);
    return ResponseEntity.ok(task);
  }

  /**
   * Actualiza una tarea existente.
   * 
   * @param id      ID de la tarea.
   * @param request Datos a actualizar.
   * @return Tarea actualizada.
   */
  @PutMapping("/{id}")
  public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody TaskRequest request) {
    TaskResponse updatedTask = taskService.updateTask(id, request);
    return ResponseEntity.ok(updatedTask);
  }

  /**
   * Elimina una tarea por ID.
   * 
   * @param id ID de la tarea.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();
  }
}
