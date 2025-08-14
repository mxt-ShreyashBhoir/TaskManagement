package com.airtribe.taskmanagement.taskmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airtribe.taskmanagement.taskmanagement.entity.Task;
import com.airtribe.taskmanagement.taskmanagement.enums.Priority;
import com.airtribe.taskmanagement.taskmanagement.enums.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findByStatus(TaskStatus status);
  List<Task> findByPriority(Priority priority);
}
