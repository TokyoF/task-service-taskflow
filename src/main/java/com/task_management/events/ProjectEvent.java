package com.task_management.events;

public record ProjectEvent(Long id, String name, String owner, boolean active) {
  public ProjectEvent {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name is required");
    }
    if (owner == null || owner.isBlank()) {
      throw new IllegalArgumentException("Owner is required");
    }
  }
}
