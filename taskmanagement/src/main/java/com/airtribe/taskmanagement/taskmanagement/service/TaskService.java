package com.airtribe.taskmanagement.taskmanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airtribe.taskmanagement.taskmanagement.dto.TaskRequestDto;
import com.airtribe.taskmanagement.taskmanagement.dto.TaskResponseDto;
import com.airtribe.taskmanagement.taskmanagement.entity.Task;
import com.airtribe.taskmanagement.taskmanagement.enums.Priority;
import com.airtribe.taskmanagement.taskmanagement.enums.TaskStatus;
import com.airtribe.taskmanagement.taskmanagement.exception.TaskNotFoundException;
import com.airtribe.taskmanagement.taskmanagement.repository.TaskRepository;

@Service
@Transactional
public class TaskService {

  private final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public TaskResponseDto create(TaskRequestDto dto) {
    Task task = new Task();
    apply(dto, task);
    Task saved = taskRepository.save(task);
    return toResponse(saved);
  }

  public TaskResponseDto getById(Long id) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    return toResponse(task);
  }

  public List<TaskResponseDto> list(TaskStatus status, Priority priority) {
    List<Task> tasks;
    if (status != null) {
      tasks = taskRepository.findByStatus(status);
    } else if (priority != null) {
      tasks = taskRepository.findByPriority(priority);
    } else {
      tasks = taskRepository.findAll();
    }
    return tasks.stream().map(this::toResponse).collect(Collectors.toList());
  }

  public TaskResponseDto update(Long id, TaskRequestDto dto) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    apply(dto, task);
    Task saved = taskRepository.save(task);
    return toResponse(saved);
  }

  public void delete(Long id) {
    if (!taskRepository.existsById(id)) {
      throw new TaskNotFoundException(id);
    }
    taskRepository.deleteById(id);
  }

  private void apply(TaskRequestDto dto, Task task) {
    task.setTitle(dto.getTitle());
    task.setDescription(dto.getDescription());
    task.setStatus(dto.getStatus());
    task.setPriority(dto.getPriority());
    task.setDueDate(dto.getDueDate());
  }

  private TaskResponseDto toResponse(Task task) {
    TaskResponseDto res = new TaskResponseDto();
    res.setId(task.getId());
    res.setTitle(task.getTitle());
    res.setDescription(task.getDescription());
    res.setStatus(task.getStatus());
    res.setPriority(task.getPriority());
    res.setDueDate(task.getDueDate());
    res.setCreatedAt(task.getCreatedAt());
    res.setUpdatedAt(task.getUpdatedAt());
    return res;
  }
}
