package dev.jakublaba.backgroundprocessingapi.infrastructure.advice;

import dev.jakublaba.backgroundprocessingapi.infrastructure.controller.JobController;
import dev.jakublaba.backgroundprocessingapi.infrastructure.dto.ApiExceptionDto;
import dev.jakublaba.backgroundprocessingapi.infrastructure.exception.InvalidJobStateException;
import dev.jakublaba.backgroundprocessingapi.infrastructure.exception.JobNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = JobController.class)
public class JobControllerAdvice {

  @ExceptionHandler(JobNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiExceptionDto handleNotFound(JobNotFoundException e) {
    return ApiExceptionDto.of(e);
  }

  @ExceptionHandler(InvalidJobStateException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ApiExceptionDto handleConflict(InvalidJobStateException e) {
    return ApiExceptionDto.of(e);
  }
}
