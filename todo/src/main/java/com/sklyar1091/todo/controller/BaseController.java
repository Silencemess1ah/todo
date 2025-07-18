package com.sklyar1091.todo.controller;

import com.sklyar1091.todo.dto.TaskCreationDto;
import com.sklyar1091.todo.dto.TaskDto;
import com.sklyar1091.todo.dto.filter.TaskFilterDto;
import com.sklyar1091.todo.dto.sort.TaskSortDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Validated
public interface BaseController {

    @PostMapping(path = "${api.path.base}")
    ResponseEntity<TaskDto> createTask(@RequestBody @Valid TaskCreationDto taskCreationDto);

    @GetMapping(path = "${api.path.base}")
    ResponseEntity<List<TaskDto>> getAllTasks();

    @DeleteMapping(path = "${api.path.delete}")
    ResponseEntity<Void> deleteTask(@PathVariable @NotNull Long taskId);

    @PutMapping(path = "${api.path.base}")
    ResponseEntity<TaskDto> updateTask(@RequestBody @Valid TaskDto taskDto);

    @GetMapping(path = "${api.path.filter}")
    ResponseEntity<List<TaskDto>> filterTasks(@RequestBody @NotNull TaskFilterDto taskFilterDto);

    @GetMapping(path = "${api.path.sort}")
    ResponseEntity<List<TaskDto>> sortTasks(@RequestBody @NotNull TaskSortDto taskSortDto);

}
