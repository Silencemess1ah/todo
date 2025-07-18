package com.sklyar1091.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sklyar1091.todo.dto.TaskCreationDto;
import com.sklyar1091.todo.dto.TaskDto;
import com.sklyar1091.todo.dto.filter.TaskFilterDto;
import com.sklyar1091.todo.dto.sort.TaskSortDto;
import com.sklyar1091.todo.model.TaskStatus;
import com.sklyar1091.todo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    private TaskDto testTaskDto;
    private TaskCreationDto testCreationDto;
    private TaskFilterDto testFilterDto;
    private TaskSortDto testSortDto;
    private final Long ID_ONE = 1L;
    private final String TEST_NAME = "Test Task";
    private final String TEST_DESCRIPTION = "Test Description";

    @BeforeEach
    void setUp() {
        testTaskDto = TaskDto.builder()
                .id(ID_ONE)
                .name(TEST_NAME)
                .description(TEST_DESCRIPTION)
                .deadLine(LocalDateTime.now())
                .taskStatus(TaskStatus.IN_PROGRESS)
                .build();

        testCreationDto = TaskCreationDto.builder()
                .name(TEST_NAME)
                .description(TEST_DESCRIPTION)
                .deadLine(LocalDateTime.now())
                .build();

        testFilterDto = TaskFilterDto.builder()
                .taskStatus(TaskStatus.IN_PROGRESS)
                .build();

        testSortDto = TaskSortDto.builder()
                .sortByStatus(true)
                .build();
    }

    @Test
    @DisplayName("Test create task")
    void whenCalledThenReturnsSavedEntity() throws Exception {
        when(taskService.createTask(testCreationDto)).thenReturn(testTaskDto);

        mockMvc.perform(post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCreationDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test get all tasks")
    void whenCalledThenReturnsAllTasks() throws Exception {
        List<TaskDto> tasks = Collections.singletonList(testTaskDto);
        when(taskService.getAll()).thenReturn(tasks);

        mockMvc.perform(get("/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Task"));
    }

    @Test
    @DisplayName("Test delete task")
    void whenCalledThenDeletesTaskById() throws Exception {
        mockMvc.perform(delete("/v1/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Test update task")
    void whenCalledThenUpdatesTaskWithGivenAttributes() throws Exception {
        when(taskService.updateTask(testTaskDto)).thenReturn(testTaskDto);

        mockMvc.perform(put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTaskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Task"));
    }

    @Test
    @DisplayName("Test filter tasks")
    void whenCalledThenReturnsFilteredTasksList() throws Exception {
        List<TaskDto> filteredTasks = List.of(testTaskDto);
        when(taskService.filterTasks(testFilterDto)).thenReturn(filteredTasks);

        mockMvc.perform(get("/v1/tasks/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFilterDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test sort tasks")
    void whenCalledThenReturnsOrderedTasksList() throws Exception {
        List<TaskDto> sortedTasks = Collections.singletonList(testTaskDto);
        when(taskService.sortTasks(testSortDto)).thenReturn(sortedTasks);

        mockMvc.perform(get("/v1/tasks/sort")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testSortDto)))
                .andExpect(status().isOk());
    }
}
