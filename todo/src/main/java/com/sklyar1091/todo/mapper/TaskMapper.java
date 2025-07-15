package com.sklyar1091.todo.mapper;

import com.sklyar1091.todo.dto.TaskDto;
import com.sklyar1091.todo.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    Task toEntity(TaskDto taskDto);

    TaskDto toDto(Task task);
}
