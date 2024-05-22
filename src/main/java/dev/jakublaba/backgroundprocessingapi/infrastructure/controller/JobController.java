package dev.jakublaba.backgroundprocessingapi.infrastructure.controller;

import dev.jakublaba.backgroundprocessingapi.infrastructure.dto.JobDto;
import dev.jakublaba.backgroundprocessingapi.model.service.JobService;
import java.net.URI;
import java.util.Collection;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/job")
@RequiredArgsConstructor
public class JobController {

  private final JobService service;

  @GetMapping
  public ResponseEntity<Collection<JobDto>> getAll() {
    return ResponseEntity
        .ok()
        .body(service.getAll());
  }

  @PostMapping
  public ResponseEntity<Void> create() {
    var uuid = service.create();
    var baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
    return ResponseEntity
        .created(URI.create(String.format("%s/%s", baseUrl, uuid)))
        .build();
  }

  @GetMapping("{uuid}")
  public ResponseEntity<JobDto> get(@PathVariable("uuid") UUID uuid) {
    return ResponseEntity
        .ok()
        .body(service.get(uuid));
  }

  @PostMapping("{uuid}")
  public ResponseEntity<Void> run(@PathVariable("uuid") UUID uuid) {
    service.run(uuid);
    return ResponseEntity
        .accepted()
        .build();
  }

  @GetMapping("{uuid}/status")
  public ResponseEntity<String> getStatus(@PathVariable("uuid") UUID uuid) {
    return ResponseEntity
        .ok()
        .body(service.getStatus(uuid).toString());
  }
}
