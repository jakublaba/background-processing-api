package dev.jakublaba.backgroundprocessingapi.infrastructure.repository;

import com.github.benmanes.caffeine.cache.Cache;
import dev.jakublaba.backgroundprocessingapi.model.entity.Job;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JobRepository {

  private final Cache<UUID, Job> cache;

  public void save(Job job) {
    cache.put(job.getUuid(), job);
  }

  public Optional<Job> get(UUID uuid) {
    return Optional.ofNullable(cache.getIfPresent(uuid));
  }

  public Collection<Job> getAll() {
    return cache.asMap().values();
  }
}
