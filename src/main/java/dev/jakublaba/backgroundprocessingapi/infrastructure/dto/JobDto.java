package dev.jakublaba.backgroundprocessingapi.infrastructure.dto;

import dev.jakublaba.backgroundprocessingapi.model.entity.Job.Status;
import java.util.UUID;

public record JobDto(
    UUID uuid,
    Status status
) {

}
