package com.airtribe.taskmanagement.taskmanagement;

import com.airtribe.taskmanagement.taskmanagement.controller.TaskController;
import com.airtribe.taskmanagement.taskmanagement.dto.TaskRequestDto;
import com.airtribe.taskmanagement.taskmanagement.dto.TaskResponseDto;
import com.airtribe.taskmanagement.taskmanagement.enums.Priority;
import com.airtribe.taskmanagement.taskmanagement.enums.TaskStatus;
import com.airtribe.taskmanagement.taskmanagement.exception.TaskNotFoundException;
import com.airtribe.taskmanagement.taskmanagement.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private TaskService taskService;

  @Test
  void createTask_Returns201() throws Exception {
    TaskRequestDto req = new TaskRequestDto();
    req.setTitle("Complete Spring Boot Assignment");
    req.setDescription("Build a task management API");
    req.setStatus(TaskStatus.PENDING);
    req.setPriority(Priority.HIGH);
    req.setDueDate(LocalDate.now().plusDays(1));

    TaskResponseDto res = new TaskResponseDto();
    res.setId(1L);
    res.setTitle(req.getTitle());
    res.setDescription(req.getDescription());
    res.setStatus(req.getStatus());
    res.setPriority(req.getPriority());
    res.setDueDate(req.getDueDate());

    given(taskService.create(any(TaskRequestDto.class))).willReturn(res);

    mockMvc.perform(post("/api/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(req)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value(1L))
      .andExpect(jsonPath("$.title").value("Complete Spring Boot Assignment"));
  }

  @Test
  void createTask_InvalidData_Returns400() throws Exception {
    TaskRequestDto req = new TaskRequestDto();
    req.setTitle("Hi"); // too short

    mockMvc.perform(post("/api/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(req)))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errors.title").exists());
  }

  @Test
  void getTask_NotFound_Returns404() throws Exception {
    given(taskService.getById(999L)).willThrow(new TaskNotFoundException(999L));

    mockMvc.perform(get("/api/tasks/999"))
      .andExpect(status().isNotFound());
  }
}
