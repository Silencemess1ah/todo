package com.sklyar1091.todo.dto;

import com.sklyar1091.todo.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    @NotNull(message = "{task.id.notnull}")
    private Long id;

    @NotBlank(message = "{task.name.notblank}")
    @Size(max = 64, message = "{task.name.size}")
    private String name;

    @NotBlank(message = "{task.description.notblank}")
    @Size(max = 256, message = "{task.description.size}")
    private String description;

    @NotNull(message = "{task.deadline.notnull}")
    private LocalDateTime deadLine;

    @NotNull(message = "{task.status.notnull}")
    private TaskStatus taskStatus;
}
