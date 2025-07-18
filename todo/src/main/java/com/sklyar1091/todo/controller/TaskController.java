package com.sklyar1091.todo.controller;

import com.sklyar1091.todo.dto.TaskCreationDto;
import com.sklyar1091.todo.dto.TaskDto;
import com.sklyar1091.todo.dto.filter.TaskFilterDto;
import com.sklyar1091.todo.dto.sort.TaskSortDto;
import com.sklyar1091.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController implements BaseController {

    private final TaskService taskService;

    @Override
    public ResponseEntity<TaskDto> createTask(TaskCreationDto taskCreationDto) {
        log.info("Creating new task with data: {}", taskCreationDto);
        TaskDto taskDto = taskService.createTask(taskCreationDto);
        log.info("Task created successfully: {}", taskDto);
        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        log.info("Getting all tasks");
        List<TaskDto> tasks = taskService.getAll();
        log.info("Retrieved {} tasks", tasks.size());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long taskId) {
        log.info("Deleting task with ID: {}", taskId);
        taskService.deleteTask(taskId);
        log.info("Task with ID {} deleted successfully", taskId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskDto> updateTask(TaskDto taskDto) {
        log.info("Updating task with ID: {}", taskDto.getId());
        TaskDto updatedTask = taskService.updateTask(taskDto);
        log.info("Task updated successfully: {}", updatedTask);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TaskDto>> filterTasks(TaskFilterDto taskFilterDto) {
        log.info("Filtering tasks with filter: {}", taskFilterDto);
        List<TaskDto> filteredTasks = taskService.filterTasks(taskFilterDto);
        log.info("Filtered {} tasks", filteredTasks.size());
        return new ResponseEntity<>(filteredTasks, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TaskDto>> sortTasks(TaskSortDto taskSortDto) {
        log.info("Sorting tasks with sort criteria: {}", taskSortDto);
        List<TaskDto> sortedTasks = taskService.sortTasks(taskSortDto);
        log.info("Sorted {} tasks", sortedTasks.size());
        return new ResponseEntity<>(sortedTasks, HttpStatus.OK);
    }

}
