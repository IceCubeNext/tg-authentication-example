package ru.nextcloud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nextcloud.model.User;
import ru.nextcloud.service.UserService;
import ru.nextcloud.utils.TelegramAuthVerifier;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/")
public class ExampleController {

    @Value("${telegram.bot-token}")
    private String botToken;

//    private UserService userService;

    @GetMapping("/")
    public String index() {
        log.error("redirect to index");
        return "index";
    }

    @PostMapping("/init")
    public String init(@RequestBody Map<String, String> body, Model model) throws Exception {
        log.error("body");
        String initData = body.get("initData");
        log.error("in init {}", initData);
        System.out.println("in init " + initData);
        // Распарсим query string вручную
        Map<String, String> data = parseInitData(initData);

        if (!TelegramAuthVerifier.isValid(data, botToken)) {
            return "error"; // создайте error.html для этого случая
        }

        ObjectMapper mapper = new ObjectMapper();
        String userJson = data.get("user");
        User user = mapper.readValue(userJson, User.class);
        model.addAttribute("user", user);

        return "userdata";
    }

    private Map<String, String> parseInitData(String initData) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = initData.split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                map.put(kv[0], URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
            }
        }
        return map;
    }
}
