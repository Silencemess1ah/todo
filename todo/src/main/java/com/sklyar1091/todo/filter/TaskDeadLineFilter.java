package com.sklyar1091.todo.filter;

import com.sklyar1091.todo.dto.filter.TaskFilterDto;
import com.sklyar1091.todo.model.Task;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class TaskDeadLineFilter implements Filter<TaskFilterDto, Task> {

    @Override
    public boolean isApplicable(TaskFilterDto filterDto) {
        return filterDto.getDeadline() != null;
    }

    @Override
    public Stream<Task> applyFilter(Stream<Task> tasks, TaskFilterDto taskFilterDto) {
        return tasks.filter(task -> task.getDeadLine().isBefore(taskFilterDto.getDeadline()));
    }
}
