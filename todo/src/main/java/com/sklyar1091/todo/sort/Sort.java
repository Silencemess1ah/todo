package com.sklyar1091.todo.sort;

import com.sklyar1091.todo.dto.sort.SortDto;

import java.util.stream.Stream;

public interface Sort<T extends SortDto, R> {

    boolean isApplicable(T sortDto);

    Stream<R> applySorting(Stream<R> tasks, T sortDto);

}
