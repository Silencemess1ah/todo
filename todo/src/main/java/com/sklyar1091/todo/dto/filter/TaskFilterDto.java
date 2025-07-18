package com.sklyar1091.todo.dto.filter;

import com.sklyar1091.todo.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskFilterDto extends FilterDto {

    private String name;
    private LocalDateTime deadline;
    private TaskStatus taskStatus;
}
