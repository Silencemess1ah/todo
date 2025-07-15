package com.sklyar1091.todo.controller;

import com.sklyar1091.todo.dto.TaskCreationDto;
import com.sklyar1091.todo.dto.TaskDto;
import com.sklyar1091.todo.dto.filter.TaskFilterDto;
import com.sklyar1091.todo.dto.sort.TaskSortDto;
import com.sklyar1091.todo.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid TaskCreationDto taskCreationDto) {
        TaskDto taskDto = taskService.createTask(taskCreationDto);
        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskService.getAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable @NotNull Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<TaskDto> updateTask(@RequestBody @Valid TaskDto taskDto) {
        TaskDto updatedTask = taskService.updateTask(taskDto);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TaskDto>> filterTasks(@RequestBody @NotNull TaskFilterDto taskFilterDto) {
        List<TaskDto> filteredTasks = taskService.filterTasks(taskFilterDto);
        return new ResponseEntity<>(filteredTasks, HttpStatus.OK);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<TaskDto>> sortTasks(@RequestBody @NotNull TaskSortDto taskSortDto) {
        List<TaskDto> sortedTasks = taskService.sortTasks(taskSortDto);
        return new ResponseEntity<>(sortedTasks, HttpStatus.OK);
    }

}
