
package com.task_management.dto;

import com.task_management.model.TaskStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
  private Long id;
  private String title;
  private String description;
  private Long projectId;
  private TaskStatus status;
  private String assignedTo;
}
