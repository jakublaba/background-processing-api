package dev.jakublaba.backgroundprocessingapi.model.service;

import dev.jakublaba.backgroundprocessingapi.infrastructure.dto.JobDto;
import dev.jakublaba.backgroundprocessingapi.infrastructure.exception.InvalidJobStateException;
import dev.jakublaba.backgroundprocessingapi.infrastructure.exception.JobNotFoundException;
import dev.jakublaba.backgroundprocessingapi.infrastructure.mapper.JobMapper;
import dev.jakublaba.backgroundprocessingapi.infrastructure.repository.JobRepository;
import dev.jakublaba.backgroundprocessingapi.model.entity.Job;
import dev.jakublaba.backgroundprocessingapi.model.entity.Job.Status;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService {

  private final JobRepository repository;
  private final JobMapper mapper;
  private final ExecutorService executor;

  public UUID create() {
    var job = new Job(UUID.randomUUID());
    repository.save(job);
    return job.getUuid();
  }

  public void run(UUID uuid) {
    var job = repository.get(uuid)
        .orElseThrow(() -> new JobNotFoundException(uuid));
    if (job.getStatus().equals(Status.READY)) {
      executor.submit(job);
    } else {
      throw InvalidJobStateException.cannotStart(job.getStatus());
    }
  }

  public JobDto get(UUID uuid) {
    var job = repository.get(uuid)
        .orElseThrow(() -> new JobNotFoundException(uuid));
    return mapper.toDto(job);
  }

  public Status getStatus(UUID uuid) {
    return get(uuid).status();
  }

  public Collection<JobDto> getAll() {
    return repository.getAll()
        .stream()
        .map(mapper::toDto)
        .toList();
  }
}
