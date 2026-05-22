package com.example.demo;


import com.example.demo.dto.PageResponse;
import com.example.demo.dto.TodoCreateRequest;
import com.example.demo.dto.TodoItemMapper;
import com.example.demo.dto.TodoResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.kafka.TodoEventProducer;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.github.pagehelper.PageHelper;

@Service
public class TodoService {


    private final TodoMapper todoMapper;
    private final TodoEventProducer todoEventProducer;

    public TodoService(TodoMapper todoMapper, TodoEventProducer todoEventProducer) {
        this.todoMapper = todoMapper;
        this.todoEventProducer = todoEventProducer;
    }

    // 缓存名为 "todosCache"，key 为一个固定字符串，表示整个列表
    /**
     * 分页查询待办列表
     * @param pageNum  页码，从1开始
     * @param pageSize 每页条数
     * @return 分页响应
     */

    //@Cacheable(value = "todosCache",key = "'allTodos'")
    public PageResponse<TodoResponse> findAll(int pageNum,int pageSize){
        System.out.println(">>>从数据库查询待办<<<");
        PageHelper.startPage(pageNum,pageSize);
        List<TodoItem> items = todoMapper.findAll();
        List<TodoResponse> dtoList = items.stream()
                .map(TodoItemMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
        PageInfo<TodoItem> pageInfo = new PageInfo<>(items);

        PageResponse<TodoResponse> response = new PageResponse<>();
        response.setTotal(pageInfo.getTotal());
        response.setPageNum(pageInfo.getPageNum());
        response.setPageSize(pageInfo.getPageSize());
        response.setPages(pageInfo.getPages());
        response.setList(dtoList);

        return response;
    }

    // 缓存名为 "todosCache"，key 为搜索关键词
    //@Cacheable(value = "todosCache",key="#keyword")
    public PageResponse<TodoResponse> searchByTitle(String keyword,int pageNum,int pageSize){
        System.out.println(">>> 从数据库搜索关键词: " + keyword + " <<<");
        PageHelper.startPage(pageNum,pageSize);
        List<TodoItem> items = todoMapper.searchTitle(keyword);
        List<TodoResponse> dtoList = items.stream()
                .map(TodoItemMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
        PageInfo<TodoItem> pageInfo = new PageInfo<>(items);

        PageResponse<TodoResponse> response = new PageResponse<>();
        response.setTotal(pageInfo.getTotal());
        response.setPageNum(pageInfo.getPageNum());
        response.setPageSize(pageInfo.getPageSize());
        response.setPages(pageInfo.getPages());
        response.setList(dtoList);

        return response;
    }



    // 新增操作，不直接缓存，但可以清除列表缓存以保证数据一致性
    @CacheEvict(value = "todosCache",key = "'allTodos'")
    public TodoResponse addTodo(TodoCreateRequest request){
        TodoItem item = new TodoItem(request.getTitle());
        todoMapper.insert(item);
        return TodoItemMapper.INSTANCE.toResponse(item);
    }

    // 缓存名为 "todosCache"，key 为传入的 id
    @Cacheable(value = "todosCache",key = "#id")
    public TodoResponse findbyId(Long id){
        System.out.println(">>>从数据库查询待办ID: \" + id + \"<<<");
        TodoItem item = todoMapper.findById(id);
        if (item==null){
            throw new ResourceNotFoundException("待办事项不存在");
        }
        return TodoItemMapper.INSTANCE.toResponse(item);
    }

    // 更新操作，清除被更新项的缓存
    @CacheEvict(value = "todosCache",key = "#id")
    public TodoResponse markDone(Long id){
        System.out.println(">>> 从数据库查询待办ID: " + id + " <<<");
        TodoItem item = todoMapper.findById(id);
        if (item == null){
            throw new ResourceNotFoundException("待办事项不存在");
        }
        item.setDone(true);
        todoMapper.update(item);
        String message = String.format("待办事项 %d '%s 已标记完成",item.getId(),item.getTitle());
        todoEventProducer.sendTodoEvent("todo-events",message);
        return TodoItemMapper.INSTANCE.toResponse(item);

    }
    //删除待办
    @CacheEvict(value = "todosCache",key = "#id")
    public void deleteById(Long id){
        TodoItem item = todoMapper.findById(id);
        if (item == null) {
            throw new ResourceNotFoundException("待办事项不存在");
        }
        todoMapper.deleteById(id);
    }


}
