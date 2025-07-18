package com.sklyar1091.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreationDto {

    @NotBlank(message = "{task.creation.name.notblank}")
    @Size(max = 64, message = "{task.creation.name.size}")
    private String name;

    @NotBlank(message = "{task.creation.description.notblank}")
    @Size(max = 256, message = "{task.creation.description.size}")
    private String description;

    @NotNull(message = "{task.creation.deadline.notnull}")
    private LocalDateTime deadLine;
}
