package dev.jakublaba.backgroundprocessingapi.infrastructure.exception;

import dev.jakublaba.backgroundprocessingapi.model.entity.Job.Status;

public class InvalidJobStateException extends RuntimeException {

  private InvalidJobStateException(String msg) {
    super(msg);
  }

  public static InvalidJobStateException cannotStart(Status state) {
    var msg = String.format(
        "Cannot start job because it's not in %s state, actual state was %s",
        Status.READY.toString(), state.toString()
    );
    return new InvalidJobStateException(msg);
  }
}
