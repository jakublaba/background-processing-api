package dev.jakublaba.backgroundprocessingapi.infrastructure.dto;

import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiExceptionDto {

  String message;
  ZonedDateTime timestamp;

  public static <E extends RuntimeException> ApiExceptionDto of(E e) {
    return new ApiExceptionDto(e.getMessage(), ZonedDateTime.now());
  }
}
