package com.example.springproject.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
 * This class define a general response and paging content
 * @author [nguyenanhtu123]
 * @version [1.0.0]
 * @since 1/6/2023
*/
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class PageResponse<T> {
  private List<T> content;

  /**
   *  This property is the actual number of records has been found
   */
  private int amount;

  /**
   *
   * @param data List of generic type
   * @param amount actual number of records
   * @return a PageResponse with generic type
   * @param <T> generic type
   */

  public static <T> PageResponse<T> of(List<T> data, Integer amount) {
    return new PageResponse<>(data, Objects.isNull(amount) ? 0 : amount.intValue());
  }
}