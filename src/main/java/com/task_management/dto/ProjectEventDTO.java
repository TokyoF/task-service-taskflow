
package com.task_management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEventDTO {
    private Long id;               // ID del proyecto
    private String name;           // Nombre del proyecto
    private String description;    // Descripción del proyecto
    private String owner;          // Propietario o Scrum Master del proyecto
    private boolean active;        // Estado del proyecto (activo o inactivo)
}

