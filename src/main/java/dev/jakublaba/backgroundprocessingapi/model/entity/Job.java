package dev.jakublaba.backgroundprocessingapi.model.entity;

import java.util.UUID;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Job implements Runnable {

  private static final long EXECUTION_TIME_MINS = 5;
  private final UUID uuid;
  private Status status = Status.READY;

  public Job(UUID uuid) {
    this.uuid = uuid;
  }

  @Override
  public void run() {
    log.info("Starting job {}", uuid);
    status = Status.RUNNING;
    try {
      Thread.sleep(1_000L * 60L * EXECUTION_TIME_MINS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error(e.getMessage());
      status = Status.INTERRUPTED;
      return;
    }
    status = Status.COMPLETED;
  }

  public enum Status {
    READY,
    RUNNING,
    COMPLETED,
    INTERRUPTED
  }
}
