package com.example.demo;

import com.example.demo.dto.PageResponse;
import com.example.demo.dto.TodoCreateRequest;
import com.example.demo.dto.TodoResponse;
import com.example.kafka.TodoEventProducer;
import com.jayway.jsonpath.JsonPath;
import io.netty.util.internal.ThreadLocalRandom;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.websocket.servlet.TomcatWebSocketServletWebServerCustomizer;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TodoServiceTest {

    private static final Logger log = LoggerFactory.getLogger(TodoServiceTest.class);
    @Autowired
    private TodoService todoService;


    @Test
    public void testSearchByTitle(){

        String fourDigit = String.format("%04d", ThreadLocalRandom.current().nextInt(10000));
        String keyWord = fourDigit+"苹果";
        TodoCreateRequest request = new TodoCreateRequest();
        request.setTitle(keyWord);
        todoService.addTodo(request);
        PageResponse<TodoResponse> result = todoService.searchByTitle(fourDigit,1,5);
        System.out.print("单元测试输出-------");


    }


}
