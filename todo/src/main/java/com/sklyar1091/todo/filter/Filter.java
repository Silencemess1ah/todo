package com.sklyar1091.todo.filter;

import com.sklyar1091.todo.dto.filter.FilterDto;

import java.util.stream.Stream;

public interface Filter<T extends FilterDto, R> {

    boolean isApplicable(T filters);

    Stream<R> applyFilter(Stream<R> entities, T filters);
}