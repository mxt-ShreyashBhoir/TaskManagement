package com.airtribe.taskmanagement.taskmanagement;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.airtribe.taskmanagement.taskmanagement.entity.Task;
import com.airtribe.taskmanagement.taskmanagement.enums.Priority;
import com.airtribe.taskmanagement.taskmanagement.enums.TaskStatus;
import com.airtribe.taskmanagement.taskmanagement.repository.TaskRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

  @Autowired
  private TaskRepository taskRepository;

  @Test
  void saveAndFindByStatus() {
    Task t = new Task();
    t.setTitle("Repo test");
    t.setDescription("Persist and query");
    t.setStatus(TaskStatus.PENDING);
    t.setPriority(Priority.MEDIUM);
    t.setDueDate(LocalDate.now().plusDays(1));

    taskRepository.save(t);

    List<Task> pending = taskRepository.findByStatus(TaskStatus.PENDING);
    assertThat(pending).isNotEmpty();
    assertThat(pending.get(0).getTitle()).isEqualTo("Repo test");
  }
}
