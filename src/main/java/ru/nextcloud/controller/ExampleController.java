package ru.nextcloud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nextcloud.service.UserService;

import java.util.Map;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/")
public class ExampleController {
    private UserService userService;

    @GetMapping("/")
    public String index() {
        log.info("redirect to index");
        return "index";
    }

    @PostMapping("/init")
    public String getUser(@RequestBody Map<String, String> initData, Model model) throws JsonProcessingException {
        log.info("Get user with initData: {}", initData);
        model.addAttribute("user", userService.addUser(initData));
        return "userdata";
    }
}
