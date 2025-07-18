package com.sklyar1091.todo.mapper;

import com.sklyar1091.todo.dto.TaskDto;
import com.sklyar1091.todo.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "deadLine", source = "deadLine")
    @Mapping(target = "taskStatus", source = "taskStatus")
    Task toEntity(TaskDto taskDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "deadLine", source = "deadLine")
    @Mapping(target = "taskStatus", source = "taskStatus")
    TaskDto toDto(Task task);
}
