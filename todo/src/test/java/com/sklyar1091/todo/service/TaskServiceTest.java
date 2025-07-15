package com.sklyar1091.todo.service;

import com.sklyar1091.todo.dto.TaskCreationDto;
import com.sklyar1091.todo.dto.TaskDto;
import com.sklyar1091.todo.dto.filter.TaskFilterDto;
import com.sklyar1091.todo.dto.sort.TaskSortDto;
import com.sklyar1091.todo.filter.Filter;
import com.sklyar1091.todo.mapper.TaskMapper;
import com.sklyar1091.todo.model.Task;
import com.sklyar1091.todo.model.TaskStatus;
import com.sklyar1091.todo.repository.TaskRepository;
import com.sklyar1091.todo.sort.Sort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;
    @Mock
    private List<Filter<TaskFilterDto, Task>> filters;
    @Mock
    private Filter<TaskFilterDto, Task> taskFilter;
    @Mock
    private List<Sort<TaskSortDto, Task>> sorters;
    @Mock
    private Sort<TaskSortDto, Task> taskSorter;
    @InjectMocks
    private TaskService taskService;

    private Task testTask;
    private Task testTask2;
    private List<Task> tasks;
    private TaskDto testTaskDto;
    private TaskDto testTaskDto2;
    private List<TaskDto> tasksDtos;
    private TaskCreationDto testTaskCreationDto;
    private TaskFilterDto taskFilterDto;
    private TaskSortDto taskSortDto;
    private final Long ID_ONE = 1L;
    private final Long ID_TWO = 2L;
    private final Long ID_THREE = 3L;
    private final String TEST_NAME = "Test Task";
    private final String TEST_DESCRIPTION = "Test description";
    private final Long LIST_SIZE_TWO = 2L;
    private final Long LIST_SIZE_THREE = 3L;
    private static final LocalDateTime FIXED_DATE =
            LocalDateTime.of(2025, 7, 15, 14, 57);


    @BeforeEach
    void setUp() {
        testTask = Task.builder()
                .id(ID_ONE)
                .name(TEST_NAME)
                .description(TEST_DESCRIPTION)
                .deadLine(FIXED_DATE)
                .taskStatus(TaskStatus.TODO)
                .build();

        testTask2 = Task.builder()
                .id(ID_TWO)
                .name(TEST_NAME)
                .description(TEST_DESCRIPTION)
                .deadLine(FIXED_DATE)
                .taskStatus(TaskStatus.TODO)
                .build();

        testTaskDto = TaskDto.builder()
                .id(ID_ONE)
                .name(TEST_NAME)
                .description(TEST_DESCRIPTION)
                .deadLine(FIXED_DATE)
                .taskStatus(TaskStatus.TODO)
                .build();

        testTaskDto2 = TaskDto.builder()
                .id(ID_TWO)
                .name(TEST_NAME)
                .description(TEST_DESCRIPTION)
                .deadLine(FIXED_DATE)
                .taskStatus(TaskStatus.TODO)
                .build();

        testTaskCreationDto = TaskCreationDto.builder()
                .name(TEST_NAME)
                .description(TEST_DESCRIPTION)
                .deadLine(FIXED_DATE)
                .build();

        taskFilterDto = TaskFilterDto.builder()
                .taskStatus(TaskStatus.TODO)
                .build();

        taskSortDto = TaskSortDto.builder()
                .sortByStatus(true)
                .build();
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(taskRepository, taskMapper, taskFilter, taskSorter);
    }

    @Test
    @DisplayName("Creating new task with given attributes from DTO")
    public void whenValidCreationDtoGivenThenSaveAndReturn() {
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);
        when(taskMapper.toDto(any(Task.class))).thenReturn(testTaskDto);

        TaskDto result = taskService.createTask(testTaskCreationDto);

        assertEquals(testTaskDto, result);
        verify(taskRepository).save(any(Task.class));
        verify(taskMapper).toDto(any(Task.class));
    }

    @Test
    @DisplayName("When called returns all tasks mapped to dto list")
    public void whenCalledThenReturnsAllTasks() {
        tasks = new ArrayList<>(Arrays.asList(testTask, testTask2));
        tasksDtos = new ArrayList<>(Arrays.asList(testTaskDto, testTaskDto2));
        when(taskRepository.findAll()).thenReturn(tasks);
        when(taskMapper.toDto(testTask)).thenReturn(testTaskDto);

        List<TaskDto> result = taskService.getAll();

        assertEquals(tasksDtos.size(), result.size());
        verify(taskRepository).findAll();
        verify(taskMapper).toDto(testTask);
    }

    @Test
    @DisplayName("When valid taskId passed deletes task")
    public void whenCalledThenDeleteTask() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(testTask));
        taskService.deleteTask(testTask.getId());
        verify(taskRepository).deleteById(testTask.getId());
    }

    @Test
    @DisplayName("Updates task's fields")
    public void whenValidDtoPassedThenUpdatesAllTaskFieldsAndSaves() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);
        when(taskMapper.toDto(any(Task.class))).thenReturn(testTaskDto);

        TaskDto result = taskService.updateTask(testTaskDto);

        assertEquals(testTaskDto, result);
        verify(taskRepository).findById(anyLong());
        verify(taskRepository).save(any(Task.class));
        verify(taskMapper).toDto(any(Task.class));
    }


    @Nested
    class FiltersTests {

        @Test
        @DisplayName("Testing filter logic with mocks")
        public void whenValidFilterPassedThenFiltersTasksByGivenAttribute() {

            Task testTaskFilter = Task.builder()
                    .id(ID_ONE)
                    .name(TEST_NAME)
                    .description(TEST_DESCRIPTION)
                    .deadLine(FIXED_DATE)
                    .taskStatus(TaskStatus.TODO)
                    .build();

            Task testTaskFilter2 = Task.builder()
                    .id(ID_TWO)
                    .name(TEST_NAME)
                    .description(TEST_DESCRIPTION)
                    .deadLine(FIXED_DATE)
                    .taskStatus(TaskStatus.TODO)
                    .build();

            TaskDto testTaskDtoFilter = TaskDto.builder()
                    .id(ID_ONE)
                    .name(TEST_NAME)
                    .description(TEST_DESCRIPTION)
                    .deadLine(FIXED_DATE)
                    .taskStatus(TaskStatus.TODO)
                    .build();

            TaskDto testTaskDtoFilter2 = TaskDto.builder()
                    .id(ID_TWO)
                    .name(TEST_NAME)
                    .description(TEST_DESCRIPTION)
                    .deadLine(FIXED_DATE)
                    .taskStatus(TaskStatus.TODO)
                    .build();

            List<Task> list = new ArrayList<>(Arrays.asList(testTaskFilter, testTaskFilter2));

            when(taskRepository.findAll()).thenReturn(list);
            when(taskMapper.toDto(testTaskFilter)).thenReturn(testTaskDtoFilter);
            when(taskMapper.toDto(testTaskFilter2)).thenReturn(testTaskDtoFilter2);

            when(taskFilter.isApplicable(taskFilterDto)).thenReturn(true);
            when(taskFilter.applyFilter(any(Stream.class), eq(taskFilterDto)))
                    .thenReturn(Stream.of(testTaskFilter, testTaskFilter2));
            when(filters.stream()).thenReturn(Stream.of(taskFilter));

            List<TaskDto> filteredTaskDto = taskService.filterTasks(taskFilterDto);

            assertEquals(LIST_SIZE_TWO, filteredTaskDto.size());
            verify(taskRepository).findAll();
            verify(taskMapper).toDto(testTaskFilter);
            verify(taskMapper).toDto(testTaskFilter2);
        }
    }

    @Nested
    class SortedTests {

        @BeforeEach
        void setUp() {
            Mockito.reset(taskRepository, taskMapper, taskFilter, filters);
            tasks = new ArrayList<>();
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(taskRepository, taskMapper, taskFilter, filters);
            verifyNoMoreInteractions(taskRepository, taskMapper, taskFilter, filters);
        }

        @Test
        @DisplayName("Testing sorting logic with mocks")
        public void whenValidSorterPassedThenSortsTasksByGivenAttribute() {
            tasks.add(testTask);
            tasks.add(testTask2);

            Task testSortTask = Task.builder()
                    .id(ID_THREE)
                    .name(TEST_NAME)
                    .description(TEST_DESCRIPTION)
                    .deadLine(FIXED_DATE)
                    .taskStatus(TaskStatus.IN_PROGRESS)
                    .build();

            TaskDto testSortTaskDto = TaskDto.builder()
                    .id(ID_THREE)
                    .name(TEST_NAME)
                    .description(TEST_DESCRIPTION)
                    .deadLine(FIXED_DATE)
                    .taskStatus(TaskStatus.IN_PROGRESS)
                    .build();

            tasks.add(0, testSortTask);

            when(taskRepository.findAll()).thenReturn(tasks);
            when(taskMapper.toDto(testTask)).thenReturn(testTaskDto);
            when(taskMapper.toDto(testTask2)).thenReturn(testTaskDto2);
            when(taskMapper.toDto(testSortTask)).thenReturn(testSortTaskDto);

            when(taskSorter.isApplicable(taskSortDto)).thenReturn(true);
            when(taskSorter.applySorting(any(Stream.class), eq(taskSortDto)))
                    .thenReturn(Stream.of(testTask, testTask2, testSortTask));
            when(sorters.stream()).thenReturn(Stream.of(taskSorter));

            List<TaskDto> sortTasks = taskService.sortTasks(taskSortDto);

            assertEquals(LIST_SIZE_THREE, sortTasks.size());
            assertEquals(TaskStatus.IN_PROGRESS, sortTasks.get(2).getTaskStatus());
            verify(taskRepository).findAll();
            verify(taskMapper).toDto(testTask);
        }
    }
}
