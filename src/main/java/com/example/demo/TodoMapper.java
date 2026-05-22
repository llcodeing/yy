package com.example.demo;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TodoMapper {

    @Select("SELECT * FROM todo")
    List<TodoItem> findAll();

    @Insert("INSERT INTO todo (title, done) VALUES (#{title}, #{done})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(TodoItem item);

    @Select("SELECT * FROM todo WHERE id = #{id}")
    TodoItem findById(Long id);

    @Update("UPDATE todo SET done = #{done} WHERE id = #{id}")
    void update(TodoItem item);

    @Select("select * from  todo where title like concat('%',#{keyword},'%')")
    List<TodoItem> searchTitle(@Param("keyword") String keyword);

    @Delete("DELETE FROM todo WHERE id = #{id}" )
    void deleteById(Long id);

}