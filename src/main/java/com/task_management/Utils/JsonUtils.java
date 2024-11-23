package com.task_management.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

  // ObjectMapper como instancia única (Singleton)
  private static final ObjectMapper objectMapper = new ObjectMapper();

  // Serializar objeto a JSON
  public static String toJson(Object object) {
    if (object == null) {
      throw new IllegalArgumentException("El objeto a serializar no puede ser nulo.");
    }
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error al serializar el objeto a JSON", e);
    }
  }

  // Deserializar JSON a objeto
  public static <T> T fromJson(String json, Class<T> clazz) {
    if (json == null || json.isEmpty()) {
      throw new IllegalArgumentException("El JSON a deserializar no puede ser nulo o vacío.");
    }
    try {
      return objectMapper.readValue(json, clazz);
    } catch (Exception e) {
      throw new RuntimeException("Error al deserializar el JSON a un objeto", e);
    }
  }
}
