package ru.nextcloud.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/")
@Tag(name = "Демо", description = "Демонстрация работы с webApp")
public class ExampleController {
    @Operation(
            summary = "Получение списка задач",
            description = "Получение списка всех задач"
    )
    @GetMapping
    public String getUserInfo() {
        log.info("Get user info");
        return "Hello World";
    }
}
