package com.sklyar1091.todo.dto.sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskSortDto extends SortDto {

    private boolean sortByStatus;
    private boolean sortByDeadline;

    private boolean descending;
}
