
package com.task_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "project_reference")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectReference {

  @Id
  private Long id; // ID del proyecto (Primary Key)

  @Column(nullable = false)
  private String name; // Nombre del proyecto

  @Column(nullable = false)
  private boolean active; // Estado del proyecto (Activo/Inactivo)
}
