package com.sklyar1091.todo.sort;

import com.sklyar1091.todo.dto.sort.TaskSortDto;
import com.sklyar1091.todo.model.Task;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Stream;

@Component
public class TaskDeadlineSort implements Sort<TaskSortDto, Task> {

    @Override
    public boolean isApplicable(TaskSortDto sortDto) {
        return sortDto.isSortByDeadline();
    }

    @Override
    public Stream<Task> applySorting(Stream<Task> tasks, TaskSortDto sortDto) {
        Comparator<Task> deadlineComparator = Comparator.comparing(Task::getDeadLine);

        if (sortDto.isDescending()) {
            deadlineComparator = deadlineComparator.reversed();
        }

        return tasks.sorted(deadlineComparator);
    }
}
