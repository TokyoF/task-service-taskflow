
package com.task_management.dto;

import com.task_management.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {

  @NotBlank(message = "El título no puede estar vacío")
  private String title;

  private String description;

  @NotNull(message = "El ID del proyecto es obligatorio")
  private Long projectId;

  @NotNull(message = "El estado de la tarea es obligatorio")
  private TaskStatus status;

  @NotBlank(message = "El usuario asignado no puede estar vacío")
  private String assignedTo;
}
