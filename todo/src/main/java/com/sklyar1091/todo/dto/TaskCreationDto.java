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

    @NotBlank
    @Size(max = 64)
    private String name;
    @NotBlank
    @Size(max = 256)
    private String description;
    @NotNull
    private LocalDateTime deadLine;
}
