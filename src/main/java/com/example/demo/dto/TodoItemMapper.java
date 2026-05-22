package com.example.demo.dto;

import com.example.demo.TodoItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TodoItemMapper {
    TodoItemMapper INSTANCE = Mappers.getMapper(TodoItemMapper.class);
    TodoResponse toResponse(TodoItem item);
}
