package dev.jakublaba.backgroundprocessingapi.infrastructure.mapper;

import dev.jakublaba.backgroundprocessingapi.infrastructure.dto.JobDto;
import dev.jakublaba.backgroundprocessingapi.model.entity.Job;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper {

  Job toEntity(JobDto dto);

  JobDto toDto(Job entity);
}
