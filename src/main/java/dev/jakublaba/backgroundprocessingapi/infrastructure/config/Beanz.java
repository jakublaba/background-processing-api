package dev.jakublaba.backgroundprocessingapi.infrastructure.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import dev.jakublaba.backgroundprocessingapi.model.entity.Job;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beanz {

  @Bean
  public ExecutorService executor() {
    return Executors.newCachedThreadPool();
  }

  @Bean
  public Cache<UUID, Job> jobCache() {
    return Caffeine.newBuilder()
        .expireAfter(new Expiry<UUID, Job>() {
          @Override
          public long expireAfterCreate(UUID uuid, Job job, long time) {
            return switch (job.getStatus()) {
              case COMPLETED -> TimeUnit.DAYS.toNanos(1);
              case INTERRUPTED -> TimeUnit.DAYS.toNanos(7);
              default -> Long.MAX_VALUE;
            };
          }

          @Override
          public long expireAfterUpdate(UUID uuid, Job job, long time,
              @NonNegative long duration) {
            return expireAfterCreate(uuid, job, time);
          }

          @Override
          public long expireAfterRead(UUID uuid, Job job, long time, @NonNegative long duration) {
            return duration;
          }
        })
        .build();
  }
}
