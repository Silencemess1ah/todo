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

    @NotNull
    private Long id;
    @NotBlank
    @Size(max = 64)
    private String name;
    @NotBlank
    @Size(max = 256)
    private String description;
    @NotNull
    private LocalDateTime deadLine;
    @NotNull
    private TaskStatus taskStatus;
}
