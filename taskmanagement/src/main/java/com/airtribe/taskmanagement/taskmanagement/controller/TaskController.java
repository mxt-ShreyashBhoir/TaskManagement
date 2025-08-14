package com.airtribe.taskmanagement.taskmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airtribe.taskmanagement.taskmanagement.dto.TaskRequestDto;
import com.airtribe.taskmanagement.taskmanagement.dto.TaskResponseDto;
import com.airtribe.taskmanagement.taskmanagement.enums.Priority;
import com.airtribe.taskmanagement.taskmanagement.enums.TaskStatus;
import com.airtribe.taskmanagement.taskmanagement.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping
  public ResponseEntity<TaskResponseDto> create(@Valid @RequestBody TaskRequestDto dto) {
    TaskResponseDto created = taskService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskResponseDto> get(@PathVariable Long id) {
    return ResponseEntity.ok(taskService.getById(id));
  }

  @GetMapping
  public ResponseEntity<List<TaskResponseDto>> list(@RequestParam(required = false) TaskStatus status,
    @RequestParam(required = false) Priority priority) {
    return ResponseEntity.ok(taskService.list(status, priority));
  }

  @PutMapping("/{id}")
  public ResponseEntity<TaskResponseDto> update(@PathVariable Long id, @Valid @RequestBody TaskRequestDto dto) {
    return ResponseEntity.ok(taskService.update(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    taskService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
