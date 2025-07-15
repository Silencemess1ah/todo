package com.sklyar1091.todo.service;

import com.sklyar1091.todo.dto.TaskCreationDto;
import com.sklyar1091.todo.dto.TaskDto;
import com.sklyar1091.todo.dto.filter.TaskFilterDto;
import com.sklyar1091.todo.dto.sort.TaskSortDto;
import com.sklyar1091.todo.exception.TaskNotFoundException;
import com.sklyar1091.todo.filter.Filter;
import com.sklyar1091.todo.mapper.TaskMapper;
import com.sklyar1091.todo.model.Task;
import com.sklyar1091.todo.model.TaskStatus;
import com.sklyar1091.todo.repository.TaskRepository;
import com.sklyar1091.todo.sort.Sort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final List<Filter<TaskFilterDto, Task>> taskFilters;
    private final List<Sort<TaskSortDto, Task>> taskSort;

    public TaskDto createTask(TaskCreationDto taskCreationDto) {
        Task task = Task.builder()
                .name(taskCreationDto.getName())
                .description(taskCreationDto.getDescription())
                .deadLine(taskCreationDto.getDeadLine())
                .taskStatus(TaskStatus.TODO)
                .build();
        log.info("Creating new task named {}", task.getName());
        return taskMapper.toDto(taskRepository.save(task));
    }

    public List<TaskDto> getAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .toList();
    }

    public void deleteTask(Long taskId) {
        Task task = getTaskById(taskId);
        taskRepository.deleteById(task.getId());
        log.info("Task with id {} , successfully deleted", taskId);
    }

    public TaskDto updateTask(TaskDto taskDto) {
        Task taskToBeUpdated = getTaskById(taskDto.getId());

        taskToBeUpdated.setName(taskDto.getName());
        taskToBeUpdated.setDescription(taskDto.getDescription());
        taskToBeUpdated.setDeadLine(taskDto.getDeadLine());
        taskToBeUpdated.setTaskStatus(taskDto.getTaskStatus());
        log.info("New Task name - {} , description - {}, deadline - {} , status - {}",
                taskToBeUpdated.getName(), taskToBeUpdated.getDescription(),
                taskToBeUpdated.getDeadLine(), taskToBeUpdated.getTaskStatus());

        return taskMapper.toDto(taskRepository.save(taskToBeUpdated));
    }

    private Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " not found! " +
                        "Check taskId and try again"));
    }


    public List<TaskDto> filterTasks(TaskFilterDto taskFilterDto) {
        List<Task> allTasks = taskRepository.findAll();

        return taskFilters.stream()
                .filter(filter -> filter.isApplicable(taskFilterDto))
                .flatMap(filter -> filter.applyFilter(allTasks.stream(), taskFilterDto))
                .map(taskMapper::toDto)
                .toList();
    }

    public List<TaskDto> sortTasks(TaskSortDto taskSortDto) {
        List<Task> tasks = taskRepository.findAll();

        return taskSort.stream()
                .filter(s -> s.isApplicable(taskSortDto))
                .flatMap(s -> s.applySorting(tasks.stream(), taskSortDto))
                .map(taskMapper::toDto)
                .toList();
    }
}
