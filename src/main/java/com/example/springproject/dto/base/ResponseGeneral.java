package com.example.springproject.dto.base;

import com.example.springproject.utils.DateUtils;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * This is a base response class
 * @param <T> generic type you want to return
 * @author [nguyenanhtu123]
 * @version [1.0.0]
 * @since 1/6/2023
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResponseGeneral<T> {
  private int status;
  private String message;
  private T data;
  private String timestamp;

  /**
   * This is a static initial method
   * @param status response status
   * @param message response message
   * @param data the generic data type in response
   * @return a ResponseGeneral with generic type input
   * @param <T> generic type
   */
  public static <T> ResponseGeneral<T> of(int status, String message, T data) {
    return of(status, message, data, DateUtils.getCurrentDateString());
  }

  /**
   * This function is used when a resource is successfully initialized
   * @param message response message
   * @param data the generic data type in response
   * @return a ResponseGeneral with generic type input
   * @param <T> generic type
   */
  public static <T> ResponseGeneral<T> ofCreated(String message, T data) {
    return of(HttpStatus.CREATED.value(), message, data, DateUtils.getCurrentDateString());
  }

  /**
   * This function is used when a resource is successfully updated or removed
   * @param message response message
   * @return a ResponseGeneral with generic type input
   * @param <T> generic type
   */
  public static <T> ResponseGeneral<T> ofSuccess(String message) {
    return of(HttpStatus.OK.value(), message, null, DateUtils.getCurrentDateString());
  }

  /**
   * This function is used when the client get a resources successfully
   * @param message response message
   * @param data the generic data type in response
   * @return a ResponseGeneral with generic type input
   * @param <T> generic type
   */
  public static <T> ResponseGeneral<T> ofSuccess(String message, T data) {
    return of(HttpStatus.OK.value(), message, data, DateUtils.getCurrentDateString());
  }


}