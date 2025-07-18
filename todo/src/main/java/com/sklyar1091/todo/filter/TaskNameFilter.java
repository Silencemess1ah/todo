package com.sklyar1091.todo.filter;

import com.sklyar1091.todo.dto.filter.TaskFilterDto;
import com.sklyar1091.todo.model.Task;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class TaskNameFilter implements Filter<TaskFilterDto, Task> {

    @Override
    public boolean isApplicable(TaskFilterDto filterDto) {
        return filterDto != null;
    }

    @Override
    public Stream<Task> applyFilter(Stream<Task> tasks, TaskFilterDto filterDto) {
        return tasks.filter(task -> task.getName().equalsIgnoreCase(filterDto.getName()));
    }
}
