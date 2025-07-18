package com.sklyar1091.todo.sort;

import com.sklyar1091.todo.dto.sort.TaskSortDto;
import com.sklyar1091.todo.model.Task;
import com.sklyar1091.todo.model.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Stream;

@Component
public class TaskStatusSort implements Sort<TaskSortDto, Task> {

    @Override
    public boolean isApplicable(TaskSortDto sortDto) {
        return sortDto.isSortByStatus();
    }

    @Override
    public Stream<Task> applySorting(Stream<Task> tasks, TaskSortDto sortDto) {
        Comparator<Task> comparator = (t1, t2) -> {
            TaskStatus status1 = t1.getTaskStatus();
            TaskStatus status2 = t2.getTaskStatus();
            return Integer.compare(status1.ordinal(), status2.ordinal());
        };

        if (sortDto.isDescending()) {
            comparator = comparator.reversed();
        }

        return tasks.sorted(comparator);
    }
}
