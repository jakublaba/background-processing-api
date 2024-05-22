package dev.jakublaba.backgroundprocessingapi.infrastructure.exception;

import java.util.UUID;

public class JobNotFoundException extends RuntimeException {

  public JobNotFoundException(UUID uuid) {
    super(String.format("Could not find job with UUID: %s", uuid));
  }
}
