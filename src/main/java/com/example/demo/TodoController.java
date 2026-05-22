package com.example.demo;

import com.example.demo.dto.PageResponse;
import com.example.demo.dto.TodoCreateRequest;
import com.example.demo.dto.TodoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/todos")
@Tag(name= "待办事项管理")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    @Operation(summary = "列出所有有待办事项")
    public Result<PageResponse<TodoResponse>> list(
            @RequestParam(name = "page",defaultValue = "1") int pageNum,
            @RequestParam(name = "size",defaultValue = "10") int pageSize) {
        PageResponse<TodoResponse> pageData = todoService.findAll(pageNum, pageSize);
        return Result.success(pageData);

    }

    @GetMapping("/search")
    @Operation(summary = "查询待办事项")
    public Result<PageResponse<TodoResponse>> search(
            @RequestParam String keyword,
            @RequestParam(name = "page",defaultValue = "1") int pageNum,
            @RequestParam(name = "size",defaultValue = "10") int pageSize){
        PageResponse<TodoResponse> pageDate = todoService.searchByTitle(keyword,pageNum,pageSize);
        return Result.success(pageDate );
    }

    @GetMapping("/{id}")
    @Operation(summary = "按照id查询待办事项")
    public Result<TodoResponse> findById(@PathVariable Long id){
        return Result.success(todoService.findbyId(id));
    }


    @PostMapping
    @Operation(summary = "添加待办事项")
    @ResponseStatus(HttpStatus.CREATED)
    public Result<TodoResponse> add(@Valid @RequestBody TodoCreateRequest request)  {
        TodoResponse response = todoService.addTodo(request);
        return Result.success(response);
    }

    @PutMapping("/{id}/done")
    @Operation(summary = "更新待办事项状态")
    public Result<TodoResponse> markDone(@PathVariable Long id) {
        TodoResponse response = todoService.markDone(id);
        return Result.success(response);
    }



    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('ADMIN')")   // 只有 ROLE_ADMIN 才能删除
    @ResponseStatus(HttpStatus.NO_CONTENT)  //删除成功无返回体，或返回空 Result
    public Result<Void> deleteTodo(@PathVariable Long id){
       todoService.deleteById(id);
       return Result.success(null);

    }


    @GetMapping("/whoami")
    public Result<String> whoami() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return Result.fail("未认证");
        }
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
        return Result.success("用户: " + auth.getName() + "，权限: " + authorities);
    }


    }



